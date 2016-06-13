package dataOptimization;

import java.util.Arrays;

// Introduction: This class allows the user to set the global variables for the run. These variables will be use all throughout the project.
public class StoreVariables
{
	// Base Url and Exclusions Set-up
	public static String globalBaseUrl;
	public static String[] globalExclusionArray;

	// Device Set-up 
	public static String globalStaticPlatform;
	public static String globalStaticBrowserVersion;
	public static String globalStaticScreenResolution;
	public static String globalSetBrowserString;
	public static String globalStaticDeviceName;
	public static String globalChromeDriverLocation;
	public static String globalSetDevice = "desktop";

	// Database Set-up
	public static String globalDbName;
	public static String global_DB_DRIVER;
	public static String global_DB_CONNECTION;
	public static String global_DB_USER;
	public static String global_DB_PASSWORD;
	
	// Other Variables 
	public static int globalCount;
	public static String globalTempTableName;

	// Set Global Variables for baseUrl and ExclusionArray
	public static void setGlobalUrlVariables(String baseUrl, String[] exclusionArray)
	{
		System.out.println("Setting Global Variables for Url and Exclusion Array...");
		System.out.println("Global Variable globalBaseUrl Change: " + globalBaseUrl + " to " + baseUrl);
		System.out.println("Global Variable globalExclusionArray Change: " + globalExclusionArray + " to " + Arrays.toString(globalExclusionArray));
		
		globalBaseUrl = baseUrl;
		globalExclusionArray = exclusionArray;
		
		System.out.println("Change successfully made for globalBaseUrl: " + globalBaseUrl);
		System.out.println("Change successfully made for globalExclusionArray: " + globalExclusionArray);
	}

	// Set Global Variables for Device Set-up
	public static void setGlobalDeviceVariables(String staticPlatform, String staticBrowserVersion,
			String staticScreenResolution, String setBrowserString, String staticDeviceName,
			String chromeDriverLocation)
	{
		System.out.println("Setting Global Variables for Device Set-up...");
		System.out.println("Global Variable globalStaticPlatform Change: " + globalStaticPlatform + " to " + staticPlatform);
		System.out.println("Global Variable globalStaticBrowserVersion Change: " + globalStaticBrowserVersion + " to " + staticBrowserVersion);
		System.out.println("Global Variable globalStaticScreenResolution Change: " + globalStaticScreenResolution + " to " + staticScreenResolution);
		System.out.println("Global Variable globalSetBrowserString Change: " + globalSetBrowserString + " to " + setBrowserString);
		System.out.println("Global Variable globalStaticDeviceName Change: " + globalStaticDeviceName + " to " + staticDeviceName);
		System.out.println("Global Variable globalChromeDriverLocation Change: " + globalChromeDriverLocation + " to " + chromeDriverLocation);
		
		globalStaticPlatform = staticPlatform;
		globalStaticBrowserVersion = staticBrowserVersion;
		globalStaticScreenResolution = staticScreenResolution;
		globalSetBrowserString = setBrowserString;
		globalStaticDeviceName = staticDeviceName;
		globalChromeDriverLocation = chromeDriverLocation;
		
		if (globalSetBrowserString.equals("chromeMobile"))
		{
			globalSetDevice = "mobile";
		}
		
		System.out.println("Change successfully made for globalStaticPlatform: " + globalStaticPlatform);
		System.out.println("Change successfully made for globalStaticBrowserVersion: " + globalStaticBrowserVersion);
		System.out.println("Change successfully made for globalStaticScreenResolution: " + globalStaticScreenResolution);
		System.out.println("Change successfully made for globalSetBrowserString: " + globalSetBrowserString);
		System.out.println("Change successfully made for globalStaticDeviceName: " + globalStaticDeviceName);
		System.out.println("Change successfully made for globalChromeDriverLocation: " + globalChromeDriverLocation);
		System.out.println("Change successfulle made for globalSetDevice: " + globalSetDevice);
	}

	// Set Global Variables for Database Set-up
	public static void setGlobalDatabaseVariables(String dbName, String DB_DRIVER, String DB_CONNECTION, String DB_USER, String DB_PASSWORD)
	{
		System.out.println("Setting Global Variables for Database Set-up...");
		System.out.println("Global Variable globalDbName Change: " + globalDbName + " to " + dbName);
		System.out.println("Global Variable global_DB_DRIVER Change: " + global_DB_DRIVER + " to " + DB_DRIVER);
		System.out.println("Global Variable global_DB_CONNECTION Change: " + global_DB_CONNECTION + " to " + DB_CONNECTION);
		System.out.println("Global Variable global_DB_USER Change: " + global_DB_USER + " to " + DB_USER);
		System.out.println("Global Variable global_DB_PASSWORD Change: " + global_DB_PASSWORD + " to " + DB_PASSWORD);
	
		globalDbName = dbName;
		global_DB_DRIVER = DB_DRIVER;
		global_DB_CONNECTION = DB_CONNECTION;
		global_DB_USER = DB_USER;
		global_DB_PASSWORD = DB_PASSWORD;
		
		System.out.println("Change successfully made for globalDbName: " + globalDbName);
		System.out.println("Change successfully made for global_DB_DRIVER: " + global_DB_DRIVER);
		System.out.println("Change successfully made for global_DB_CONNECTION: " + global_DB_CONNECTION);
		System.out.println("Change successfully made for global_DB_USER: " + global_DB_USER);
		System.out.println("Change successfully made for global_DB_PASSWORD: " + global_DB_PASSWORD);
	}

	// Setting a single string variable
	public static void setSingleStringVariable(String variableName, String variableSetData)
	{
		switch (variableName)
		{
			case "baseurl":
				System.out.println(
						"Global Variable: " + variableName + " Change: " + globalBaseUrl + " to " + variableSetData);
				globalBaseUrl = variableSetData;

			case "staticplatform":
				System.out.println("Global Variable: " + variableName + " Change: " + globalStaticPlatform + " to "
						+ variableSetData);
				globalStaticPlatform = variableSetData;
			case "staticbrowserversion":
				System.out.println("Global Variable: " + variableName + " Change: " + globalStaticBrowserVersion
						+ " to " + variableSetData);
				globalStaticBrowserVersion = variableSetData;
			case "staticscreenresolution":
				System.out.println("Global Variable: " + variableName + " Change: " + globalStaticScreenResolution
						+ " to " + variableSetData);
				globalStaticScreenResolution = variableSetData;
			case "setbrowserstring":
				System.out.println("Global Variable: " + variableName + " Change: " + globalSetBrowserString + " to "
						+ variableSetData);
				globalSetBrowserString = variableSetData;
			case "staticdevicename":
				System.out.println("Global Variable: " + variableName + " Change: " + globalStaticDeviceName + " to "
						+ variableSetData);
				globalStaticDeviceName = variableSetData;
			case "chromedriverlocation":
				System.out.println("Global Variable: " + variableName + " Change: " + globalChromeDriverLocation
						+ " to " + variableSetData);
				globalChromeDriverLocation = variableSetData;
			case "db_driver":
				System.out.println("Global Variable: " + variableName + " Change: " + global_DB_DRIVER
						+ " to " + variableSetData);
				global_DB_DRIVER = variableSetData;
			case "db_connection":
				System.out.println("Global Variable: " + variableName + " Change: " + global_DB_CONNECTION
						+ " to " + variableSetData);
				global_DB_CONNECTION = variableSetData;
			case "db_user":
				System.out.println("Global Variable: " + variableName + " Change: " + global_DB_USER
						+ " to " + variableSetData);
				global_DB_USER = variableSetData;
			case "db_password":
				System.out.println("Global Variable: " + variableName + " Change: " + global_DB_PASSWORD
						+ " to " + variableSetData);
				global_DB_PASSWORD = variableSetData;
			default:
				System.out.println("Sorry, the global variable: " + variableName
						+ " does not exist. Cannot update it with: " + variableSetData);

		}
	}
	
	// Setting a single integer variable
	public static void setSingleIntegerVariable(String variableName, int variableSetData)
	{
		switch (variableName)
		{
			case "count":
				System.out.println("Global Variable: " + variableName + " Change: " + globalCount
						+ " to " + variableSetData);
				globalCount = variableSetData;
			default:
				System.out.println("Sorry, the global variable: " + variableName
						+ " does not exist. Cannot update it with: " + variableSetData);
		}
	}
	
	// Set Array for Exclusion Criteria
	public void setExclusionCriteria (String[] exclusionArray)
	{
		System.out.println("Global Variable globalExclusionArray Change: " + Arrays.toString(globalExclusionArray) + " to " + Arrays.toString(exclusionArray));
		
		globalExclusionArray = exclusionArray;
		
		System.out.println("Change successfully made for globalExclusionArray: " + Arrays.toString(globalExclusionArray));
	}
	
	// Set Global Temporary Table Name for Table Creation Purposes
	public static void setGlobalTempTableName(String fullSrc)
	{
		int startIndex;
		if (fullSrc.contains("www."))
		{
			startIndex = fullSrc.lastIndexOf("www.") - 1;
		}
		else
		{
			startIndex = 0;
		}

		int endIndex;
		if (fullSrc.length() > 60)
		{
			endIndex = 59;
		}
		else
		{
			endIndex = fullSrc.length() - 1;
		}
		String newTableName = fullSrc.substring(startIndex, endIndex);

		System.out.println("New Table Name before replace: " + newTableName);

		newTableName = newTableName.replace(".", "");
		newTableName = newTableName.replace("/", "");
		newTableName = newTableName.replace("_", "");
		newTableName = newTableName.replace("-", "");
		newTableName = newTableName.replace("%", "");
		newTableName = newTableName.replace("?", "");
		newTableName = newTableName.replace("=", "");
		newTableName = newTableName.replace("&", "");
		newTableName = newTableName.replace("$", "");
		newTableName = newTableName.replace("#", "");
		newTableName = newTableName.replace("+", "");
		newTableName = newTableName.replace(":", "");
		
		System.out.println("fullSrc: " + fullSrc + " StartIndex: " + startIndex + " End Index: " + endIndex);
		System.out.println("New Table Name: " + newTableName);
		System.out.println();
		
		globalTempTableName = newTableName;
	}

	// Get Global Variable Methods
	public static String getGlobalBaseUrl()
	{
		return globalBaseUrl;
	}

	public static String[] getGlobalExclusionArray()
	{
		return globalExclusionArray;
	}

	public static String getGlobalStaticPlatform()
	{
		return globalStaticPlatform;
	}

	public static String getGlobalStaticBrowserVersion()
	{
		return globalStaticBrowserVersion;
	}

	public static String getGlobalStaticScreenResolution()
	{
		return globalStaticScreenResolution;
	}

	public static String getGlobalSetBrowserString()
	{
		return globalSetBrowserString;
	}

	public static String getGlobalStaticDeviceName()
	{
		return globalStaticDeviceName;
	}

	public static String getGlobalChromeDriverLocation()
	{
		return globalChromeDriverLocation;
	}
	
	public static String getGlobalDbName()
	{
		return globalDbName;
	}
	
	public static String getGlobal_DB_DRIVER()
	{
		return global_DB_DRIVER;
	}

	public static String getGlobal_DB_CONNECTION()
	{
		return global_DB_CONNECTION;
	}

	public static String getGlobal_DB_USER()
	{
		return global_DB_USER;
	}

	public static String getGlobal_DB_PASSWORD()
	{
		return global_DB_PASSWORD;
	}
	
	public static String getGlobalTempTableName()
	{
		return globalTempTableName;
	}
	
	public static String getGlobalSetDevice()
	{
		return globalSetDevice;
	}
}
