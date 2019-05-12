package com.freddiemac.automation.utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.freddiemac.automation.constant.Constant;

import cucumber.api.DataTable;
import cucumber.api.Scenario;

/**
 * This class contains all common functions to interact with WebElements
 *
 */
public class CommonFunction {

	public static WebDriver driver = null;
	public static WebDriverWait Wait_For_Object;
	public static Logger log = null;
	public static XmlReader xmlReader = null;
	public static Properties ConfigProp;
	public static Scenario scenario;
	// public static String dbjdbcClassName =
	// xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
	// Constant.ENVIRONMENT_NAME, "dbjdbcclassname");
	// public static String dburl =
	// xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
	// Constant.ENVIRONMENT_NAME,
	// "dburl");
	// public static String dbusername =
	// xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
	// Constant.ENVIRONMENT_NAME,
	// "dbusername");
	// public static String dbpassword =
	// xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
	// Constant.ENVIRONMENT_NAME,
	// "dbpassword");
	// public static String dbSchema =
	// xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
	// Constant.ENVIRONMENT_NAME,
	// "dbSchema");

	public static Connection con = null;
	public static Statement stmt = null;
	public static PreparedStatement pstmt = null;
	public static ResultSet rs = null;
	public static ResultSetMetaData rsmd = null;
	public static Actions elementAction;

	// dbf, db, doc variable used for XML reader
	public static DocumentBuilderFactory dbf;
	public static DocumentBuilder db;
	public static Document doc;

	/*
	 ************ Getter and Setter functions*************
	 */
	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		CommonFunction.driver = driver;
	}

	public static void main(String[] args) {
		CommonFunction common = new CommonFunction();
		common.openBroswer("IE");
	}

	public CommonFunction() {
		// QuickSanity - Comment property file initialization and
		// ConfigProp.load
		// String propertyFile = Constant.GALLAGHER_DB_PROPERTYFILE +
		// Constant.SELLING_ENVIRONMENT + ".properties";
		// Initializing Logging object
		log = Logger.getLogger(CommonFunction.class);

		// Initializing Properties
		ConfigProp = new Properties();
		try {
			// ConfigProp.load(new FileInputStream(propertyFile));
		} catch (Exception e) {
			log.error("Error:: Property file not loaded | method:: CommonFunction | class:: CommonFunction with error "
					+ e.getMessage());
			Assert.fail("Error:: Property file not loaded | method:: CommonFunction | class:: CommonFunction");
		}
	}

	/**
	 * @openBrowser function will take browser parameter and will open the
	 *              browser as per the parameter passed
	 **/
	public void openBroswer(String browser) {
		try {
			if (browser.equalsIgnoreCase("mozilla") || browser.equalsIgnoreCase("firefox")) {

				log.info("Opening browser " + browser + " : started ");
				setDriver(new FirefoxDriver());
				log.info("Opening browser " + browser + " : completed ");

			} else if (browser.equalsIgnoreCase("IE")) {

				log.info("Opening browser " + browser + " : started ");
				if (Constant.IEDRIVER_NAME.toLowerCase().equalsIgnoreCase("Jenkins".toLowerCase())) {
					System.setProperty("webdriver.ie.driver", Constant.IE_DRIVER_PATH);
				} else if (Constant.IEDRIVER_NAME.toLowerCase().equalsIgnoreCase("Local".toLowerCase())) {
					System.setProperty("webdriver.ie.driver", Constant.IE_DRIVER_PATH_local);
				}
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				setDriver(new InternetExplorerDriver(ieCapabilities));
				log.info("Opening browser " + browser + " : completed ");
			}

			else if (browser.equalsIgnoreCase("chrome")) {

				log.info("Opening browser " + browser + " : started ");
				System.setProperty("webdriver.chrome.driver", Constant.CHROME_DRIVER_PATH);
				setDriver(new ChromeDriver());
				log.info("Opening browser " + browser + " : completed ");

			} else if (browser.equalsIgnoreCase("grid")) {
				DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
				ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						true);
				ieCapabilities.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				ieCapabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
				ieCapabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
				ieCapabilities.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
				ieCapabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, true);
				ieCapabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				ieCapabilities.setPlatform(Platform.WINDOWS);
				InetAddress localhost = InetAddress.getLocalHost();

				String Node = "http://" + localhost.getHostAddress() + ":4444/wd/hub";
				try {
					setDriver(new RemoteWebDriver(new URL(Node), ieCapabilities));
				} catch (MalformedURLException e1) {

					e1.printStackTrace();
				}

			}
			// Delete all cookies
			getDriver().manage().deleteAllCookies();

			// Maximize the browser
			getDriver().manage().window().maximize();
			log.info("Browser maximized");

			// Sets zoom to 100%
			// getDriver().findElement(By.tagName("html")).sendKeys(Keys.chord(Keys.CONTROL,
			// "0"));

			// Implicit wait applied on the driver
			getDriver().manage().timeouts().implicitlyWait(Constant.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);
			log.info("Implicit wait time added for " + Constant.IMPLICIT_WAIT_TIME + " seconds");

			getDriver().manage().timeouts().pageLoadTimeout(Constant.PAGELOAD_TIME, TimeUnit.SECONDS);

		} catch (Exception e) {
			log.error("Failed to open the browser || class:: CommonFunction || method:: openBroswer with error "
					+ e.getMessage());
			Assert.fail("Failed to open the browser || class:: CommonFunction || method:: openBroswer");
		}

	}

	public String getPath() throws Exception {
		String projectPath = null;
		File proectDir = null;
		try {

			proectDir = new File(".");
			projectPath = proectDir.getCanonicalPath();
		} catch (Exception e) {
			log.error("Failed to get the path || class:: CommonFunction || method:: getPath with error "
					+ e.getMessage());
			Assert.fail("Failed to get the path  || class:: CommonFunction || method:: getPath");
		}
		return projectPath;
	}

	public String getEnvPath() throws Exception {
		return getPath() + "\\src\\test\\resources\\TestData\\Env\\";
	}

	public void launchFirefoxBrowser() {

		try {
			FirefoxProfile FProfile = new FirefoxProfile();
			FirefoxBinary FBinary = new FirefoxBinary();

			FProfile.setPreference("browser.download.folderList", 2);
			FProfile.setPreference("browser.download.manager.showWhenStarting", false);
			FProfile.setPreference("browser.download.dir", System.getProperty("user.dir") + "\\target\\pdffiles");
			FProfile.setPreference("browser.helperApps.neverAsk.openFile",
					"text/csv,application/x-msexcel,application/excel,application/x-excel,application/vnd.ms-excel,image/png,image/jpeg,text/html,text/plain,application/msword,application/xml,application/pdf");
			FProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
			FProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
			FProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			FProfile.setPreference("browser.download.manager.focusWhenStarting", false);
			FProfile.setPreference("browser.download.manager.useWindow", false);
			FProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
			FProfile.setPreference("browser.download.manager.closeWhenDone", false);

			// FProfile.setPreference("browser.privatebrowsing.autostart",
			// true);
			// FProfile.setPreference("network.proxy.http", "BProxy.fhlmc.com");
			// FProfile.setPreference("network.proxy.http_port", "8080");
			FBinary.setTimeout((TimeUnit.SECONDS.toMillis(180)));
			setDriver(new FirefoxDriver(FBinary, FProfile));
			getDriver().manage().deleteAllCookies();
			getDriver().manage().window().maximize();
			Wait_For_Object = new WebDriverWait(driver, 120);
		} catch (Exception e) {
			log.error(
					"Failed to open firfox browser || class:: CommonFunction || method:: launchFirefoxBrowser with error "
							+ e.getMessage());
			Assert.fail("Failed to open firfox browser || class:: CommonFunction || method:: launchFirefoxBrowser");
		}
	}

	/**
	 * @closeBrowser function will close the current instance of the browser
	 **/
	public void closeBrowser() {
		try {
			log.info("Closing the Browser");
			getDriver().close();
			wait(2000);
			log.info("Browser Closed");
		} catch (Exception e) {
			log.error("Failed to close the browser || class:: CommonFunction || method:: closeBrowser with error "
					+ e.getMessage());
			// Assert.fail("Failed to close the browser || class::
			// CommonFunction || method:: closeBrowser");
		}
	}

	/**
	 * @closeBrowser will exit/quit all the browser instance
	 **/

	public void quitBrowser() {
		try {
			log.info("Quit browswer - started");
			getDriver().quit();
			wait(2000);
			log.info("Quit browser - completed");
		} catch (Exception e) {
			log.error("Failed to Quit the browser || class:: CommonFunction || method:: quitBrowser with error "
					+ e.getMessage());
			Assert.fail("Failed to Quit the browser || class:: CommonFunction || method:: quitBrowser");
		}
	}

	/**
	 * @Author: Samir Shukla
	 * @navigate function will take the url from the property file and will
	 *           launch the url in the browser
	 **/
	public void navigateToUrl(String application, String url) {

		try {
			// switch (application) {
			// case Constant.SELLING_APPLICATION:
			// log.info("Navigating to " + url + "url:: started");
			// getDriver().navigate().to(url);
			// log.info("Navigating to " + url + " :: completed");
			// break;
			// case Constant.GALLAGHER_APPLICATION:
			// log.info("Navigating to " + xmlReader.getXmlDataValue("pma",
			// Constant.ENVIRONMENT_NAME, "gallagherurl")
			// + " :: started");
			// getDriver().navigate().to(xmlReader.getXmlDataValue("pma",
			// Constant.ENVIRONMENT_NAME, "gallagherurl"));
			// log.info("Navigating to " + xmlReader.getXmlDataValue("pma",
			// Constant.ENVIRONMENT_NAME, "gallagherurl")
			// + " :: completed");
			// break;
			// case Constant.PMA_APPLICATION:
			// default:
			// log.info("Navigating to "
			// + xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
			// Constant.ENVIRONMENT_NAME, "url")
			// + " :: started");
			// getDriver().navigate()
			// .to(xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
			// Constant.ENVIRONMENT_NAME, "url"));
			// log.info("Navigating to "
			// + xmlReader.getXmlDataValue(Constant.APPLICATION_NAME,
			// Constant.ENVIRONMENT_NAME, "url")
			// + " :: completed");
			// wait(Constant.WAITTIME_THREE_SEC);
			// break;
			// }
		} catch (Exception e) {
			log.error("Failed to navigate to URL || class:: CommonFunction || method:: navigateToUrl with error "
					+ e.getMessage());
			Assert.fail("Failed to navigate to URL || class:: CommonFunction || method:: navigateToUrl");
		}
	}

	/**
	 * @Author: Samir Shukla
	 * @navigate function will make the browser go to the forward/next page
	 **/
	public void navigateForward() {
		try {
			log.info("Navigating to forward page :: started");
			getDriver().navigate().forward();
			log.info("Navigating to forward page :: completed");
		} catch (Exception e) {
			log.error(
					"Failed to navigate forward to URL || class:: CommonFunction || method:: navigateForward with error "
							+ e.getMessage());
			Assert.fail("Failed to navigate forward to URL || class:: CommonFunction || method:: navigateForward");
		}
	}

	/**
	 * @Author: Samir Shukla
	 * @navigate function will make the browser go to the back/previous page
	 **/
	public void navigateBack() {
		try {
			log.info("Navigating to back page :: started");
			getDriver().navigate().forward();
			log.info("Navigating to back page :: completed");
		} catch (Exception e) {
			log.error("Failed to navigate back to URL || class:: CommonFunction || method:: navigateBack with error "
					+ e.getMessage());
			Assert.fail("Failed to navigate back to URL || class:: CommonFunction || method:: navigateBack");
		}
	}

	/**
	 * @Author: Samir Shukla
	 * @param by
	 *            - accessing object using By operator
	 * @return true: when element is present
	 * @return false: when element is not present
	 * @isElementPresent function will return boolean true if the element is
	 *                   present or the function will return false if the
	 *                   element is not present
	 **/

	public boolean isElementPresent(By by) {
		boolean blnFlag = true;
		try {
			int num = getDriver().findElements(by).size();

			if (num == 0) {
				blnFlag = false;
			} else {
				blnFlag = true;
			}

		} catch (NoSuchElementException e) {
			log.error("Element is not present with error " + e.getMessage());
			Assert.fail("Element is not present || class:: CommonFunction || method:: isElementPresent");
		}
		return blnFlag;

	}

	/**
	 * @Author: Samir Shukla
	 * @param by
	 *            - accessing object using By operator
	 * @return true: when element is displayed
	 * @return false: when element is not displayed
	 * @isElementDisplayed function will return boolean true if the element is
	 *                     displayed or the function will return false if the
	 *                     element is not displayed
	 **/

	public boolean isElementDisplayed(By by) {
		boolean blnFlag = true;
		if (getDriver().findElement(by).isDisplayed()) {
			blnFlag = true;
		} else {

			blnFlag = false;
		}
		return blnFlag;
	}

	/**
	 * @Author: Samir Shukla
	 * 
	 * @param by
	 * @return
	 */
	public boolean isElementNotPresent(By by) {

		int size = getDriver().findElements(by).size();

		if (size == 0) {
			return false;
		} else {
			return true;
		}

	}

	public boolean isElementNotPresentBySize(By by) {

		int size = getDriver().findElements(by).size();

		if (size == 0) {
			return true;
		} else {
			return false;
		}

	}

	// without assert for optional fields
	public boolean isElementDisplayedChk(By by) {
		boolean blnFlag = true;
		try {
			if (getDriver().findElements(by).size() > 0) {
				blnFlag = true;
			} else {

				blnFlag = false;
			}
		} catch (NoSuchElementException e) {
			log.error("Element is not displayed with error " + e.getMessage());
			// Assert.fail("Element is not displayed || class:: CommonFunction
			// || method:: isElementDisplayed");
		}
		return blnFlag;
	}

	/**
	 * @Author: Samir Shukla
	 * @UpdatedBy: @param by - accessing object using By operator
	 * @return true: when element is selected
	 * @return false: when element is not selected
	 * @isElementSelected function will return boolean true if the element is
	 *                    selected or the function will return false if the
	 *                    element is not selected
	 **/

	public boolean isElementSelected(By by) {
		boolean blnFlag = true;
		try {
			if (getDriver().findElement(by).isSelected()) {
				blnFlag = true;
			} else {
				blnFlag = false;
			}
		} catch (NoSuchElementException e) {
			log.error("Element is not selected with error " + e.getMessage());
			Assert.fail("Element is not selected || class:: CommonFunction || method:: isElementDisplayed");
		}
		return blnFlag;
	}

	/**
	 * @Author: Samir Shukla
	 * @return true: when element is Enabled
	 * @return false: when element is not Enabled
	 * @isElementEnabled function will return boolean true if the element is
	 *                   Enabled or the function will return false if the
	 *                   element is not Enabled
	 **/

	public boolean isElementEnabled(By by) {
		boolean blnFlag = true;
		try {
			if (getDriver().findElement(by).isEnabled()) {
				blnFlag = true;
			} else {
				blnFlag = false;
			}
		} catch (NoSuchElementException e) {
			log.error("Element is not enabled with error " + e.getMessage());
			Assert.fail("Element is not enabled || class:: CommonFunction || method:: isElementDisplayed");
		}
		return blnFlag;
	}

	/**
	 * @Author: Samir Shukla
	 * @param milliSeconds:
	 *            wait time specified in milliseconds
	 * @wait function will wait for the given milliseconds
	 **/
	public void wait(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (Exception e) {

			log.error("Class: CommonFunction | Method: wait | Messaege: Error when waiting");
			log.error(e.getMessage());

		}
	}

	/**
	 * @Author: Samir Shukla
	 * @param milliSeconds:
	 *            wait time specified in milliseconds
	 * @waitForElement function will wait for the element to be present in the
	 *                 page for the given milliseconds
	 **/

	public boolean waitForVisibilityOfElementLocated(By by) {
		boolean blnFlag = true;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Constant.WEBDRIVERWAIT);
			WebElement waitElement = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			if (waitElement != null) {
				blnFlag = true;
			}

		} catch (TimeoutException exception) {
			log.error(
					"Class: CommonFunction | method:waitForElement | message: Element is not present after waiting for "
							+ Constant.WEBDRIVERWAIT + " seconds with error " + exception.getMessage());
			blnFlag = false;

		}
		return blnFlag;
	}

	/**
	 * @Author: Samir Shukla
	 * @param By
	 *            by: Element for which you want to wait for it to get Clickable
	 * @waitForElement function will wait for the element to be clickable in the
	 *                 page for the given milliseconds
	 **/

	public boolean waitForElementToBeClickable(By by) {
		boolean blnFlag = true;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), Constant.WEBDRIVERWAIT);
			WebElement waitElement = wait.until(ExpectedConditions.elementToBeClickable(by));

			if (waitElement != null) {
				return true;
			}
		} catch (TimeoutException exception) {
			log.error(
					"Class: CommonFunction | method:waitForElementToBeClickable | message: Element is not clickable after waiting for "
							+ Constant.WEBDRIVERWAIT + " seconds with error " + exception.getMessage());
			blnFlag = false;

		}
		return blnFlag;
	}

	/**
	 * @Author: Samir Shukla
	 * @param milliSeconds:
	 *            wait time specified in milliseconds
	 * @waitForPresenceOfElementLocated function will wait for the elements
	 *                                  presence in the page for the given
	 *                                  milliseconds
	 **/

	public boolean waitForPresenceOfElementLocated(By by) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Constant.WEBDRIVERWAIT);
		WebElement waitElement = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		if (waitElement != null) {
			return true;
		} else {
			log.error(
					"Class: CommonFunction | method:waitForElement | message: Element is not present after waiting for "
							+ Constant.WEBDRIVERWAIT + " seconds");
			return false;

		}
	}

	/**
	 * @Author: Samir Shukla
	 * @param milliSeconds:
	 *            wait time specified in milliseconds
	 * @waitForElement function will wait for the title of the page is present
	 *                 for the given milliseconds
	 **/

	public boolean waitForTitleOfPage(String title) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Constant.WEBDRIVERWAIT);
		Boolean waitTitle = wait.until(ExpectedConditions.titleContains(title));

		if (!waitTitle) {
			log.error(
					"Class: CommonFunction | method:waitForElement | message: Element is not present after waiting for "
							+ Constant.WEBDRIVERWAIT + " seconds");
			return false;
		} else
			return true;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 16May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where text needs to be entered
	 * @param text:
	 *            text to be entered
	 * @sendKeys function enter text in the object
	 **/

	public void sendKeys(By by, String text) {
		highlightElement(by);
		getDriver().findElement(by).sendKeys(text);
		log.info("Text Entered:: " + text);
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 16May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where text needs to be entered
	 * @param text:
	 *            text to be entered
	 * @sendKeys function enter text in the object
	 **/

	public void sendKeys_Action(By by, String text) {
		Actions action = new Actions(getDriver());
		highlightElement(by);
		WebElement preview = getDriver().findElement(by);
		action.moveToElement(preview).click().sendKeys(text).build().perform();
		log.info("Text Entered:: " + text);
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where click action will be performed
	 * @click function click in the object
	 **/

	public void click(By by) {
		try {
			waitForElementToBeClickable(by);
			highlightElement(by);
			// click
			getDriver().findElement(by).click();
			wait(Constant.WAITTIME_TINY);
			log.info("Clicked successfully");

		} catch (Exception e) {
			log.info("Unable to click the link " + e);
		}

	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where click action will be performed from Javascript
	 *            executor clickByAction function click in the object
	 **/
	public void clickByAction(By by) {
		try {
			Actions action = new Actions(getDriver());
			waitForVisibilityOfElementLocated(by);
			waitForElementToBeClickable(by);
			highlightElement(by);
			WebElement preview = getDriver().findElement(by);
			action.moveToElement(preview).click(preview).build().perform();
			log.info("Clicked successfully");
		} catch (Exception e) {
			log.info("Unable to click the link " + e);
		}
	}

	/**
	 * @author Amit Pathak Method helps to press the control key down
	 */
	public void controlKeyDown() {
		try {
			Actions action = new Actions(getDriver());
			action.keyDown(Keys.CONTROL);
			log.info("Control Key pressed down");
		} catch (Exception e) {
			log.info("Unable to press the key" + e);
		}
	}

	/**
	 * @author Amit Pathak Method helps to press the control key up
	 */
	public void controlKeyUp() {
		try {
			Actions action = new Actions(getDriver());
			action.keyUp(Keys.CONTROL);
			log.info("Control Key released");
		} catch (Exception e) {
			log.info("Unable to release the key" + e);
		}
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where click action will be performed from Javascript
	 *            executor
	 * @clickJavascriptExecutor function click in the object
	 **/
	public void clickJavascriptExecutor(By by) {
		try {
			WebElement element = getDriver().findElement(by);
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("Clicked successfully");

		} catch (Exception e) {
			log.info("The error message is " + e);
		}
	}

	/**
	 * @Author: Chethan
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where click action will be performed from Javascript
	 *            executor
	 * @clickJavascriptExecutor function click in the object
	 **/
	public void clickJavascriptExecutor(WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();
			executor.executeScript("arguments[0].click();", element);
			log.info("Clicked successfully");
		} catch (Exception e) {
			log.info("The error message is " + e);
		}
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed
	 * @param value:
	 *            value to be selected
	 * @selectByValue function will select value from the dropdown
	 **/
	public void selectByValue(By by, String value) {

		try {
			Select select = new Select(getDriver().findElement(by));
			select.selectByValue(value);
			wait(Constant.WAITTIME_THREE_SEC);
			log.info("Selected value is:: " + value);
		} catch (Exception e) {
			log.info("Unable to select the value with error message  " + e);
		}

	}

	/*****
	 * @author Amit Pathak Selects the value from dropdown using part of the
	 *         string and compare the value with input
	 * @param by
	 * @param state
	 */
	public void getVisibleText(By by, String piVal) {

		String selectedPI = "";
		WebElement webel = getDriver().findElement(by);
		Select dropdown1 = new Select(webel);
		List<WebElement> drpList = dropdown1.getOptions();
		dropdown1.selectByIndex(1);

		try {
			Thread.sleep(1000);
			for (int i = 1; i < drpList.size(); i++) {
				dropdown1.selectByIndex(i);
				Thread.sleep(1000);
				WebElement visibleValue = dropdown1.getFirstSelectedOption();
				selectedPI = visibleValue.getText();
				if (selectedPI.contains(piVal)) {
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/***
	 * @author Amit Pathak
	 * @param by
	 * @param value
	 *            Select the dropdown value by index
	 */

	public void selectDropDownbyIndex(By by, String value) {
		try {
			WebElement webElement = getDriver().findElement(by);
			Select select = new Select(webElement);
			List<WebElement> options = select.getOptions();
			// System.out.println(options.size());
			for (int k = 1; k <= options.size(); k++) {
				boolean pI_Present = webElement.getText().contains(value);
				if (pI_Present) {
					System.out.println("success");
					select.selectByIndex(k);
					break;
				}
			}
		} catch (Exception e) {
			log.info("Unable to select the PI value with error message  " + e);
		}
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed
	 * @param visibleText:
	 *            text to be selected
	 * @selectByVisibleText function will select visible text from the dropdown
	 **/
	public void selectByVisibleText(By by, String visibleText) {
		try {
			Select select = new Select(getDriver().findElement(by));
			select.selectByVisibleText(visibleText);

			wait(Constant.WAITTIME_THREE_SEC);
			log.info("Selected visible text is:: " + visibleText);
		} catch (Exception e) {
			log.error(
					"Class:: CommonFunction || Method:: selectByVisibleText || Message:: Unable to select the visible text "
							+ visibleText);
			e.printStackTrace();
			Assert.fail(
					"Class:: CommonFunction || Method:: selectByVisibleText || Message:: Unable to select the visible text "
							+ visibleText);
		}
	}

	/**
	 * @Author: Amit
	 * @DateCreated: July2018
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed
	 * @param visibleText:
	 *            text to be selected
	 * @selectByVisibleText function will select text by value from the dropdown
	 **/

	public void selectBydrpDwnValue(By by, String visibleText) {
		try {
			Select select = new Select(getDriver().findElement(by));
			select.selectByValue(visibleText);

			wait(Constant.WAITTIME_THREE_SEC);
			log.info("Selected visible text is:: " + visibleText);
		} catch (Exception e) {
			log.info("Unable to select the visible text with error message  " + e);
		}
	}

	/**
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed *
	 **/
	public List<WebElement> getAllOptionsFromDropDown(By by) {
		List<WebElement> dropDownList = null;
		try {
			Select dropDown = new Select(getDriver().findElement(by));
			dropDownList = dropDown.getOptions();
		} catch (Exception e) {
			log.info("Unable to select the visible text with error message  " + e);
		}
		return dropDownList;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed
	 * @param index:
	 *            index to be selected
	 * @selectByIndex function will select item from the dropdown as per the
	 *                index
	 **/

	public void selectByIndex(By by, int index) {
		try {
			Select select = new Select(getDriver().findElement(by));
			select.selectByIndex(index);
			log.info("Selected index is:: " + index);
		} catch (Exception e) {
			log.info("Unable to select the value with error message  " + e);
		}
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 26Jun2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed
	 * @getDropdownSelectedOption function will get the all options
	 **/

	public List<WebElement> getDropdownAllOption(By by) {
		List<WebElement> allOptions = null;
		try {
			Select select = new Select(getDriver().findElement(by));
			allOptions = select.getOptions();
		} catch (Exception e) {
			log.info("Unable to return all the values with error message  " + e);
		}
		return allOptions;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 26Jun2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where select action will be performed
	 * @getDropdownSelectedOption function will get the selected options
	 **/

	public String getDropdownSelectedOption(By by) {
		Select select = null;
		try {
			select = new Select(getDriver().findElement(by));

		} catch (Exception e) {
			log.info("Unable to get selected options with error message  " + e);
		}
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where deselect action will be performed
	 * @param value:
	 *            value to be deselected
	 * @deselectByValue function will deselect value from the dropdown
	 **/

	public void deselectByValue(By by, String value) {
		try {
			Select deselect = new Select(getDriver().findElement(by));
			deselect.deselectByValue(value);
			log.info("Deselected value is:: " + value);
		} catch (Exception e) {
			log.info("Unable to de-select the value with error message  " + e);
		}

	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where deselect action will be performed
	 * @param visibleText:
	 *            text to be deselected
	 * @deselectByVisibleText function will deselect visible text from the
	 *                        dropdown
	 **/

	public void deselectByVisibleText(By by, String visibleText) {
		try {
			Select deselect = new Select(getDriver().findElement(by));
			deselect.deselectByVisibleText(visibleText);
			log.info("Deselected visible text is:: " + visibleText);
		} catch (Exception e) {
			log.info("Unable to de-select visible text with error message  " + e);
		}
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where deselect action will be performed
	 * @param index:
	 *            index to be deselected
	 * @deselectByIndex function will deselect item from the dropdown as per the
	 *                  index
	 **/

	public void deselectByIndex(By by, int index) {
		try {
			Select deselect = new Select(getDriver().findElement(by));
			deselect.deselectByIndex(index);
			log.info("Deselected index is:: " + index);
		} catch (Exception e) {
			log.info("Unable to de-select index text with error message  " + e);
		}
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object where deselect action will be performed
	 * @deselectAll function will deselect all the selected item from the
	 *              dropdown
	 **/

	public void deselectByIndex(By by) {
		try {
			Select deselect = new Select(getDriver().findElement(by));
			deselect.deselectAll();
			log.info("Deselected all the items");
		} catch (Exception e) {
			log.info("Unable to de-select all the items with error message  " + e);
		}
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object to read its text
	 * @return String: will return the text
	 * @getText function will return the text of the given object
	 **/
	public String getText(By by) {
		String text = null;
		try {
			highlightElement(by);
			text = getDriver().findElement(by).getText();
			log.info("Text is :: " + text);

		} catch (Exception e) {
			log.info("Unable to get the text with error message  " + e);
		}
		return text;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object to read its tagname
	 * @return String: will return the tagname
	 * @getTagName function will return the tagname of the given object
	 **/
	public String getTagName(By by) {
		String tagname = null;
		try {
			highlightElement(by);
			tagname = getDriver().findElement(by).getTagName();
			log.info("TagName is :: " + tagname);

		} catch (Exception e) {
			log.info("Unable to get the text with error message  " + e);
		}

		return tagname;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object to get the attribute
	 * @param value:
	 *            value of the attribute
	 * @return String: will return the value of the attribute
	 * @getAttribute function will return the value of the attribute of the
	 *               given object
	 **/

	public String getAttribute(By by, String value) {
		highlightElement(by);
		String attribute = getDriver().findElement(by).getAttribute(value);
		log.info("Attribute is :: " + attribute);
		return attribute;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 1July2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            object to get the attribute
	 * @return String: will return string
	 * @getTagname function will return string contaning the tagname of the
	 *             element
	 **/

	public String getTagname(By by) {
		return getDriver().findElement(by).getTagName();
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param datatable:
	 *            datatable from the feature file
	 * @return List<String>: will return the List<String>
	 * @getDataTableAsListOfString function will return the list of data table
	 *                             as string object
	 **/

	public List<String> getDataTableAsListOfString(DataTable datatable) {
		List<String> listOfString = datatable.asList(String.class);

		return listOfString;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 17May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param datatable:
	 *            datatable from the feature file
	 * @return List<String>: will return the List<String>
	 * @getDataTableAsListOfString function will return the list of data table
	 *                             as string object
	 **/

	// *****************VERIFICATION FUNCTIONS*******************************

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is present
	 **/
	public void verifyElementIsPresent(By by) {
		Assert.assertTrue("Element is not present ", isElementPresent(by));
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsDisplayed function will Assert if element is displayed
	 **/
	public void verifyElementIsDisplayed(By by) {
		Assert.assertTrue("Element is displayed ", isElementDisplayed(by));

	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is enabled
	 **/
	public void verifyElementIsEnabled(By by) {
		Assert.assertTrue("Element is not enabled ", isElementEnabled(by));
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is selected
	 **/
	public void verifyElementIsSelected(By by) {
		Assert.assertTrue("Element is not selected ", isElementSelected(by));
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is selected
	 **/
	public void verifyPageTitle(String actualStringTitle) {
		waitForTitleOfPage(actualStringTitle);
		String expectedStringTitle = getDriver().getTitle();

		// By by = getDriver().findElement(arg0)
		Assert.assertEquals("Verification Failed:: Page title did not match", expectedStringTitle, actualStringTitle);
		// Assert.assertTrue("Element is not selected ", isElementSelected(by));
	}

	/*****
	 * @author Amit Pathak
	 * @param by
	 * @return flag @ check if the element is available for the operation
	 */
	public boolean verifyElementIsAvailable(By by) {
		// boolean disFlg=isElementDisplayed(by);
		boolean disFlg = false;
		if (getDriver().findElements(by).size() > 0) {
			disFlg = true;
		}
		return disFlg;
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is not present
	 **/
	public void verifyElementIsNotPresent(By by) {
		Assert.assertFalse("Element is present, It should not be present ", isElementNotPresent(by));
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsDisplayed function will Assert if element is not
	 *                           displayed
	 **/
	public void verifyElementIsNotDisplayed(By by) {
		Assert.assertFalse("Element is displayed, It should not be displayed ", isElementDisplayed(by));
	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is not enabled
	 **/
	public void verifyElementIsNotEnabled(By by) {
		Assert.assertFalse("Element is enabled, It should not be enabled ", isElementEnabled(by));
	}

	/**
	 * @Author: Chethan
	 * @DateCreated: 14 Nov 2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyTextPresentInCurrentWindow function will verify if text is present
	 *                                   in the current window
	 **/

	public boolean verifyTextPresentInCurrentWindow(String expectedValue) {

		String xpath = "//*[text()='" + expectedValue + "']";

		return isElementPresent(By.xpath(xpath));

	}

	/**
	 * @Author: Mirtunjay Prasad
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is not selected
	 **/
	public void verifyElementIsNotSelected(By by) {
		Assert.assertFalse("Element is selected, It should not be selected ", isElementSelected(by));
	}

	// *****************APPLICATION Functions*********************

	/**
	 * @Author: Gokul
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is not selected
	 **/
	public void selectRadioButtonOptions(By radioGroupList, String value) {
		List<WebElement> radioGroup = getDriver().findElements(radioGroupList);
		wait(Constant.WAITTIME_THREE_SEC);
		if (value.toLowerCase().equalsIgnoreCase("Yes".toLowerCase())) {
			radioGroup.get(0).click();
		} else if (value.toLowerCase().equalsIgnoreCase("No".toLowerCase())) {
			radioGroup.get(1).click();
		}
		log.info(value + " -->Column is clicked and expanded");
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 18May2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by
	 * @verifyElementIsPresent function will Assert if element is not selected
	 **/

	public void clickSubmenuFromMenu(By SummaryList, String value) {
		String singleValue = null;
		List<WebElement> summaryView = getDriver().findElements(SummaryList);
		for (WebElement element : summaryView) {
			singleValue = element.getText();
			if (singleValue.trim().equalsIgnoreCase(value.trim())) {
				element.click();
				break;
			}
		}
		log.info(value + " is selected from the dropown");
	}

	/**
	 * @Author: Chethan
	 * @DateCreated: 12 July 2018
	 * @param filePath:
	 *            path of the file
	 * @deserializeObject function will deserialize an object from given file.
	 **/
	public Object deserializeObject(String filePath) throws IOException, ClassNotFoundException {
		FileInputStream fis = new FileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}

	/**
	 * @Author:Samir Shukla
	 * @param by,ement
	 *            to be highlighted
	 */
	public void highlightElement(By by) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		WebElement element = getDriver().findElement(by);
		js.executeScript("arguments[0].style.border='3px solid blue'", element);
	}

	/**
	 * @Author:Samir Shukla
	 * @param by,
	 *            Element to be highlighted
	 * @param colorStyle,
	 *            color style to highlight if null or default will highlight 5px
	 *            solid red border
	 */
	public void highlightElement(WebElement element, String colorStyle) {
		JavascriptExecutor js = (JavascriptExecutor) getDriver();
		if (colorStyle == null || colorStyle.equalsIgnoreCase("default"))
			js.executeScript("arguments[0].style.border='5px solid red'", element);
		else
			js.executeScript(colorStyle, element);
	}

	/**
	 * @Author:Samir Shukla
	 * @param colorStyle,
	 *            color style to highlight if null or default will highlight 5px
	 *            solid red border
	 * 
	 */
	public void highlightElement(By by, String colorStyle) {
		WebElement element = getDriver().findElement(by);
		JavascriptExecutor js = (JavascriptExecutor) getDriver();

		if (colorStyle == null || colorStyle.equalsIgnoreCase("default"))
			js.executeScript("arguments[0].style.border='5px solid red'", element);
		else
			js.executeScript(colorStyle, element);
	}

	/**
	 * @Author: Samir Shukla
	 * @param filename
	 *            - filename of the screenshot taken
	 * @takeScreenshot Method will take screenshot and place is under the
	 *                 respective location
	 **/

	public void takeScreenshot() {

		TakesScreenshot takescreenshot = (TakesScreenshot) getDriver();
		File source = takescreenshot.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(source, new File(Constant.SCREENSHOT_LOCATION + screenshotFilename()));
		} catch (IOException e) {
			log.error("Class: CommonFunctions | Method: takeScreenshot | Message: failed when taking screnshot");
			log.error(e.getMessage());

		}
	}

	/**
	 * @Author: Samir Shukla
	 * @UpdatedBy:
	 * @screenshotFilename Method will generate unique name for the screenshot
	 *                     file
	 **/

	public String screenshotFilename() {
		Date d = new Date();
		String filename = d.toString().replace(":", "_").replace(" ", "_") + ".png";

		return filename;
	}

	/**
	 * @Author: Samir Shukla
	 * @embedScreenshotToReport Method will take screenshot and embed it to the
	 *                          html report
	 **/
	public void embedScreenshotToReport(Scenario scenario) {
		scenario.embed(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES), "image/png");
		log.info("Screenshot captured and embedded");

	}

	/**
	 * @Author: Samir Shukla
	 * @DateCreated: 1Jun2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param filename
	 *            : file to be uploaded
	 * @uploadFile Method will will upload the filename to the associated
	 *             Byobject
	 **/

	public void uploadFile(By by, String filename) {
		try {
			log.info(filename + " :: File uploading Started");
			getDriver().findElement(by).sendKeys(Constant.FILEUPLOAD_PATH + filename);
			log.info("File uploading Completed");
		} catch (Exception e) {
			log.error("Class:: CommonFuntion | Method:: uploadFile | Error Message :: Unable to upload file");
			log.error(e.getMessage());

		}
	}

	/**
	 * @Author: Samir Shukla
	 * @param filename
	 *            : file to be uploaded
	 * @uploadFile Method will will upload the file
	 **/

	public void uploadFileUsingRobot(By by, String filename) {
		Robot robot = null;
		click(by);
		wait(Constant.WAITTIME_SMALL);
		try {
			robot = new Robot();
			StringSelection stringSelection = new StringSelection(Constant.FILEUPLOAD_PATH + filename);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			log.info("Filepath:: " + Constant.FILEUPLOAD_PATH);
			log.info(filename + " :: File upload started");
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			wait(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			wait(2000);
			log.info(filename + " :: File upload completed");

		} catch (Exception e) {
			log.error("Class:: CommonFuntion | Method:: uploadFileUsingRobot | Error Message :: Unable to upload file");
			log.error(e.getMessage());
		}
	}

	/**
	 * @method clickEnterUsingRobot : will click on Enter button
	 * @author Samir Shukla
	 */
	public void clickEnterUsingRobot() {
		try {
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			log.info("Enter KeyPress via Robot");
		} catch (AWTException e) {
			log.error("Enter KeyPress Failed | Method: clickEnterUsingRobot | Class: CommonFunction");
			Assert.fail("Enter KeyPress Failed | Method: clickEnterUsingRobot | Class: CommonFunction");
		}
	}

	/**
	 * @Author:Gokul
	 * @Date Created:19May2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 *            selectValueFromDropDownList function will click the value from
	 *            the corresponding Drop down List
	 **/

	public void selectValueFromDropDownList(By SummaryList, String value) {
		boolean drpValue = false;
		String singleValue = null;

		List<WebElement> summaryView = getDriver().findElements(SummaryList);
		for (WebElement element : summaryView) {
			singleValue = element.getText();
			if (singleValue.trim().equalsIgnoreCase(value.trim())) {
				element.click();
				wait(Constant.WAITTIME_SMALL);
				drpValue = true;
				break;
			}
		}
		if (drpValue)
			log.info(value + " :: is selected from the dropown");
		else {
			log.error(value + " :: is not selected from the dropown");
			Assert.fail(value.toUpperCase() + " value was not selected from dropdown "
					+ getText(SummaryList).toUpperCase());
		}
	}

	public void verifyDropdownAllValues(By byActualValues, String[] expectedValues) {

		List<WebElement> weValues = getDriver().findElements(byActualValues);

		List<Object> actualValues = new ArrayList<Object>();

		// To get all the values in list
		for (int i = 0; i < weValues.size(); i++) {
			// actualValues.add(weValues.get(i).getText());
			actualValues.add(weValues.get(i).getAttribute("textContent"));
		}
		log.info("Actual Value in dropdown ::" + actualValues);

		// Compare actualValues list with expectedValues string array
		boolean compareValues = false;
		if ((actualValues.size() == expectedValues.length)) {

			for (int j = 0; j < actualValues.size(); j++) {
				if (expectedValues[j].equals(actualValues.get(j))) {
					compareValues = true;
				} else {
					compareValues = false;
					break;
				}
			}
		}
		Assert.assertTrue(compareValues);
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 29June2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:
	 *            -
	 * @sendKeys function is to clear the text in the field
	 **/

	public void clearText(By by) {
		highlightElement(by);
		getDriver().findElement(by).clear();
		wait(Constant.WAITTIME_THREE_SEC);
		log.info("Clear the existing Text");
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 02July2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param by:strText-Partial
	 *            Text -
	 * @verifyPartialText function is to verify the text in the message
	 **/

	public boolean verifyPartialText(By bylist, String strText) {
		boolean text = false;
		highlightElement(bylist);
		List<WebElement> partialText = getDriver().findElements(bylist);
		for (WebElement option : partialText) {
			if (option.getText().replaceAll("[^\\w\\s]", "").trim()
					.contains(strText.trim().replaceAll("[^\\w\\s]", ""))) {
				log.info(strText + " => message is displayed");
				text = true;
				break;

			}
		}
		return text;
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 12July2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param locator,duration
	 *            -
	 * @waitForElementToBeVisibleForMinimunPolling function is to wait with
	 *                                             minimun poling
	 **/

	public WebElement waitForElementToBeVisibleForMinimunPolling(By locator, int... duration) {
		int durationNewValue = 0;
		if (duration.length == 0) {
			durationNewValue = 50;
		} else {
			durationNewValue = duration[0];
		}
		Wait<WebDriver> wait = new FluentWait<WebDriver>(getDriver()).withTimeout(durationNewValue, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);
		WebElement webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		return webElement;
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 12July2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param duration
	 *            -
	 * @lessTimedriverLoad function is to wait for less time
	 **/

	public void lessTimedriverLoad(int... duration) {
		int durationNewValue = 0;
		if (duration.length == 0) {
			durationNewValue = 5;
		} else {
			durationNewValue = duration[0];
		}
		getDriver().manage().timeouts().implicitlyWait(durationNewValue, TimeUnit.SECONDS);
		getDriver().manage().timeouts().setScriptTimeout(durationNewValue, TimeUnit.SECONDS);
		getDriver().manage().timeouts().pageLoadTimeout(durationNewValue, TimeUnit.SECONDS);
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 12July2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @param duration
	 *            -
	 * @lessTimedriverLoad function is to wait for more time
	 **/

	public void moreTimedriverLoad() {
		getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
		getDriver().manage().timeouts().setScriptTimeout(50, TimeUnit.SECONDS);
		getDriver().manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
	}

	/**
	 * @Author:Mirtunjay
	 * @Date Created:5July2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 *            deselectValueFromDropDownList function will deselect the value
	 *            from the corresponding Drop down List
	 **/

	public void deselectValueFromDropDownList(By summaryList, String value) {
		String singleValue = null;
		List<WebElement> summaryView = getDriver().findElements(summaryList);
		for (WebElement element : summaryView) {
			singleValue = element.getText();
			if (singleValue.trim().equalsIgnoreCase(value.trim())) {
				if (element.isEnabled()) {
					element.click();
					wait(Constant.WAITTIME_SMALL);
					break;
				}
			}
		}
		log.info(value + " :: is De-selected from the dropown");
	}

	public void verifyRadiobuttonOptions(By radioGroupList, String value) {
		waitForPresenceOfElementLocated(radioGroupList);

		List<WebElement> radioGroup = getDriver().findElements(radioGroupList);
		if (value.toLowerCase().equalsIgnoreCase("yes")) {
			Assert.assertTrue(radioGroup.get(0).isSelected());

		} else if (value.toLowerCase().equalsIgnoreCase("no")) {
			Assert.assertTrue(radioGroup.get(1).isSelected());
		} else {
			Assert.assertFalse("Radiobutton selection option should be Yes nor No, but option is " + value, true);
		}

	}

	/**
	 * @Author:Mirtunjay Prasad
	 * @Date Created:27Sep2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 * @homeAction function will perform home action
	 **/

	public void homeAction(By by) {
		getDriver().findElement(by).sendKeys(Keys.HOME);
		log.info("Home action performed");
	}

	/**
	 * @Author:Mirtunjay Prasad
	 * @Date Created:27Sep2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 * @endAction function will perform END action
	 **/

	public void endAction(By by) {
		getDriver().findElement(by).sendKeys(Keys.END);
		log.info("End action performed");
	}

	/**
	 * @Author:Mirtunjay Prasad
	 * @Date Created:27Sep2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 * @tabAction function will perform arrow left action
	 **/

	public void arrowLeftAction(By by) {
		getDriver().findElement(by).sendKeys(Keys.ARROW_LEFT);
		log.info("Arrow Left action performed");
	}

	/**
	 * @Author:Mirtunjay Prasad
	 * @Date Created:27Sep2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 * @tabAction function will perform arrow right action
	 **/

	public void arrowRightAction(By by) {
		getDriver().findElement(by).sendKeys(Keys.ARROW_RIGHT);
		log.info("Arrow Left action performed");
	}

	/**
	 * @Author:Mirtunjay
	 * @Date Created:17July2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 * @tabAction function will tab to the next element
	 **/

	public void tabAction(By by) {
		getDriver().findElement(by).sendKeys(Keys.TAB);
		log.info("Tab action performed");
	}

	/**
	 * @Author:Mirtunjay
	 * @Date Created:17July2017
	 * @Date Updated:
	 * @Updated By:
	 * @param by
	 * @tabAction function will perform a backspace function
	 **/

	public void backspaceAction(By by) {
		getDriver().findElement(by).sendKeys(Keys.BACK_SPACE);
		log.info("Backspace action performed");
	}

	public void clickFirstElement(By by) {
		highlightElement(by);
		getDriver().findElements(by).get(0).click();
		log.info("Clicked successfully");
	}

	/**
	 * @param string:
	 *            regex <String>_<num>_<updatedUnit> Eg. Date_5_Sec, Date_9_Min,
	 *            Date_2_Hrs, Date_6_Day, Date_4_Month, Date_2_Yrs
	 * @getUpdatedDateTime function will return string with current date + the
	 *                     updated num unit will be added to the current date
	 **/

	public static String getUpdatedDateTime(String datetime) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
		Calendar now = Calendar.getInstance();

		if (datetime.contains("_")) {

			String[] splitString = datetime.split("_");
			String num = splitString[1];
			int updatedNumber = Integer.parseInt(num);
			String updatedDateTime = splitString[2];

			switch (updatedDateTime.toLowerCase()) {

			case "sec":
			case "second":
			case "seconds":

				now.add(Calendar.SECOND, updatedNumber);
				break;

			case "min":
			case "mins":
			case "minute":
			case "minutes":

				now.add(Calendar.MINUTE, updatedNumber);
				break;

			case "hrs":
			case "hour":
			case "hours":

				now.add(Calendar.HOUR, updatedNumber);
				break;

			case "day":
			case "days":
				now.add(Calendar.DAY_OF_MONTH, updatedNumber);
				break;

			case "mon":
			case "month":
			case "months":
				now.add(Calendar.MONTH, updatedNumber);
				break;

			case "yrs":
			case "year":
			case "years":
				now.add(Calendar.YEAR, updatedNumber);
				break;

			}

			return sdf.format(now.getTime());
		}

		else
			return datetime;
	}

	/**
	 * @by:
	 * 
	 * @highlightElements: function will highlight the last row
	 **/
	public void highlightElements(By by, List<Integer> listofrows, String colorStyle) {
		List<WebElement> listOfWebElement = getDriver().findElements(by);

		for (int i = 0; i < listofrows.size(); i++) {
			highlightElement(listOfWebElement.get(listofrows.get(i) - 1), colorStyle);
		}
	}

	public void verifyTextPresentInList(By bylist, By list, String strHeader, String strText) {
		boolean text = false;
		highlightElement(bylist);
		List<WebElement> valueText = getDriver().findElements(bylist);
		List<WebElement> headerText = getDriver().findElements(list);
		for (int i = 0; i < valueText.size(); i++) {
			if (valueText.get(i).getText().toLowerCase().replaceAll("[^\\w\\s]", "").trim()
					.equals(strText.toLowerCase().trim().replaceAll("[^\\w\\s]", ""))) {
				if (headerText.get(i).getText().toLowerCase().replaceAll("[^\\w\\s]", "").trim()
						.equals(strHeader.toLowerCase().trim().replaceAll("[^\\w\\s]", ""))) {
					text = true;
					break;
				}
			}
		}

		if (text)
			log.info(strHeader + ": " + strText + " => message is displayed");
		else {
			log.info(strHeader + ": " + strText + " => message is not displayed");
			Assert.fail(strText.toUpperCase() + "message is not displayed");
		}
	}

	/**
	 * @getListDetails function will return list of string
	 **/

	public List<String> getListDetails(By by) {
		List<WebElement> listOfWebElement = getDriver().findElements(by);

		List<String> list = new ArrayList<String>();

		for (WebElement webelement : listOfWebElement) {
			list.add(webelement.getText());
		}
		return list;
	}

	/**
	 * @Author:Gokul
	 * @Date Created:14AUG2017
	 * @Date Updated:
	 * @Updated By:
	 * @param By,Value
	 * 
	 * @clickAndSelectValueFromDropDownfunction select if the value is present
	 *                                          in list
	 **/
	public void clickAndSelectValueFromDropDown(By byClickDropDown, By dropDownHeader, String optionValue,
			String dropDownHeaderValue) {
		waitForElementToBeClickable(byClickDropDown);
		click(byClickDropDown);
		wait(Constant.WAITTIME_THREE_SEC);
		selectValueFromDropDownList(dropDownHeader, optionValue);
		tabAction(byClickDropDown);
		log.info("Verified: The value is " + optionValue + " selected from the drop down :: " + dropDownHeaderValue);
		wait(2000);
	}

	/**
	 * 
	 * 
	 * /**
	 * 
	 * @Param : by:List of checkbox checkBoxNumber:All the number to check
	 * @selectCheckBoxInTable function to select all the checkbox given in the
	 *                        count
	 **/
	public void selectCheckBoxInTable(By by, String checkBoxNumber) {
		try {
			List<WebElement> listOfCheckBox = getDriver().findElements(by);
			Boolean blnFlag = false;
			for (int i = 0; i < listOfCheckBox.size(); i++) {
				if (i >= Integer.parseInt("1") && i <= Integer.parseInt(checkBoxNumber)) {
					// WebElement element = listOfCheckBox.get(i);
					// JavascriptExecutor executor = (JavascriptExecutor)
					// getDriver();
					// executor.executeScript("arguments[].click();", element);
					listOfCheckBox.get(i).click();
					wait(3000);
					blnFlag = true;
					log.info("All " + i + "");
				}
				if (i > Integer.parseInt(checkBoxNumber)) {
					break;
				}
			}

			if (blnFlag) {
				log.info("All " + checkBoxNumber + " :: is Selected in List");
			} else {
				log.info("All " + checkBoxNumber + " :: is NOT Selected in List");
				Assert.fail("All " + checkBoxNumber + " :: is NOT Selected in List");
			}
		} catch (Exception e) {
			log.info("All " + checkBoxNumber + " :: is NOT Selected in List");
			Assert.fail("All " + checkBoxNumber + " :: is NOT Selected in List");
		}
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 30Aug2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @Param by:List of checkbox checkBoxNumber:Number to check
	 * @selectCheckBoxInTable function to check a checkbox given in the count
	 **/
	public void selectSelectedCheckBoxInTable(By by, String checkBoxNumber) {
		List<WebElement> listOfCheckBox = getDriver().findElements(by);
		Boolean blnFlag = false;
		if (listOfCheckBox.size() > 0) {
			listOfCheckBox.get(Integer.parseInt(checkBoxNumber) - 1).click();
			wait(Constant.WAITTIME_SMALL);
			blnFlag = true;
		}

		if (blnFlag) {
			log.info(checkBoxNumber + " :: is Selected in List");
		} else {
			log.info(checkBoxNumber + " :: is NOT Listed");
			Assert.fail(checkBoxNumber + " :: is NOT Listed");
		}
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 30Aug2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @Param : by:List of checkbox checkBoxNumber:All the number to
	 *        check,checkBlank
	 * @verifyCheckBoxInTable function to verify all the checkbox are Selected
	 **/
	public void verifyCheckBoxInTable(By by, String checkBlank, String checkBoxNumberFrom, String checkBoxNumberTo) {
		wait(Constant.WAITTIME_SMALL);
		List<WebElement> listOfCheckBox = getDriver().findElements(by);
		Boolean blnFlag = false;
		for (int i = 0; i < listOfCheckBox.size(); i++) {
			if (i == Integer.parseInt(checkBoxNumberFrom) && i <= Integer.parseInt(checkBoxNumberTo) - 1) {
				if (listOfCheckBox.get(i).getAttribute("class").contains(checkBlank.toLowerCase())) {
					blnFlag = true;
				}
			}
		}

		if (blnFlag) {
			log.info("All Checkbox from " + checkBoxNumberFrom + " to " + checkBoxNumberTo + " :: is " + checkBlank
					+ " in List");
		} else {
			log.info("All Checkbox from " + checkBoxNumberFrom + " to " + checkBoxNumberTo + " :: is " + checkBlank
					+ " in List");
			Assert.fail("All Checkbox from " + checkBoxNumberFrom + " to " + checkBoxNumberTo + " :: is " + checkBlank
					+ " in List");
		}
	}

	/**
	 * @Author: Gokul
	 * @DateCreated: 30Aug2017
	 * @DateUpdated:
	 * @UpdatedBy:
	 * @Param by:List of checkbox checkBoxNumber:Number to check,checkBlank-Pass
	 *        check/blank
	 * @selectCheckBoxInTable function to verify the selected checkbox is
	 *                        checked
	 **/
	public void verifySelectedCheckBoxInTable(By by, String checkBoxNumber, String checkBlank) {
		List<WebElement> listOfCheckBox = getDriver().findElements(by);
		Boolean blnFlag = false;
		if (listOfCheckBox.size() > 0) {
			if (listOfCheckBox.get(Integer.parseInt(checkBoxNumber) - 1).getAttribute("class")
					.contains(checkBlank.toLowerCase())) {
				blnFlag = true;
			}

		}

		if (blnFlag) {
			log.info(checkBoxNumber + " :: is " + checkBlank + " in List");
		} else {
			log.info(checkBoxNumber + " :: is NOT " + checkBlank + " in List");
			Assert.fail(checkBoxNumber + " :: is NOT " + checkBlank + " in List");
		}
	}

	/**
	 * @UpdatedBy:
	 * @scrollOnTopOfPage: function will scroll the page on top
	 **/
	public void scrollOnTopOfPage() {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("window.scrollBy(0,-800)", "");
		log.info("Scrolled on Top Of the page");
	}

	/**
	 * @scrollOnBottomOfPage: function will scroll the page on Bottom
	 **/

	public void scrollOnBottomOfPage() {
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();
		executor.executeScript("window.scrollBy(0, 800)", "");
		log.info("Scrolled on Bottom Of the page");

	}

	/**
	 * @param folderLocation,fileName
	 *            - saveAsFile function is to save as a file in the target
	 *            location
	 **/

	public void saveAsFile(String folderLocation, String fileName) {
		Robot robot = null;
		wait(2000);

		try {
			createFolderIfExits(folderLocation);
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_F6);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_F6);
			robot.keyPress(KeyEvent.VK_TAB);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_DOWN);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_DOWN);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_DOWN);
			robot.keyPress(KeyEvent.VK_ENTER);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_ENTER);
			StringSelection stringSelection = new StringSelection(folderLocation + fileName);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			log.info("Filepath:: " + folderLocation + fileName);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			wait(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			wait(2000);
			log.info("Verified::  Save as a file in the target location " + folderLocation + fileName);
		} catch (AWTException e) {
			log.error(" Folder is not created in the directory");
			Assert.fail(" Folder is not created ");
			e.printStackTrace();
		}

	}

	/**
	 * @param Filelocation:
	 *            location of file - createFolderIfExits function is create a
	 *            folder
	 * @return
	 **/
	public void createFolderIfExits(String filelocation) {
		File theDir = new File(filelocation);
		// if the directory does not exist, create it
		if (!theDir.exists()) {
			log.info("creating directory: " + theDir.getName());
			theDir.mkdir();
			log.info("Directory is created newly in the location:: " + filelocation);
		} else {
			log.info("Directory is already created exists in the location:: " + filelocation);
		}
	}

	/**
	 * Replaces [ and ] brackets in a string with empty ""
	 * 
	 * @param stringToReplace
	 * @return
	 */
	public String replaceBracketsInString(String stringToReplace) {

		String str = stringToReplace.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\(", "").replaceAll("\\)",
				"");

		// Adding logic to return only one value;
		String[] splitString = str.split(",");
		return splitString[0];
	}

	/**
	 * @waitForElementToBeClickableUntilVisibleNotStale:This method is used to
	 *                                                       wait for element to
	 *                                                       be clickable,not
	 *                                                       visible and not
	 *                                                       stale
	 * @param by:
	 *            By of xpath
	 */
	public void waitForElementToBeClickableUntilVisibleNotStale(By by) {
		new WebDriverWait(getDriver(), Constant.WEBDRIVERWAIT).ignoring(StaleElementReferenceException.class)
				.ignoring(ElementNotVisibleException.class).until(ExpectedConditions.elementToBeClickable(by));

	}

	/**
	 * @param by
	 *            selectValueFromDropDownList function will click the value from
	 *            the corresponding Drop down List
	 **/

	public void selectValueFromDropDownListJavaScriptExecutor(By SummaryList, String value) {
		boolean drpValue = false;
		String singleValue = null;

		List<WebElement> summaryView = getDriver().findElements(SummaryList);
		for (WebElement element : summaryView) {

			singleValue = element.getAttribute("innerText");

			System.out.println("values:" + singleValue);

			wait(Constant.WAITTIME_SMALL);

			if (singleValue.trim().equalsIgnoreCase(value.trim())) {
				clickJavascriptExecutor(element);
				drpValue = true;
				break;
			}
		}
		if (drpValue)
			log.info(value + " :: is selected from the dropown");
		else {
			log.error(value + " :: is not selected from the dropown");
			Assert.fail(value.toUpperCase() + " value was not selected from dropdown "
					+ getText(SummaryList).toUpperCase());
		}
	}

	/**
	 * @getCurrentDateTime: Returns the current date and time in yyyyMMddHHmmss
	 * @param dateFormat
	 *            TODO
	 */
	public String getCurrentDateTime(String dateFormat) {
		DateFormat dateFrmt = new SimpleDateFormat(dateFormat);
		Date date = new Date();
		return dateFrmt.format(date);
	}

	/**
	 * @getDateFromString: gets date object from 'date' string of format
	 *                     'format'
	 * @param date
	 *            : date string
	 * @param format:
	 *            format of the date string
	 */
	public Date getDateFromString(String date, String format) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

			return simpleDateFormat.parse(date);

		} catch (ParseException exception) {
			Assert.fail("could not parse date:" + date);
		}
		return null;
	}

	/**
	 * @param date,date
	 * @param sourceFormat,source
	 *            format
	 * @param targetFormat,target
	 *            format
	 * @return date in target from source format
	 */
	public String convertDateFromOneFormatToAnotherFormat(String date, String sourceFormat, String targetFormat) {
		DateFormat targetFormatDt = new SimpleDateFormat(targetFormat);
		return (targetFormatDt.format(getDateFromString(date, sourceFormat)));
	}

	/**
	 * @param date
	 * @param format
	 * @return
	 */
	public boolean isDateOfFormat(String date, String format) {
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

			simpleDateFormat.parse(date);

		} catch (ParseException exception) {
			return false;
		}
		return true;
	}

	/**
	 * @Updated By:
	 * @param by
	 *            selectValueFromDropDownList function will click the value from
	 *            the corresponding Drop down List
	 **/

	public void selectMultipleValuesFromDropDownList(By SummaryList, String value) {
		boolean drpValue = false;
		String singleValue = null;
		List<WebElement> summaryView = getDriver().findElements(SummaryList);

		HashSet<String> set = new HashSet<String>(Arrays.asList(value.split(",")));

		List<WebElement> selectList = new ArrayList<WebElement>();

		for (WebElement element : summaryView) {
			if (set.size() == 0)
				break;
			singleValue = element.getAttribute("textContent");

			log.info("reading value:" + singleValue);

			if (set.contains(singleValue.trim())) {
				selectList.add(element);
				set.remove(singleValue.trim());
				// element.click();
				drpValue = true;
			}
		}

		if (drpValue) {
			clickMultipleElementsJavaScriptExecutor(selectList);
			log.info(value + " :: is selected from the dropown");
		} else {
			log.error(value + " :: is not selected from the dropown");
			Assert.fail(value.toUpperCase() + " value was not selected from dropdown "
					+ getText(SummaryList).toUpperCase());
		}
	}

	/**
	 * @clickMultipleElementsJavaScriptExecutor: click multiple elements from
	 *                                           the list
	 * 
	 * @param list:
	 *            list of elements to click
	 * 
	 **/
	public void clickMultipleElementsJavaScriptExecutor(List<WebElement> list) {
		log.info("in clickMultipleElementsJavaScriptExecutor");

		if (list.size() == 0)
			return;
		wait(Constant.WAITTIME_TINY);

		Robot robot = null;

		try {

			robot = new Robot();

			list.get(0).click();

			robot.keyPress(KeyEvent.VK_SHIFT);

			log.info("robot clicks shift");
			wait(Constant.WAITTIME_TINY);

			// robot.keyPress(KeyEvent.VK_CONTROL);
			JavascriptExecutor executor = (JavascriptExecutor) getDriver();

			for (int i = 1; i < list.size(); i++) {
				wait(Constant.WAITTIME_TINY);

				executor.executeScript("arguments[0].click();", list.get(i));

				wait(Constant.WAITTIME_TINY);

				log.info("clicked on element:" + list.get(i).getText());

			}
			wait(Constant.WAITTIME_TINY);

			robot.keyRelease(KeyEvent.VK_SHIFT);

			wait(Constant.WAITTIME_TINY);

		} catch (AWTException e) {
			Assert.fail("AWT Exception occured and application crashed");

		} finally {
			robot.keyRelease(KeyEvent.VK_SHIFT);

		}

		log.info("robot releases shift");

	}

	/**
	 * @validateDropdownValues: validate options of drop down using Select class
	 * @selectElementRef: By of Select element
	 * @expectedValues: String array of expected values
	 * 
	 **/
	public void validateDropdownValues(By selectElementRef, String[] expectedValues) {

		WebElement webElement = getDriver().findElement(selectElementRef);
		Select selectElement = new Select(webElement);
		List<WebElement> selectOptions = selectElement.getOptions();
		boolean valuesEqual = false;
		if ((selectOptions.size() == expectedValues.length)) {
			for (int i = 0; i < selectOptions.size(); i++) {
				log.info("expectedValues[i]: " + expectedValues[i] + " :selectOptions.get(i).getText(): "
						+ selectOptions.get(i).getAttribute("textContent"));
				if (expectedValues[i].equals(selectOptions.get(i).getAttribute("textContent"))) {
					valuesEqual = true;
				} else {
					valuesEqual = false;
					break;
				}
			}
		}
		Assert.assertTrue(valuesEqual);
	}

	public void activateBrowsers() {
		Set<String> BrowserList = null;
		Iterator<String> IteratorList = null;
		int Browser_Cnt = 0;
		int i;
		BrowserList = getDriver().getWindowHandles();
		Browser_Cnt = BrowserList.size();
		IteratorList = BrowserList.iterator();
		String[] WinList = new String[Browser_Cnt];
		for (i = 0; i < Browser_Cnt; i++) {
			WinList[i] = IteratorList.next();
		}
		getDriver().switchTo().window(WinList[i - 1]).manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	public void switchToSellingFrame() {
		getDriver().switchTo().frame(getDriver().findElement(By.xpath("//iframe[@id='content-iframe-id']")));
		log.info("Switched to Selling frame");
	}

	public void hoverAndClick(By mainMenuPro, By subMenuPro) {
		WebElement elemnetToHover = null;
		WebElement elementToClick = null;
		elemnetToHover = getDriver().findElement(mainMenuPro);
		if (elemnetToHover != null) {
			elementAction.moveToElement(elemnetToHover).build().perform();
			elementToClick = getDriver().findElement(subMenuPro);
			elementAction.moveToElement(elementToClick).click().perform();
		}
		// waitForSpinnerTopMenu();
	}

	/**
	 * Method that checks if a an element is present and visible. It will wait
	 * for only 2 seconds.
	 * 
	 * @param xpath
	 *            - String with the xpath of the element
	 * @return
	 */
	public boolean isPresentAndVisibleImmediately(By xpath) {
		boolean isPresentAndVisible = false;
		int seconds = 2;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
			isPresentAndVisible = true;
		} catch (Exception e) {
			log.error("Element not present and visible:" + e.getMessage());
		}
		return isPresentAndVisible;
	}

	public void selectDropDownByVisibleText(By dropDownRef, String dropDownVal) throws Exception {
		if (!(dropDownVal == null)) {
			if (!(dropDownVal.length() < 1)) {
				if (!dropDownVal.matches("NA")) {
					WebElement element = null;
					element = getDriver().findElement(dropDownRef);
					Select DropDownList = new Select(element);
					DropDownList.selectByVisibleText(dropDownVal);
					element.sendKeys(Keys.TAB);
				}
			}
		}
	}

	/**
	 * @evaluateConditionExpression: validate condition expression
	 **/
	public boolean evaluateConditionExpression(String expression) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine jsEngine = manager.getEngineByName("JavaScript");
		Object result = null;
		try {
			result = jsEngine.eval(expression);
		} catch (ScriptException e) {
			e.printStackTrace();
			Assert.fail("Script evaluate exception occured for expression:" + expression);
		}
		return Boolean.parseBoolean(result.toString());
	}

	public WebElement getWebElementForVerification(By elementRef) {
		Wait_For_Object.until(ExpectedConditions.visibilityOfElementLocated(elementRef));
		WebElement element = getDriver().findElement(elementRef);
		if (element.isDisplayed()) {
			JavascriptExecutor js = (JavascriptExecutor) getDriver();
			js.executeScript("arguments[0].style.border='3px solid blue'", element);
		}
		return element;
	}

	// TO BE DELETED AFTER REFACTORING
	public String getWebTableCellData(By table, int rowIndex, int colIndex, String Event, String Desc)
			throws Exception {
		String ret = null;
		if (!Event.matches("NA")) {

			WebElement WebTableElement = null;
			WebElement TableRowElement = null;
			WebElement TableColumnElement = null;
			WebTableElement = driver.findElement(table);
			TableRowElement = WebTableElement.findElements(By.tagName("tr")).get(rowIndex);
			TableColumnElement = TableRowElement.findElements(By.tagName("td")).get(colIndex);
			TableColumnElement.click();
			ret = TableColumnElement.getText();
			if (!Desc.isEmpty()) {
				// ReportingUtils.Add_Step(Desc + " : " + ret, Status.PASS,
				// true);
			}
		}
		return ret;
	}

	/**
	 * @method getWebTableCellData : will return the text present in table as
	 *         per row and column index
	 * @param table
	 * @param rowIndex
	 * @param colIndex
	 * @return
	 * @throws Exception
	 */
	public String getWebTableCellData(By table, int rowIndex, int colIndex) throws Exception {
		WebElement tableElement = driver.findElement(table);
		WebElement tableRow = tableElement.findElements(By.tagName("tr")).get(rowIndex);
		WebElement tableColumn = tableRow.findElements(By.tagName("td")).get(colIndex);
		return tableColumn.getText();
	}

	public static int getRandomNumbers(int min, int max) {
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

	public static void addTextToReport(String text) {
		scenario.embed(text.getBytes(), "text/html");
	}

	public void addTextToReport(Scenario scenario, String text) {
		scenario.embed(text.getBytes(), "text/html");
	}

	public static void addXMLToReport(String text) {
		scenario.embed(text.getBytes(), "application/xml");
	}

	public void addXMLToReport(Scenario scenario, String text) {
		scenario.embed(text.getBytes(), "application/xml");
	}

	public void addPDFToReport(String text) {
		scenario.embed(text.getBytes(), "application/pdf");
	}

	/**
	 * @method convertStringToXmlDocument will convert the string to xml format
	 * @param strToXmlDoc
	 *            string to convert into xml document
	 * @throws Exception
	 */
	public static void convertStringToXmlDocument(String strToXmlDoc) throws Exception {
		// Check if the path is present, else create the path
		String file = Constant.XML_RESULT_LOCATION + "xml" + System.currentTimeMillis() + ".xml";

		// File details stored in Global Variable
		Constant.XMLResultFile = file;
		createDirectory(Constant.XML_RESULT_LOCATION);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;

		System.out.println("Convert String to XML Document :: STARTED");
		builder = factory.newDocumentBuilder();
		Document doc = builder.parse(new InputSource(new StringReader(strToXmlDoc)));

		TransformerFactory tranFactory = TransformerFactory.newInstance();
		Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		aTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");

		Source src = new DOMSource(doc);

		Result dest = new StreamResult(new File(file));
		aTransformer.transform(src, dest);
		log.info("XML File Created :: " + file);
		log.info("Convert String to XML Document :: COMPLETED");

	}

	/**
	 * @param dir
	 *            directory
	 * @throws Exception
	 */
	public static void createDirectory(String dir) throws Exception {
		Path path = Paths.get(dir);
		if (!Files.exists(path)) {
			System.out.println(dir + " :: directory creation ---> STARTED");
			Files.createDirectories(path);
			System.out.println(dir + " :: directory creation ---> COMPLETED");
		}
	}

	/**
	 * @method getTodaysDateMMDDYYYY: will return todays date in MM/dd/yyyy
	 *         format
	 * @return
	 */
	public static String getTodaysDateMMDDYYYY() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		log.info("Todays Date :: " + dateFormat.format(date));
		return dateFormat.format(date);
	}

	/**
	 * @method acceptAlertPopup : will accept the alert popup
	 */
	public void acceptAlertPopup() {
		Alert alert = getDriver().switchTo().alert();
		alert.accept();
		wait(Constant.WAITTIME_THREE_SEC);
		log.info("Alert Popup:: ACCEPTED");
	}

	/**
	 * @method dismissAlertPopup : will dismiss the alert popup
	 */
	public void dismissAlertPopup() {
		Alert alert = getDriver().switchTo().alert();
		alert.dismiss();
		wait(Constant.WAITTIME_THREE_SEC);
		log.info("Alert Popup DISMISSED");
	}

	/**
	 * @method printDetailsOfPropertyFiles : print all details from the property
	 *         files
	 * @param property
	 */
	public void printDetailsOfPropertyFiles(Properties property) {
		Enumeration<?> e = property.propertyNames();
		log.info("*****************Property File Contains below details*******************");
		try {
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				log.info(key + " :: " + property.getProperty(key));
			}
			log.info("************************************************************************");
			log.info("");
		} catch (Exception exception) {
			log.error(
					"Unable to print details from property file | method printDetailsOfPropertyFiles | class:: CommonFuntion");
			Assert.fail(
					"Unable to print details from property file | method printDetailsOfPropertyFiles | class:: CommonFuntion");
		}
	}

	/**
	 * Method that checks if a an element is present and visible. It will wait
	 * for 20 seconds. This method is used in places where a previous loading of
	 * an element might take place, giving more time for the element search.
	 * 
	 * @param xpath
	 *            - String with the xpath of the element
	 * @return
	 */
	public static boolean IsPresentAndVisible(By by) {
		boolean isPresentAndVisible = false;
		int seconds = 20;
		try {
			WebDriverWait wait = new WebDriverWait(driver, seconds);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			wait.until(ExpectedConditions.visibilityOfElementLocated(by));
			isPresentAndVisible = true;
		} catch (Exception e) {
			isPresentAndVisible = false;
		}
		return isPresentAndVisible;
	}

	/**
	 * @param by
	 *            selectValueFromList function will click the value from the
	 *            corresponding Drop down List
	 **/

	public void selectValueFromList(By SummaryList, String value) {
		boolean drpValue = false;
		String singleValue = null;

		List<WebElement> summaryView = getDriver().findElements(SummaryList);
		for (WebElement element : summaryView) {
			singleValue = element.getText();
			if (singleValue.trim().equalsIgnoreCase(value.trim())) {
				// JavascriptExecutor executor = (JavascriptExecutor)
				// getDriver();
				// executor.executeScript("arguments[0].click();", summaryView);
				element.click();
				wait(Constant.WAITTIME_MEDIUM);
				drpValue = true;
				break;
			}
		}
		if (drpValue)
			log.info(value + " :: is selected from the dropown");
		else {
			log.error(value + " :: is not selected from the dropown");
			Assert.fail(value.toUpperCase() + " value was not selected from dropdown "
					+ getText(SummaryList).toUpperCase());
		}
	}

	/**
	 * Method that checks if an element is clickable. It will wait for only 2
	 * seconds
	 * 
	 * @param xpath
	 *            - String with teh xpath of the element
	 * @return
	 * @throws InterruptedException
	 */
	public boolean IsClickableImmediately(By xpath) throws InterruptedException {
		boolean clickable = false;
		int seconds = 5;
		try {

			WebDriverWait wait = new WebDriverWait(getDriver(), seconds);
			WebElement element = null;
			try {
				getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
				element = wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
				element = wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
			} catch (Exception e) {
				// e.printStackTrace();
			}
			if (element != null) {
				try {
					getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
					wait.until(ExpectedConditions.elementToBeClickable(xpath));
					clickable = true;
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		wait(Constant.WAITTIME_THREE_SEC);
		return clickable;
	}

	/**
	 * Method that checks if a an element is present and visible. It will wait
	 * for only 2 seconds.
	 * 
	 * @param xpath
	 *            - String with the xpath of the element
	 * @return
	 */
	public boolean IsPresentAndVisibleImmediately(By xpath) {
		boolean isPresentAndVisible = false;
		int seconds = 2;
		try {
			WebDriverWait wait = new WebDriverWait(getDriver(), seconds);

			wait.until(ExpectedConditions.presenceOfElementLocated(xpath));
			wait.until(ExpectedConditions.visibilityOfElementLocated(xpath));
			isPresentAndVisible = true;
		} catch (Exception e) {
			// Nothing
		}
		return isPresentAndVisible;
	}

	/**
	 * Method that checks if a an element is present and visible. It will wait
	 * for only 2 seconds.
	 * 
	 * @param xpath
	 *            - String with the xpath of the element
	 * @return
	 */
	public void waitForSpinnerSelling() {
		new WebDriverWait(getDriver(), 120).until(
				(ExpectedCondition<Object>) wd -> ((JavascriptExecutor) wd).executeScript("return jQuery.active == 0"));
	}

	/**
	 * Method to iterate a string that represents multiple values grouped
	 * together. Example: completeValues: a,b,c - separator: "," Becomes:
	 * token[0] = a token[1] = b token[2] = c
	 * 
	 * @param completeValues
	 *            - String with the complete value
	 * @param separator
	 *            - String that indicates the character that will act as a
	 *            separator
	 * @return
	 */
	public String[] getMultipleValues(String completeValues, String separator) {
		String[] tokens = completeValues.split(separator);
		for (int i = 0; i < tokens.length; i++) {
			tokens[i] = tokens[i].trim();
		}
		return tokens;
	}

	public void typeWebElementKey(By WebElement_Pro, String KeyVal) throws Exception {
		if (!KeyVal.matches("NA")) {
			WebElement Element = getDriver().findElement(WebElement_Pro);
			Element.click();
			Element.sendKeys(KeyVal);
			Element.sendKeys(Keys.TAB);

		}
	}

	/**
	 * @param by:
	 *            object where click action will be performed
	 * @click function click in the object
	 **/

	public void doubleClick(By by) {

		Actions action = new Actions(getDriver());
		waitForElementToBeClickable(by);
		highlightElement(by);
		WebElement clickObject = getDriver().findElement(by);
		action.moveToElement(clickObject).doubleClick(clickObject).build().perform();
		log.info("Clicked successfully");

	}

	/**
	 * This method is used to return all web elements mathing the By
	 * 
	 * @param By
	 *            - by of the elements to be returned
	 **/
	public List<WebElement> getListOfWebElements(By by) {
		return getDriver().findElements(by);

	}

	public int elementSize(By by) {

		int size = getDriver().findElements(by).size();

		if (size == 0) {
			return size;
		} else {
			return size;
		}
	}

	public String getElementText(By by) {
		String tagText = getDriver().findElement(by).getText();
		return tagText;
	}

	/**
	 * This method is used to click on a WebElement
	 * 
	 * @param WebElement
	 *            - element to be clicked
	 **/
	public void clickElement(WebElement element) {
		element.click();

	}

	/**
	 * @method selectFromDropdownByPartialText: will select from dropdown using
	 *         partial text match
	 * @param byDropdown
	 * @param byListDropdown
	 * @param value
	 */
	public void selectFromDropdownByPartialText(By byDropdown, By byListDropdown, String value) {
		try {
			String strValueInDropdown = "";
			boolean isValueInDropdown = false;
			waitForElementToBeClickable(byDropdown);
			click(byDropdown);
			wait(Constant.WAITTIME_SMALL);
			List<WebElement> listDropdownValues = getDriver().findElements(byListDropdown);
			for (WebElement element : listDropdownValues) {
				strValueInDropdown = element.getText();
				if (strValueInDropdown.trim().contains(value.trim())) {
					// element.click();
					clickJavascriptExecutor(element);
					isValueInDropdown = true;
					break;
				}
			}
			if (isValueInDropdown) {
				log.info("Selected value from dropdown is ::  " + value);
				wait(Constant.WAITTIME_SMALL);
				// tabAction(byListDropdown);
				// wait(Constant.WAITTIME_SMALL);

			} else {
				log.error(
						"Class:: CommonFunction || Method:: selectDropdownByPartialText || Message:: Value is not present in the dropdown = "
								+ value);
				Assert.fail(
						"Class:: CommonFunction || Method:: selectDropdownByPartialText || Message:: Value is not present in the dropdown = "
								+ value);
			}

		} catch (Exception e) {
			log.error(
					"Class:: CommonFunction || Method:: selectDropdownByPartialText || Message:: Error when selecting dropdown value = "
							+ value);
			e.printStackTrace();
			Assert.fail(
					"Class:: CommonFunction || Method:: selectDropdownByPartialText || Message:: Error when selecting dropdown value = "
							+ value);
		}
	}

	/**
	 * @selectRadioButtonOptionsJavaScript function will select radio button if
	 *                                     element is not selected
	 **/
	public void selectRadioButtonOptionsJavaScript(By radioGroupList, String value) {
		List<WebElement> radioGroup = getDriver().findElements(radioGroupList);
		wait(Constant.WAITTIME_THREE_SEC);
		JavascriptExecutor executor = (JavascriptExecutor) getDriver();

		if (value.toLowerCase().equalsIgnoreCase("Yes".toLowerCase())) {
			executor.executeScript("arguments[0].click();", radioGroup.get(0));
		} else if (value.toLowerCase().equalsIgnoreCase("No".toLowerCase())) {
			executor.executeScript("arguments[0].click();", radioGroup.get(1));
		}
		log.info(value + " -->Column is clicked and expanded");
	}

	public void attachFileUsingRobot(String filename) {
		Robot robot = null;
		wait(Constant.WAITTIME_SMALL);

		try {
			robot = new Robot();
			StringSelection stringSelection = new StringSelection(filename);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
			log.info("Filepath:: " + Constant.FILEUPLOAD_PATH);
			log.info(filename + " :: File upload started");
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_V);
			wait(2000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_V);
			wait(2000);
			robot.keyPress(KeyEvent.VK_ENTER);
			robot.keyRelease(KeyEvent.VK_ENTER);
			wait(2000);
			log.info(filename + " :: File upload completed");

		} catch (Exception e) {
			log.error("Class:: CommonFuntion | Method:: uploadFileUsingRobot | Error Message :: Unable to upload file");
			log.error(e.getMessage());
		}
	}

	public void navigateToPFDCUrl(String pmaApplication, Object object) {
		// TODO Auto-generated method stub

	}

}
