package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import core.BasePage;

public class HomePage extends BasePage {
	@FindBy(how = How.ID, using = "inpHeaderSearch")
	private WebElement searchField;
	@FindBy(how = How.ID, using = "btnHeaderSearch")
	private WebElement searchButton;
	
	public HomePage() {
		super();
	}
	
	public void openEcommerce() {
		navigateTo("http://www.magazineluiza.com.br");
	}
	
	public void searchProduct(String id) {
		search(id);
	}

	private void search(String productId) {
		sendKeys(searchField, productId);
		click(searchButton);
	}
}
