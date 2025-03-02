Feature: End to End simulation test

Scenario: As a user I can add new data
    Given A list of products are available
    When I add new products to etalase
    Then The product is available

Scenario Outline: As a user I can add new data with some data 
    Given A list of products are available
    When I add new "<payload>" to etalase
    Then The product is available
Examples:
    |payload    |
    |addItem    |
    |addItem2   |

Scenario Outline: As a user I can update data
    Given A list of item are available
    When I add new "<payload>" to etalase
    And The product is available
    Then I can update item "<update>"

    Examples:
    |payload    | update        | 
    |addItem    | updateItem    |
    |addItem2   | updateItem2   |
