@introductionControllerGemRac
Feature: Introduction Controller APIs

  Scenario: Introduction Controller - API to get <paramValue> intro details
    Given Step "1. Get Introduction Details for <paramValue>"
    When user creates a new request named "getIntroDetails" request and sets "getIntro" as endpoint
    When user adds Query Parameter named "Parameter Key" with value "Parameter value" to the request
      | Parameter Key    | Parameter Value |
      | gemName          | GemJar          |
      | gemName          | GemPyp          |
      | gemName          | GemPerf         |
    When user sends a "GET" request
    Then user verifies "200" status code


  Scenario Outline: Introduction Controller - API to get all intro details
    Given Step "1. Get Introduction Details"
    When User sets endpoint "getIntro" and sets "get" as method
    Then User verifies "<paramValue>" is present in response body
    And User verifies 200 is the response code

    Examples:
      | paramValue |
      | GemJar     |
      | GemPyp     |
      | GemPerf    |

  Scenario Outline: Introduction Controller - API to create <paramValue> intro details
    Given Step "1. Create Introduction Details for <paramValue>"
    When User sets endpoint "createIntro" method "post" for "<paramValue>" payload
    Then User verifies 200 is the response code

    Examples:
      | paramValue      |
      | sampleValue     |

  Scenario Outline: Introduction Controller - API to get <paramValue> intro details
    Given Step "1. Get Introduction Details for <paramValue>"
    When User sets endpoint "getIntro" method "get" and "<paramValue>" as "gemName" parameter
    Then User verifies 200 is the response code

    Examples:
      | paramValue     |
      | GemJar         |
      | GemPyp         |
      | GemPerf        |
      | sampleValue    |

  Scenario Outline: Introduction Controller - API to delete <paramValue> intro details
    Given Step "1. Get Introduction Details for <paramValue>"
    When User sets endpoint "getIntro" method "get" and "<paramValue>" as "gemName" parameter
    Then User verifies 200 is the response code

    Examples:
      | paramValue     |
      | sampleValue    |



#  Scenario Outline: Introduction Controller - API to get non existing intro details
#  Scenario Outline: Introduction Controller - API to create and delete intro details
#  Scenario Outline: Introduction Controller - API to create and delete intro details with invalid payload
#  Scenario Outline: Introduction Controller - API to create and delete intro details with empty field payload
