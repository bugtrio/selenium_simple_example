package core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Classe base de todas as pages, abstrai as chamadas ao driver para garantir esperas e tratamentos de erros. 
 */
public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
	
	/**
	 * Construtor que cria/atribui o driver, o objeto de espera wait e inicia os elementos da pagina utilizando PageFactory.
	 */
	public BasePage() {
		driver = DriverManager.getDriver();
		wait = new WebDriverWait(driver, 30);
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, 30), this);
	}
	
	/**
	 * sendKeys: Recebe o elemento e o texto e faz a chamada ao driver.
	 * @param element
	 * @param text
	 */
	protected void sendKeys(WebElement element, String text) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Send keys:" + text);
			element.sendKeys(text);
		} catch (Exception e) {
			logger.error("Error Send Keys:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * click: Realiza o click ao elemento informado
	 * @param element
	 */
	protected void click(WebElement element) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(element));
			logger.info("Click: " + element.getTagName());
			element.click();
		} catch (Exception e) {
			logger.error("Error Click:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Retrona o texto do elemento desejado.
	 * @param element
	 * @return texto do elemento
	 */
	protected String getText(WebElement element) {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			logger.info("Get text: " + element.getTagName());
			return element.getText();
		} catch (Exception e) {
			logger.error("Error Click:" + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Abre a url informada.
	 * @param url
	 */
	protected void navigateTo(String url) {
		try {
			driver.get(url);
			logger.info("Navigate To: " + url);
		} catch (Exception e) {
			logger.error("Error Click:" + e.getMessage());
			throw e;
		}
	}
}
