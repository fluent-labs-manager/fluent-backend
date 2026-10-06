#!/usr/bin/env bash
set -euo pipefail

if [[ -z "${BEFORE_SHA:-}" || -z "${AFTER_SHA:-}" ]]; then
  echo "BEFORE_SHA and AFTER_SHA must be set" >&2
  exit 1
fi

if [[ "$BEFORE_SHA" == "0000000000000000000000000000000000000000" ]]; then
  commit_range="$AFTER_SHA"
else
  commit_range="$BEFORE_SHA..$AFTER_SHA"
fi

COUNT=$(git rev-list --count "$commit_range")
COMMIT_LIST=$(git log --format='- %s' "$commit_range")

{
  echo "count=$COUNT"
  echo "commit_list<<EOF"
  echo "$COMMIT_LIST"
  echo "EOF"
} >> "$GITHUB_OUTPUT"
