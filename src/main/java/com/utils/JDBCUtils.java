package com.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;

public class JDBCUtils {
	private static ResourceBundle resourceBundle;
	private static Properties connectionProperties;
	static {
		resourceBundle = ResourceBundle.getBundle("dev");
		connectionProperties = new Properties();
		connectionProperties.put("user",
				resourceBundle.getString("jdbc.username"));
		connectionProperties.put("password",
				resourceBundle.getString("jdbc.password"));
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName(resourceBundle.getString("jdbc.driver.class.name"));
		Connection connection = null;
		connection = DriverManager.getConnection(
				resourceBundle.getString("jdbc.url"), connectionProperties);
		return connection;
	}
}
