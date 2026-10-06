#!/usr/bin/env bash
set -euo pipefail

ssh -o BatchMode=yes "$SERVER_USER@$SERVER_HOST" <<'EOF'
  set -Eeuo pipefail
  cd "$HOME/fluent"
  docker compose --env-file dev.env -p fluent-dev -f dev.docker-compose.yml pull
  docker compose --env-file dev.env -p fluent-dev -f dev.docker-compose.yml up -d --remove-orphans
  docker compose --env-file dev.env -p fluent-dev -f dev.docker-compose.yml ps
EOF
