
import dataOptimization.ImageCheck;
import dataOptimization.SetInternalBrowser;

import org.junit.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.*;
import java.io.FileWriter;
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

public class SubCatPageCheck
{
	/**
	 * ****DEPRECATED CLASS
	 * 
	 * 
	 * Introduction: This code will allow the user to set an array of URLs which
	 * the test will run through.
	 * 
	 * In each URL, the code will look for all other URLs, and direct to each
	 * one of them. The url will check the images in the secondary URLs.
	 * 
	 * Please change only the variables below this.
	 */
	// Project Name to append to the file name. Please do not include any characters or spaces. Just letters
	private String projName = "maquillage";

	// Directory where the XML file is saved 
	private String tempFileDir = "C:/Users/User1/Documents/jjavier/";

	// Array of Product Page URLs
	private String[] productPageUrls =
	{ "http://www.lancome.fr/maquillage/yeux/", "http://www.lancome.fr/maquillage/yeux/mascaras/",
			"http://www.lancome.fr/maquillage/yeux/mascaras-waterproof/",
			"http://www.lancome.fr/maquillage/yeux/ombres-a-paupieres/",
			"http://www.lancome.fr/maquillage/yeux/eyeliners-crayons-khol/",
			"http://www.lancome.fr/maquillage/yeux/sourcils/",
			"http://www.lancome.fr/maquillage/yeux/demaquillants-yeux/",
			"http://www.lancome.fr/maquillage/yeux/eye-did-it/", "http://www.lancome.fr/maquillage/yeux/coffrets/" };

	// Main Category Page and Main Site URLs
	private String mainCatPage = "http://www.lancome.fr/maquillage/";
	private String mainSiteUrl = "http://www.lancome.fr/";
	
	
	// Values for Desktop Testing: staticPlatform = Windows 10, Windows 8, Windows 7, Windows XP, OS X [version_no. e.g. 10.11. OS X 10.11] 
	private String staticPlatform = "Windows 10";
	private String staticBrowserVersion = "45.0";
	private String staticScreenResolution = "1920x1080";

	// set Browser to: firefox, chrome, chromeMobile, safari
	private String setBrowserString = "firefox";

	// Values for Mobile Emulator Testing
	private String staticDeviceName = "Google Nexus 5";

	// set Chrome Driver Location
	private String setChromeDriverLocation = "C:/Users/User1/Downloads/chromedriver_win32/chromedriver.exe";

	/**
	 * IMPORTANT NOTE: Please change only the variables above this.
	 */

	private WebDriver driver;
	private String baseUrl;
	private String fileName;
	private StringBuffer verificationErrors = new StringBuffer();
	private int count = 0;
	private int checkedNo = 0;
	private int skippedNo = 0;
	private int urlNo = 0;
	private boolean dupeCheck = false;
	private boolean uniqueCheck = false;
	public static FileWriter file;

	@Before
	public void setUp() throws Exception
	{
		System.out.println("**START TEST**");
	}

	@Test
	public void subCatPageCheck() throws Exception
	{

		// Array of all Product Page Urls that needs to be tested
		List<String> mainUrlList = new ArrayList<String>(Arrays.asList(productPageUrls));

		for (String baseUrl : mainUrlList)
		{
			SetInternalBrowser browser = new SetInternalBrowser(setBrowserString, driver, setChromeDriverLocation,
					staticDeviceName, staticPlatform, staticBrowserVersion, staticScreenResolution);

			// Start the browser and go to URL
			driver = browser.getWebDriver();

			// Console reporting
			System.out.println("Starting Browser...");
			System.out.println("Directing to URL: " + baseUrl);

			// Open Browser and Navigate to Page
			driver.get(baseUrl);

			// Create the file name by appending the first 4 chars of the url with the date and time
			DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
			Date date = new Date();
			fileName = baseUrl.substring(11, 15) + "_" + projName + "_" + dateFormat.format(date);

			// Create the file in directory with first 4 chars of website url with date and time
			File tempFile = new File(tempFileDir + fileName + ".xml");

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
			//ImageCheck ic = new ImageCheck(count, driver, doc, xmlOutput, file);

			// Find all elements with tag name = a
			List<WebElement> urlList = driver.findElements(By.tagName("a"));

			// Array to store the elements with href
			List<String> realUrlList = new ArrayList<String>();
			try
			{
				// loops to segregate the non-url from the urls
				for (WebElement imgElement : urlList)
				{
					// if href is null, then there is no url available
					if (imgElement.getAttribute("href") == null)
					{
						// do nothing if there is no url available
					}
					else
					{
						// add url to array
						realUrlList.add(imgElement.getAttribute("href").toString());
						urlNo++;
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("JTJ//ERROR// - Exception: ");
				e.printStackTrace();
			}

			// Print the total of all urls
			System.out.println("Total no. of urls are " + urlNo);
			System.out.println("Starting test on specific pages...");

			// Array to store the unique urls
			List<String> uniqueUrlList = new ArrayList<String>();
			uniqueUrlList.add(mainSiteUrl);
			uniqueUrlList.add(mainCatPage);

			// Cycle through all the pages
			for (String temp : realUrlList)
			{
				// internal count of all the images
				count = ImageCheck.getInternalCountClass();

				if (temp.contains(baseUrl) == true)
				{
					// Runs through all the unique URLs and checks against all.
					for (String urlStored : uniqueUrlList)
					{
						// if the URL is equal to a stored URL, it stops the checking
						if (temp.equals(urlStored.toString()))
						{
							System.out.println("Duplicate url, Skipping this url: " + temp);
							dupeCheck = true;
							break;
						}
						else if (temp.equals("http://www.lancome.fr/"))
						{
							System.out.println("Duplicate url, Skipping this url: " + temp);
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
						skippedNo++;
						uniqueCheck = false;
						dupeCheck = false;
					}
					else
					{
						uniqueUrlList.add(temp);
						uniqueCheck = true;
					}
				}
				else
				{
					System.out.println("Skipping: " + temp);
					uniqueUrlList.add(temp);
					skippedNo++;
				}
				
				if (uniqueCheck == true)
				{
					// Start the browser and go to URL
					driver = new FirefoxDriver();
					driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
					driver.navigate().to(temp);
					driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					System.out.println("Navigating to: " + temp);
					
					// Image counting and checking
					//ImageCheck ic2 = new ImageCheck(count, driver, doc, xmlOutput, file);

					checkedNo++;
					uniqueCheck = false;
					driver.close();
				}
				else
				{
					// do nothing
					uniqueCheck = false;
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
		}
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

