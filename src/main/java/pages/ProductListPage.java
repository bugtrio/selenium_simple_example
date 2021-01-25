package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import core.BasePage;

public class ProductListPage extends BasePage {
	@FindBy(how = How.CSS, using = "li[id^='product_'] a[class='product-li']")
	private WebElement validProductButton;
	@FindBy(how = How.CSS, using = "span[class='product-soldout']")
	private WebElement productSoldOut;
	@FindBy(how = How.CSS, using = "div[class='header-not-found']")
	private WebElement productNotFoundText;
	
	public ProductListPage() {
		super();
	}
	
	public void clickProduct() {
		click(validProductButton);
	}
	
	public String checkSoldOut() {
		return getText(productSoldOut);
	}
	
	public String checkErrorMessage() {
		return getText(productNotFoundText);
	}
	
	public String soldOutMessage() {
		return "não disponível";
	}
	
	public String invalidMessage(String productId) {
		return "Sua busca por " + productId + " não encontrou resultado algum";
	}
}
