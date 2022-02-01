package utils;

import java.sql.*;

public class JdbcConnection {

    static final String DB_URL = "jdbc:mysql://localhost:3306/bookxchange";
    static final String USER = "root";
    static final String PASS = "root";




    private static final String insertBook =  "INSERT INTO books(isbn, title, author, description , sellAvailability,rentAvailability, quantity)"
        + " VALUES('1234','harap alb', '1', ' o carte frumoasa', true, false, 2)";

    private static final String insertAuthor = "INSERT INTO Authors(id,name, surname, birthDate)"
        + " VALUES('1','andrei','popescu','2022-02-01')";

    private static final String getSql = "SELECT * FROM Authors";

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("Got it!");

            // create a Statement from the connection
          Statement statement = conn.createStatement();
//
//            statement.executeUpdate(insertBook);


            ResultSet result  = statement.executeQuery(getSql);

            while(result.next()) {
                System.out.println(result.getInt(1));
                System.out.println(result.getString(2));
                System.out.println(result.getString(3));
                System.out.println(result.getString(4));
            }


        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


}
