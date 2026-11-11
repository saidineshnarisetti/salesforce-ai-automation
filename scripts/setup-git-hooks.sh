#!/bin/sh
set -eu

repo_root=$(git rev-parse --show-toplevel)
cd "$repo_root"

chmod +x scripts/hooks/commit-msg
git config --local core.hooksPath scripts/hooks

echo "Git commit message validation configured."
echo "Required format: Jira:<ticket-number> <description>"
