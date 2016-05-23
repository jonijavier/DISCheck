
import dataOptimization.Database;
import dataOptimization.ImageCheck;
import dataOptimization.SetFileDetails;
import dataOptimization.SetInternalBrowser;
import dataOptimization.StoreVariables;
import dataOptimization.UrlCheck;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Date;

@SuppressWarnings("unused")
public class MainPageCheck
{
	// Set-up: Url and Exclusion Array variables
	private static String yourBaseUrl = StoreVariables.getGlobalBaseUrl();
	private static String[] exclusionArray = StoreVariables.getGlobalExclusionArray();

	// Set-up: Device Platform and Browser variables
	private static String staticPlatform = StoreVariables.getGlobalStaticPlatform();
	private static String staticBrowserVersion = StoreVariables.getGlobalStaticBrowserVersion();
	private static String staticScreenResolution = StoreVariables.getGlobalStaticScreenResolution();
	private static String setBrowserString = StoreVariables.getGlobalSetBrowserString();
	private static String staticDeviceName = StoreVariables.getGlobalStaticDeviceName();

	// Set-up: Chrome Driver Location
	private static String setChromeDriverLocation = StoreVariables.getGlobalChromeDriverLocation();

	// Set-up: Database
	private static String dbName = StoreVariables.getGlobalDbName();
	private static final String DB_DRIVER = StoreVariables.getGlobal_DB_DRIVER();
	private static final String DB_CONNECTION = StoreVariables.getGlobal_DB_CONNECTION();
	private static final String DB_USER = StoreVariables.getGlobal_DB_USER();
	private static final String DB_PASSWORD = StoreVariables.getGlobal_DB_PASSWORD();

	// Set-up other variables
	private static WebDriver driver;
	private static FileWriter file;
	private static Database db;
	private static SetInternalBrowser browser;
	private static UrlCheck mainUrlCheck;

	private static String baseUrl;
	private static int count = 0;

	@Before
	public static void setUp() throws Exception
	{
		try
		{
			System.out.println("**START TEST**");

			browser = new SetInternalBrowser(setBrowserString, driver, setChromeDriverLocation, staticDeviceName,
					staticPlatform, staticBrowserVersion, staticScreenResolution);

			// Start the browser and go to URL
			baseUrl = yourBaseUrl;
			driver = browser.getWebDriver();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

			// Console reporting
			System.out.println("Starting Browser...");
			System.out.println("Directing to URL: " + baseUrl);
		}
		catch (Exception e)
		{
			System.out.println("Exception found: MainPageCheck, setUp - Exception: ");
			e.printStackTrace();
		}
	}

	@Test
	public static void mainCode() throws Exception
	{
		// Open Browser and Navigate to Page
		driver.get(baseUrl);

		// Find all elements with tag name = a and store it in a list
		List<WebElement> urlList = driver.findElements(By.tagName("a"));
		try
		{
			//mainUrlCheck = new UrlCheck(urlList, exclusionArray, driver, baseUrl);
		}
		catch (UnreachableBrowserException ube)
		{
			System.out.println("UnreachableBrowserException found: MainPageCheck, MainCode - Exception: ");
			ube.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Exception found: MainPageCheck, MainCode - Exception: ");
			e.printStackTrace();
		}

		// Check all the images and store them in SQL in the main page
		String fullSrc = baseUrl;
		
		StoreVariables.setGlobalTempTableName(fullSrc);
		ImageCheck ic = new ImageCheck(count, driver, StoreVariables.getGlobalTempTableName());
	}

	@After
	public static void tearDown() throws Exception
	{
		try
		{
			System.out.println("**END TEST**");
			
			driver = browser.getWebDriver();
			driver.quit();
		}
		catch (UnreachableBrowserException ube)
		{
			System.out.println("UnreachableBrowserException found: MainPageCheck, tearDown - Exception: ");
			ube.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Exception found: MainPageCheck, tearDown - Exception: ");
			e.printStackTrace();
		}
	}
}
