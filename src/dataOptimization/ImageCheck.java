package dataOptimization;

import org.openqa.selenium.*;

import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.XMLOutputter;

public class ImageCheck
{
	private int invalidImageCount;
	private static int internalCount;
	private static URLConnection urlConnection;
	private int imgSize = 0;
	private boolean imgNull = false;

	private String fullSrc = "";
	private String imgName = "";
	private int startIndex = 0;
	private int endIndex = 0;

	// For SQL Entry
	private String columnNames = "pageurl, imagename, optimizationcheck, width, height, weight, weightstatus, imageurl";
	private String currentPageUrl = "";
	private String currentImageWidth = "";
	private String currentImageHeight = "";
	private String weightStatus = "no weight status";
	private String currentImageUrl = "";

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
				internalCount++;

				// Checks for the image's HttpStatus
				if (imgElement != null)
				{
					verifyimageActive(imgElement);
					imgNull = true;
				}

				if (imgNull == true)
				{
					try
					{
						// Gets the image file size aka "weight"
						urlConnection = new URL(imgElement.getAttribute("src")).openConnection();
						imgSize = urlConnection.getContentLength();
						imgNull = false;
					}
					catch (Exception e)
					{
						System.out.println("Exception found: ImageCheck, ImageCheck(2nd) - Exception: ");
						e.printStackTrace();
					}
				}
				else
				{
					imgNull = false;
				}

				// Checks if the image is optimized
				if (imgElement.getAttribute("data-is-responsive-loaded") == null)
				{
					try
					{
						if (imgElement.getAttribute("src").contains("?sw="))
						{
							// Set Strings and Integers
							currentPageUrl = driver.getCurrentUrl().toString();
							currentImageWidth = imgElement.getAttribute("width");
							currentImageHeight = imgElement.getAttribute("height");
							weightStatus = "no weight status";
							currentImageUrl = imgElement.getAttribute("src");

							// If weight is greater than 150kb then tag as Image Weight Fail
							if (imgSize > 150000)
							{
								weightStatus = "Image Weight Fail";
							}
							else
							{
								weightStatus = "Image Weight Pass";
							}

							// get Image Name
							fullSrc = imgElement.getAttribute("src");
							startIndex = fullSrc.lastIndexOf("/") + 1;
							endIndex = fullSrc.indexOf("?");
							imgName = fullSrc.substring(startIndex, endIndex);

							// Console print
							System.out.println();
							System.out.println(internalCount + "- SRC: " + imgElement.getAttribute("src"));
							System.out.println(internalCount + "- This image is optimized.");
							System.out.println(
									internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
							System.out.println(internalCount + "- Width:" + imgElement.getAttribute("width")
									+ ", Height: " + imgElement.getAttribute("height"));
							System.out.println(internalCount + "- bytes: " + Integer.toString(imgSize));
							System.out.println(internalCount + "- imageName: " + imgName);

							// SQL Insert
							String stringData = "'" + currentPageUrl + "' , '" + imgName + "' , 'optimized', "
									+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '"
									+ weightStatus + "', '" + currentImageUrl + "'";
							String exclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '"
									+ imgName + "'";
							Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
									exclusionCriteria);

						}
						else
						{
							// Set Strings and Integers
							currentPageUrl = driver.getCurrentUrl().toString();
							currentImageWidth = imgElement.getAttribute("width");
							currentImageHeight = imgElement.getAttribute("height");
							weightStatus = "no weight status";
							currentImageUrl = imgElement.getAttribute("src");

							// If weight is greater than 150kb then tag as Image Weight Fail
							if (imgSize > 150000)
							{
								weightStatus = "Image Weight Fail";
							}
							else
							{
								weightStatus = "Image Weight Pass";
							}

							// get Image Name
							fullSrc = imgElement.getAttribute("src");
							startIndex = fullSrc.lastIndexOf("/") + 1;

							if (fullSrc.contains(".jpg"))
							{
								endIndex = fullSrc.lastIndexOf(".jpg");
								imgName = fullSrc.substring(startIndex, endIndex) + ".jpg";

								// Console print
								System.out.println();
								System.out.println(internalCount + "- SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "- This image is not optimized.");
								System.out.println(internalCount + "- DIS: "
										+ imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "- bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);

								// SQL Insert
								String stringData = "'" + currentPageUrl + "' , '" + imgName + "' , 'not optimized', "
										+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '"
										+ weightStatus + "', '" + currentImageUrl + "'";
								String exclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '"
										+ imgName + "'";
								Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
										exclusionCriteria);
							}
							else if (fullSrc.contains(".png"))
							{
								endIndex = fullSrc.lastIndexOf(".png");
								imgName = fullSrc.substring(startIndex, endIndex) + ".png";

								// Console print
								System.out.println();
								System.out.println(internalCount + "- SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "- This image is not optimized.");
								System.out.println(internalCount + "- DIS: "
										+ imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "- bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);

								// SQL Insert
								String stringData = "'" + currentPageUrl + "' , '" + imgName + "' , 'not optimized', "
										+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '"
										+ weightStatus + "', '" + currentImageUrl + "'";
								String exclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '"
										+ imgName + "'";
								Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
										exclusionCriteria);
							}
							else if (fullSrc.contains(".gif"))
							{
								endIndex = fullSrc.lastIndexOf(".gif");
								imgName = fullSrc.substring(startIndex, endIndex) + ".gif";

								// Console print
								System.out.println();
								System.out.println(internalCount + "- SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "- This image is not optimized.");
								System.out.println(internalCount + "- DIS: "
										+ imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "- bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);

								// SQL Insert
								String stringData = "'" + currentPageUrl + "' , '" + imgName + "' , 'not optimized', "
										+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '"
										+ weightStatus + "', '" + currentImageUrl + "'";
								String exclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '"
										+ imgName + "'";
								Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
										exclusionCriteria);
							}
							else
							{
								// do nothing
								imgName = "No Image Name";

								// Console print
								System.out.println();
								System.out.println(internalCount + "- SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "- This image is not optimized.");
								System.out.println(internalCount + "- DIS: "
										+ imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "- bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);

								// SQL Insert
								String stringData = "'" + currentPageUrl + "' , '" + imgName + "' , 'not optimized', "
										+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '"
										+ weightStatus + "', '" + currentImageUrl + "'";
								String exclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '"
										+ imgName + "'";
								Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
										exclusionCriteria);
							}
						}
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
						// Set Strings and Integers
						currentPageUrl = driver.getCurrentUrl().toString();
						currentImageWidth = imgElement.getAttribute("width");
						currentImageHeight = imgElement.getAttribute("height");
						weightStatus = "no weight status";
						currentImageUrl = imgElement.getAttribute("src");

						// If weight is greater than 150kb then tag as Image Weight Fail
						if (imgSize > 150000)
						{
							weightStatus = "Image Weight Fail";
						}
						else
						{
							weightStatus = "Image Weight Pass";
						}

						// get Image Name
						fullSrc = imgElement.getAttribute("src");
						startIndex = fullSrc.lastIndexOf("/") + 1;
						endIndex = fullSrc.indexOf("?");
						imgName = fullSrc.substring(startIndex, endIndex);

						// Console print
						System.out.println();
						System.out.println(internalCount + "- SRC: " + imgElement.getAttribute("src"));
						System.out.println(internalCount + "- This image is optimized.");
						System.out.println(
								internalCount + "- DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
						System.out.println(internalCount + "- Width:" + imgElement.getAttribute("width") + ", Height: "
								+ imgElement.getAttribute("height"));
						System.out.println(internalCount + "- bytes: " + Integer.toString(imgSize));
						System.out.println(internalCount + "- imageName: " + imgName);

						// SQL Insert
						String stringData = "'" + currentPageUrl + "' , '" + imgName + "' , 'optimized', "
								+ currentImageWidth + ", " + currentImageHeight + ", " + imgSize + ", '" + weightStatus
								+ "', '" + currentImageUrl + "'";
						String exclusionCriteria = "imageurl = '" + currentImageUrl + "' AND imagename = '" + imgName
								+ "'";
						Database.insertMultipleRecordsIntoTable(newTableName, columnNames, stringData,
								exclusionCriteria);
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
			System.out.println("Exception found: ImageCheck, ImageCheck(2nd) - Exception: ");
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
}
