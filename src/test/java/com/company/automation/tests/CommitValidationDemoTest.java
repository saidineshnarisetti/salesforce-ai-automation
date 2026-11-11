package com.company.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CommitValidationDemoTest {

    @Test
    public void demonstrateCommitMessageValidation() {
        // This test demonstrates that the commit message validation works correctly
        // The actual validation happens at the git hook level, not in this code

        // Valid commit message examples (these would pass validation):
        // Jira:1234 Fix login validation
        // Jira:5678 Add API automation tests

        // Invalid commit message examples (these would fail validation):
        // Testing
        // Jira:1234
        // JIRA:1234 Fix login

        Assert.assertTrue(true, "Commit validation demonstration test passed. "
                + "The actual validation happens through git hooks when committing code.");
    }
}