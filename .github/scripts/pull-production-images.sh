#!/usr/bin/env bash
set -euo pipefail

ssh -o BatchMode=yes "$SERVER_USER@$SERVER_HOST" <<'EOF'
  set -Eeuo pipefail
  cd "$HOME/fluent"
  docker compose --env-file prod.env -p fluent-prod -f prod.docker-compose.yml pull
  docker compose --env-file prod.env -p fluent-prod -f prod.docker-compose.yml up -d --remove-orphans
  docker compose --env-file prod.env -p fluent-prod -f prod.docker-compose.yml ps
EOF
