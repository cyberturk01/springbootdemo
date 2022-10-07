package com.yigit.web.utilities;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;

/**
* This class is mostly used for UI TESTING
* Selenium needs to connect browser with a driver chrome, firefox, safari or edge
* This class provides web driver with the help of Bonigarcia dependency
* Multiple thread can be created and parallel testing is possible
*/
@Slf4j
public class Driver {

 private Driver() {
 }

 /**
  * InheritableThreadLocal  --> this is like a container, bag, pool.
  * in this pool we can have separate objects for each thread
  * for each thread, in InheritableThreadLocal we can have separate object for that thread
  * driver class will provide separate web driver object per thread
  */
 private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();
 private static final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
 private static final ChromeOptions chromeOptions = new ChromeOptions();
 private static final FirefoxOptions firefoxOptions = new FirefoxOptions();
 private static final EdgeOptions edgeOptions = new EdgeOptions();

 /**
  * Creates a Web driver and add to pool.<p>
  *
  ** Usage of Browsers With Option:<p>
  ** Add browser name and option to the configuration.properties file<p>
  *
  ** browser=chromeWithOptions<p>
  ** addBrowserOptions=--headless,--verbose,--allow-insecure-localhost,--disable-dev-shm-usage,--no-sandbox,--disable-gpu<p>
  *
  ** Browsers Supported: <p>
  * chromeWithOptions, fireFoxWithOptions, edgeWithOptions, chrome, chrome-headless, chrome-docker, firefox, firefox-headless, edge, edge-headless, ie and safari<p>
  ** Browser Options:<p>
  *--headless: Starts Browser in Headless mode.<p>
  *--verbose: Starts driver in the command prompt/terminal with verbose logging<p>
  *--allow-insecure-localhost: Enables TLS/SSL errors on localhost to be ignored (no blocking of requests)<p>
  *--disable-dev-shm-usage: The /dev/shm partition is too small in certain VM environments, causing Chrome to fail or crash<p>
  *--no-sandbox: Disables the sandbox for all process types that are normally sandboxed. Meant to be used as a browser-level switch for testing purposes only.<p>
  *--disable-gpu: Disables GPU hardware acceleration. If software renderer is not in place, then the GPU process won't launch.<p>
  *--disable-extensions: Disables extensions.<p>
  *--start-maximized: Starts the browser maximized, regardless of any previous settings.<p>
  *--start-in-incognito: Starts the shell with the profile in incognito mode<p>
  *
  *
  * If user wants to test with chrome-docker should download the docker image and afterwards should clean <p>
  * --> docker run -d -p 4444:4444 --shm-size="2g" selenium/standalone-chrome:latest
  *
  *
  * @return Web driver
  */
 public static WebDriver get() {
   if (driverPool.get() == null) {
     String browser = System.getProperty("browser") != null ? System.getProperty("browser") : ConfigurationReader.get("browser");
     if (browser != null) {
       switch (browser) {
         case "chrome":
           System.setProperty("webdriver.chrome.whitelistedIps", "");
           WebDriverManager.chromedriver().setup();
           driverPool.set(new ChromeDriver());
           break;
         case "chromeWithOptions":
           desiredCapabilities.setBrowserName("chrome");
           desiredCapabilities.setCapability("platform", Platform.ANY);
           chromeOptions.addArguments(ConfigurationReader.getValues("addBrowserOptions"));
           chromeOptions.merge(desiredCapabilities);
           System.setProperty("webdriver.chrome.whitelistedIps", "");
           WebDriverManager.chromedriver().setup();
           driverPool.set(new ChromeDriver(chromeOptions));
           break;
         case "firefox":
           WebDriverManager.firefoxdriver().setup();
           driverPool.set(new FirefoxDriver());
           break;
         case "firefox-headless":
           WebDriverManager.firefoxdriver().setup();
           desiredCapabilities.setBrowserName("firefox");
           firefoxOptions.addArguments("--headless");
           firefoxOptions.merge(desiredCapabilities);
           driverPool.set(new FirefoxDriver(firefoxOptions));
           break;
         case "firefoxWithOptions":
           desiredCapabilities.setBrowserName("firefox");
           desiredCapabilities.setCapability("platform", Platform.ANY);
           firefoxOptions.addArguments(ConfigurationReader.getValues("addBrowserOptions"));
           firefoxOptions.merge(desiredCapabilities);
           WebDriverManager.firefoxdriver().setup();
           driverPool.set(new FirefoxDriver(firefoxOptions));
           break;
         case "edge":
           if (!System.getProperty("os.name").toLowerCase().contains("windows")){
             throw new WebDriverException("Your OS doesn't support Edge");
           }
           WebDriverManager.edgedriver().setup();
           driverPool.set(new EdgeDriver());
           break;
         case "edgeWithOptions":
           desiredCapabilities.setBrowserName("edge");
           desiredCapabilities.setCapability("platform", Platform.ANY);
           edgeOptions.addArguments(ConfigurationReader.getValues("addBrowserOptions"));
           edgeOptions.merge(desiredCapabilities);
           WebDriverManager.edgedriver().setup();
           driverPool.set(new EdgeDriver(edgeOptions));
           break;
         case "ie":
           if (!System.getProperty("os.name").toLowerCase().contains("windows")){
             throw new WebDriverException("Your OS doesn't support Internet Explorer");
           }
           WebDriverManager.iedriver().setup();
           driverPool.set(new InternetExplorerDriver());
           break;
         case "safari":
           if (!System.getProperty("os.name").toLowerCase().contains("mac")){
             throw new WebDriverException("Your OS doesn't support Safari");
           }
           WebDriverManager.getInstance(SafariDriver.class).setup();
           driverPool.set(new SafariDriver());
           break;
         case "chrome-headless":
           desiredCapabilities.setBrowserName("chrome");
           desiredCapabilities.setCapability("platform", Platform.ANY);
           chromeOptions.addArguments(
             "--verbose",
             "--headless",
             "--allow-insecure-localhost",
             "--disable-dev-shm-usage",
             "--no-sandbox",
             "--disable-gpu");
           chromeOptions.merge(desiredCapabilities);
           System.setProperty("webdriver.chrome.whitelistedIps", "");
           WebDriverManager.chromedriver().setup();
           driverPool.set(new ChromeDriver(chromeOptions));
           break;
         case "chrome-docker":
           desiredCapabilities.setCapability("platform", Platform.ANY);
           desiredCapabilities.setBrowserName("chrome");
           desiredCapabilities.setCapability("name", "ChromeTest");
           chromeOptions.addArguments(
             "--verbose",
             "--disable-gpu");
           chromeOptions.merge(desiredCapabilities);
           System.setProperty("webdriver.chrome.whitelistedIps", "");
           try {
             driverPool.set(new RemoteWebDriver(new URL("http://localhost:4444"), chromeOptions));
           } catch (MalformedURLException e) {
             e.printStackTrace();
           }
           break;
       }
     }else{
       log.error("Browser name should be provided!");
     }
   }
   return driverPool.get();
 }

 /**
  * Closes the opened driver
  */
 public static void closeDriver() {
   driverPool.get().quit();
   driverPool.remove();
 }
}
