package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import core.BasePage;

public class ShoppingCartPage extends BasePage {
	@FindBy(how = How.CSS, using = "a[class='BasketItemProduct-info-title']")
	private WebElement cartDetailText;
	
	public ShoppingCartPage() {
		super();
	}
	
	public String checkCart() {
		return getText(cartDetailText);
	}
}
