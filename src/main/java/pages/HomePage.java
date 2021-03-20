package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import core.BasePage;
import exceptions.PageException;

public class HomePage extends BasePage {
	@FindBy(how = How.ID, using = "inpHeaderSearch")
	private WebElement searchField;
	@FindBy(how = How.ID, using = "btnHeaderSearch")
	private WebElement searchButton;
	
	public HomePage() {
		super();
	}
	
	public void openEcommerce() throws PageException {
		navigateTo("http://www.magazineluiza.com.br");
	}
	
	public void searchProduct(String id) throws PageException {
		search(id);
	}

	private void search(String productId) throws PageException {
		sendKeys(searchField, productId);
		click(searchButton);
	}
}
