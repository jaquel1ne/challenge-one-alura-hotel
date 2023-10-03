package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	public static Connection getConnection() throws SQLException {
        try {
            Properties properties = new Properties();
            properties.load(ConnectionFactory.class.getClassLoader().getResourceAsStream("connection.properties"));
            
            String url = properties.getProperty("dataBase.url");
            String user = properties.getProperty("dataBase.user");
            String password = properties.getProperty("dataBase.password");

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com o banco de dados", e);
        }
        
	}

}
