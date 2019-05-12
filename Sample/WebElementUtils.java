package com.webautomation.utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementUtils {
	ConfigUtil configUtil = new ConfigUtil();
	long timeout = Long.parseLong(configUtil.getProperty("selenium.timeout"));
	WebDriverWait wait = new WebDriverWait(WebDriverUtils.getDriver(), timeout);

	public void waitForElementPresent(WebElement element) {
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementClickable(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public boolean isDisplayed(WebElement element) {
		try {
			element.isDisplayed();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public void safeClick(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			jsClick(element);
		}
	}

	public void jsClick(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverUtils.getDriver();
		executor.executeScript("argument[0].click()", element);
	}

	public void jsScrollDown() {
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverUtils.getDriver();
		executor.executeScript("window.scrollBy(0,250)", "");
	}

	public void jsScrollUp() {
		JavascriptExecutor executor = (JavascriptExecutor) WebDriverUtils.getDriver();
		executor.executeScript("window.scrollBy(0,-250)", "");
	}
}
