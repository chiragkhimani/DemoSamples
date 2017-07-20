package wallethub.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import wallethub.automation.locator.WalletHubReviewPageLoc;
import wallethub.automation.utils.DriverUtil;
import wallethub.automation.utils.VerificationUtil;

public class WalletHubReviewPage extends WalletHubReviewPageLoc {
	WebDriver driver;
	static final String reviewText = "WalletHub is the first-ever website to offer free credit scores and full credit reports that are updated on a daily basis. But we consider that just an appetizer, as we’ve built the brain of an artificially intelligent financial advisor that will truly leave your wallet full. WalletHub’s brain performs three primary functions";

	public WalletHubReviewPage() {
		driver = DriverUtil.getWebDriver();
		PageFactory.initElements(driver, this);
	}

	public void verifyReviewPage() {
		VerificationUtil.verifyVisible(getReviewContentTextArea(), "Review page textarea");
	}

	public void selectOptionFromPolicyDropdown(String option) {
		getPolicyDropDown().click();
		String dropdownOptLoc = String.format(XPATH_POLICY_DROPDOWN_OPTION, option);
		driver.findElement(By.xpath(dropdownOptLoc)).click();
	}

	public void writeReviewText() {
		getReviewContentTextArea().sendKeys(reviewText);
	}

	public void clickSubmitBtn() {
		getSubmitBtn().click();
	}

	public void verifyReview() {
		VerificationUtil.verifyVisible(getReviewRating(), "Review Page Rating");
		VerificationUtil.verifyText(getReviewPageText(), reviewText, "Review page text");
	}

}
