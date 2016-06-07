package dataOptimization;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.UnreachableBrowserException;

public class UrlCheck
{
	public boolean checkSkipped = false;
	public int skippedNo = 0;
	public int urlNo = 0;
	public int storedNo = 0;

	public UrlCheck(List<WebElement> urlList, String[] exclusionArray, WebDriver driver, String baseUrl)
	{
		try
		{
			System.out.println("Starting URL Check for " + baseUrl);
			
			// loops to segregate the non-url from the urls
			for (WebElement aHrefElement : urlList)
			{
				// if href is null, then there is no url available
				if (aHrefElement.getAttribute("href") == null)
				{
					// do nothing if there is no url available
					checkSkipped = true;
					skippedNo++;
				}
				else if (aHrefElement.getAttribute("href").toString().contains("javascript"))
				{
					// do nothing if this url is javascript
					checkSkipped = true;
					skippedNo++;
				}
				else if (aHrefElement.getAttribute("href").toString().equals(baseUrl))
				{
					// do nothing if this url is equal to the baseUrl
					checkSkipped = true;
					skippedNo++;
				}
				else if (aHrefElement.getAttribute("href").toString().equals(""))
				{
					// do nothing if this url is equal to the baseUrl
					checkSkipped = true;
					skippedNo++;
				}
				else
				{
					// runs through all the exclusions skiplist
					for (int i = 0; i < exclusionArray.length; i++)
					{
						// reset checkSkipped value
						checkSkipped = false;

						if (aHrefElement.getAttribute("href").toString().contains(exclusionArray[i]) == true)
						{
							System.out.println("SkipWord: " + exclusionArray[i]);
							System.out.println("Skipping url: " + aHrefElement.getAttribute("href").toString());
							checkSkipped = true;
							break;
						}
						else
						{
							checkSkipped = false;
						}
					}
				}

				if (checkSkipped == false)
				{
					try
					{
						// Add URL to the SQL database
						Database.insertRecordIntoTable("urlrepository", "URL", aHrefElement.getAttribute("href").toString());
						
						storedNo++;
					}
					catch (SQLException sqle)
					{
						System.out.println("Class: UrlCheck, UrlCheck. Exception found: ");
						sqle.printStackTrace();
					}
				}

				// increment for final URL count
				urlNo++;

				driver.manage().timeouts().implicitlyWait(1, TimeUnit.MILLISECONDS);
			}
			
			int urlsStored = this.getUrlNo() - this.getSkippedNo();
			System.out.println();
			System.out.println("Total No. of URLS: " + this.getUrlNo());
			System.out.println("Total No. of Skipped URLs: " + this.getSkippedNo());
			System.out.println("Total No. of Stored URLs: " + urlsStored);
			System.out.println();
		}
		catch (UnreachableBrowserException ube)
		{
			System.out.println("UnreachableBrowserException found: UrlCheck, UrlCheck - Exception: ");
			ube.printStackTrace();
		}
		catch (Exception e)
		{
			System.out.println("Exception found: UrlCheck, UrlCheck - Exception: ");
			e.printStackTrace();
		}

	}

	public int getSkippedNo()
	{
		return skippedNo;
	}

	public int getUrlNo()
	{
		return urlNo;
	}
}
