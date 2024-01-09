#Author: shubham.kumar@geminisolutions.com
@test-api
@UserManager
@AuthTokenController
Feature: Auth Token Controller APIs

  Scenario Outline: Auth Token Controller - API to Change the bridge Token
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "new" user
    And Step "2. Change Bridge Token"
    And Set endpoint "<changeBridgeToken>" method "<post_method>" to change bridge token
    And Verify status code <status_200>
    And Verify response body message for "change bridge token" and operation message as "success"
    And Verify user change token response body
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | changeBridgeToken | post_method | status_200 |
      | changeBridgeToken | post        | 200        |

  Scenario Outline: Auth Token Controller - API to Change the bridge Token with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Change Bridge Token"
    And Set endpoint "<changeBridgeToken>" method "<post_method>" to change bridge token
    And Verify status code <status_403>

    Examples:
      | changeBridgeToken | post_method | status_403 | token   |
      | changeBridgeToken | post        | 403        | invalid |
      | changeBridgeToken | post        | 403        | empty   |

  Scenario Outline: Auth Token Controller - API to Get the bridge Token
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "new" user
    And Step "2. Get Bridge Token"
    And Set endpoint "<getBridgeToken>" method "<get_method>" to get bridge token
    And Verify status code <status_200>
    And Verify response body message for "get bridge token" and operation message as "success"
    And Verify user get token response body
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | getBridgeToken | get_method | status_200 |
      | getBridgeToken | get        | 200        |

  Scenario Outline: Auth Token Controller - API to Get the bridge last <count> Token
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "new" user
    And Step "2. Change Bridge Token"
    And Set endpoint "changeBridgeToken" method "post" to change bridge token
    And Set endpoint "changeBridgeToken" method "post" to change bridge token
    And Set endpoint "changeBridgeToken" method "post" to change bridge token
    And Step "3. Get last bridge token"
    And Set endpoint "<getBridgeToken>" method "<get_method>" to get last "<count>" bridge token
    And Verify status code <status_200>
    And Verify response body message for rowdata "get bridge token" and operation message as "success"
    And User authenticate as "admin" user
    And Step "4. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | getBridgeToken | get_method | status_200 | count |
      | getBridgeToken | get        | 200        | 5     |

  Scenario Outline: Auth Token Controller - API to Get the bridge Token with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Get Bridge Token"
    And Set endpoint "<getBridgeToken>" method "<get_method>" to change bridge token
    And Verify status code <status_403>

    Examples:
      | getBridgeToken | get_method | status_403 | token   |
      | getBridgeToken | get        | 403        | invalid |
      | getBridgeToken | get        | 403        | empty   |

  Scenario Outline: Auth Token Controller - API to Get the User status
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "new" user
    And Step "2. Get User status"
    And Set endpoint "<getUserStatus>" method "<get_method>" to get user status
    And Verify status code <status_200>
    And Verify user get user status response body
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | getUserStatus | get_method | status_200 |
      | getUserStatus | get        | 200        |

  Scenario Outline: Auth Token Controller - API to Get the User status with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Get User status"
    And Set endpoint "<getUserStatus>" method "<get_method>" to get user status
    And Verify status code <status_403>

    Examples:
      | getUserStatus | get_method | status_403 | token   |
      | getUserStatus | get        | 403        | invalid |
      | getUserStatus | get        | 403        | empty   |