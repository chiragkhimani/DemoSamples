package wallethub.automation.locator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WalletHubReviewPageLoc {
	public static final String XPATH_POLICY_DROPDOWN_OPTION = "//ul[@class='drop-el']/li/a[text()='Health']";

	@FindBy(xpath = "//div[@class='dropdown-title']")
	private WebElement policyDropDown;

	@FindBy(css = "#review-content")
	private WebElement reviewContentTextArea;

	@FindBy(css = ".btn.blue")
	private WebElement submitBtn;

	@FindBy(css = ".content.small>p")
	private WebElement reviewPageText;

	@FindBy(css = "#reviewform>div.rating.small")
	private WebElement reviewRating;

	public WebElement getPolicyDropDown() {
		return policyDropDown;
	}

	public WebElement getReviewContentTextArea() {
		return reviewContentTextArea;
	}

	public WebElement getSubmitBtn() {
		return submitBtn;
	}

	public WebElement getReviewPageText() {
		return reviewPageText;
	}

	public WebElement getReviewRating() {
		return reviewRating;
	}

}
