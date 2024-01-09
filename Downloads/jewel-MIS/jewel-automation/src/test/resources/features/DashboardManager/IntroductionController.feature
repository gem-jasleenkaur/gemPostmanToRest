@introductionController
Feature: Introduction Controller APIs

################################################## GET INTRO ##################################################

  Scenario Outline: Introduction Controller - API to get <paramValue> intro details
    Given Step "1. Get Introduction Details for <paramValue>"
    When User sets endpoint "getIntro" method "get" and "<paramValue>" as "gemName" parameter
    Then User verifies 200 is the response code

    Examples:
      | paramValue |
      | GemJar     |
      | GemPyp     |
      | GemPerf    |

  Scenario Outline: Introduction Controller - API to get all intro details
    Given Step "1. Get All Introduction Details"
    When User sets endpoint "getIntro" and sets "get" as method
    Then User verifies "<paramValue>" is present in response body
    And User verifies 200 is the response code

    Examples:
      | paramValue |
      | GemJar     |
      | GemPyp     |
      | GemPerf    |

  Scenario Outline: Introduction Controller - API to get multiple comma separated <paramValue> intro details
    Given Step "1. Get Introduction Details for multiple comma separated <paramValue>"
    When User sets endpoint "getIntro" method "get" and "<paramValue>" as "gemName" parameter
    Then User verifies 200 is the response code

    Examples:
      |  paramValue   |
      | GemJar,GemPyp |


################################################## CREATE INTRO ##################################################

  Scenario Outline: Introduction Controller - API to create <paramValue> intro details
    Given Step "1. Create Introduction Details for <paramValue>"
    When User sets endpoint "createIntro" method "post" for "<paramValue>" payload
    Then User verifies 201 is the response code

    Examples:
      | paramValue      |
      | sampleValue     |

  Scenario Outline: Introduction Controller - API to create and verify <paramValue> intro details
    Given Step "1. Create Introduction Details for <paramValue>"
    When User sets endpoint "createIntro" method "post" for "<paramValue>" payload
    Then User verifies 201 is the response code
    And Step "2. Verify Introduction Details for newly created <paramValue>"
    When User sets endpoint "getIntro" method "get" and "<paramValue>" as "gemName" parameter
    Then User verifies "<paramValue>" is present in response body
    Then User verifies 200 is the response code

    Examples:
      |     paramValue       |
      | anotherSampleValue   |

  Scenario Outline: Introduction Controller - API to verify <paramValue> does not exist and then create Intro
    Given Step "1. Verify if Introduction Details exist for <paramValue>"
    When User sets endpoint "getIntro" and sets "get" as method
    Then User verifies "<paramValue>" is not present in response body
    And User verifies 200 is the response code
    And Step "2. Create Introduction Details for <paramValue>"
    When User sets endpoint "createIntro" method "post" for "<paramValue>" payload
    Then User verifies 201 is the response code

    Examples:
      |     paramValue       |
      |    checkAndInsert    |

################################################## DELETE INTRO ##################################################


  Scenario Outline: Introduction Controller - API to delete <paramValue> intro details
    Given Step "1. Delete Introduction Details for newly created <paramValue>"
    When User sets endpoint "deleteIntro" method "delete" and "<paramValue>" as "gemNames" parameter
    Then User verifies 200 is the response code

    Examples:
      | paramValue     |
      | sampleValue    |

  Scenario Outline: Introduction Controller - API to delete <paramValue> multiple intro details
    Given Step "1. Delete Introduction Details for newly created <paramValue>"
    When User sets endpoint "deleteIntro" method "delete" and "<paramValue>" as "gemNames" parameter
    Then User verifies 200 is the response code

    Examples:
      |           paramValue                  |
      | checkAndInsert,anotherSampleValue    |

################################################## NEGATIVE GET INTRO ##################################################

  #there needs to be a different status code for this
  Scenario Outline: Introduction Controller - API to get intro details that does not exist
    Given Step "1. Get Introduction Details for <paramValue> that does not exist"
    When User sets endpoint "getIntro" method "get" and "<paramValue>" as "gemName" parameter
    Then User verifies 200 is the response code

    Examples:
      |  paramValue   |
      | sampleName    |

################################################## NEGATIVE CREATE INTRO ##################################################

  Scenario Outline: Introduction Controller - API to create <paramValue> intro details with duplicate gemName
    Given Step "1. Create Introduction Details for duplicate gemName: <paramValue>"
    When User sets endpoint "createIntro" method "post" for "<paramValue>" payload
    Then User verifies 409 is the response code

    Examples:
      | paramValue   |
      |   GemJar     |

  #recheck
  Scenario Outline: Introduction Controller - API to create intro details with empty gemName
    Given Step "1. Create Introduction Details with empty <paramKey>"
    When User sets endpoint "createIntro" method "post" with empty "<paramKey>" key in payload
    Then User verifies 400 is the response code

    Examples:
      |  paramKey   |
      |  gemName    |
      |   data      |

  #recheck
  Scenario Outline: Introduction Controller - API to create intro details without gemName key
    Given Step "1. Create Introduction Details without <paramKey> key"
    When User sets endpoint "createIntro" method "post" without "<paramKey>" key
    Then User verifies 400 is the response code
    Examples:
      |  paramKey   |
      |  gemName    |
      |   data      |

################################################## NEGATIVE DELETE INTRO ##################################################

  Scenario Outline: Introduction Controller - API to delete a deleted <paramValue> intro details
    Given Step "1. Delete Introduction Details for previously deleted <paramValue>"
    When User sets endpoint "deleteIntro" method "delete" and "<paramValue>" as "gemNames" parameter
    Then User verifies 409 is the response code

    Examples:
      | paramValue     |
      | sampleValue    |

  Scenario: Introduction Controller - API to delete a intro details with empty gemNames parameter
    Given Step "1. Delete Introduction Details for with empty gemName parameter"
    When User sets endpoint "deleteIntro" method "delete" and "" as "gemNames" parameter
    Then User verifies 400 is the response code

