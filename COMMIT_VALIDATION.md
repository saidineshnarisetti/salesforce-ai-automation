# Commit Message Validation

This project enforces a standardized commit message format using Git hooks to ensure consistency across the codebase.

## Required Format

All commits must follow this format:
```
Jira:<ticket-number> <description>
```

### Valid Examples
- `Jira:1234 Fix login validation`
- `Jira:5678 Add API automation tests`
- `Jira:9999 Update documentation`

### Invalid Examples
- `Testing`
- `Jira:1234` (missing description)
- `JIRA:1234 Fix login` (wrong case)
- `Fix login validation` (missing Jira prefix)

## How It Works

The commit validation is implemented using a Git pre-commit hook located at:
```
scripts/hooks/commit-msg
```

When you try to make a commit with an invalid message format, Git will reject the commit and show an error message with instructions.

## Setup

After cloning the repository, run the setup script:
```bash
./scripts/setup-git-hooks.sh
```

This will configure Git to use the commit validation hook automatically.

## Testing the Validation

To test that the validation is working:

1. Make a commit with an invalid format:
   ```bash
   git commit -m "Testing"
   ```
   This should fail with an error message.

2. Make a commit with a valid format:
   ```bash
   git commit -m "Jira:1234 Fix login validation"
   ```
   This should succeed.

## Customization

The validation logic is in `scripts/hooks/commit-msg`. You can modify this file if you need to change the validation rules, but make sure to keep the exit codes consistent (0 for success, 1 for failure).