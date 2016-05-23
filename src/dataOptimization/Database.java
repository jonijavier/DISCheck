package dataOptimization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Database
{
	public static Connection internalDbConnection;
	public static Statement internalStatement;

	public static String internalDBName = "";
	public static String internalDBDriver = "";
	public static String internalDBConnectionString = "";
	public static String internalDBUser = "";
	public static String internalDBPassword = "";

	public static String internalTableName = "";
	public static String internalColumnName = "";

	public static String internalUrlValue = "";

	public static void Database(String dbName, String DB_DRIVER, String DB_CONNECTION, String DB_USER,
			String DB_PASSWORD)
	{
		internalDBName = dbName;
		internalDBDriver = DB_DRIVER;
		internalDBConnectionString = DB_CONNECTION;
		internalDBUser = DB_USER;
		internalDBPassword = DB_PASSWORD;

		System.out.println("-------- MySQL JDBC Connection ------------");

		try
		{
			Class.forName(internalDBDriver);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("MySQL JDBC Driver " + internalDBDriver + " not found.");
			e.printStackTrace();
			return;
		}

		System.out.println("MySQL JDBC Driver Registered.");
		internalDbConnection = null;

		try
		{
			internalDbConnection = DriverManager.getConnection(internalDBConnectionString, internalDBUser,
					internalDBPassword);

		}
		catch (SQLException e)
		{
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			System.out.println();
			System.out.println("**TEST ABORTED**");
			SetInternalBrowser.thisDriver.quit();
			System.exit(0);
		}

		if (internalDbConnection != null)
		{
			System.out.println("mySQL Database Connection made.");
		}
		else
		{
			System.out.println("Failed to make connection!");
		}
	}

	public static void createTable(String tableName, String columnNames) throws SQLException
	{

		internalDbConnection = null;
		internalStatement = null;

		String createTableSQL = "CREATE TABLE IF NOT EXISTS `" + tableName + "` (" + columnNames
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1;";

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println(createTableSQL);

			// execute the SQL Statement
			internalStatement.execute(createTableSQL);

			System.out.println("Table " + tableName + " is created");

		}
		catch (SQLException e)
		{
			System.out.println("Exception found: Database, createDbUserTable - Exception: ");
			e.printStackTrace();
		}
		finally
		{

			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}

	}

	public static void insertRecordIntoTable(String tableName, String columnName, String stringData) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;
		internalColumnName = columnName;

		String insertTableSQL = "INSERT INTO " + internalTableName + "(" + internalColumnName + ") SELECT '"
				+ stringData + "' FROM dual WHERE NOT EXISTS (SELECT * FROM " + internalTableName + " WHERE URL = '"
				+ stringData + "');";

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println(insertTableSQL);

			// execute insert SQL statement
			internalStatement.executeUpdate(insertTableSQL);

			System.out.println("Record is inserted into " + internalTableName + " table");
		}
		catch (SQLException e)
		{
			System.out.println("Exception found: Database, insertRecordIntoTable - Exception: ");
			e.printStackTrace();
		}
		finally
		{
			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}

	}

	public static void insertMultipleRecordsIntoTable(String tableName, String columnNames, String stringData,
			String exclusionCriteria) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;
		internalColumnName = columnNames;

		String newInsertMultipleRecordsSQL = "INSERT INTO " + internalTableName + "(" + internalColumnName + ")"
				+ " SELECT " + stringData + " FROM dual WHERE NOT EXISTS (SELECT * FROM " + internalTableName
				+ " WHERE " + exclusionCriteria + ");";

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println(newInsertMultipleRecordsSQL);

			// execute insert SQL statement
			internalStatement.executeUpdate(newInsertMultipleRecordsSQL);

			System.out.println("Record is inserted into " + internalTableName + " table");
		}
		catch (SQLException e)
		{
			System.out.println("Exception found: Database, insertMultipleRecordsIntoTable - Exception: ");
			e.printStackTrace();
		}
		finally
		{
			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}

	}

	public static void updateRecordInTable(String tableName, String setColumnName, String setStringData,
			String conditionColumnName, String conditionStringData) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;

		String updateRecordSQL = "UPDATE " + internalTableName + " SET " + setColumnName + " = '" + setStringData + "' "
				+ " WHERE " + conditionColumnName + " = '" + conditionStringData + "'";

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println(updateRecordSQL);

			// Execute the Update SQL
			internalStatement.execute(updateRecordSQL);

			System.out.println("Record is updated on " + tableName + " table");

		}
		catch (SQLException e)
		{
			System.out.println("Exception found: Database, updateRecordInTable - Exception: ");
			e.printStackTrace();
		}
		finally
		{

			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}

	}

	// Special Method to get URl
	public static void selectUrlAndRunImageCheck(String tableName, String columnName) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;
		internalColumnName = columnName;

		String selectRecordsFromTableSQL = "SELECT " + internalColumnName + " from " + internalTableName;
		String[] tempExclusionArray = StoreVariables.getGlobalExclusionArray();
		Boolean excluded = false;

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println("Starting Image Check for each Unique URL on: " + StoreVariables.getGlobalDbName() + "."
					+ internalTableName);
			System.out.println();
			System.out.println(selectRecordsFromTableSQL);

			// Execute Select SQL Statement
			ResultSet currResultSet = internalStatement.executeQuery(selectRecordsFromTableSQL);

			while (currResultSet.next())
			{
				internalUrlValue = currResultSet.getString("URL");

				System.out.println("internalUrlValue: " + internalUrlValue);

				String fullSrc = internalUrlValue;
				StoreVariables.setGlobalTempTableName(fullSrc);
				
				// runs through all the exclusions skiplist
				for (int i = 0; i < tempExclusionArray.length; i++)
				{
					// reset checkSkipped value
					excluded = false;

					if (currResultSet.getString("URL").contains(tempExclusionArray[i]) == true)
					{
						System.out.println("SkipWord: " + tempExclusionArray[i]);
						excluded = true;
						break;
					}
					else
					{
						excluded = false;
					}
				}

				if (excluded == true)
				{
					System.out.println("Skipping url: " + currResultSet.getString("URL").toString());
				}
				else
				{
					System.out.println("Running Image Check on: " + internalUrlValue);
					SetInternalBrowser.thisDriver.get(internalUrlValue);
					SetInternalBrowser.thisDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					ImageCheck internalImageCheck = new ImageCheck(ImageCheck.getInternalCountClass(),
							SetInternalBrowser.thisDriver, StoreVariables.getGlobalTempTableName());
				}
			}

		}
		catch (Exception e)
		{
			System.out.println("Exception found: Database, selectUrlAndRunImageCheck - Exception: ");
			e.printStackTrace();
		}
		finally
		{

			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}
	}

	// Duplicate to restart test on imagechecks for all links from a specific url point
	public static void selectUrlAndRunImageCheck(String tableName, String columnName, String restartCondition)
			throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;
		internalColumnName = columnName;

		String selectRecordsFromTableSQL = "SELECT " + internalColumnName + " from " + internalTableName;
		String[] tempExclusionArray = StoreVariables.getGlobalExclusionArray();
		Boolean excluded = false;

		Boolean savePoint = false;

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println("Starting Image Check for each Unique URL on: " + StoreVariables.getGlobalDbName() + "."
					+ internalTableName);
			System.out.println();
			System.out.println(selectRecordsFromTableSQL);

			// Execute Select SQL Statement
			ResultSet currResultSet = internalStatement.executeQuery(selectRecordsFromTableSQL);

			while (currResultSet.next())
			{
				internalUrlValue = currResultSet.getString("URL");

				if (internalUrlValue.equals(restartCondition))
				{
					savePoint = true;
				}

				if (savePoint == true)
				{
					System.out.println("internalUrlValue: " + internalUrlValue);

					String fullSrc = internalUrlValue;
					StoreVariables.setGlobalTempTableName(fullSrc);

					// runs through all the exclusions skiplist
					for (int i = 0; i < tempExclusionArray.length; i++)
					{
						// reset checkSkipped value
						excluded = false;

						if (currResultSet.getString("URL").contains(tempExclusionArray[i]) == true)
						{
							System.out.println("SkipWord: " + tempExclusionArray[i]);
							excluded = true;
							break;
						}
						else
						{
							excluded = false;
						}
					}

					if (excluded == true)
					{
						System.out.println("Skipping url: " + currResultSet.getString("URL").toString());
					}
					else
					{
						System.out.println("Running Image Check on: " + internalUrlValue);
						SetInternalBrowser.thisDriver.get(internalUrlValue);
						SetInternalBrowser.thisDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
						ImageCheck internalImageCheck = new ImageCheck(ImageCheck.getInternalCountClass(),
								SetInternalBrowser.thisDriver, StoreVariables.getGlobalTempTableName());
					}
				}
			}

		}
		catch (Exception e)
		{
			System.out.println("Exception found: Database, selectUrlAndRunImageCheck - Exception: ");
			e.printStackTrace();
		}
		finally
		{

			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}
	}

	public static void selectUniqueUrlAndGetLinks(String[] exclusionArray) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = "urlrepository";
		internalColumnName = "URL";

		String selectRecordsFromTableSQL = "SELECT " + internalColumnName + " FROM " + internalTableName
				+ " WHERE 'UNIQUECHECK' NOT LIKE '';";

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println(selectRecordsFromTableSQL);

			// Execute Select SQL Statement
			ResultSet currResultSet = internalStatement.executeQuery(selectRecordsFromTableSQL);

			while (currResultSet.next())
			{
				internalUrlValue = currResultSet.getString("URL");
				System.out.println("Running URL Check on: " + internalUrlValue);
				SetInternalBrowser.thisDriver.get(internalUrlValue);
				SetInternalBrowser.thisDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				List<WebElement> urlList = SetInternalBrowser.thisDriver.findElements(By.tagName("a"));

				UrlCheck uc = new UrlCheck(urlList, exclusionArray, SetInternalBrowser.thisDriver, internalUrlValue);
			}
		}
		catch (SQLException e)
		{
			System.out.println("Exception found: Database, selectUniqueUrlAndGetLinks - Exception: ");
			e.printStackTrace();
		}
		finally
		{

			if (internalStatement != null)
			{
				internalStatement.close();
			}

			if (internalDbConnection != null)
			{
				internalDbConnection.close();
			}

		}
	}

	public static Connection getDBConnection()
	{

		internalDbConnection = null;

		try
		{
			// load DB Driver
			Class.forName(internalDBDriver);
		}
		catch (ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}

		try
		{
			internalDbConnection = DriverManager.getConnection(internalDBConnectionString, internalDBUser,
					internalDBPassword);
			return internalDbConnection;
		}
		catch (SQLException e)
		{
			System.out.println("Exception found: Database, Connection - Exception: ");
			e.printStackTrace();
		}

		return internalDbConnection;

	}

	public String getInternalUrlValue()
	{
		return internalUrlValue;
	}

	public void getDataBase()
	{

	}
}
