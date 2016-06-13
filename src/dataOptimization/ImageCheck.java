package dataOptimization;

import org.openqa.selenium.*;

import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URL;
import java.net.URLConnection;

public class ImageCheck
{
	private int invalidImageCount;
	private static int internalCount;
	private static URLConnection urlConnection;
	private int imgSize = 0;

	private String fullSrc = "";
	private String imgName = "";
	private int startIndex = 0;
	private int endIndex = 0;

	// For SQL Entry
	private String columnNames = "pageurl, imagename, optimizationcheck, width, height, weight, weightstatus, imageurl"; // These are the column names for ALL image repositories for each table made
	private String currentPageUrl = "";
	private String currentImageWidth = "";
	private String currentImageHeight = "";
	private String weightStatus = "no weight status";
	private String currentImageUrl = "";
	private String disStatus = "init dis status";
	private String widthText = "no width";
	private String heightText = "no height";
	private String imageExclusionCriteria = "imageurl = '' AND imagename = ''";
	private String optStatus = "init opt status";
	private String stringData = "init string data";

	// Create a new table for the URL and store all checked Images in it.
	public ImageCheck(int count, WebDriver driver, String newTableName) throws Exception
	{
		int internalCount = count;

		try
		{
			// Create new Image Repository Table for storing imagecheck data
			String tableColumns = "`RecordID` INT(11) NOT NULL AUTO_INCREMENT, `PageURL` text NOT NULL, `ImageName` text NOT NULL, `OptimizationCheck` text NOT NULL, `Width` text NOT NULL, `Height` text NOT NULL, `Weight` text NOT NULL, `WeightStatus` text NOT NULL, `ImageUrl` text NOT NULL, PRIMARY KEY (`RecordID`)";
			Database.createTable(newTableName, tableColumns);

			// Start image count and breakdown
			invalidImageCount = 0;
			List<WebElement> imagesList = driver.findElements(By.tagName("img"));
			System.out.println("Total no. of images are " + imagesList.size());

			for (WebElement imgElement : imagesList)
			{
				// iterates the internal image count
				internalCount++;

				// Checks for the image's HttpStatus
				verifyimageActive(imgElement);
				
				// Gets the image file size aka "weight"
				urlConnection = new URL(imgElement.getAttribute("src")).openConnection();
				imgSize = urlConnection.getContentLength();

				// Set Strings and Integers for image attributes needed for storage and console print
				currentPageUrl = driver.getCurrentUrl().toString();
				currentImageWidth = imgElement.getAttribute("width");
				currentImageHeight = imgElement.getAttribute("height");
				weightStatus = "no weight status";
				currentImageUrl = imgElement.getAttribute("src");
				disStatus = imgElement.getAttribute("data-is-responsive-loaded");
				widthText = imgElement.getAttribute("width");
				heightText = imgElement.getAttribute("height");
				imageExclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '"
						+ imgName + "'";


				if (StoreVariables.globalSetDevice.equals("desktop"))
				{
					// If on desktop and weight is greater than 150kb then tag as Image Weight Fail
					if (imgSize > 150000)
					{
						weightStatus = "Desktop Image Weight Fail";
					}
					else
					{
						weightStatus = "Desktop Image Weight Pass";
					}
				}
				else if (StoreVariables.globalSetDevice.equals("mobile"))
				{
					// If on mobile and weight is greater than 50kb then tag as Image Weight Fail
					if (imgSize > 50000)
					{
						weightStatus = "Mobile Image Weight Fail";
					}
					else
					{
						weightStatus = "Mobile Image Weight Pass";
					}
				}
				else
				{
					// Default Image Weight is to check against desktop restrictions
					if (imgSize > 150000)
					{
						weightStatus = "Image Weight Fail (Pls take note)";
					}
					else
					{
						weightStatus = "Image Weight Pass (Pls take note)";
					}
				}
				
				// Checks if the image is optimized
				if (imgElement.getAttribute("data-is-responsive-loaded") == null)
				{
					try
					{
						if (imgElement.getAttribute("src").contains("?sw="))
						{
							// get Image Name
							fullSrc = imgElement.getAttribute("src");
							startIndex = fullSrc.lastIndexOf("/") + 1;
							endIndex = fullSrc.indexOf("?");
							imgName = fullSrc.substring(startIndex, endIndex);
							
							// Setting optimization status
							optStatus = "optimized";
						}
						else
						{
							// get Image Name
							fullSrc = imgElement.getAttribute("src");
							startIndex = fullSrc.lastIndexOf("/") + 1;

							// Setting optimization status
							optStatus = "not optimized";
							
							if (fullSrc.contains(".jpg"))
							{
								endIndex = fullSrc.lastIndexOf(".jpg");
								imgName = fullSrc.substring(startIndex, endIndex) + ".jpg";
							}
							else if (fullSrc.contains(".png"))
							{
								endIndex = fullSrc.lastIndexOf(".png");
								imgName = fullSrc.substring(startIndex, endIndex) + ".png";
							}
							else if (fullSrc.contains(".gif"))
							{
								endIndex = fullSrc.lastIndexOf(".gif");
								imgName = fullSrc.substring(startIndex, endIndex) + ".gif";
							}
							else
							{
								imgName = "No Image Name";
							}
						}
						
						// This replaces the console print information section
						this.printConsoleInformation(internalCount, fullSrc, optStatus, disStatus,
								widthText, heightText, imgSize, imgName);

						// SQL Insert information into table
						this.setStringData(currentPageUrl, imgName, optStatus, currentImageWidth, currentImageHeight, imgSize, weightStatus, currentImageUrl);
						Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
								imageExclusionCriteria);
					}
					catch (Exception e)
					{
						System.out.println("Exception found: ImageCheck, ImageCheck(2nd) - Exception: ");
						e.printStackTrace();
					}
				}
				else
				{
					try
					{
						// get Image Name
						fullSrc = imgElement.getAttribute("src");
						startIndex = fullSrc.lastIndexOf("/") + 1;
						endIndex = fullSrc.indexOf("?");
						imgName = fullSrc.substring(startIndex, endIndex);
						
						// Setting optimization status
						optStatus = "optimized";

						// This replaces the console print information section
						this.printConsoleInformation(internalCount, fullSrc, optStatus,
								disStatus, widthText, heightText, imgSize, imgName);

						// SQL Insert information into table
						this.setStringData(currentPageUrl, imgName, optStatus, currentImageWidth, currentImageHeight, imgSize, weightStatus, currentImageUrl);
						Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
								imageExclusionCriteria);
					}
					catch (Exception e)
					{
						System.out.println("Exception found: ImageCheck, ImageCheck(2nd) - Exception: ");
						e.printStackTrace();
					}
				}
			}

			// Gives the total number of images that have invalid HttpStatus
			System.out.println("Image Count Done.");
			System.out.println("Total no. of images: " + internalCount);
			System.out.println("Total no. of invalid images: " + invalidImageCount);
		}
		catch (Exception e)
		{
			System.out.println("Exception found: ImageCheck, ImageCheck - Exception: ");
			e.printStackTrace();
		}

		internalCountClass(internalCount);
	}

	public int internalCountClass(int count)
	{
		internalCount = count;
		return internalCount;
	}

	public static int getInternalCountClass()
	{
		return internalCount;
	}

	// Verify if image HttpStatus is 200
	public void verifyimageActive(WebElement imgElement)
	{
		try
		{
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(imgElement.getAttribute("src"));
			HttpResponse response = client.execute(request);
			/*
			 * The following line verifies the response code and the HttpStatus.
			 * The HttpStatus should be 200. If not, increment as invalid images
			 * count
			 */
			if (response.getStatusLine().getStatusCode() != 200) invalidImageCount++;
		}
		catch (Exception e)
		{
			System.out.println("Exception found: ImageCheck, verifyimageActive - Exception: ");
			e.printStackTrace();
		}
	}

	// Console Print Information
	public void printConsoleInformation(int internalCount, String srcText, String optStatus, String disStatus,
			String widthText, String heightText, int imgSize, String imgName)
	{
		System.out.println();
		System.out.println(internalCount + "- SRC: " + srcText);
		System.out.println(internalCount + "- This image is: " + optStatus);
		System.out.println(internalCount + "- DIS: " + disStatus);
		System.out.println(internalCount + "- Width:" + widthText + ", Height: " + heightText);
		System.out.println(internalCount + "- Bytes: " + Integer.toString(imgSize));
		System.out.println(internalCount + "- Image Name: " + imgName);
		System.out.println();
	}
	
	// Set String Data for SQL entry
	public void setStringData(String currentPageUrl, String imgName, String optStatus, String currentImageWidth, String currentImageHeight, int imgSize, String weightStatus, String currentImageUrl)
	{
		stringData = "'" + currentPageUrl + "' , '" + imgName + "' , '"+ optStatus+"', "
				+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '"
				+ weightStatus + "', '" + currentImageUrl + "'";
	}
}
