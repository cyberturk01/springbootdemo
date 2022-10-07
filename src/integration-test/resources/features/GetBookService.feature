@smoke
Feature: Get Book Information

    Scenario: Positive Test for Getting book Information
        Given Route user gets data from "/books" resource with GET request
        Then Verify that user gets success code 200
