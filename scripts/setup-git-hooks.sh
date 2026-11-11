#!/bin/sh
set -eu

echo "Setting up Git commit message validation..."

repo_root=$(git rev-parse --show-toplevel)
cd "$repo_root"

# Check if hooks directory exists, create if not
if [ ! -d "scripts/hooks" ]; then
    mkdir -p scripts/hooks
fi

# Make sure the hook script is executable
chmod +x scripts/hooks/commit-msg
git config --local core.hooksPath scripts/hooks

echo "✅ Git commit message validation configured successfully."
echo "Required format: Jira:<ticket-number> <description>"
echo "Example: Jira:1234 Fix login validation"
echo ""
echo "You can test this by making a commit with an invalid format:"
echo "  git commit -m 'Testing'"
echo "Or a valid format:"
echo "  git commit -m 'Jira:1234 Fix login validation'"
