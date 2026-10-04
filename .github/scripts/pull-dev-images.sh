#!/usr/bin/env bash
set -euo pipefail

ssh -o BatchMode=yes "$SERVER_USER@$SERVER_HOST" <<'EOF'
  set -Eeuo pipefail
  cd "$HOME/fluent"
  docker compose -p fluent-dev -f dev.docker-compose.yml pull
  docker compose -p fluent-dev -f dev.docker-compose.yml up -d --remove-orphans
  docker compose -p fluent-dev -f dev.docker-compose.yml ps
EOF