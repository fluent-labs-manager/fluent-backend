#!/usr/bin/env python3
from __future__ import annotations

"""Build test and Docker matrices from changed Gradle modules."""

import json
import os
from pathlib import Path
import re
import subprocess


ROOT = Path(__file__).resolve().parents[2]
ZERO_SHA = "0" * 40
PROJECT_DEPENDENCY = re.compile(r'project\(\s*"(:[A-Za-z0-9_-]+)"\s*\)')


def git(*args: str) -> str:
    return subprocess.check_output(["git", *args], cwd=ROOT, text=True).strip()


def modules_and_dependencies() -> tuple[list[str], dict[str, set[str]]]:
    settings = (ROOT / "settings.gradle.kts").read_text()
    modules = [
        name
        for arguments in re.findall(r"include\((.*?)\)", settings, re.DOTALL)
        for name in re.findall(r'":([A-Za-z0-9_-]+)"', arguments)
    ]
    if not modules or len(modules) != len(set(modules)):
        raise ValueError("Expected unique Gradle modules in settings.gradle.kts")

    known_modules = set(modules)
    dependencies = {}
    for module in modules:
        build_file = ROOT / module / "build.gradle.kts"
        if not build_file.is_file():
            raise ValueError(f"Missing build file for module {module}")
        declared = {
            name.removeprefix(":")
            for name in PROJECT_DEPENDENCY.findall(build_file.read_text())
        }
        unknown = declared - known_modules
        if unknown:
            raise ValueError(f"Unknown project dependencies in {module}: {sorted(unknown)}")
        dependencies[module] = declared
    return modules, dependencies


def changed_files(base: str, head: str, event_name: str) -> list[str] | None:
    if not base or base == ZERO_SHA or not head:
        return None  # Initial push: check every module.

    if event_name == "pull_request":
        base = git("merge-base", base, head)

    output = subprocess.check_output(
        ["git", "diff", "--no-renames", "--name-only", "-z", base, head], cwd=ROOT
    )
    return [path.decode() for path in output.split(b"\0") if path]


def affected_matrices(
    modules: list[str], dependencies: dict[str, set[str]], paths: list[str] | None
) -> dict[str, str]:
    shared_paths = {
        "build.gradle.kts",
        "settings.gradle.kts",
        "gradle.properties",
        "gradlew",
        "gradlew.bat",
        ".dockerignore",
        ".github/workflows/ci.yml",
    }
    shared_prefixes = (
        "buildSrc/",
        "gradle/",
        ".github/actions/",
        ".github/scripts/",
    )
    all_modules = paths is None or any(
        path in shared_paths or path.startswith(shared_prefixes) for path in paths
    )

    affected = (
        set(modules)
        if all_modules
        else {
            module
            for module in modules
            if any(path.startswith(f"{module}/") for path in paths)
        }
    )
    while True:
        dependents = {module for module in modules if dependencies[module] & affected}
        expanded = affected | dependents
        if expanded == affected:
            break
        affected = expanded

    test_modules = [module for module in modules if module in affected]
    image_modules = [
        module for module in test_modules if (ROOT / module / "Dockerfile").is_file()
    ]
    return {
        "test_matrix": json.dumps(
            {"include": [{"module": module} for module in test_modules]},
            separators=(",", ":"),
        ),
        "image_matrix": json.dumps(
            {"include": [{"module": module} for module in image_modules]},
            separators=(",", ":"),
        ),
        "test_tasks": " ".join(f":{module}:testClasses" for module in test_modules),
        "has_tests": str(bool(test_modules)).lower(),
        "has_images": str(bool(image_modules)).lower(),
    }


def main() -> None:
    modules, dependencies = modules_and_dependencies()
    paths = changed_files(
        os.environ.get("BASE_SHA", ""),
        os.environ.get("HEAD_SHA", ""),
        os.environ.get("EVENT_NAME", ""),
    )
    outputs = affected_matrices(modules, dependencies, paths)
    print(f"Changed files: {paths if paths is not None else 'initial push'}")
    print(f"Test matrix: {outputs['test_matrix']}")
    print(f"Docker matrix: {outputs['image_matrix']}")
    with Path(os.environ["GITHUB_OUTPUT"]).open("a") as output_file:
        for name, value in outputs.items():
            print(f"{name}={value}", file=output_file)


if __name__ == "__main__":
    main()
