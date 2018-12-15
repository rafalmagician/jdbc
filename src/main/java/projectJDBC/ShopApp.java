package projectJDBC;

import com.mysql.jdbc.Driver;

import java.sql.*;

public class ShopApp {
    private static final String DB_URL = "jdbc:mysql://localhost/produkty";
    private static final String USER = "newuser";
    private static final String PASS = "brak";

    public static void main(String[] args) {

        //register mysql driver
        try {
            Driver driver = new Driver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //establish connection
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //query execution
        Statement statement = null;
        PreparedStatement preparedStatement = null;

        try {
//            preparedStatement = connection.prepareStatement(
//                        "UPDATE products "+
//                            "SET description = ? WHERE product_id = ?"
//            );
            //updateProducts(preparedStatement);

            statement = connection.createStatement();
            switch (1){
                case 1:{
                    showAllProducts(statement);
                    break;
                }
            }
            //insertProduct(statement);
            //finalAllQuery(statement);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            if(preparedStatement != null){
                try{
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            try{
                if(statement != null){
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //close connection
        try {
            if(connection != null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
//-------------------------------------------------------------------------------------------------------------
    private static void showAllProducts(Statement statement) {
        ResultSet resultSet = null;
        try {
            resultSet = statement.
                    executeQuery("SELECT product_id,catalog_number,name,description FROM products");

            while(resultSet.next()){
                int product_id = resultSet.getInt("product_id");
                int catalog_number = resultSet.getInt("catalog_number");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");

                System.out.println(
                        "Product with id: "+product_id+
                                " catalog number: "+catalog_number+
                                " name: "+name+
                                " and description: "+description+"."
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void updateProducts(PreparedStatement preparedStatement) {
        try {
            preparedStatement.setString(1,"bla bla bla");
            preparedStatement.setInt(2,1);

            int update = preparedStatement.executeUpdate();

            System.out.println(update+" updated products.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//    private static void finalAllQuery(Statement statement) {
//        ResultSet resultSet = null;
//        try {
//            resultSet = statement.
//                    executeQuery("SELECT product_id,name,description FROM products");
//
//            while(resultSet.next()){
//                int product_id = resultSet.getInt("product_id");
//                String name = resultSet.getString("name");
//                String description = resultSet.getString("description");
//
//                System.out.println(
//                                "Product with id: "+product_id+
//                                " and name: "+name+
//                                " and description: "+description+"."
//                );
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        } finally {
//            if(resultSet != null){
//                try {
//                    resultSet.close();
//                }catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    private static void insertProduct(Statement statement) {
        try {
            int inserted = statement.executeUpdate(
                        "INSERT INTO products VALUES "+
                            "(1,123456,'first product',null)"
            );

            System.out.println(inserted+" new products added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
