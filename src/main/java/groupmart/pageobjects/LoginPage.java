package groupmart.pageobjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import groupmart.AbstractComponents.AbstractComponent;

public class LoginPage extends AbstractComponent {

	WebDriver driver;
	String url = "https://rahulshettyacademy.com/client";// added in env.json. could be removed
	By incorrectLoginErrorText = By.className("toast-message");
	By emailInvalidityText = By.xpath("//div[contains(text(),'Enter Valid Email')]");
	By emptyEmailError = By.xpath("//div[contains(text(),'Email is required')]");
	By emptyPasswordError = By.xpath("//div[contains(text(),'Password is required')]");

	public LoginPage(WebDriver driver) throws IOException {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);

	}

	// introducing PageFactory page design
	@FindBy(id = "userEmail")
	WebElement emailField;

	@FindBy(id = "userPassword")
	WebElement passwordField;

	@FindBy(id = "login")
	WebElement submit;

	public ProductCatalogue appLogin(String email, String password) throws IOException {
		emailField.sendKeys(email);
		passwordField.sendKeys(password);
		submit.click();
		return new ProductCatalogue(driver);
	}

	public void goTo() {
		driver.get(envJson.path("loginPage").path("url").asText());

	}

	public String getInvalidLoginError() {
		waitForAnElementToAppear(incorrectLoginErrorText, 7);
		String errorText = driver.findElement(incorrectLoginErrorText).getText().trim();
		return errorText;
	}

	public String getEmailInvalidityText() {
		waitForAnElementToAppear(emailInvalidityText, 7);
		String invalidEmailText = driver.findElement(emailInvalidityText).getText().trim();
		return invalidEmailText;
	}

	public String getEmptyEmailError() {
		waitForAnElementToAppear(emptyEmailError, 7);
		String emptyEmailText = driver.findElement(emptyEmailError).getText().trim();
		return emptyEmailText;
	}

	public String getEmptyPasswordError() {
		waitForAnElementToAppear(emptyPasswordError, 7);
		String emptyPasswordText = driver.findElement(emptyPasswordError).getText().trim();
		return emptyPasswordText;
	}

	public String getLoginPageTitle() {
		return driver.getTitle();

	}

}
