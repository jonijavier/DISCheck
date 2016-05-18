
// Internal Class
import dataOptimization.ImageCheck;
import dataOptimization.SetFileDetails;
import dataOptimization.SetInternalBrowser;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;

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

public class MainPageCheck
{
	/**
	 * INTRODUCTION: This code visits the primary url and checks all the images
	 * from
	 * that url. The next step the code does is that it cycles through all
	 * the unique links on the primary url page.
	 * 
	 * It stores these links and cycles through each one, and performs an image
	 * check on each unique URL.
	 * 
	 * IMPORTANT NOTE: Please change variables below this
	 */

	// File Directory
	private String fileLocation = "C:/Users/User1/Documents/jjavier/";

	// Primary URL link. Location of the main home page where all the links will be extracted.
	private String yourBaseUrl = "http://www.lancome.fr/";

	// All links and string that will not be included in the URLs that the code will cycle through for the image check
	private String[] exclusionArrayList =
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
	private String setBrowserString = "chromeMobile";

	// Values for Mobile Emulator Testing
	private String staticDeviceName = "Google Nexus 5";

	// set Chrome Driver Location
	private String setChromeDriverLocation = "C:/Users/User1/Downloads/chromedriver_win32/chromedriver.exe";

	private static String dbName = "LancomeFr";

	/**
	 * IMPORTANT NOTE: Please change variables above this
	 */

	public static WebDriver driver;
	private String baseUrl;
	private String fileName;
	public static FileWriter file;

	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/" + dbName;
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	@Before
	public void setUp() throws Exception
	{
		System.out.println("**START TEST**");

		SetInternalBrowser browser = new SetInternalBrowser(setBrowserString, driver, setChromeDriverLocation,
				staticDeviceName, staticPlatform, staticBrowserVersion, staticScreenResolution);

		// Start the browser and go to URL
		baseUrl = yourBaseUrl;
		driver = browser.getWebDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Console reporting
		System.out.println("Starting Browser...");
		System.out.println("Directing to URL: " + baseUrl);
	}

	@Test
	public void testLancomeFrance() throws Exception
	{
		// Open Browser and Navigate to Page
		driver.get(baseUrl);

		// Create the file in directory with first 4 chars of website url with date and time
		SetFileDetails fileDetails = new SetFileDetails(fileLocation, baseUrl.substring(11, 15), ".xml");
		File tempFile = new File(fileDetails.getFileName());
		fileDetails.checkIfFileExists(tempFile);

		System.out.println("**END TEST**");

	}

	@After
	public void tearDown() throws Exception
	{
		driver.quit();
		
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString))
		{
			fail(verificationErrorString);
		}
	}

}
