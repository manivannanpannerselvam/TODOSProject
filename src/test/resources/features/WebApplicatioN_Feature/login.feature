@TestWeb
Feature: Verify todos functionality


  Scenario Outline:: Verify the clear Completed "<todos>" items
    Given I Launch the Application "${app.url}"
    And   I Enter the Name "<todos>" in todos screen
    And   Verify the Name "<todosList>" in todos screen
    And I click the submit button in todos screen
    And I click the "<buttons>" in todos screen


    Examples:
      | todos | todosList | buttons         |
      | Test  | Test      | Clear completed |
      | 123   | 123       | Clear completed |
      | Login | Login     | Clear completed |



  Scenario Outline:: Verify the todos Active "<todos>" items
    Given I Launch the Application "${app.url}"
    And   I Enter the Name "<todos>" in todos screen
    And   Verify the Name "<todosList>" in todos screen
    And   I click the "<buttons>" in todoslist screen
    And   Verify the Name "<todosList>" in todos screen

    Examples:
      | todos | todosList | buttons |
      | Test  | Test      | Active  |
      | 123   | 123       | Active  |
      | Login | Login     | Active  |


  Scenario Outline:: Verify the Todos Completed "<todos>" Items
    Given I Launch the Application "${app.url}"
    And   I Enter the Name "<todos>" in todos screen
    And   Verify the Name "<todosList>" in todos screen
    And I click the submit button in todos screen
    And   I click the "<buttons>" in todoslist screen
    And   Verify the Name "<todosList>" in todos screen


    Examples:
      | todos | todosList | buttons   |
      | Test  | Test      | Completed |
      | 123   | 123       | Completed |
      | Login | Login     | Completed |


  Scenario Outline:: Verify the Todos All "<todos>" Items
    Given I Launch the Application "${app.url}"
    And   I Enter the Name "<todos>" in todos screen
    And   Verify the Name "<todosList>" in todos screen
    And I click the submit button in todos screen
    And   I click the "<buttons>" in todoslist screen
    And   Verify the Name "<todosList>" in todos screen


    Examples:
      | todos | todosList | buttons |
      | Test  | Test      | All     |
      | 123   | 123       | All     |
      | Login | Login     | All     |


  Scenario Outline:: The completed items list should be empty if there are no active "<todos>" items in the to-dos list.
    Given I Launch the Application "${app.url}"
    And   I Enter the Name "<todos>" in todos screen
    And   Verify the Name "<todosList>" in todos screen
    And   I click the "<buttons>" in todoslist screen

    Examples:
      | todos | todosList | buttons   |
      | Test  | Test      | Completed |
      | 123   | 123       | Completed |
      | Login | Login     | Completed |




