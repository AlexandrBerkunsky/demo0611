package servlets;

import com.zaxxer.hikari.HikariDataSource;
import data.MyExample;
import entities.Driver;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MyServlet", urlPatterns = "/")
public class MyServlet extends HttpServlet {

//    Connection connection;

//    @Override
//    public void init() throws ServletException {
//        super.init();
//        try {
//            DataSource dataSource = MyExample.getDataSource();
//            connection = dataSource.getConnection();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Driver> result = null;
        try {
            result = getDrivers();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Application Servlet Page</title>");
        writer.println("</head>");
        writer.println("<body bgcolor=beige>");
//        writer.println("<h1>Hello, World!</h1>");

        if (result != null) {
            for (Driver driver : result) {
                writer.println(driver.getId() + " | " + driver.getName() + " | " + driver.getAge() + "<br>");
            }
        }
        writer.println("</body>");
        writer.println("</html>");

    }

//    @org.jetbrain
//    s.annotations.NotNull
    private List<Driver> getDrivers() throws NamingException {
        List<Driver> result = new ArrayList<>();
        PreparedStatement ps;
        InitialContext initialContext = new InitialContext();

//        DataSource dataSource = (DataSource) initialContext.lookup("java:comp/env/jdbc/postgrest");;
        HikariDataSource dataSource = MyExample.getDataSource();
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ps = connection.prepareStatement("select * from driver");
//            ps.executeUpdate();

            ResultSet rs = ps.executeQuery();
            connection.close();
//            ResultSetMetaData metaData = rs.getMetaData();
//            for (int i = 0; i < metaData.getColumnCount(); i++) {
//                System.out.println(metaData.getColumnName(i+1));
//            }
            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                result.add(new Driver(id, name, age));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
