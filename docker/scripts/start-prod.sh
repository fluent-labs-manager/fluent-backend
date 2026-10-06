#!/usr/bin/env bash
set -euo pipefail

docker compose \
  --env-file ../prod.env \
  -f ../prod.docker-compose.yml \
  up -d