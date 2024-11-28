package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public static WebDriver driver;
	public Properties prop;
	public Logger logger;

	@BeforeClass(groups = { "Regression", "Master", "Ddt" })
	@Parameters({ "OS", "browser" })
	public void setUp(String os, String br) throws IOException {

		FileReader file = new FileReader(".\\src\\test\\resources\\config.properties");
		prop = new Properties();
		prop.load(file);

		logger = LogManager.getLogger(this.getClass());

		switch (br.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			System.out.println("This browser is not supported");
			return;
		}

		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		logger.info("Launching application");
		driver.get(prop.getProperty("url"));

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}

	public String randomString() {
		return RandomStringUtils.randomAlphabetic(5);
	}

	public String randomNumber() {
		return RandomStringUtils.randomNumeric(10);
	}

	public String randomAlphaNumeric() {
		String str = RandomStringUtils.randomAlphabetic(3);
		String num = RandomStringUtils.randomNumeric(3);
		return (str + "_" + num);
	}

	public String captureScreenshot(String name) {

		// Get the current timestamp formatted as "yyyy.MM.dd.HH.mm.ss"
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		// Create a target file path in the "screenShots" directory with the given name
		// and timestamp
		File targetFile = new File(System.getProperty("user.dir") + "/screenShots/" + name + "_" + timeStamp + ".png");
		// Capture a screenshot and store it as a temporary file
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		// Rename the temporary screenshot file to the target file path
		src.renameTo(targetFile);
		// Return the path of the newly created screenshot file
		return targetFile.getPath();
	}

}
