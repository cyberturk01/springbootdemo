package com.yigit.web.utilities;


import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
* This class is mostly used for UI TESTING
*/
public class BrowserUtil {

 /**
  * Switches to new window by the exact title. Returns to original window if target title not found
  *
  * @param targetTitle gets the title of target page
  */
 public static void switchToWindow(String targetTitle) {
   String origin = Driver.get().getWindowHandle();
   for (String handle : Driver.get().getWindowHandles()) {
     Driver.get().switchTo().window(handle);
     if (Driver.get().getTitle().equals(targetTitle)) {
       return;
     }
   }
   Driver.get().switchTo().window(origin);
 }

 /**
  * Moves the mouse to given element
  *
  * @param element on which to hover
  */
 public static void hover(WebElement element) {
   Actions actions = new Actions(Driver.get());
   actions.moveToElement(element).perform();
 }

 /**
  * Returns a list of string from a list of elements
  *
  * @param list of web elements
  * @return list of string
  */
 public static List<String> getElementsText(List<WebElement> list) {
   List<String> elemTexts = new ArrayList<>();
   for (WebElement el : list) {
     elemTexts.add(el.getText());
   }
   return elemTexts;
 }

 /**
  * Extracts text from list of elements matching the provided locator into new List<String>
  *
  * @param locator by id, name, css, xpath, link_text, partial_link_text, tag_name
  * @return list of strings
  */
 public static List<String> getElementsText(By locator) {

   List<WebElement> elems = Driver.get().findElements(locator);
   List<String> elemTexts = new ArrayList<>();

   for (WebElement el : elems) {
     elemTexts.add(el.getText());
   }
   return elemTexts;
 }

 /**
  * Performs a pause
  *
  * @param seconds waiting time period with seconds
  */
 public static void waitFor(int seconds) {
   try {
     Thread.sleep(seconds * 1000);
   } catch (InterruptedException e) {
     Thread.currentThread().interrupt();
     e.printStackTrace();
   }
 }

 /**
  * Waits for the provided element to be visible on the page
  *
  * @param element         web element find by locators in the website
  * @param timeToWaitInSec waiting time period with seconds
  * @return type of WebElement
  */
 public static WebElement waitForVisibility(WebElement element, int timeToWaitInSec) {
   WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeToWaitInSec));
   return wait.until(ExpectedConditions.visibilityOf(element));
 }

 /**
  * Waits for element matching the locator to be visible on the page
  *
  * @param locator by id, name, css, xpath, link_text, partial_link_text, tag_name
  * @param timeout amount of time the WebDriver must wait for an asynchronous script
  * @return type of WebElement
  */
 public static WebElement waitForVisibility(By locator, int timeout) {
   WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
   return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
 }

 /**
  * Waits for provided element to be clickable
  *
  * @param element web element find by locators in the website
  * @param timeout amount of time the WebDriver must wait for an asynchronous script
  * @ type of WebElement
  */
 public static WebElement waitForClickAbility(WebElement element, int timeout) {
   WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
   return wait.until(ExpectedConditions.elementToBeClickable(element));
 }

 /**
  * Waits for element matching the locator to be clickable
  *
  * @param locator by id, name, css, xpath, link_text, partial_link_text, tag_name
  * @param timeout amount of time the WebDriver must wait for an asynchronous script
  * @return type of WebElement
  */
 public static WebElement waitForClickAbility(By locator, int timeout) {
   WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeout));
   return wait.until(ExpectedConditions.elementToBeClickable(locator));
 }

 /**
  * waits for backgrounds processes on the browser to complete
  *
  * @param timeOutInSeconds amount of time the WebDriver must wait in Seconds
  */
 public static void waitForPageToLoad(long timeOutInSeconds) {
   ExpectedCondition<Boolean> expectation = driver -> {
     if (driver != null) {
       return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
     }
     return null;
   };
   try {
     WebDriverWait wait = new WebDriverWait(Driver.get(), Duration.ofSeconds(timeOutInSeconds));
     wait.until(expectation);
   } catch (Exception error) {
     error.printStackTrace();
   }
 }

 /**
  * Verifies whether the element matching the provided locator is displayed on page
  *
  * @param by checking with different kind of locator xpath, name, id, css, etc.
  * @throws AssertionError if the element matching the provided locator is not found or not displayed
  */
 public static void verifyElementDisplayed(By by) {
   try {
     Assert.assertTrue("Element not visible: " + by, Driver.get().findElement(by).isDisplayed());
   } catch (NoSuchElementException e) {
     e.printStackTrace();
     Assert.fail("Element not found: " + by);
   }
 }

 /**
  * Verifies whether the element matching the provided locator is NOT displayed on page
  *
  * @param by checking with different kind of locator xpath, name, id, css, etc.
  * @throws AssertionError the element matching the provided locator is displayed
  */
 public static void verifyElementNotDisplayed(By by) {
   try {
     Assert.assertFalse("Element should not be visible: " + by, Driver.get().findElement(by).isDisplayed());
   } catch (NoSuchElementException e) {
     e.printStackTrace();
   }
 }


 /**
  * Verifies whether the element is displayed on page
  *
  * @param element web element find by locators in the website
  * @throws AssertionError if the element is not found or not displayed
  */
 public static void verifyElementDisplayed(WebElement element) {
   try {
     Assert.assertTrue("Element not visible: " + element, element.isDisplayed());
   } catch (NoSuchElementException e) {
     e.printStackTrace();
     Assert.fail("Element not found: " + element);
   }
 }


 /**
  * Waits for element to be not stale
  *
  * @param element web element find by locators in the website
  */
 public static void waitForStaleElement(WebElement element) {
   int y = 0;
   while (y <= 15) {
     if (y == 1)
       try {
         element.isDisplayed();
         break;
       } catch (WebDriverException st) {
         y++;
         try {
           Thread.sleep(300);
         } catch (InterruptedException e) {
           Thread.currentThread().interrupt();
           e.printStackTrace();
         }
       }
   }
 }


 /**
  * Clicks on an element using JavaScript
  *
  * @param element web element find by locators in the website
  */
 public static void clickWithJS(WebElement element) {
   ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
   ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].click();", element);
 }


 /**
  * Scrolls down to an element using JavaScript
  *
  * @param element web element find by locators in the website
  */
 public static void scrollToElement(WebElement element) {
   ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].scrollIntoView(true);", element);
 }

 /**
  * Performs double click action on an element
  *
  * @param element web element find by locators in the website
  */
 public static void doubleClick(WebElement element) {
   new Actions(Driver.get()).doubleClick(element).build().perform();
 }

 /**
  * Changes the HTML attribute of a Web Element to the given value using JavaScript
  *
  * @param element        web element find by locators in the website
  * @param attributeName  name of the attribute of html element name="subject"
  * @param attributeValue value of the attribute of html element value="HTML"
  */
 public static void setAttribute(WebElement element, String attributeName, String attributeValue) {
   ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, attributeValue);
 }

 /**
  * Highlights an element by changing its background and border color
  *
  * @param element web element find by locators in the website
  */
 public static void highlight(WebElement element) {
   ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
   waitFor(1);
   ((JavascriptExecutor) Driver.get()).executeScript("arguments[0].removeAttribute('style', 'background: yellow; border: 2px solid red;');", element);
 }

 /**
  * Checks or unchecks given checkbox
  *
  * @param element web element find by locators in the website
  * @param check   checkbox checked or not true-false
  */
 public static void selectCheckBox(WebElement element, boolean check) {
   if (check) {
     if (!element.isSelected()) {
       element.click();
     }
   } else {
     if (element.isSelected()) {
       element.click();
     }
   }
 }

 /**
  * attempts to click on provided element until given time runs out
  *
  * @param element web element find by locators in the website
  * @param timeout amount of time the WebDriver must wait for an asynchronous script
  */
 public static void clickWithTimeOut(WebElement element, int timeout) {
   for (int i = 0; i < timeout; i++) {
     try {
       element.click();
       return;
     } catch (WebDriverException e) {
       waitFor(1);
     }
   }
 }

 /**
  * executes the given JavaScript command on given web element
  *
  * @param element web element find by locators in the website
  */
 public static void executeJSCommand(WebElement element, String command) {
   JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
   jse.executeScript(command, element);

 }

 /**
  * executes the given JavaScript command on given web element
  *
  * @param command js command enter here ("arguments[0].click();", button)
  */
 public static void executeJSCommand(String command) {
   JavascriptExecutor jse = (JavascriptExecutor) Driver.get();
   jse.executeScript(command);
 }


 /**
  * This method will recover in case of exception after unsuccessful the click,
  * and will try to click on element again.
  *
  * @param by       id, name, tag name, xpath, css etc.
  * @param attempts how many times want to try
  */
 public static void clickWithWait(By by, int attempts) {
   int counter = 0;
   //click on element as many as you specified in attempts parameter
   while (counter < attempts) {
     try {
       //selenium must look for element again
       clickWithJS(Driver.get().findElement(by));
       //if click is successful - then break
       break;
     } catch (WebDriverException e) {
       //if click failed
       //print exception
       //print attempt
       e.printStackTrace();
       ++counter;
       //wait for 1 second, and try to click again
       waitFor(1);
     }
   }
 }

 /**
  * Checks that an element is present on the DOM of a page. This does not
  * necessarily mean that the element is visible.
  *
  * @param by   id, name, tag name, xpath, css etc.
  * @param time duration of waiting time
  */
 public static void waitForPresenceOfElement(By by, long time) {
   new WebDriverWait(Driver.get(), Duration.ofSeconds(time)).until(ExpectedConditions.presenceOfElementLocated(by));
 }
}

