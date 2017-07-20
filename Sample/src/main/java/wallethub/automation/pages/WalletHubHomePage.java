package wallethub.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wallethub.automation.locator.WalletHubHomePageLoc;
import wallethub.automation.utils.DriverUtil;
import wallethub.automation.utils.PropertyUtil;
import wallethub.automation.utils.VerificationUtil;
import wallethub.automation.utils.WebElementUtil;

public class WalletHubHomePage extends WalletHubHomePageLoc {
	WebDriver driver;

	public WalletHubHomePage() {
		driver = DriverUtil.getWebDriver();
		PageFactory.initElements(driver, this);
	}

	public void openTestInsuranceWebsite() {
		driver.get(PropertyUtil.getProperty("test.ins.company.baseurl"));
		driver.manage().window().maximize();
	}

	public void openWalletHubWebsite() {
		driver.get(PropertyUtil.getProperty("wallethub.baseurl"));
		driver.manage().window().maximize();
	}

	public void selectStars(String stars) {
		WebElementUtil.moveMouseOnElement(getSelectRatingBtn());
		VerificationUtil.verifyVisible(getSelectRatingChoiceHolder(), "Rating choice holder");
		String ratingLoc = String.format(XPATH_RATING_STARTS, stars);
		driver.findElement(By.xpath(ratingLoc)).click();
	}
}
