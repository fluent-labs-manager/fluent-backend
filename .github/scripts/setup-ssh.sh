#!/usr/bin/env bash
set -euo pipefail

mkdir -p ~/.ssh

printf '%s' "$SSH_KEY" > ~/.ssh/id_ed25519
chmod 600 ~/.ssh/id_ed25519

ssh-keyscan -H "$SSH_HOST" >> ~/.ssh/known_hosts 2>/dev/null