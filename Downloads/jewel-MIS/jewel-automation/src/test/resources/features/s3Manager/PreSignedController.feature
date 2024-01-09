#Author: shubham.kumar@geminisolutions.com

@test-api
@S3Manager
@PreSignedController
Feature: Pre-Signed Controller APIs

  Scenario Outline: Pre Signed Controller - API to generate pre signed url and upload <num> file
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Generate preSigned URL"
    When Set endpoint "<generatePreSigned>" method "<post_method>" and with pre signed payload to generate <num> url
    Then Verify status code <status_200>
    And Verify the number of file as <num> in response
    And Verify response body message for "generate pre signed url successful" and operation message as "success"
    And Hit the each url with file to upload and verify status code <status_200>

    Examples:
      | generatePreSigned | post_method | status_200 | num |
      | generatePreSigned | post        | 200        | 1   |
      | generatePreSigned | post        | 200        | 3   |

  Scenario Outline: Pre Signed Controller - API to generate pre signed url with empty <field>
    Given User authenticate as "normal" user
    When Set the username and bridge token header
    And Step "1. Generate preSigned URL with empty <field>"
    Then Set endpoint "<generatePreSigned>" method "<post_method>" and with empty "<field>" payload to generate url
    And Verify status code <status>
    And Verify response body message for "generate pre signed url with empty <field>" and operation message as "failure"

    Examples:
      | generatePreSigned | post_method | status | field    |
      | generatePreSigned | post        | 400    | file     |
      | generatePreSigned | post        | 206    | fileName |
      | generatePreSigned | post        | 404    | s_run_id |
      | generatePreSigned | post        | 400    | tag      |

  Scenario Outline: Pre Signed Controller - API to generate pre signed url with invalid <field>
    Given User authenticate as "normal" user
    When Set the username and bridge token header
    And Step "1. Generate preSigned URL with invalid <field>"
    Then Set endpoint "<generatePreSigned>" method "<post_method>" and with invalid "<field>" payload to generate url
    And Verify status code <status>
    And Verify response body message for "generate pre signed url with invalid <field>" and operation message as "failure"

    Examples:
      | generatePreSigned | post_method | status | field    |
      | generatePreSigned | post        | 404    | s_run_id |
      | generatePreSigned | post        | 400    | tag      |

  Scenario Outline: Pre Signed Controller - API to generate pre signed url without <auth> in header
    Given User authenticate as "normal" user
    When Set the username and bridge token header as without "<auth>"
    And Step "1. Generate preSigned URL without <auth>"
    Then Set endpoint "<generatePreSigned>" method "<post_method>" and with pre signed payload to generate 1 url
    And Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"

    Examples:
      | generatePreSigned | post_method | status_403 | auth        |
      | generatePreSigned | post        | 403        | all         |
      | generatePreSigned | post        | 403        | username    |
      | generatePreSigned | post        | 403        | bridgeToken |

  Scenario Outline: Pre Signed Controller - API to upload file using expired pre-signed url
    Given Step "1. Upload file using expired pre-signed url"
    When Upload file using expired pre-signed url using "<put_method>"
    Then Verify status code <status_403>
    And Verify response body message for "upload file using expired pre-signed url"

    Examples:
      | put_method | status_403 |
      | put        | 403        |
