package com.webautomation.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;

public class ReportUtils {
	static WebDriver driver = WebDriverUtils.getDriver();
	public static Scenario scenario   ;

	public static void getScreenshot() {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(getScreenshotPath()));
			scenario.embed(FileUtils.readFileToByteArray(scrFile), "image/png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void write(String message) {
		scenario.write(message);
	}

	private static String getScreenshotPath() {
		String path = System.getProperty("user.dir") + "//target//%s.png";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddhhmmss");
		path = String.format(path, "screenshot" + dateFormat.format(cal.getTime()));
		return path;
	}

}
