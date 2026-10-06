#!/usr/bin/env bash
set -euo pipefail

docker build -f Dockerfile -t scobca/java21-doppler:latest .
docker push scobca/java21-doppler:latest