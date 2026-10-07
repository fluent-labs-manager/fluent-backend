#!/usr/bin/env bash
set -euo pipefail

docker compose \
  --env-file ../dev.env \
  -f ../dev.docker-compose.yml \
  up -d