
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

	public ImageCheck(int count, WebDriver driver, Document doc, XMLOutputter xmlOutput, FileWriter file)
	{
		int internalCount = count;
		try
		{
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
						System.out.println("JTJ//ERROR// - Exception caught:");
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
							// get Image Name
							fullSrc = imgElement.getAttribute("src");
							startIndex = fullSrc.lastIndexOf("/") + 1;
							endIndex = fullSrc.indexOf("?");
							imgName = fullSrc.substring(startIndex, endIndex);

							// Console print
							System.out.println(internalCount + "-SRC: " + imgElement.getAttribute("src"));
							System.out.println(internalCount + "> This image is optimized.");
							System.out.println(
									internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
							System.out.println(internalCount + "- Width:" + imgElement.getAttribute("width")
									+ ", Height: " + imgElement.getAttribute("height"));
							System.out.println(internalCount + "-bytes: " + Integer.toString(imgSize));
							System.out.println(internalCount + "- imageName: " + imgName);

							// Insert into XML
							Element image = new Element("image");
							image.setAttribute(new Attribute("id", "" + internalCount));
							image.setAttribute(new Attribute("url", driver.getCurrentUrl()));
							image.addContent(new Element("status").setText("optimized"));
							image.addContent(new Element("name").setText(imgName));
							image.addContent(new Element("SRC").setText(imgElement.getAttribute("src")));
							image.addContent(new Element("width").setText(imgElement.getAttribute("width")));
							image.addContent(new Element("height").setText(imgElement.getAttribute("height")));
							image.addContent(new Element("weight").setText(Integer.toString(imgSize)));

							doc.getRootElement().addContent(image);
						}
						else
						{
							// get Image Name
							fullSrc = imgElement.getAttribute("src");
							startIndex = fullSrc.lastIndexOf("/") + 1;
							
							if (fullSrc.contains(".jpg"))
							{
								endIndex = fullSrc.lastIndexOf(".jpg");
								imgName = fullSrc.substring(startIndex, endIndex)+".jpg";
								
								// Console print
								System.out.println(internalCount + "-SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "> This image is not optimized.");
								System.out.println(
										internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "-bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);
								
								// Insert into XML
								Element image = new Element("image");
								image.setAttribute(new Attribute("id", "" + internalCount));
								image.setAttribute(new Attribute("url", driver.getCurrentUrl()));
								image.addContent(new Element("status").setText("not optimized"));
								image.addContent(new Element("name").setText(imgName));
								image.addContent(new Element("SRC").setText(imgElement.getAttribute("src")));
								image.addContent(new Element("width").setText(imgElement.getAttribute("width")));
								image.addContent(new Element("height").setText(imgElement.getAttribute("height")));
								image.addContent(new Element("weight").setText(Integer.toString(imgSize)));

								doc.getRootElement().addContent(image);
							}
							else if (fullSrc.contains(".png"))
							{
								endIndex = fullSrc.lastIndexOf(".png");
								imgName = fullSrc.substring(startIndex, endIndex)+".png";
								
								// Console print
								System.out.println(internalCount + "-SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "> This image is not optimized.");
								System.out.println(
										internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "-bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);
								
								// Insert into XML
								Element image = new Element("image");
								image.setAttribute(new Attribute("id", "" + internalCount));
								image.setAttribute(new Attribute("url", driver.getCurrentUrl()));
								image.addContent(new Element("status").setText("not optimized"));
								image.addContent(new Element("name").setText(imgName));
								image.addContent(new Element("SRC").setText(imgElement.getAttribute("src")));
								image.addContent(new Element("width").setText(imgElement.getAttribute("width")));
								image.addContent(new Element("height").setText(imgElement.getAttribute("height")));
								image.addContent(new Element("weight").setText(Integer.toString(imgSize)));

								doc.getRootElement().addContent(image);
							}
							else if (fullSrc.contains(".gif"))
							{
								endIndex = fullSrc.lastIndexOf(".gif");
								imgName = fullSrc.substring(startIndex, endIndex)+".gif";
								
								// Console print
								System.out.println(internalCount + "-SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "> This image is not optimized.");
								System.out.println(
										internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "-bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);
								
								// Insert into XML
								Element image = new Element("image");
								image.setAttribute(new Attribute("id", "" + internalCount));
								image.setAttribute(new Attribute("url", driver.getCurrentUrl()));
								image.addContent(new Element("status").setText("not optimized"));
								image.addContent(new Element("name").setText(imgName));
								image.addContent(new Element("SRC").setText(imgElement.getAttribute("src")));
								image.addContent(new Element("width").setText(imgElement.getAttribute("width")));
								image.addContent(new Element("height").setText(imgElement.getAttribute("height")));
								image.addContent(new Element("weight").setText(Integer.toString(imgSize)));

								doc.getRootElement().addContent(image);
							}
							else
							{
								// do nothing
								imgName = "No Image Name";
								
								// Console print
								System.out.println(internalCount + "-SRC: " + imgElement.getAttribute("src"));
								System.out.println(internalCount + "> This image is not optimized.");
								System.out.println(
										internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
								System.out.println(internalCount + "-bytes: " + Integer.toString(imgSize));
								System.out.println(internalCount + "- imageName: " + imgName);
								
								// Insert into XML
								Element image = new Element("image");
								image.setAttribute(new Attribute("id", "" + internalCount));
								image.setAttribute(new Attribute("url", driver.getCurrentUrl()));
								image.addContent(new Element("status").setText("not optimized"));
								image.addContent(new Element("name").setText(imgName));
								image.addContent(new Element("SRC").setText(imgElement.getAttribute("src")));
								image.addContent(new Element("width").setText(imgElement.getAttribute("width")));
								image.addContent(new Element("height").setText(imgElement.getAttribute("height")));
								image.addContent(new Element("weight").setText(Integer.toString(imgSize)));

								doc.getRootElement().addContent(image);
							}
						}
					}
					catch (Exception e)
					{
						System.out.println("JTJ//ERROR// - Exception caught:");
						e.printStackTrace();
						System.out.println(e.getMessage());
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

						// Console print
						System.out.println(internalCount + "-SRC: " + imgElement.getAttribute("src"));
						System.out.println(internalCount + "> This image is optimized.");
						System.out.println(
								internalCount + "-DIS: " + imgElement.getAttribute("data-is-responsive-loaded"));
						System.out.println(internalCount + "- Width:" + imgElement.getAttribute("width")
								+ ", Height: " + imgElement.getAttribute("height"));
						System.out.println(internalCount + "-bytes: " + Integer.toString(imgSize));
						System.out.println(internalCount + "- imageName: " + imgName);

						// Insert into XML
						Element image = new Element("image");
						image.setAttribute(new Attribute("id", "" + internalCount));
						image.setAttribute(new Attribute("url", driver.getCurrentUrl()));
						image.addContent(new Element("status").setText("optimized"));
						image.addContent(new Element("name").setText(imgName));
						image.addContent(new Element("SRC").setText(imgElement.getAttribute("src")));
						image.addContent(new Element("width").setText(imgElement.getAttribute("width")));
						image.addContent(new Element("height").setText(imgElement.getAttribute("height")));
						image.addContent(new Element("weight").setText(Integer.toString(imgSize)));

						doc.getRootElement().addContent(image);
					}
					catch (Exception e)
					{
						System.out.println("JTJ//ERROR// - Exception caught:");
						e.printStackTrace();
						System.out.println(e.getMessage());
					}
				}
			}

			// Gives the total number of images that have invalid HttpStatus
			System.out.println("Image Count Done.");
			System.out.println("Total no. of invalid images are " + invalidImageCount);
		}
		catch (Exception e)
		{
			System.out.println("JTJ//ERROR// - Exception caught:");
			e.printStackTrace();
			System.out.println(e.getMessage());
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
			System.out.println("JTJ//ERROR// - Exception: ");
			e.printStackTrace();
		}
	}
}
