## Git Commit Message Convention
All developers must follow this commit message format:
`Jira:<ticket-number> <description>`
### Examples
- **Valid:** `Jira:1234 Fix login validation`
- **Valid:** `Jira:5678 Add API automation tests`
- **Invalid:** `Testing`
- **Invalid:** `Jira:1234`
- **Invalid:** `JIRA:1234 Fix login`
### Setup
After cloning the repository, activate local verification:
```bash
./scripts/setup-git-hooks.sh