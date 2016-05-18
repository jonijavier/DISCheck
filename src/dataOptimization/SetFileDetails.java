package dataOptimization;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SetFileDetails
{
	public static String internalFileName;
	public static String internalFileLocation;
	public static String internalAppendText;
	public static String internalFileExtension;

	public static FileWriter internalFileWriter;

	public SetFileDetails(String fileLocation, String fileName, String appendText, String fileExtension)
	{
		internalFileName = fileName;
		internalFileLocation = fileLocation;
		internalAppendText = appendText;
		internalFileExtension = fileExtension;

		// Create the file name by appending the first 4 chars of the url with the date and time
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		Date date = new Date();

		String tempFileName = internalFileName + "_" + internalAppendText + dateFormat.format(date)
				+ internalFileExtension;

		internalFileName = tempFileName;

		System.out.println("File Created");
		System.out.println("File Location: " + internalFileLocation);
		System.out.println("File Name: " + internalFileName);
	}

	public SetFileDetails(String fileLocation, String fileName, String fileExtension)
	{
		internalFileName = fileName;
		internalFileLocation = fileLocation;
		internalFileExtension = fileExtension;

		// Create the file name by appending the first 4 chars of the url with the date and time
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd_HHmmss");
		Date date = new Date();

		String tempFileName = internalFileName + "_" + dateFormat.format(date) + internalFileExtension;

		internalFileName = tempFileName;
		
		System.out.println("File Created");
		System.out.println("File Location: " + internalFileLocation);
		System.out.println("File Name: " + internalFileName);
	}

	public static void checkIfFileExists(File tempFile) throws Exception
	{
		try
		{
			// Check if file exists in the directory
			if (tempFile.exists())
			{
				// Create a copy of the file in the same directory and location
				File createCopy = new File("Copy of " + getFileName());
				internalFileWriter = new FileWriter(createCopy);
			}
			else
			{
				// Create file
				internalFileWriter = new FileWriter(tempFile);
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR. Details: ");
			e.printStackTrace();
		}
	}

	public static String getFileName()
	{
		return internalFileName;
	}

	public static String getFileLocation()
	{
		return internalFileLocation;
	}

	public static String getAppendedText()
	{
		return internalAppendText;
	}

	public static String getFileExtension()
	{
		return internalFileExtension;
	}
	
	public static FileWriter getFileWriter()
	{
		return internalFileWriter;
	}
}
