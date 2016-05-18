package dataOptimization;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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
			return;
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

	public static void insertRecordIntoTable(String tableName, String columnName, String stringData) throws SQLException
	{
		internalDbConnection = null;
		internalStatement = null;

		internalTableName = tableName;
		internalColumnName = columnName;

		String insertTableSQL = "INSERT INTO " + internalTableName + " " + "(" + internalColumnName + ")" + " VALUES "
				+ "('" + stringData + "');";

		String newInsertTableSQL = "INSERT INTO " + internalTableName + "(" + internalColumnName + ") SELECT '"
				+ stringData + "' FROM dual WHERE NOT EXISTS (SELECT * FROM " + internalTableName + " WHERE URL = '"
				+ stringData + "');";

		try
		{
			internalDbConnection = getDBConnection();
			internalStatement = internalDbConnection.createStatement();

			System.out.println(newInsertTableSQL);

			// execute insert SQL statement
			internalStatement.executeUpdate(newInsertTableSQL);

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
}
