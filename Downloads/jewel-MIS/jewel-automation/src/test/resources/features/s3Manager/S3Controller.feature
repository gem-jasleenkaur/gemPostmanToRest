##Author: shubham.kumar@geminisolutions.com
#
@test-api
@S3Manager
@S3Controller
Feature: S3 Controller APIs
#
  Scenario Outline: S3 Controller - API to upload and delete <count> file(s)
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload <count> file(s)"
    When Set endpoint "<uploadFile>" method "<post_method>" param and <count> file to upload
    Then Verify status code <status_200>
    And Verify the number of file as <count> in response
    And Verify response body message for "upload successful" and operation message as "success"
    And Step "2. Delete <count> file(s)"
    And Set endpoint "<deleteFile>" method "<delete_method>" and payload to delete uploaded files
    Then Verify status code <status_200>
    And Verify response body message for "file delete successful" and operation message as "success"

    Examples:
      | uploadFile | deleteFile | post_method | delete_method | status_200 | count |
      | uploadFile | deleteFile | post        | delete        | 200        | 1     |
      | uploadFile | deleteFile | post        | delete        | 200        | 3     |
#
  Scenario Outline: S3 Controller - API to upload <count> file(s) with invalid <param>
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload <count> file(s) using invalid <param>"
    When Set endpoint "<uploadFile>" method "<post_method>" invalid "<param>" param and <count> file to upload
    Then Verify status code <status>
    And Verify response body message for "upload with invalid <param>" and operation message as "failure"

    Examples:
      | uploadFile | post_method | status | count | param    |
      | uploadFile | post        | 404    | 1     | suite id |
      | uploadFile | post        | 400    | 1     | tag      |

  Scenario Outline: S3 Controller - API to upload file without file
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload file without file"
    When Set endpoint "<uploadFile>" method "<post_method>" param and no file to upload
    Then Verify status code <status_400>
    And Verify response body message for "upload with no files" and operation message as "failure"

    Examples:
      | uploadFile | post_method | status_400 |
      | uploadFile | post        | 400        |

  Scenario Outline: S3 Controller - API to upload file without <auth> in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header as without "<auth>"
    And Step "1. Upload <count> file(s)"
    When Set endpoint "<uploadFile>" method "<post_method>" param and <count> file to upload
    Then Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"

    Examples:
      | uploadFile | post_method | status_403 | auth        | count |
      | uploadFile | post        | 403        | all         | 1     |
      | uploadFile | post        | 403        | username    | 1     |
      | uploadFile | post        | 403        | bridgeToken | 1     |

  Scenario Outline: S3 Controller - API to download file with bridge token in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload <count> file(s)"
    When Set endpoint "uploadFile" method "post" param and <count> file to upload
    And Step "2. Download file"
    Then Set endpoint "<downloadFile>" method "<get_method>" param to download file using method 1
    And Verify status code <status_200>
    And Step "3. Delete <count> file(s)"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files

    Examples:
      | downloadFile | get_method | status_200 | count |
      | downloadFile | get        | 200        | 1     |

  Scenario Outline: S3 Controller - API to download file with bearer token as authorization
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload <count> file(s)"
    When Set endpoint "uploadFile" method "post" param and <count> file to upload
    And Step "2. Download file"
    Then Set endpoint "<downloadFile>" method "<get_method>" param to download file using method 2
    And Verify status code <status_200>
    And Step "3. Delete <count> file(s)"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files

    Examples:
      | downloadFile | get_method | status_200 | count |
      | downloadFile | get        | 200        | 1     |

  Scenario Outline: S3 Controller - API to download file with token as query param
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload <count> file(s)"
    When Set endpoint "uploadFile" method "post" param and <count> file to upload
    And Step "2. Download file"
    Then Set endpoint "<downloadFile>" method "<get_method>" param to download file using method 3
    And Verify status code <status_200>
    And Step "3. Delete <count> file(s)"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files

    Examples:
      | downloadFile | get_method | status_200 | count |
      | downloadFile | get        | 200        | 1     |

  Scenario Outline: S3 Controller - API to download file with invalid file id
    Given User authenticate as "normal" user
    When Set the username and bridge token header
    And Step "1. Download file"
    Then Set endpoint "<downloadFile>" method "<get_method>" invalid param to download file
    And Verify status code <status_404>
    And Verify response body message for "file not present" and operation message as "failure"

    Examples:
      | downloadFile | get_method | status_404 |
      | downloadFile | get        | 404        |

  Scenario Outline: S3 Controller - API to download file without <auth> in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload <count> file(s)"
    When Set endpoint "uploadFile" method "post" param and <count> file to upload
    And Set the username and bridge token header as without "<auth>"
    And Step "2. Download file"
    When Set endpoint "<downloadFile>" method "<get_method>" param to download file using method 1
    Then Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"
    And Set the username and bridge token header
    And Step "3. Delete <count> file(s)"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files

    Examples:
      | downloadFile | get_method | status_403 | auth        | count |
      | downloadFile | get        | 403        | all         | 1     |
      | downloadFile | get        | 403        | username    | 1     |
      | downloadFile | get        | 403        | bridgeToken | 1     |

  Scenario Outline: S3 Controller - API to upload and delete data
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload data"
    When Set endpoint "<uploadData>" method "<post_method>" param and to upload data
    Then Verify status code <status_200>
    And Verify response body message for "data upload successful" and operation message as "success"
    And Step "2. Delete file"
    And Set endpoint "<deleteFile>" method "<delete_method>" and payload to delete uploaded files
    Then Verify status code <status_200>

    Examples:
      | uploadData | deleteFile | post_method | delete_method | status_200 |
      | uploadData | deleteFile | post        | delete        | 200        |

  Scenario Outline: S3 Controller - API to upload data with invalid <param>
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload data using invalid <param>"
    When Set endpoint "<uploadData>" method "<post_method>" invalid "<param>" param and to upload data
    Then Verify status code <status>
    And Verify response body message for "upload with invalid <param>" and operation message as "failure"

    Examples:
      | uploadData | post_method | status | param       |
      | uploadData | post        | 404    | suite id    |
      | uploadData | post        | 400    | access type |

  Scenario Outline: S3 Controller - API to upload data without <auth> in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header as without "<auth>"
    And Step "1. Upload data"
    When Set endpoint "<uploadData>" method "<post_method>" param and to upload data
    Then Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"

    Examples:
      | uploadData | post_method | status_403 | auth        |
      | uploadData | post        | 403        | all         |
      | uploadData | post        | 403        | username    |
      | uploadData | post        | 403        | bridgeToken |

  Scenario Outline: S3 Controller - API to provide and revoke folder access to other user
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload file"
    When Set endpoint "uploadFile" method "post" param and 1 file to upload
    And Step "2. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    And Step "3. Provide Folder access"
    And Set endpoint "<folderAccess>" method "<post_method>" param and with access payload to "provide" folder access
    Then Verify status code <status_200>
    And Verify response body message for "folder access provided" and operation message as "success"
    And Step "4. Revoke Folder access"
    And Set endpoint "<folderAccess>" method "<post_method>" param and with access payload to "revoke" folder access
    Then Verify status code <status_200>
    And Verify response body message for "folder access revoked" and operation message as "success"
    And Step "5. Delete file"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files
    And Step "6. Delete New User"
    And User authenticate as "admin" user
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | folderAccess | post_method | status_200 |
      | folderAccess | post        | 200        |

  Scenario Outline: S3 Controller - API to provide and revoke folder access to other user for non-existing folder
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload file"
    When Set endpoint "uploadFile" method "post" param and 1 file to upload
    And Step "2. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    And Step "3. Provide Folder access"
    And Set endpoint "<folderAccess>" method "<post_method>" param and with non-existing folder access payload to "provide" folder access
    Then Verify status code <status_206>
    And Verify response body message for "invalid folder access provided" and operation message as "success"
    And Step "4. Revoke Folder access"
    And Set endpoint "<folderAccess>" method "<post_method>" param and with non-existing folder access payload to "revoke" folder access
    Then Verify status code <status_206>
    And Verify response body message for "invalid folder access revoked" and operation message as "success"
    And Step "5. Delete file"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files
    And Step "6. Delete New User"
    And User authenticate as "admin" user
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | folderAccess | post_method | status_206 |
      | folderAccess | post        | 206        |

  Scenario Outline: S3 Controller - API to provide folder access with invalid query param
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Provide Folder access with invalid query param"
    And Set endpoint "<folderAccess>" method "<post_method>" invalid param and with access payload for folder access
    Then Verify status code <status_400>
    And Verify response body message for "folder access with invalid param" and operation message as "failure"

    Examples:
      | folderAccess | post_method | status_400 |
      | folderAccess | post        | 400        |

  Scenario Outline: S3 Controller - API to provide folder access without <auth> in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header as without "<auth>"
    And Step "1. Provide folder access"
    And Set endpoint "<folderAccess>" method "<post_method>" param and with access payload to "provide" folder access
    Then Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"

    Examples:
      | folderAccess | post_method | status_403 | auth        |
      | folderAccess | post        | 403        | all         |
      | folderAccess | post        | 403        | username    |
      | folderAccess | post        | 403        | bridgeToken |

  Scenario Outline: S3 Controller - API to provide and revoke file <mode> access to other user
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload file"
    When Set endpoint "uploadFile" method "post" param and 1 file to upload
    And Step "2. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    And Step "3. Provide File <mode> access"
    And Set endpoint "<fileAccess>" method "<post_method>" param and with access payload to "provide" file access in "<mode>" mode
    Then Verify status code <status_200>
    And Verify response body message for "file <mode> access provided" and operation message as "success"
    And Step "4. Revoke File <mode> access"
    And Set endpoint "<fileAccess>" method "<post_method>" param and with access payload to "revoke" file access in "<mode>" mode
    Then Verify status code <status_200>
    And Verify response body message for "file <mode> access revoked" and operation message as "success"
    And Step "5. Delete file"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files
    And Step "6. Delete New User"
    And User authenticate as "admin" user
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | fileAccess | post_method | status_200 | mode  |
      | fileAccess | post        | 200        | read  |
      | fileAccess | post        | 200        | write |

  Scenario Outline: S3 Controller - API to provide and revoke file <mode> access to other user for non-existing file
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload file"
    When Set endpoint "uploadFile" method "post" param and 1 file to upload
    And Step "2. Create New User"
    And Set endpoint "createUser" method "post" and with user payload to create user
    And Step "3. Provide File <mode> access"
    And Set endpoint "<fileAccess>" method "<post_method>" param and with non-existing file payload to "provide" file access in "<mode>" mode
    Then Verify status code <status_206>
    And Verify response body message for "invalid file access provided" and operation message as "success"
    And Step "4. Revoke Folder access"
    And Set endpoint "<fileAccess>" method "<post_method>" param and with non-existing file payload to "revoke" file access in "<mode>" mode
    Then Verify status code <status_206>
    And Verify response body message for "invalid file access revoked" and operation message as "success"
    And Step "5. Delete file"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files
    And Step "6. Delete New User"
    And User authenticate as "admin" user
    And Set endpoint "deleteUser" method "post" and with user payload to delete user

    Examples:
      | fileAccess | post_method | status_206 | mode  |
      | fileAccess | post        | 206        | read  |
      | fileAccess | post        | 206        | write |

  Scenario Outline: S3 Controller - API to provide file access with invalid query param for <param>
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Provide File access with invalid query param"
    And Set endpoint "<fileAccess>" method "<post_method>" invalid "<param>" param and with access payload for file access
    Then Verify status code <status_400>
    And Verify response body message for "file access with invalid <param> param" and operation message as "failure"

    Examples:
      | fileAccess | post_method | status_400 | param |
      | fileAccess | post        | 400        | type  |
      | fileAccess | post        | 400        | mode  |

  Scenario Outline: S3 Controller - API to provide file <mode> access without <auth> in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header as without "<auth>"
    And Step "1. Provide folder access"
    And Set endpoint "<fileAccess>" method "<post_method>" param and with access payload to "provide" file access in "<mode>" mode
    Then Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"

    Examples:
      | fileAccess | post_method | status_403 | auth        | mode  |
      | fileAccess | post        | 403        | all         | read  |
      | fileAccess | post        | 403        | all         | write |
      | fileAccess | post        | 403        | username    | read  |
      | fileAccess | post        | 403        | username    | write |
      | fileAccess | post        | 403        | bridgeToken | read  |
      | fileAccess | post        | 403        | bridgeToken | write |

  Scenario Outline: S3 Controller - API to get recycle bin files
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Upload file"
    When Set endpoint "uploadFile" method "post" param and 1 file to upload
    And Step "2. Delete file"
    And Set endpoint "deleteFile" method "delete" and payload to delete uploaded files
    And Step "3. Get Recycle Bin Files"
    Then Set endpoint "<fileRecycleBin>" method "<get_method>" and param to get recycle bin files
    And Verify status code <status_200>
    And Verify response body message for "get recycle bin files" and operation message as "success"

    Examples:
      | fileRecycleBin | get_method | status_200 |
      | fileRecycleBin | get        | 200        |

  Scenario Outline: S3 Controller - API to get recycle bin files without <auth> in header
    Given User authenticate as "normal" user
    And Set the username and bridge token header as without "<auth>"
    And Step "1. Get Recycle Bin Files"
    Then Set endpoint "<fileRecycleBin>" method "<get_method>" and param to get recycle bin files
    Then Verify status code <status_403>
    And Verify response body message for "upload without <auth>" and operation message as "failure"

    Examples:
      | fileRecycleBin | get_method | status_403 | auth        |
      | fileRecycleBin | get        | 403        | all         |
      | fileRecycleBin | get        | 403        | username    |
      | fileRecycleBin | get        | 403        | bridgeToken |

  Scenario Outline: S3 Controller - API to delete file from recycle bin
    Given User authenticate as "normal" user
    And Set the username and bridge token header
    And Step "1. Get file from recycle bin"
    And Set endpoint "fileRecycleBin" method "get" and param to get recycle bin files
    And Step "2. Delete file from recycle bin"
    Then Set endpoint "<recycleBinDelete>" method "<delete_method>" and payload to delete file from recycle bin
    And Verify status code <status_200>
    And Verify response body message for "delete recycle bin file" and operation message as "success"

    Examples:
      | recycleBinDelete | delete_method | status_200 |
      | recycleBinDelete | delete        | 200        |
