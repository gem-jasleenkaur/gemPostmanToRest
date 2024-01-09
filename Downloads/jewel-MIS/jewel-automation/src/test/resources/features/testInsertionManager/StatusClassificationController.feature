#Author: Dipanshu.Kapoor
#
@TestInsertionManager
@StatusClassificationController
Feature: Status Classification Controller APIs

  Scenario Outline: Status Classification Controller - API to create get and reset variance on testcase level
    Given User authenticate as "normal" user
    And Step "1. Create Variance on testcase level"
    When Set endpoint "<createVariance>" method "post" and payload to create variance on testcase "<testcase>" level
    And Verify status code <status_200>
    And Verify response body message for "variance added successful" and operation message as "success"
    And Step "2. Get Variance using variance Id"
    When Set endpoint "<getVariance>" method "get" and param to get variance
    And Verify status code <status_200>
    And Verify response body message for "variance found" and operation message as "success"
    And Step "3. Reset Variance"
    When Set endpoint "<resetVariance>" method "put" and payload to reset variance
    Then Verify status code <status_200>
    And Verify response body message for "variance reset successful" and operation message as "success"

    Examples:
      | createVariance | resetVariance | getVariance | status_200 | testcase                |
      | createVariance | resetVariance | getVariance | 200        | Pass and Fail scenario1 |

  Scenario Outline: Status Classification Controller - API to create get and reset variance on step level
    Given User authenticate as "normal" user
    And Step "1. Create Variance on step level"
    When Set endpoint "<createVariance>" method "post" and payload to create variance on step "<step>" level
    And Verify status code <status_200>
    And Verify response body message for "variance added successful" and operation message as "success"
    And Step "2. Get Variance using variance Id"
    When Set endpoint "<getVariance>" method "get" and param to get variance
    And Verify status code <status_200>
    And Verify response body message for "variance found" and operation message as "success"
    And Step "3. Reset Variance"
    When Set endpoint "<resetVariance>" method "put" and payload to reset variance
    Then Verify status code <status_200>
    And Verify response body message for "variance reset successful" and operation message as "success"

    Examples:
      | createVariance | resetVariance | getVariance | status_200 | step                                                     |
      | createVariance | resetVariance | getVariance | 200        | Given  Verify response body for \"fail2\" false positive |

  Scenario Outline: Status Classification Controller - API to create get and reset variance with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Create Variance with <token> token"
    When Set endpoint "<createVariance>" method "post" and payload to create variance on testcase "<testcase>" level
    And Verify status code <status_403>
    And Step "2. Get Variance with <token> token"
    When Set endpoint "<getVariance>" method "get" and param to get variance
    And Verify status code <status_403>
    And Step "3. Reset Variance with <token> token"
    When Set endpoint "<resetVariance>" method "put" and payload to reset variance
    And Verify status code <status_403>

    Examples:
      | createVariance | resetVariance | getVariance | testcase                | status_403 | token   |
      | createVariance | resetVariance | getVariance | Pass and Fail scenario1 | 403        | invalid |
      | createVariance | resetVariance | getVariance | Pass and Fail scenario1 | 403        | empty   |

  Scenario Outline: Status Classification Controller - API to get variance with invalid param
    Given User authenticate as "normal" user
    And Step "1. Get Variance with invalid param"
    When Set endpoint "<getVariance>" method "get" and param to get variance
    And Verify status code <status_404>
    Then Verify response body message for "variance not found" and operation message as "failure"

    Examples:
      | getVariance | status_404 |
      | getVariance | 404        |

  Scenario Outline: Status Classification Controller - API to reset variance with invalid param
    Given User authenticate as "normal" user
    And Step "1. Reset Variance with invalid param"
    When Set endpoint "<resetVariance>" method "put" and payload to reset variance
    And Verify status code <status_404>
    Then Verify response body message for "variance not found" and operation message as "failure"

    Examples:
      | resetVariance | status_404 |
      | resetVariance | 404        |

  Scenario Outline: Status Classification Controller - API to create variance with null payload
    Given User authenticate as "normal" user
    And Step "1. Create Variance on testcase level without any payload"
    When Set endpoint "<createVariance>" method "post" to create variance
    And Verify status code <status_415>
    Examples:
      | status_415 | createVariance |
      | 415        | createVariance |

  Scenario Outline: Status Classification Controller - API to create variance with empty payload
    Given User authenticate as "normal" user
    And Step "1. Create Variance on testcase level with empty request payload"
    When Set endpoint "<createVariance>" method "post" to create variance with empty payload
    And Verify status code <status_400>
    Examples:
      | status_400 | createVariance |
      | 400        | createVariance |

    Scenario Outline: Status Classification Controller - API to create variance with null fields
      Given User authenticate as "normal" user
      And Step "1. Create Variance on testcase level with null fields in request payload"
      When Set endpoint "<createVariance>" method "post" to create variance with null "<field>" on "testcase" level
      And Verify status code <status_400>
      Examples:
        | status_400 | createVariance |field|
        | 400        | createVariance |category|
        | 400        | createVariance |endDate|
        | 404        | createVariance |name|
        | 400        | createVariance |reason|
        | 400        | createVariance |startTime|
        | 400        | createVariance |tc_run_id|

  Scenario Outline: Status Classification Controller - API to create variance with empty fields
    Given User authenticate as "normal" user
    And Step "1. Create Variance on testcase level with empty fields in request payload"
    When Set endpoint "<createVariance>" method "post" to create variance with empty "<field>" on "testcase" level
    And Verify status code <status_400>
    Examples:
      | status_400 | createVariance |field|
      | 400        | createVariance |category|
      | 400        | createVariance |endDate|
      | 400        | createVariance |name|
      | 400        | createVariance |reason|
      | 400        | createVariance |startTime|
      | 400        | createVariance |tc_run_id|

  Scenario Outline: Status Classification Controller - API to create variance with invalid field values
    Given User authenticate as "normal" user
    And Step "1. Create Variance on testcase level with invalid field values in request payload"
    When Set endpoint "<createVariance>" method "post" to create variance with invalid "<field>" on "testcase" level
    And Verify status code <status>
    Examples:
      | status | createVariance |field|
      | 400        | createVariance |category|
      | 400        | createVariance |endDate|
      | 400        | createVariance |name|
      | 400        | createVariance |startTime|
      | 400        | createVariance |tc_run_id|
