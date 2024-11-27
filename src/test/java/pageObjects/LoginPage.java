package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

	// Constructor
	public LoginPage(WebDriver driver) {
		super(driver);
	}

	// Locators
	@FindBy(xpath = "//input[@name='email']")
	WebElement txtEmail;
	@FindBy(xpath = "//input[@name='password']")
	WebElement txtPassword;
	@FindBy(xpath = "//input[@value='Login']")
	WebElement btnLogin;

	// Action Methods
	public void setEmail(String email) {
		txtEmail.sendKeys(email);
	}

	public void setPassword(String pwd) {
		txtPassword.sendKeys(pwd);
	}

	public void clickLogin() {
		btnLogin.click();
	}

}