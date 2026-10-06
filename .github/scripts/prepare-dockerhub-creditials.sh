#!/usr/bin/env bash
set -euo pipefail

dockerhub_username="$(doppler secrets get DOCKERHUB_USERNAME --plain)"
dockerhub_token="$(doppler secrets get DOCKERHUB_TOKEN --plain)"

echo "::add-mask::$dockerhub_username"
echo "::add-mask::$dockerhub_token"

{
  echo "DOCKERHUB_USERNAME=$dockerhub_username"
  echo "DOCKERHUB_TOKEN=$dockerhub_token"
} >> "$GITHUB_ENV"