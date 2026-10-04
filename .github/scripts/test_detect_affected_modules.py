import importlib.util
import json
from pathlib import Path
import unittest
from unittest.mock import patch


SCRIPT = Path(__file__).with_name("detect-affected-modules.py")
SPEC = importlib.util.spec_from_file_location("detect_affected_modules", SCRIPT)
detector = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(detector)


class AffectedModulesTests(unittest.TestCase):
    def setUp(self):
        self.modules, self.dependencies = detector.modules_and_dependencies()

    def matrices(self, paths):
        def dockerfile_exists(path):
            return path.name == "Dockerfile" and path.parent.name == "api-entrypoint"

        with patch.object(Path, "is_file", dockerfile_exists):
            return detector.affected_matrices(self.modules, self.dependencies, paths)

    def test_core_change_rebuilds_dependent_service(self):
        result = self.matrices(["core/src/main/kotlin/Example.kt"])
        test_modules = {
            item["module"] for item in json.loads(result["test_matrix"])["include"]
        }
        self.assertTrue({"api-entrypoint", "core"} <= test_modules)
        self.assertEqual(result["image_matrix"], '{"include":[{"module":"api-entrypoint"}]}')

    def test_service_change_does_not_select_core(self):
        result = self.matrices(["api-entrypoint/src/main/kotlin/Example.kt"])
        self.assertIn(":api-entrypoint:testClasses", result["test_tasks"].split())
        self.assertNotIn(":core:testClasses", result["test_tasks"].split())
        self.assertEqual(result["has_images"], "true")

    def test_shared_gradle_change_selects_all_modules(self):
        result = self.matrices(["gradle/libs.versions.toml"])
        self.assertEqual(
            set(result["test_tasks"].split()),
            {f":{module}:testClasses" for module in self.modules},
        )

    def test_unrelated_file_selects_no_matrix_jobs(self):
        result = self.matrices(["README.md"])
        self.assertEqual(result["has_tests"], "false")
        self.assertEqual(result["has_images"], "false")

    def test_initial_push_selects_every_module(self):
        result = self.matrices(None)
        self.assertEqual(result["has_tests"], "true")
        self.assertEqual(result["has_images"], "true")

    def test_dependencies_are_followed_transitively(self):
        modules = ["base", "middle", "service"]
        dependencies = {"base": set(), "middle": {"base"}, "service": {"middle"}}
        result = detector.affected_matrices(modules, dependencies, ["base/src/Main.kt"])
        self.assertEqual(
            result["test_tasks"],
            ":base:testClasses :middle:testClasses :service:testClasses",
        )


if __name__ == "__main__":
    unittest.main()
