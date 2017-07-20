package wallethub.automation.locator;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WalletHubLoginPageLoc {
	@FindBy(xpath = "//input[@ng-model='fields.email']")
	private WebElement loginEmail;

	@FindBy(xpath = "//input[@ng-model='fields.password']")
	private WebElement loginPassword;

	@FindBy(xpath = "//button[@class='btn blue touch-element-cl']")
	private WebElement loginBtn;

	public WebElement getLoginEmail() {
		return loginEmail;
	}

	public WebElement getLoginPassword() {
		return loginPassword;
	}

	public WebElement getLoginBtn() {
		return loginBtn;
	}
}
