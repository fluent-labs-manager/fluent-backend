#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
version="$(tr -d '\r\n' < "$script_dir/VERSION")"

if [[ -z "$version" ]]; then
  echo "VERSION must not be empty" >&2
  exit 1
fi

docker buildx build \
  --platform linux/amd64,linux/arm64 \
  --file "$script_dir/Dockerfile" \
  --tag "scobca/java21-doppler:latest" \
  --tag "scobca/java21-doppler:$version" \
  --push \
  "$script_dir"
