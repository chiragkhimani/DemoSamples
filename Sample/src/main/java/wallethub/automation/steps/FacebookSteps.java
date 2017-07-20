package wallethub.automation.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import wallethub.automation.pages.FacebookHomePage;
import wallethub.automation.pages.FacebookLoginPage;

public class FacebookSteps {
	String statusMsg;

	@Given("^I open facebook website$")
	public void i_open_facebook_website() {
		FacebookLoginPage loginPage = new FacebookLoginPage();
		loginPage.openFacebookWebsite();
	}

	@When("^I login with username \"([^\"]*)\" and password \"([^\"]*)\"$")
	public void i_login_with_username_and_password(String username, String password) {
		FacebookLoginPage loginPage = new FacebookLoginPage();
		loginPage.doLogin(username, password);
	}

	@Then("^verify homepage should be dispalyed$")
	public void verify_homepage_should_be_dispalyed() {
		FacebookHomePage homePage = new FacebookHomePage();
		homePage.verifyHomePage();
	}

	@When("^I post status messsae \"([^\"]*)\"$")
	public void i_post_status_messsae(String msg) {
		statusMsg = msg;
		FacebookHomePage homePage = new FacebookHomePage();
		homePage.postStatus(msg);
	}

	@Then("^verify status message should be updated succesfully$")
	public void verify_status_message_should_be_updated_succesfully() {
		FacebookHomePage homePage = new FacebookHomePage();
		homePage.verifyStatus(statusMsg);
	}
}
