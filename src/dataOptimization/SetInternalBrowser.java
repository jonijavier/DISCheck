package dataOptimization;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

public class SetInternalBrowser
{
	public static WebDriver thisDriver;

	public SetInternalBrowser(String setBrowserString, WebDriver driver, String setChromeDriverLocation,
			String staticDeviceName, String staticPlatform, String staticBrowserVersion, String staticScreenResolution)
	{
		// Sets the browser to be used.
		if (setBrowserString.equals("chrome"))
		{
			// Setup Desired Capabilities
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();

			System.setProperty("webdriver.chrome.driver", setChromeDriverLocation);
			driver = new ChromeDriver(capabilities);

			setWebDriver(driver);
		}
		else if (setBrowserString.equals("chromeMobile"))
		{
			Map<String, String> mobileEmulation = new HashMap<String, String>();
			mobileEmulation.put("deviceName", staticDeviceName);

			Map<String, Object> chromeOptions = new HashMap<String, Object>();
			chromeOptions.put("mobileEmulation", mobileEmulation);

			// Setup Desired Capabilities for mobile run
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
			System.setProperty("webdriver.chrome.driver", setChromeDriverLocation);
			driver = new ChromeDriver(capabilities);

			setWebDriver(driver);
		}
		else if (setBrowserString.equals("firefox"))
		{
			// Setup Desired Capabilities
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("platform", staticPlatform);
			capabilities.setCapability("version", staticBrowserVersion);
			capabilities.setCapability("screenResolution", staticScreenResolution);

			driver = new FirefoxDriver(capabilities);

			setWebDriver(driver);
		}
		else if (setBrowserString.equals("safari"))
		{
			// Setup Desired Capabilities
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setCapability("platform", staticPlatform);
			capabilities.setCapability("version", staticBrowserVersion);
			capabilities.setCapability("screenResolution", staticScreenResolution);

			driver = new SafariDriver();

			setWebDriver(driver);
		}
		else
		{
			// Setup Desired Capabilities
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("platform", staticPlatform);
			capabilities.setCapability("version", staticBrowserVersion);
			capabilities.setCapability("screenResolution", staticScreenResolution);

			driver = new FirefoxDriver(capabilities);

			setWebDriver(driver);
		}
	}
	
	public static void quitWebDriver()
	{
		thisDriver.quit();
	}

	public void setWebDriver(WebDriver driver)
	{
		thisDriver = driver;
	}

	public static WebDriver getWebDriver()
	{
		return thisDriver;
	}
}
