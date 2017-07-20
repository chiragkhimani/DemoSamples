package wallethub.automation.steps;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import wallethub.automation.pages.WalletHubHomePage;
import wallethub.automation.pages.WalletHubLoginPage;
import wallethub.automation.pages.WalletHubReviewPage;

public class WalletHubSteps {

	@Then("^I login with username \"([^\"]*)\" and password \"([^\"]*)\" in wallethub app$")
	public void i_login_with_username_and_password_in_wallethub_app(String username, String password) {
		WalletHubLoginPage loginPage = new WalletHubLoginPage();
		loginPage.doLogin(username, password);
	}

	@When("^I navigate to test_insurance_company website$")
	public void i_navigate_to_test_insurance_company_website() {
		WalletHubHomePage homePage = new WalletHubHomePage();
		homePage.openTestInsuranceWebsite();
	}

	@Given("^I open wallethub website$")
	public void i_open_wallethub_website() {
		WalletHubHomePage homePage = new WalletHubHomePage();
		homePage.openWalletHubWebsite();
	}

	@Given("^I select \"([^\"]*)\" stars for review$")
	public void i_select_starts_for_review(String stars) {
		WalletHubHomePage homePage = new WalletHubHomePage();
		homePage.selectStars(stars);
	}

	@Then("^verify review page should be displayed$")
	public void verify_review_page_should_be_displayed() {
		WalletHubReviewPage reviewPage = new WalletHubReviewPage();
		reviewPage.verifyReviewPage();
	}

	@When("^I select \"([^\"]*)\" from the policy dropdown$")
	public void i_select_from_the_policy_dropdown(String option) {
		WalletHubReviewPage reviewPage = new WalletHubReviewPage();
		reviewPage.selectOptionFromPolicyDropdown(option);
	}

	@When("^I write text in review text box$")
	public void i_write_text_in_review_text_box() {
		WalletHubReviewPage reviewPage = new WalletHubReviewPage();
		reviewPage.writeReviewText();
	}

	@When("^I click on submit button$")
	public void i_click_on_submit_button() {
		WalletHubReviewPage reviewPage = new WalletHubReviewPage();
		reviewPage.clickSubmitBtn();
	}

	@Then("^verify review has been posted successfully$")
	public void verify_review_has_been_posted_successfully() {
		WalletHubReviewPage reviewPage = new WalletHubReviewPage();
		reviewPage.verifyReview();
	}

}
