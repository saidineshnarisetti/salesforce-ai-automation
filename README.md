# Salesforce AI Automation

This project is a Selenium-based automation framework for Salesforce and other web applications. It includes test automation capabilities with support for multiple browsers, screenshots on failure, and comprehensive test reporting.

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
After cloning the repository, activate local verifications:
```bash
./scripts/setup-git-hooks.sh
```

## Project Structure

```
.
├── src/
│   ├── main/java/          # Main application code
│   └── test/java/          # Test code
├── scripts/                # Utility scripts
│   └── hooks/              # Git hooks
├── logs/                   # Test execution logs
├── target/                 # Build output
└── pom.xml                 # Maven configuration
```

## Features

- Selenium WebDriver integration for browser automation
- TestNG framework for test execution and reporting
- Cross-browser support (Chrome, Firefox, Edge)
- Screenshot capture on test failures
- Configuration management
- Page Object Model design pattern
- Headless mode support

## Prerequisites

- Java 21 or higher
- Maven 3.8+
- WebDriver binaries (ChromeDriver, GeckoDriver) in PATH

## Getting Started

1. Clone the repository
2. Run `./scripts/setup-git-hooks.sh` to enable commit validation
3. Build the project: `mvn clean install`
4. Run tests: `mvn test`

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Class
```bash
mvn test -Dtest=SmokeTest
```

### Run Specific Test Method
```bash
mvn test -Dtest=SmokeTest#openGoogle
```

### Run with Headless Mode
```bash
mvn test -Dbrowser=chrome -Dheadless=true
```

## Configuration

Configuration is managed through `config.properties` file in the `src/main/resources` directory.

## Git Hooks

The project includes a pre-commit hook that enforces commit message format:
- Must start with `Jira:<ticket-number> `
- Must have a description after the ticket number
- The validation ensures consistent commit history for traceability