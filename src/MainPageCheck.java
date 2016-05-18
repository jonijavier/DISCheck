
import org.junit.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;

import testForOptimizedImages.imageCount;
import testForOptimizedImages.internalSetBrowser;

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

	/**
	 * IMPORTANT NOTE: Please change variables above this
	 */

	public static WebDriver driver;
	private String baseUrl;
	private String fileName;
	private StringBuffer verificationErrors = new StringBuffer();
	private int count = 0;
	private int skipListCount = 0;
	private int checkedNo = 0;
	private int skippedNo = 0;
	private int urlNo = 0;
	private boolean checkSkipped = false;
	private boolean dupeCheck = false;
	private boolean uniqueCheck = false;
	public static FileWriter file;

	@Before
	public void setUp() throws Exception
	{
		System.out.println("**START TEST**");

		internalSetBrowser browser = new internalSetBrowser(setBrowserString, driver, setChromeDriverLocation,
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

		// Create the file name by appending the first 4 chars of the url with the date and time
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		Date date = new Date();
		fileName = baseUrl.substring(11, 15) + "_" + dateFormat.format(date);

		// Create the file in directory with first 4 chars of website url with date and time
		File tempFile = new File(fileLocation + fileName + ".xml");

		// Check if file exists
		if (tempFile.exists())
		{
			// do nothing
		}
		else
		{
			file = new FileWriter(tempFile);
		}

		// Create XML structure
		Element company = new Element("company");
		Document doc = new Document(company);
		doc.setRootElement(company);

		// Creates an XML outputter, the file to be saved into
		XMLOutputter xmlOutput = new XMLOutputter();

		// Start initial image count on baseUrl page
		imageCount ic = new imageCount(count, driver, doc, xmlOutput, file);

		// Find all elements with tag name = a
		List<WebElement> urlList = driver.findElements(By.tagName("a"));

		// Array to store the elements with href
		List<String> realUrlList = new ArrayList<String>();
		try
		{
			// loops to segregate the non-url from the urls
			for (WebElement aHrefElement : urlList)
			{
				// if href is null, then there is no url available
				if (aHrefElement.getAttribute("href") == null)
				{
					// do nothing if there is no url available
				}
				else
				{
					// add url to array
					realUrlList.add(aHrefElement.getAttribute("href").toString());
					urlNo++;
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("JTJ//ERROR// - Exception: ");
			e.printStackTrace();
		}

		// Print the total no. of all urls
		System.out.println("Total no. of urls are " + urlNo);
		System.out.println("Starting test on specific pages...");

		// Array to store the unique urls
		List<String> uniqueUrlList = new ArrayList<String>();
		uniqueUrlList.add(yourBaseUrl);

		// Cycle through all the pages
		for (String temp : realUrlList)
		{
			// internal count of all the images
			count = imageCount.getInternalCountClass();
			checkSkipped = false;

			try
			{
				// array of all the exclusions from the url checks based on a string found in the url
				List<String> skipList = new ArrayList<String>(Arrays.asList(exclusionArrayList));

				// count for all the urls skipped, including duplicate urls
				skipListCount = 0;

				// runs through all the exclusions skiplist
				for (String skipWord : skipList)
				{
					if (temp.contains(skipList.get(skipListCount)) == true)
					{
						System.out.println("Skipping url: " + temp);
						checkSkipped = true;
					}
					else if (temp.equals(baseUrl))
					{
						System.out.println("Skipping url: " + temp);
						checkSkipped = true;
					}
					else
					{
						checkSkipped = false;
					}

					if (checkSkipped == true)
					{
						skippedNo++;
						break;
					}
					// this count is for the array
					skipListCount++;
				}

				if (checkSkipped == false)
				{
					// Console print
					System.out.println("Checking url: " + temp);

					try
					{
						// Runs through all the unique URLs and checks against all.
						for (String urlStored : uniqueUrlList)
						{
							// if the URL is equal to a stored URL, it stops the checking
							if (temp.equals(urlStored.toString()))
							{
								System.out.println("Duplicate url, Skipping this url: " + temp);
								skippedNo++;
								dupeCheck = true;
								break;
							}
							else
							{
								// do nothing
							}
						}

						if (dupeCheck == true)
						{
							// do nothing
							uniqueCheck = false;
							dupeCheck = false;
						}
						else
						{
							uniqueUrlList.add(temp);
							uniqueCheck = true;
						}
					}
					catch (Exception e)
					{
						System.out.println("JTJ//ERROR// - Exception: ");
						e.printStackTrace();
					}
				}
				else
				{
					checkSkipped = false;
				}

				// If the Url is unique and not a duplicate, go through image count and check
				if (uniqueCheck == true)
				{
					driver.navigate().to(temp);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

					System.out.println("Navigating to: " + temp);
					// Image counting and checking
					imageCount ic2 = new imageCount(count, driver, doc, xmlOutput, file);

					checkedNo++;
					uniqueCheck = false;
				}
				else
				{
					// do nothing
					uniqueCheck = false;
				}

			}
			catch (Exception e)
			{
				System.out.println("JTJ//ERROR// - Exception caught!");
				System.out.println("Skipping url: " + temp);
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}

		try
		{
			// put it into the XML file
			xmlOutput.setFormat(Format.getPrettyFormat());
			xmlOutput.output(doc, file);
		}
		catch (Exception e)
		{
			// Show exception
			System.out.println("JTJ//ERROR// - Exception!");
			e.printStackTrace();
		}

		System.out.println("Checked Urls: " + checkedNo);
		System.out.println("Skipped Urls: " + skippedNo);

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
