import dataOptimization.Database;
import dataOptimization.StoreVariables;

public class MainExecute
{
	/**
	 * INTRODUCTION:
	 * 1. Before running this script, please make sure your SQL server is
	 * available and online.
	 * 2. Please make sure that the SQL Database has been created, and the
	 * urlrepository has been created.
	 * Below is the SQL code to create the table in the database.
	 * 
	 * CREATE TABLE IF NOT EXISTS `urlrepository` ( `RecordID` INT(11) NOT NULL
	 * AUTO_INCREMENT, `URL` text NOT NULL, PRIMARY KEY (`RecordID`) )
	 * ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
	 * 
	 * 3. This MainExecute class is where you can run all the methods found
	 * throughout the default package, as well as the dataOptimization package.
	 * 
	 * IMPORTANT NOTE: Please change ONLY the variables BELOW THIS
	 */

	// Primary URL link. Location of the main home page where all the links will be extracted.
	private static String yourBaseUrl = "http://www.kiehls.co.uk/site-map.html";

	// All links and string that will not be included in the URLs that the code will cycle through for the image check
	private static String[] exclusionArray =
	{ "javascript", "cookies", "index", "index", "#", ".be", ".dk", "spain", ".gr", ".it", ".lu", ".nl", ".no", ".at",
			".pl", ".pt", ".ru", ".fi", ".se", ".ch", ".tr", ".fr", ".de", ".au", ".cn", ".hk", ".in", ".jp", ".com.tw",
			"-usa", ".my", ".sg", ".co.kr", ".co.th", "youtube", "twitter", "facebook", "store-locator", ".ca", ".br",
			".ar", "tel:", "google", "instagram", "SendToFriend", "pinterest", "WishList-Add", "Wishlist-Add",
			"consignesdetri", "my-account", "IncludeSignIn", "https", "gift-finder-pages", "wishlist", "contact",
			"GiftRegistry", "ProductFinder-Quiz", "csort", "Review", "ProductFinder-BeautyConsultation", "loreal",
			"search?", "Lancome-RefinementsShow?", "pdf", "register", "customer-service", "sitemap", "india", ".id",
			"kiehls-me", ".es", "kiehlstimes", "mailto:" };

	// Only the following values are allowed: firefox, chrome, chromeMobile, safari
	private static String setBrowserString = "firefox";

	// Values for Desktop Testing (if setBrowserString = chrome or firefox): staticPlatform = Windows 10, Windows 8, Windows 7, Windows XP, OS X [version_no. e.g. 10.11. OS X 10.11] 
	private static String staticPlatform = "Windows 10";
	private static String staticBrowserVersion = "20.0";
	private static String staticScreenResolution = "1920x1080";

	// Values for Mobile Emulator Testing (if setBrowserString = chromeMobile)
	private static String staticDeviceName = "Google Nexus 5";

	// Set Chrome Driver Location
	private static String chromeDriverLocation = "C:/Users/User1/Downloads/chromedriver_win32/chromedriver.exe";

	// Set Database Information
	private static String dbName = "kiehlsmobiledummy";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/" + dbName;
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	// This will indicate if you want to do URL Check or Image Check on the Main URL indicated above (e.g. yourBaseUrl). Allowable Values = yes, skip, imagecheckonly, urlcheckonly
	private static String runMainUrlCheck = "yes";

	// Indicate URL to start image check from
	private static String startingUrl = "http://www.kiehls.co.uk/on/demandware.store/Sites-kiehls-uk-Site/en_GB/Page-Show?cid=cookie-policy";
	
	/**
	 * IMPORTANT NOTE: Please change ONLY the variables ABOVE THIS
	 */

	// Set URL Repository Table Name and Column Name
	private static String urlColumnName = "URL";
	private static String urlTableName = "urlrepository";

	public static void main(String[] args)
	{
		// Setting all global variables for the run
		StoreVariables.setGlobalUrlVariables(yourBaseUrl, exclusionArray);
		StoreVariables.setGlobalDeviceVariables(staticPlatform, staticBrowserVersion, staticScreenResolution,
				setBrowserString, staticDeviceName, chromeDriverLocation);
		StoreVariables.setGlobalDatabaseVariables(dbName, DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);

		// Initialize Database
		Database.Database(dbName, DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);

		try
		{
			MainPageCheck.setUp();
			MainPageCheck.mainCode(runMainUrlCheck);

			// For getting all the links in all of the available urls in the urlrepository
			//Database.selectUniqueUrlAndGetLinks(exclusionArray);

			// Doing image check starting from a specific URL in the database. 
			//Database.selectUrlAndRunImageCheck(urlTableName, urlColumnName, startingUrl);

			MainPageCheck.tearDown();
		}
		catch (Exception e)
		{
			System.out.println("Exception found: MainExecute, main - Exception: ");
			e.printStackTrace();
		}
	}

}
