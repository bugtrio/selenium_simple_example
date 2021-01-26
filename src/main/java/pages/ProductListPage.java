package pages;

import java.text.MessageFormat;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import core.BasePage;

public class ProductListPage extends BasePage {
	
	private final static String SOLD_OUT_MSG = "não disponível";
	private final static String INVALID_MSG = "Sua busca por {0} não encontrou resultado algum";
	
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
		return SOLD_OUT_MSG;
	}
	
	public String invalidMessage(String productId) {
		return MessageFormat.format(INVALID_MSG, productId);
	}
}
