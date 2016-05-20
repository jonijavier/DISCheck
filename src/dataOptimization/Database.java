package dataOptimization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

	public Database(String dbName, String DB_DRIVER, String DB_CONNECTION, String DB_USER, String DB_PASSWORD)
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

	public static void createDbUserTable(String tableName, String dbName, String DB_DRIVER, String DB_CONNECTION,
			String DB_USER, String DB_PASSWORD) throws SQLException
	{

		internalDbConnection = null;
		internalStatement = null;

		String createTableSQL = "CREATE TABLE " + tableName + "(" + "USER_ID NUMBER(5) NOT NULL, "
				+ "USERNAME VARCHAR(20) NOT NULL, " + "CREATED_BY VARCHAR(20) NOT NULL, "
				+ "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) " + ")";

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

			System.out.println(e.getMessage());

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

	public void insertRecordIntoTable(String tableName, String columnName, String stringData) throws SQLException
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

			System.out.println(e.getMessage());

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

	public void insertMultipleRecordsIntoTable(String tableName, String columnNames, String stringData,
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

			System.out.println(e.getMessage());

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

	public void updateRecordInTable(String tableName, String setColumnName, String setStringData,
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

			System.out.println(e.getMessage());

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

	// Special Methods

	public void selectUrlAndRunImageCheck(String tableName, String columnName, Database db) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;
		internalColumnName = columnName;

		String selectRecordsFromTableSQL = "SELECT " + internalColumnName + " from " + internalTableName;

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
				System.out.println("Running Image Check on: " + internalUrlValue);
				SetInternalBrowser.thisDriver.get(internalUrlValue);
				SetInternalBrowser.thisDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
				ImageCheck internalImageCheck = new ImageCheck(ImageCheck.getInternalCountClass(),
						SetInternalBrowser.thisDriver, db);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());

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

	public void selectUniqueUrlAndGetLinks(Database db, String[] exclusionArray) throws SQLException
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

				UrlCheck uc = new UrlCheck(urlList, exclusionArray, SetInternalBrowser.thisDriver, internalUrlValue,
						db);
			}
		}
		catch (SQLException e)
		{
			System.out.println(e.getMessage());

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

			System.out.println(e.getMessage());

		}

		return internalDbConnection;

	}

	public String getInternalUrlValue()
	{
		return internalUrlValue;
	}
}
