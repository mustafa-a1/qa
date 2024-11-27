package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class LoginDDT extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void validate_loginDDT(String email, String password, String dataType) {

		logger.info("***** Starting Login DDT *****");
		logger.debug("capturing application debug logs.....");

		try {
			// HomePage Object
			HomePage homePage = new HomePage(driver);
			homePage.clickOnMyAccount();
			homePage.clickOnLogin();

			// Login Object
			LoginPage loginPage = new LoginPage(driver);
			loginPage.setEmail(email);
			loginPage.setPassword(password);
			loginPage.clickLogin();

			// My Account Page object
			MyAccountPage myAcctPage = new MyAccountPage(driver);
			boolean targetPage = myAcctPage.isMyAccountPageExists();

			if (dataType.equalsIgnoreCase("valid")) {
				if (targetPage == true) {
					myAcctPage.clickOnLogout();
					Assert.assertTrue(true);
				} else {
					Assert.assertTrue(false);
				}
			}

			if (dataType.equalsIgnoreCase("Invalid")) {
				if (targetPage == true) {
					myAcctPage.clickOnLogout();
					Assert.fail();
				} else {
					Assert.assertTrue(true);
				}
			}
		} catch (Exception e) {
			Assert.fail("An exception occured: " + e.getMessage());
		} finally {
			logger.info("***** Finished Login DDT *****");
		}

	}

}
