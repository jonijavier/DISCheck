import dataOptimization.StoreVariables;

public class MainExecute
{
	/**
	 * INTRODUCTION:
	 * 
	 * IMPORTANT NOTE: Please change ONLY the variables below this
	 */

	// Primary URL link. Location of the main home page where all the links will be extracted.
	private static String yourBaseUrl = "http://www.lancome.fr/";

	// All links and string that will not be included in the URLs that the code will cycle through for the image check
	private static String[] exclusionArray =
	{ "javascript", "cookies", "index", "index", "#", ".be", ".dk", "spain", ".gr", ".it", ".lu", ".nl", ".no", ".at",
			".pl", ".pt", ".ru", ".fi", ".se", ".ch", ".tr", ".co.uk", ".de", ".au", ".cn", ".hk", ".in", ".jp",
			".com.tw", "-usa", ".my", ".sg", ".co.kr", ".co.th", "youtube", "twitter", "facebook", "store-locator",
			".ca", ".br", ".ar", "tel:", "google", "instagram", "SendToFriend", "pinterest", "WishList-Add",
			"Wishlist-Add", "consignesdetri", "my-account", "IncludeSignIn", "https", "gift-finder-pages", "wishlist",
			"contact", "GiftRegistry", "ProductFinder-Quiz", "csort", "Review", "ProductFinder-BeautyConsultation",
			"loreal", "search?", "Lancome-RefinementShow?", "pdf", "register", "customer-service", "sitemap" };

	// Values for Desktop Testing: staticPlatform = Windows 10, Windows 8, Windows 7, Windows XP, OS X [version_no. e.g. 10.11. OS X 10.11] 
	private static String staticPlatform = "Windows 10";
	private static String staticBrowserVersion = "20.0";
	private static String staticScreenResolution = "1920x1080";

	// Only the following values are allowed: firefox, chrome, chromeMobile, safari
	private static String setBrowserString = "firefox";

	// Values for Mobile Emulator Testing
	private static String staticDeviceName = "Google Nexus 5";

	// Set Chrome Driver Location
	private static String chromeDriverLocation = "C:/Users/User1/Downloads/chromedriver_win32/chromedriver.exe";

	// Set Database Information
	private static String dbName = "LancomeFr";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/" + dbName;
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "";

	/**
	 * IMPORTANT NOTE: Please change ONLY the variables above this
	 */

	public static void main(String[] args)
	{
		// Setting all global variables for the run
		StoreVariables.setGlobalUrlVariables(yourBaseUrl, exclusionArray);
		StoreVariables.setGlobalDeviceVariables(staticPlatform, staticBrowserVersion, staticScreenResolution,
				setBrowserString, staticDeviceName, chromeDriverLocation);
		StoreVariables.setGlobalDatabaseVariables(dbName, DB_DRIVER, DB_CONNECTION, DB_USER, DB_PASSWORD);

		try
		{
			MainPageCheck.setUp();
			MainPageCheck.mainCode();
			MainPageCheck.tearDown();
		}
		catch (Exception e)
		{
			System.out.println("Exception found: MainExecute, main - Exception: ");
			e.printStackTrace();
		}
	}

}
