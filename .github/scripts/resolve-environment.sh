#!/usr/bin/env bash
set -euo pipefail

case "$BRANCH_NAME" in
  dev)
    echo "deployment_environment=dev" >> "$GITHUB_OUTPUT"
    ;;
  main)
    echo "deployment_environment=prd" >> "$GITHUB_OUTPUT"
    ;;
  *)
    echo "Unsupported branch: $BRANCH_NAME" >&2
    exit 1
    ;;
  esac