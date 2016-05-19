
// Internal Class
import dataOptimization.Database;
import dataOptimization.ImageCheck;
import dataOptimization.SetFileDetails;
import dataOptimization.SetInternalBrowser;
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

//@SuppressWarnings("unused")
public class MainPageCheck
{
	/**
	 * INTRODUCTION:
	 * 
	 * IMPORTANT NOTE: Please change variables below this
	 */

	// File Directory
	//private String fileLocation = "C:/Users/User1/Documents/jjavier/";

	// Primary URL link. Location of the main home page where all the links will be extracted.
	private String yourBaseUrl = "http://www.lancome.fr/";

	// All links and string that will not be included in the URLs that the code will cycle through for the image check
	private String[] exclusionArray =
	{ "javascript", "cookies", "index", "index", "#", ".be", ".dk", "spain", ".gr", ".it", ".lu", ".nl", ".no", ".at",
			".pl", ".pt", ".ru", ".fi", ".se", ".ch", ".tr", ".co.uk", ".de", ".au", ".cn", ".hk", ".in", ".jp",
			".com.tw", "-usa", ".my", ".sg", ".co.kr", ".co.th", "youtube", "twitter", "facebook", "store-locator",
			".ca", ".br", ".ar", "tel:", "google", "instagram", "SendToFriend", "pinterest", "WishList-Add",
			"Wishlist-Add", "consignesdetri" };

	// Values for Desktop Testing: staticPlatform = Windows 10, Windows 8, Windows 7, Windows XP, OS X [version_no. e.g. 10.11. OS X 10.11] 
	private String staticPlatform = "Windows 10";
	private String staticBrowserVersion = "20.0";
	private String staticScreenResolution = "1920x1080";

	// set Browser to: firefox, chrome, chromeMobile, safari
	private String setBrowserString = "firefox";

	// Values for Mobile Emulator Testing
	private String staticDeviceName = "Google Nexus 5";

	// set Chrome Driver Location
	private String setChromeDriverLocation = "C:/Users/User1/Downloads/chromedriver_win32/chromedriver.exe";

	private static String dbName = "LancomeFr";

	/**
	 * IMPORTANT NOTE: Please change variables above this
	 */

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/" + dbName;
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	public static WebDriver driver;
	public static FileWriter file;
	public Database db;
	public SetInternalBrowser browser;
	public UrlCheck mainUrlCheck;

	private String baseUrl;
	//private String fileName;
	public int count = 0;

	@Before
	public void setUp() throws Exception
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

	@Test
	public void mainCode() throws Exception
	{
		// Open Browser and Navigate to Page
		driver.get(baseUrl);

		// Create the file in directory with first 4 chars of website url with date and time
		//SetFileDetails fileDetails = new SetFileDetails(fileLocation, baseUrl.substring(11, 15), ".xml");
		//File tempFile = new File(fileDetails.getFileName());
		//fileDetails.checkIfFileExists(tempFile);

		// Initialize Database
		db = new Database(dbName, DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);

		// Find all elements with tag name = a and store it in a list
		List<WebElement> urlList = driver.findElements(By.tagName("a"));
		try
		{
			mainUrlCheck = new UrlCheck(urlList, exclusionArray, driver, baseUrl, db);
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

		// Give a summary of URLs checked in the main page
		int urlsStored = mainUrlCheck.getUrlNo() - mainUrlCheck.getSkippedNo();
		System.out.println("Total No. of URLS: " + mainUrlCheck.getUrlNo());
		System.out.println("Total No. of Skipped URLs: " + mainUrlCheck.getSkippedNo());
		System.out.println("Total No. of Stored URLs: " + urlsStored);
		System.out.println();
		System.out.println("Starting Image Check for " + baseUrl);
		
		// Check all the images and store them in SQL in the main page
		ImageCheck ic = new ImageCheck(count, driver, db);
		
		// Start checking all Unique URLs and store images
		System.out.println("Starting Image Check for each Unique URL");

		db.selectUniqueUrlAndGetLinks("urlrepository", "URL", db, exclusionArray);
		db.selectUrlAndRunImageCheck("urlrepository", "URL", db);

		System.out.println("**END TEST**");
	}

	@After
	public void tearDown() throws Exception
	{
		try
		{
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
