package wallethub.automation.locator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WalletHubHomePageLoc {
	public static final String XPATH_RATING_STARTS = "//div[@class='wh-rating-choices-holder']/a[text()='%s']";

	@FindBy(xpath = "//span[contains(@class,'rating_4_5')]")
	private WebElement selectRatingBtn;

	@FindBy(xpath = "//div[@class='wh-rating-choices-holder']")
	private WebElement selectRatingChoiceHolder;

	public WebElement getSelectRatingBtn() {
		return selectRatingBtn;
	}

	public WebElement getSelectRatingChoiceHolder() {
		return selectRatingChoiceHolder;
	}

}
