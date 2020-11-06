package data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class MyExample {
    private static HikariDataSource datasource;

    public static HikariDataSource getDataSource() {
        if (datasource == null) {

//            Properties props = new Properties();
//            props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
//            props.setProperty("dataSource.user", "user");
//            props.setProperty("dataSource.password", "123");
//            props.setProperty("dataSource.databaseName", "postgrest");
//            props.put("dataSource.logWriter", new PrintWriter(System.out));
//
            HikariConfig config = new HikariConfig();

//            config.setJdbcUrl("jdbc:mysql://localhost/test");
            config.setJdbcUrl("jdbc:postgresql://localhost/postgres");
            config.setUsername("user");
            config.setPassword("123");
            config.setDriverClassName("org.postgresql.Driver"); //
//            config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
            config.setMaximumPoolSize(10);
            config.setPoolName("postgres");
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            datasource = new HikariDataSource(config);
        }

        return datasource;
    }

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ResultSet resultSet = null;
        try {
            DataSource dataSource = MyExample.getDataSource();
            connection = dataSource.getConnection();
            pstmt = connection.prepareStatement("SELECT * FROM driver");

            System.out.println("The Connection Object is of Class: " + connection.getClass());

            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2) + " | " + resultSet.getString(3));
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
