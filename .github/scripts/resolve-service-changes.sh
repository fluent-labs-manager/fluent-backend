#!/usr/bin/env bash
set -euo pipefail

output_name="${SERVICE_NAME}-changed"

if git diff --quiet "$BASE_SHA" "$HEAD_SHA" -- "$SERVICE_NAME/src/"; then
  printf '%s=%s\n' "$output_name" "false" >> "$GITHUB_OUTPUT"
else
  printf '%s=%s\n' "$output_name" "true" >> "$GITHUB_OUTPUT"
fi
