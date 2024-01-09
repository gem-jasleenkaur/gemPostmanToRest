#Author: shubham.kumar@geminisolutions.com
@test-api
@UserManager
@UserController
Feature: User Controller APIs

  Scenario Outline: User Controller - API to create and delete new user
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    Then Verify status code <status_201>
    And Verify response body message for "create user" and operation message as "success"
    And Verify user creation response body
    And User authenticate as "admin" user
    And Step "2. Delete New User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user
    And Verify status code <status_200>
    And Verify response body message for "delete user with admin user" and operation message as "success"

    Examples:
      | createUser | deleteUser | post_method | status_200 | status_201 |
      | createUser | deleteUser | post        | 200        | 201        |

  Scenario Outline: User Controller - API to create user using existing username
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    And Step "2. Create New User using existing username"
    And Set endpoint "<createUser>" method "<post_method>" and with user payload to create user with existing "username"
    And Verify status code <status_409>
    And Verify response body message for "create user with existing username" and operation message as "failure"
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user

    Examples:
      | createUser | deleteUser | post_method | status_409 |
      | createUser | deleteUser | post        | 409        |

  Scenario Outline: User Controller - API to create user using existing email
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    And Step "2. Create New User using existing username"
    And Set endpoint "<createUser>" method "<post_method>" and with user payload to create user with existing "email"
    And Verify status code <status_409>
    And Verify response body message for "create user with existing email" and operation message as "failure"
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user

    Examples:
      | createUser | deleteUser | post_method | status_409 |
      | createUser | deleteUser | post        | 409        |

  Scenario Outline: User Controller - API to create user using unregistered company and email domain
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    And Step "2. Create New User using unregistered company"
    And Set endpoint "<createUser>" method "<post_method>" and with user payload to create user with unregistered company
    Then Verify status code <status_201>
    And Verify response body message for "create user" and operation message as "success"
    And User authenticate as "admin" user
    And Step "2. Delete New User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user

    Examples:
      | createUser | deleteUser | post_method | status_201 |
      | createUser | deleteUser | post        | 201        |

  Scenario Outline: User Controller - API to create user with empty <field> value
    Given Step "1. Create New User without <field> value"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "create user with empty field" and operation message as "failure"

    Examples:
      | createUser | post_method | status_400 | field     |
      | createUser | post        | 400        | all       |
      | createUser | post        | 400        | username  |
      | createUser | post        | 400        | firstname |
      | createUser | post        | 400        | lastname  |
      | createUser | post        | 400        | email     |
      | createUser | post        | 400        | password  |
      | createUser | post        | 400        | company   |

  Scenario Outline: User Controller - API to create user with invalid payload without <field> key
    Given Step "1. Create New User without <field> value"
    When Set endpoint "<createUser>" method "<post_method>" and with user invalid payload to create user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "invalid payload" and operation message as "failure"

    Examples:
      | createUser | post_method | status_400 | field     |
      | createUser | post        | 400        | all       |
      | createUser | post        | 400        | username  |
      | createUser | post        | 400        | firstname |
      | createUser | post        | 400        | lastname  |
      | createUser | post        | 400        | email     |
      | createUser | post        | 400        | password  |
      | createUser | post        | 400        | company   |

  Scenario Outline: User Controller - API to delete user with normal user
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    And Step "2. Delete User with normal user"
    And User authenticate as "normal" user
    Then Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user
    And Verify status code <status_200>
    And Verify response body message for "accessing without admin" and operation message as "failure"
    And Step "3. Delete User with admin user"
    And User authenticate as "admin" user
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user

    Examples:
      | createUser | deleteUser | post_method | status_200 |
      | createUser | deleteUser | post        | 200        |

  Scenario Outline: User Controller - API to delete user with empty <field> value
    Given User authenticate as "admin" user
    And Step "1. Delete New User without <field> value"
    When Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "delete user with empty field" and operation message as "failure"

    Examples:
      | deleteUser | post_method | status_400 | field    |
      | deleteUser | post        | 400        | username |

  Scenario Outline: User Controller - API to delete user with invalid payload without <field> key
    Given User authenticate as "admin" user
    And Step "1. Delete New User without <field> value"
    When Set endpoint "<deleteUser>" method "<post_method>" and with user invalid payload to delete user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "invalid payload" and operation message as "failure"

    Examples:
      | deleteUser | post_method | status_400 | field    |
      | deleteUser | post        | 400        | username |

  Scenario Outline: User Controller - API to delete user with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Delete User"
    When Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user
    Then Verify status code <status_403>

    Examples:
      | deleteUser | post_method | status_403 | token   |
      | deleteUser | post        | 403        | empty   |
      | deleteUser | post        | 403        | invalid |

  Scenario Outline: User Controller - API to unblock deleted user with Admin User
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    Then User authenticate as "admin" user
    And Step "2. Delete New User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user
    And Step "3. Unblock deleted User with admin user"
    And Set endpoint "<unblockUser>" method "<post_method>" and with user payload to unblock user
    And Verify status code <status_200>
    And Verify response body message for "unblock user with admin user" and operation message as "success"
    And Step "4. Again Delete the same User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user

    Examples:
      | createUser | deleteUser | unblockUser | post_method | status_200 |
      | createUser | deleteUser | unblockUser | post        | 200        |

  Scenario Outline: User Controller - API to unblock deleted user with Normal User
    Given Step "1. Create New User"
    When Set endpoint "<createUser>" method "<post_method>" and with user payload to create user
    Then User authenticate as "normal" user
    And Step "2. Delete New User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user
    And Step "3. Unblock deleted User with normal user"
    And Set endpoint "<unblockUser>" method "<post_method>" and with user payload to unblock user
    And Verify status code <status_200>
    And Verify response body message for "accessing without admin" and operation message as "failure"
    And User authenticate as "admin" user
    And Step "4. Again Delete the same User"
    And Set endpoint "<deleteUser>" method "<post_method>" and with user payload to delete user

    Examples:
      | createUser | deleteUser | unblockUser | post_method | status_200 |
      | createUser | deleteUser | unblockUser | post        | 200        |

  Scenario Outline: User Controller - API to unblock deleted user with empty <field> value
    Given User authenticate as "admin" user
    And Step "1. Unblock deleted user without <field> value"
    When Set endpoint "<unblockUser>" method "<post_method>" and with user payload to unblock user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "delete user with empty field" and operation message as "failure"

    Examples:
      | unblockUser | post_method | status_400 | field    |
      | unblockUser | post        | 400        | username |

  Scenario Outline: User Controller - API to unblock deleted user with invalid payload without <field> key
    Given User authenticate as "admin" user
    And Step "1. Unblock deleted user without <field> value"
    When Set endpoint "<unblockUser>" method "<post_method>" and with user invalid payload to unblock user without "<field>"
    Then Verify status code <status_400>
    And Verify response body message for "invalid payload" and operation message as "failure"

    Examples:
      | unblockUser | post_method | status_400 | field    |
      | unblockUser | post        | 400        | username |

  Scenario Outline: User Controller - API to unblock deleted user with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Unblock deleted User"
    When Set endpoint "<unblockUser>" method "<post_method>" and with user payload to unblock user
    Then Verify status code <status_403>

    Examples:
      | unblockUser | post_method | status_403 | token   |
      | unblockUser | post        | 403        | empty   |
      | unblockUser | post        | 403        | invalid |

  Scenario Outline: User Controller - API to add and remove admin
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "superadmin" user
    And Step "2. Provide admin access to the user"
    And Set endpoint "<addAdmin>" method "<post_method>" and with user payload to add admin
    And Verify status code <status_200>
    And Verify response body message for "add admin with superadmin user" and operation message as "success"
    And Step "3. Remove admin access from the user"
    And Set endpoint "<removeAdmin>" method "<post_method>" and with user payload to remove admin
    And Verify status code <status_200>
    And Verify response body message for "remove admin with superadmin user" and operation message as "success"
    And Step "4. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | addAdmin | removeAdmin | post_method | status_200 |
      | addAdmin | removeAdmin | post        | 200        |

  Scenario Outline: User Controller - API to add and remove admin with <user> user
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "<user>" user
    And Step "2. Provide admin access to the user with <user> user"
    And Set endpoint "<addAdmin>" method "<post_method>" and with user payload to add admin
    And Verify status code <status_200>
    And Verify response body message for "accessing without superadmin" and operation message as "failure"
    And Step "3. Remove admin access from the user with <user> user"
    And Set endpoint "<removeAdmin>" method "<post_method>" and with user payload to remove admin
    And Verify status code <status_200>
    And Verify response body message for "accessing without superadmin" and operation message as "failure"
    And Step "4. Delete New User"
    And User authenticate as "admin" user
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | addAdmin | removeAdmin | post_method | status_200 | user   |
      | addAdmin | removeAdmin | post        | 200        | normal |
      | addAdmin | removeAdmin | post        | 200        | admin  |

  Scenario Outline: User Controller - API to add and remove admin with empty <field> value
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "superadmin" user
    And Step "2. Provide admin access to the user"
    And Set endpoint "<addAdmin>" method "<post_method>" and with user payload to add admin without "<field>"
    And Verify status code <status_400>
    And Verify response body message for "add admin without <field>" and operation message as "failure"
    And Step "3. Remove admin access from the user"
    And Set endpoint "<removeAdmin>" method "<post_method>" and with user payload to remove admin without "<field>"
    And Verify status code <status_400>
    And Verify response body message for "remove admin without <field>" and operation message as "failure"
    And Step "4. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | addAdmin | removeAdmin | post_method | status_400 | field           |
      | addAdmin | removeAdmin | post        | 400        | all             |
      | addAdmin | removeAdmin | post        | 400        | realCompanyName |
      | addAdmin | removeAdmin | post        | 400        | username        |

  Scenario Outline: User Controller - API to add and remove admin with invalid payload without <field> key
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "superadmin" user
    And Step "2. Provide admin access to the user"
    And Set endpoint "<addAdmin>" method "<post_method>" and with user invalid payload to add admin without "<field>"
    And Verify status code <status_400>
    And Verify response body message for "invalid payload" and operation message as "failure"
    And Step "3. Remove admin access from the user"
    And Set endpoint "<removeAdmin>" method "<post_method>" and with user invalid payload to remove admin without "<field>"
    And Verify status code <status_400>
    And Verify response body message for "invalid payload" and operation message as "failure"
    And Step "4. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | addAdmin | removeAdmin | post_method | status_400 | field           |
      | addAdmin | removeAdmin | post        | 400        | all             |
      | addAdmin | removeAdmin | post        | 400        | realCompanyName |
      | addAdmin | removeAdmin | post        | 400        | username        |

  Scenario Outline: User Controller - API to add and remove admin with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Provide admin access to the user"
    And Set endpoint "<addAdmin>" method "<post_method>" and with user payload to add admin
    And Verify status code <status_403>
    And Step "2. Remove admin access from the user"
    And Set endpoint "<removeAdmin>" method "<post_method>" and with user payload to remove admin
    And Verify status code <status_403>

    Examples:
      | addAdmin | removeAdmin | post_method | status_403 | token   |
      | addAdmin | removeAdmin | post        | 403        | empty   |
      | addAdmin | removeAdmin | post        | 403        | invalid |

  Scenario Outline: User Controller - API to add admin using incorrect company name
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And User authenticate as "superadmin" user
    And Step "2. Provide admin access to the user"
    And Set endpoint "<addAdmin>" method "<post_method>" and with user payload having incorrect company name to add admin
    And Verify status code <status_200>
    And Verify response body message for "incorrect company" and operation message as "failure"
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | addAdmin | post_method | status_200 |
      | addAdmin | post        | 200        |

  Scenario Outline: User Controller - API to validate username
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "post" and with user payload to create user
    And Step "2. Validate username"
    And Set endpoint "<validateUsername>" method "<get_method>" and param to validate username
    And Verify status code <status_200>
    And Verify response body message for "validate username" and operation message as "success"
    And User authenticate as "admin" user
    And Step "3. Delete New User"
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | validateUsername | get_method | status_200 |
      | validateUsername | get        | 200        |

  Scenario Outline: User Controller - API to validate username with invalid param
    And Step "1. Validate invalid username"
    And Set endpoint "<validateUsername>" method "<get_method>" and param to validate invalid username
    And Verify status code <status_200>
    And Verify response body message for "validate invalid username" and operation message as "success"

    Examples:
      | validateUsername | get_method | status_200 |
      | validateUsername | get        | 200        |

  Scenario Outline: User Controller - API to get all verified users for existing company
    Given User authenticate as "superadmin" user
    And Step "1. Get verified users"
    And Set endpoint "<getVerifiedUser>" method "<get_method>" and param to get verified users for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for data "records found" and operation message as "success"

    Examples:
      | getVerifiedUser | get_method | status_200 | companyName       |
      | getVerifiedUser | get        | 200        | GEMPERF PVT. LTD. |

  Scenario Outline: User Controller - API to get all verified users for non existing company
    Given User authenticate as "superadmin" user
    And Step "1. Get verified users"
    And Set endpoint "<getVerifiedUser>" method "<get_method>" and param to get verified users for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for "incorrect company" and operation message as "failure"

    Examples:
      | getVerifiedUser | get_method | status_200 | companyName         |
      | getVerifiedUser | get        | 200        | NOT FOUND PVT. LTD. |

  Scenario Outline: User Controller - API to get all verified users with <user> user
    Given User authenticate as "<user>" user
    And Step "1. Get verified users with <user> user"
    And Set endpoint "<getVerifiedUser>" method "<get_method>" and param to get verified users for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for "accessing without superadmin" and operation message as "failure"

    Examples:
      | getVerifiedUser | get_method | status_200 | companyName       | user   |
      | getVerifiedUser | get        | 200        | GEMPERF PVT. LTD. | normal |
      | getVerifiedUser | get        | 200        | GEMPERF PVT. LTD. | admin  |

  Scenario Outline: User Controller - API to get all verified users with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Get verified users"
    And Set endpoint "<getVerifiedUser>" method "<get_method>" and param to get verified users for company "<companyName>"
    And Verify status code <status_403>

    Examples:
      | getVerifiedUser | get_method | status_403 | companyName       | token   |
      | getVerifiedUser | get        | 403        | GEMPERF PVT. LTD. | empty   |
      | getVerifiedUser | get        | 403        | GEMPERF PVT. LTD. | invalid |

  Scenario Outline: User Controller - API to get all same company users
    Given User authenticate as "normal" user
    And Step "1. Get same company users"
    And Set endpoint "<getSameCompanyUserName>" method "<get_method>" and param to get same company users
    And Verify status code <status_200>
    And Verify response body message for data "users found" and operation message as "success"

    Examples:
      | getSameCompanyUserName | get_method | status_200 |
      | getSameCompanyUserName | get        | 200        |

  Scenario Outline: User Controller - API to get all same company users for existing project
    Given User authenticate as "normal" user
    And Step "1. Get same company users"
    And Set endpoint "<getSameCompanyUserName>" method "<get_method>" and param to get same company users for project <projectId>
    And Verify status code <status_200>
    And Verify response body message for data "users found" and operation message as "success"


    Examples:
      | getSameCompanyUserName | get_method | status_200 | projectId |
      | getSameCompanyUserName | get        | 200        | 1         |

  Scenario Outline: User Controller - API to get all same company users for non existing project
    Given User authenticate as "normal" user
    And Step "1. Get same company users"
    And Set endpoint "<getSameCompanyUserName>" method "<get_method>" and param to get same company users for project <projectId>
    And Verify status code <status_200>
    And Verify response body message for "project not found" and operation message as "failure"

    Examples:
      | getSameCompanyUserName | get_method | status_200 | projectId |
      | getSameCompanyUserName | get        | 200        | -1        |

  Scenario Outline: User Controller - API to get all same company users with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Get same company users"
    And Set endpoint "<getSameCompanyUserName>" method "<get_method>" and param to get same company users
    And Verify status code <status_403>

    Examples:
      | getSameCompanyUserName | get_method | status_403 | token   |
      | getSameCompanyUserName | get        | 403        | empty   |
      | getSameCompanyUserName | get        | 403        | invalid |

  Scenario Outline: User Controller - API to get all company users for existing company with super admin
    Given User authenticate as "superadmin" user
    And Step "1. Get company users"
    And Set endpoint "<getCompanyUsers>" method "<get_method>" and param to get company users for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for data "records are found" and operation message as "success"

    Examples:
      | getCompanyUsers | get_method | status_200 | companyName       |
      | getCompanyUsers | get        | 200        | GEMPERF PVT. LTD. |

  Scenario Outline: User Controller - API to get all company users for non existing company with super admin
    Given User authenticate as "superadmin" user
    And Step "1. Get company users"
    And Set endpoint "<getCompanyUsers>" method "<get_method>" and param to get company users for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for "user details not found" and operation message as "success"

    Examples:
      | getCompanyUsers | get_method | status_200 | companyName         |
      | getCompanyUsers | get        | 200        | NOT FOUND PVT. LTD. |

  Scenario Outline: User Controller - API to get all company users with normal user
    Given User authenticate as "normal" user
    And Step "1. Get company users"
    And Set endpoint "<getCompanyUsers>" method "<get_method>" and param to get company users for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for "accessing without admin" and operation message as "success"


    Examples:
      | getCompanyUsers | get_method | status_200 | companyName       |
      | getCompanyUsers | get        | 200        | GEMPERF PVT. LTD. |

  Scenario Outline: User Controller - API to get all company users with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Get company admins with <token> token"
    And Set endpoint "<getCompanyUsers>" method "<get_method>" and param to get company users for company "<companyName>"
    And Verify status code <status_403>

    Examples:
      | getCompanyUsers | get_method | status_403 | companyName       | token   |
      | getCompanyUsers | get        | 403        | GEMPERF PVT. LTD. | empty   |
      | getCompanyUsers | get        | 403        | GEMPERF PVT. LTD. | invalid |

  Scenario Outline: User Controller - API to get all company admins for existing company
    Given User authenticate as "superadmin" user
    And Step "1. Get company admins"
    And Set endpoint "<getCompanyAdmins>" method "<get_method>" and param to get company admins for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for data "records found" and operation message as "success"

    Examples:
      | getCompanyAdmins | get_method | status_200 | companyName       |
      | getCompanyAdmins | get        | 200        | GEMPERF PVT. LTD. |

  Scenario Outline: User Controller - API to get all company admins for non existing company
    Given User authenticate as "superadmin" user
    And Step "1. Get verified users"
    And Set endpoint "<getCompanyAdmins>" method "<get_method>" and param to get company admins for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for "incorrect company" and operation message as "success"

    Examples:
      | getCompanyAdmins | get_method | status_200 | companyName         |
      | getCompanyAdmins | get        | 200        | NOT FOUND PVT. LTD. |

  Scenario Outline: User Controller - API to get all company admins with <user> user
    Given User authenticate as "<user>" user
    And Step "1. Get company admins with <user> user"
    And Set endpoint "<getCompanyAdmins>" method "<get_method>" and param to get company admins for company "<companyName>"
    And Verify status code <status_200>
    And Verify response body message for "accessing without superadmin 2" and operation message as "success"

    Examples:
      | getCompanyAdmins | get_method | status_200 | companyName       | user   |
      | getCompanyAdmins | get        | 200        | GEMPERF PVT. LTD. | normal |
      | getCompanyAdmins | get        | 200        | GEMPERF PVT. LTD. | admin  |

  Scenario Outline: User Controller - API to get all company admins with <token> token
    Given User tries to authenticate as "<token>" token
    And Step "1. Get company admins with <token> token"
    And Set endpoint "<getCompanyAdmins>" method "<get_method>" and param to get company admins for company "<companyName>"
    And Verify status code <status_403>

    Examples:
      | getCompanyAdmins | get_method | status_403 | companyName       | token   |
      | getCompanyAdmins | get        | 403        | GEMPERF PVT. LTD. | empty   |
      | getCompanyAdmins | get        | 403        | GEMPERF PVT. LTD. | invalid |

  Scenario Outline: User Controller - API to add as Unverified
    Given Step "1. Create New User with unregistered Company"
    And Set endpoint "createUser" method "post" and with user payload to create user with unregistered company
    And User authenticate as "superadmin" user
    When Step "2. Add Unverified"
    And Set endpoint "<addUnverifiedUser>" method "<post_method>" and with username as param to add as unverified
    And Verify status code <status_200>
    And Verify response body message for "add to unverified" and operation message as "success"
    When Step "3. Delete User"
    Then Set endpoint "deleteUser" method "post" and with user payload to delete user
    Examples:
      | addUnverifiedUser | status_200 | post_method |
      | addUnverifiedUser | 200        | post        |

  Scenario Outline: User Controller - API to add as Unverified using normal user
    Given Step "1. Create New User with unregistered Company"
    And Set endpoint "createUser" method "<post_method>" and with user payload to create user with unregistered company
    And User authenticate as "normal" user
    When Step "2. Add Unverified"
    And Set endpoint "<addUnverifiedUser>" method "<post_method>" and with username as param to add as unverified
    And Verify status code <status_200>
    And Verify response body message for "accessing without admin" and operation message as "failure"
    And User authenticate as "admin" user
    When Step "3. Delete User"
    Then Set endpoint "deleteUser" method "<post_method>" and with user payload to delete user
    Examples:
      | addUnverifiedUser | post_method | status_200 |
      | addUnverifiedUser | post        | 200        |

  Scenario Outline: User Controller - API to add as Unverified when user is from Registered Company
    Given Step "1. Create New User"
    When Set endpoint "createUser" method "<post_method>" and with user payload to create user
    And User authenticate as "superadmin" user
    When Step "2. Add Unverified"
    And Set endpoint "<addUnverifiedUser>" method "<post_method>" and with username as param to add as unverified
    And Verify status code <status_200>
    And Verify response body message for "no users found" and operation message as "failure"
    And User authenticate as "admin" user
    When Step "3. Delete User"
    Then Set endpoint "deleteUser" method "<post_method>" and with user payload to delete user
    Examples:
      | addUnverifiedUser | status_200 | post_method |
      | addUnverifiedUser | 200        | post        |

  Scenario Outline: User Controller - API to add as unverified using invalid user
    Given User authenticate as "superadmin" user
    When Step "1. Add Unverified"
    And Set endpoint "<addUnverifiedUser>" method "<post_method>" and with username as param to add as unverified
    And Verify status code <status_200>
    And Verify response body message for "unverified invalid user" and operation message as "failure"

    Examples:
      | addUnverifiedUser | post_method | status_200 |
      | addUnverifiedUser | post        | 200        |

  Scenario Outline: User Controller - API to add as unverified with <token> token
    Given User tries to authenticate as "<token>" token
    When Step "2. Add Unverified"
    And Set endpoint "<addUnverifiedUser>" method "<post_method>" and with username as param to add as unverified
    And Verify status code <status_403>
    Examples:
      | addUnverifiedUser | post_method | status_403 | token   |
      | addUnverifiedUser | post        | 403        | invalid |
      | addUnverifiedUser | post        | 403        | empty   |


      #      Below scenarios are belonging to internal API calls. If we need to automate those internal \
      #      API calls in future we will uncomment all scenarios.

#  Scenario Outline: User Controller - API to get all users with <user> user
#    Given User authenticate as "<user>" user
#    And Step "1. Get all users with <user> user"
#    And Set endpoint "<getAllUsers>" method "<get_method>" to get all users
#    And Verify status code <status_200>
#    And Verify response body for getting all users
#
#    Examples:
#      | getAllUsers | get_method | status_200 | user       |
#      | getAllUsers | get        | 200        | normal     |
#      | getAllUsers | get        | 200        | admin      |
#      | getAllUsers | get        | 200        | superadmin |
#
#  Scenario Outline: User Controller - API to get all users with <token> token
#    Given User tries to authenticate as "<token>" token
#    When Step "1. Get all users with <user> user"
#    And Set endpoint "<getAllUsers>" method "<get_method>" to get all users
#    And Verify status code <status_403>
#
#    Examples:
#      | getAllUsers | get_method | status_403 | token   |
#      | getAllUsers | get        | 403        | invalid |
#      | getAllUsers | get        | 403        | empty   |
#
#  Scenario Outline: User Controller - API to get username details with <user> user
#    Given User authenticate as "<user>" user
#    And Step "1. Get username details with <user> user"
#    And Set endpoint "<getUsername>" method "<get_method>" and param to get username "<username>" details
#    And Verify status code <status_200>
#    And Verify response body message for "user exists" and operation message as "success"
#
#    Examples:
#      | getUsername | get_method | status_200 | user       | username  |
#      | getUsername | get        | 200        | normal     | perf-test |
#      | getUsername | get        | 200        | admin      |           |
#      | getUsername | get        | 200        | superadmin |           |
#
#  Scenario Outline: User Controller - API to get username details with <token> token
#    Given User tries to authenticate as "<token>" token
#    When Step "1. Get username details with <user> user"
#    And Set endpoint "<getUsername>" method "<get_method>" and param to get username "<username>" details
#    And Verify status code <status_403>
#
#    Examples:
#      | getUsername | get_method | status_403 | token   | username |
#      | getUsername | get        | 403        | invalid |          |
#      | getUsername | get        | 403        | empty   |          |
#
#  Scenario Outline: User Controller - API to get username deleted details with <user> user
#    Given Step "1. Create New User"
#    When Set endpoint "createUser" method "post" and with user payload to create user
#    And Step "2. Get username deleted details with <user> user"
#    And User authenticate as "admin" user
#    And Step "3. Delete New User"
#    And Set endpoint "deleteUser" method "post" and with user payload to delete user
#    Then User authenticate as "<user>" user
#    And Step "4. Get username details with <user> user"
#    And Set endpoint "<getUsernameDeleted>" method "<get_method>" and param to get username deleted details
#    And Verify status code <status_200>
#    And Verify response body message for "user exists" and operation message as "success"
#
#    Examples:
#      | getUsernameDeleted | get_method | status_200 | user       |
#      | getUsernameDeleted | get        | 200        | normal     |
#      | getUsernameDeleted | get        | 200        | admin      |
#      | getUsernameDeleted | get        | 200        | superadmin |
#
#  Scenario Outline: User Controller - API to get username deleted details with <token> token
#    Given User tries to authenticate as "<token>" token
#    When Step "1. Get username deleted details with <user> user"
#    And Set endpoint "<getUsernameDeleted>" method "<get_method>" and param to get username deleted details
#    And Verify status code <status_403>
#
#    Examples:
#      | getUsernameDeleted | get_method | status_403 | token   |
#      | getUsernameDeleted | get        | 403        | invalid |
#      | getUsernameDeleted | get        | 403        | empty   |
#
#  Scenario Outline: User Controller - API to get reaCompany users details with <user> user
#    Given User authenticate as "<user>" user
#    And Step "1. Get realCompany users details with <user> user"
#    And Set endpoint "<getRealCompany>" method "<get_method>" and param to get company "<companyName>" details
#    And Verify status code <status_200>
#    And Verify response body for getting all users
#
#    Examples:
#      | getRealCompany | get_method | status_200 | user       | companyName       |
#      | getRealCompany | get        | 200        | normal     | GEMPERF PVT. LTD. |
#      | getRealCompany | get        | 200        | admin      | GEMPERF PVT. LTD. |
#      | getRealCompany | get        | 200        | superadmin | GEMPERF PVT. LTD. |
#
#  Scenario Outline: User Controller - API to get reaCompany users details with <token> token
#    Given User tries to authenticate as "<token>" token
#    And Step "1. Get realCompany users details with <user> user"
#    And Set endpoint "<getRealCompany>" method "<get_method>" and param to get company "<getRealCompany>" details
#    And Verify status code <status_403>
#
#    Examples:
#      | getRealCompany | get_method | status_403 | token   | getRealCompany    |
#      | getRealCompany | get        | 403        | invalid | GEMPERF PVT. LTD. |
#      | getRealCompany | get        | 403        | empty   | GEMPERF PVT. LTD. |