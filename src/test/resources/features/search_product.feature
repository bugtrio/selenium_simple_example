Feature: Search Product
  Search a product in ecommerce

Background:
	Given I am at Magazine Luiza website

@Smoke @Regression
Scenario: a valid product
    When i try to buy a valid product
    Then it should be added to cart
 
 @Regression
 Scenario: an unavailable product
    When search for an unavailable product
    Then it should be sold out
 
 @Regression   
 Scenario: an invalid product
    When search for an invalid product
    Then an error message should be shown