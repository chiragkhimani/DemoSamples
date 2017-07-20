package wallethub.automation.locator;

/**
 * This class contains locators / WebElements of facebook login page
 */
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FacebookLoginPageLoc {

	@FindBy(css = "#email")
	private WebElement loginEmail;

	@FindBy(css = "#pass")
	private WebElement loginPassword;

	@FindBy(css = "#loginbutton")
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
