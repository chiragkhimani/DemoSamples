package wallethub.automation.locator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FacebookHomePageLoc {

	@FindBy(xpath = "//h1[@data-click='bluebar_logo']/a")
	private WebElement homepageLogo;

	@FindBy(xpath = "//div[contains(@class,'clearfix')]//textarea")
	private WebElement statusUpdateTextArea;

	@FindBy(xpath = "//button[@type='submit' and contains(@data-testid,'post-button')]")
	private WebElement postBtn;

	@FindBy(xpath = "//a[@data-testid='post_chevron_button']")
	private WebElement deleteStatusDropDown;

	@FindBy(xpath = "//li[@data-feed-option-name='FeedDeleteOption']/a")
	private WebElement deleteStatusLink;

	@FindBy(xpath = "//button[@type='submit' and contains(text(),'Delete')]")
	private WebElement deleteConfirmBtn;

	@FindBy(xpath = "//p")
	private WebElement statusMsgText;

	public WebElement getStatusMsgText() {
		return statusMsgText;
	}

	public WebElement getDeleteStatusDropDown() {
		return deleteStatusDropDown;
	}

	public WebElement getDeleteStatusLink() {
		return deleteStatusLink;
	}

	public WebElement getDeleteConfirmBtn() {
		return deleteConfirmBtn;
	}

	public WebElement getPostBtn() {
		return postBtn;
	}

	public WebElement getHomepageLogo() {
		return homepageLogo;
	}

	public WebElement getStatusUpdateTextArea() {
		return statusUpdateTextArea;
	}

}
