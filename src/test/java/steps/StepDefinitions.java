package steps;

import core.DataLakeClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.HomePage;
import pages.ProductListPage;
import pages.ProductPage;
import pages.ShoppingCartPage;
import validators.BasicAssert;

public class StepDefinitions {
	HomePage homePage;
	ProductListPage productListPage;
	ProductPage productPage;
	ShoppingCartPage shoppingCartPage;

	@Given("I am at Magazine Luiza website")
	public void i_am_at_magazine_luiza_website() {
		homePage = new HomePage();
		homePage.openEcommerce();
	}

	@When("i try to buy a valid product")
	public void i_try_to_buy_a_valid_product() {
		homePage.searchProduct(DataLakeClient.productMap.get("valid").id);
		productListPage = new ProductListPage();
		productListPage.clickProduct();
		productPage = new ProductPage();
		productPage.addToCart();
	}

	@When("search for an unavailable product")
	public void search_for_an_unavailable_product() {
		homePage.searchProduct(DataLakeClient.productMap.get("unavailable").id);
	}

	@When("search for an invalid product")
	public void search_for_an_invalid_product() {
		homePage.searchProduct(DataLakeClient.productMap.get("invalid").id);
	}

	@Then("it should be added to cart")
	public void it_will_be_added_to_cart() {
		shoppingCartPage = new ShoppingCartPage();
		BasicAssert.containsItems(shoppingCartPage.checkCart(), DataLakeClient.productMap.get("valid").name);
	}

	@Then("it should be sold out")
	public void it_should_be_sold_out() {
		productListPage = new ProductListPage();
		BasicAssert.containsMessage(productListPage.checkSoldOut(), productListPage.soldOutMessage());
	}

	@Then("an error message should be shown")
	public void an_error_message_should_be_shown() {
		productListPage = new ProductListPage();
		BasicAssert.containsMessage(productListPage.checkErrorMessage(),
				productListPage.invalidMessage(DataLakeClient.productMap.get("invalid").id));
	}
}