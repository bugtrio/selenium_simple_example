package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import core.BasePage;

public class ProductPage extends BasePage {
	@FindBy(how = How.CSS, using = "p[class='text-button-cookie']")
	private WebElement cookieButton;
	@FindBy(how = How.CSS, using = "button[class*='js-main-add-cart-button'] span[class^='button']")
	private WebElement addCartButton;
	
	public ProductPage() {
		super();
	}
	
	public void addToCart() {
		click(cookieButton); //Closing cookie banner before click to avoid it breaks the next click
		click(addCartButton);
	}

}
