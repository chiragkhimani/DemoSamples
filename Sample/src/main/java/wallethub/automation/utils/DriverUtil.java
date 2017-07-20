package wallethub.automation.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverUtil {

	private static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

	static void setDriver(WebDriver driver) {
		DriverUtil.driver.set(driver);
	}

	public static WebDriver getWebDriver() {
		return driver.get();
	}

	public static String getScreenshot() {
		String name = getScreenshotName();
		name = name + ".png";
		File scrFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
		System.out.println("====>" + scrFile);

		try {
			File f = new File("Report\\" + name);
			System.err.println(f);
			FileUtils.copyFile(scrFile, f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return name;
	}

	public static String getScreenshotName() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar cal = Calendar.getInstance();
		return (dateFormat.format(cal.getTime()).toString());
	}

	public static void initDriver() {
		PropertyUtil.initProperties();
		String browser = PropertyUtil.getProperty("selenium.browser");

		if (browser.equalsIgnoreCase("firefox")) {
			setDriver(new FirefoxDriver());
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			setDriver(new ChromeDriver(options));
		} else {
			// Default browser is firefox
			setDriver(new FirefoxDriver());
		}
	}

	public static void quit() {
		driver.get().quit();
	}
}
