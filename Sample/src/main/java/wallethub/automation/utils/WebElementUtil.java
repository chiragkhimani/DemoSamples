package wallethub.automation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementUtil {
	static WebDriver driver = DriverUtil.getWebDriver();

	public static boolean isDisplayed(WebElement ele) {
		try {
			if (ele.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public static void moveMouseOnElement(WebElement elel) {
		Actions builder = new Actions(driver);
		builder.moveToElement(elel).build().perform();
	}

	public static void clickElementUsingJs(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public static void waitForElement(WebElement ele) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 20000);
			wait.until(ExpectedConditions.visibilityOf(ele));
		} catch (Exception e) {

		}
	}

	public static void waitForElementNotVisible(WebElement ele) {
		int counter = 0;
		try {
			while (ele.isDisplayed() && counter < 20) {
				counter++;
				Thread.sleep(1000);
			}
		} catch (Exception e) {
		}
	}
}
