package org.wcontacts.commons;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Commands {

	public static void waitForElement(final WebDriverWait wait,
			final String locator) {
		try {
			wait.until(new ExpectedCondition<WebElement>() {
				public WebElement apply(WebDriver newDriver) {
					return newDriver.findElement(By.xpath(locator));
				}
			});
		} catch (Exception e) {
			
			System.out.println(e);
		}
	}

	public static boolean isElementPresent(final WebDriver driver,
			final String locator) {
		try {
			driver.findElement(By.xpath(locator));
			return true;
		} catch (Exception e) {
			
			System.out.println(e);
			return false;
		}
	}

	public static void waitUntilElementInvisible(final WebDriverWait wait,
			final String locator) {
		try {
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By
					.xpath(locator)));
		} catch (Exception e) {
			
			System.out.println(e);
		}
	}

	public static void waitUntilAllElementsVisible(final WebDriverWait wait,
			final String locator) {
		try {
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By
					.xpath(locator)));
		} catch (Exception e) {
			
			System.out.println(e);
		}
	}
}