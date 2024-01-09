#Author: shubham.kumar@geminisolutions.com
@test-api
@UserManager
@LoginController
Feature: Login Controller APIs

  Scenario Outline: Login Controller - API to login user with valid credentials
    Given Step "1. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    When Step "2. Login with valid credentials"
    And Set endpoint "<login>" method "<post_method>" and with user payload to login user
    Then Verify status code <status_200>
    And Verify response body message for "login successful" and operation message as "success"
    And Verify user login response body
    And Verify user details in response body of login controller
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | login | post_method | status_200 |
      | login | post        | 200        |

  Scenario Outline: Login Controller - API to login user with invalid credentials <field>
    Given Step "1. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    When Step "2. Login with incorrect <field>"
    And Set endpoint "<login>" method "<post_method>" and with user incorrect "<field>" payload to login user
    Then Verify status code <status_400>
    And Verify response body message for "login with incorrect <field>" and operation message as "failure"
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | login | post_method | status_400 | field    |
      | login | post        | 400        | all      |
      | login | post        | 400        | username |
      | login | post        | 400        | password |

  Scenario Outline: Login Controller - API to login user with empty <field> value
    Given Step "1. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    When Step "2. Login without <field> value"
    And Set endpoint "<login>" method "<post_method>" and with user payload to login user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "login with empty <field>" and operation message as "failure"
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | login | post_method | status_400 | field    |
      | login | post        | 400        | all      |
      | login | post        | 400        | username |
      | login | post        | 400        | password |

  Scenario Outline: Login Controller - API to login user with invalid payload without <field> key
    Given Step "1. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    And Step "2. Login without <field> value"
    When Set endpoint "<login>" method "<post_method>" and with user invalid payload to login user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "invalid payload" and operation message as "failure"
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | login | post_method | status_400 | field    |
      | login | post        | 400        | all      |
      | login | post        | 400        | username |
      | login | post        | 400        | password |

