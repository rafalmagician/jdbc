package projectJDBC;

import com.mysql.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class MyShop {
    private static final String DB_URL = "jdbc:mysql://localhost/produkty";
    private static final String USER = "newuser";
    private static final String PASS = "brak";
    private static Scanner reader = new Scanner(System.in);

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
            assert connection != null;
            statement = connection.createStatement();

            switch (1){
                case 1:{
                    showAllProducts(statement);
                    break;
                }
                case 2:{
                    preparedStatement = connection.prepareStatement(
                            "INSERT INTO products VALUES"+
                                    "(?,?,?,?)"
                    );
                    addOneProduct(preparedStatement);
                    break;
                }
                case 3:{
                    preparedStatement = connection.prepareStatement(
                            "DELETE FROM products "+
                                    "WHERE product_id=?"
                    );
                    deleteProduct(preparedStatement);
                    break;
                }
                case 4:{
                    preparedStatement = connection.prepareStatement(
                            "UPDATE products "+
                                    "SET catalog_number=?,name=?,description=? WHERE product_id = ?"
                    );
                    updateProduct(preparedStatement);
                    break;
                }
            }
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
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------------------------------------
    private static void updateProduct(PreparedStatement preparedStatement) {
        try {
            System.out.println("Please enter new data: ");
            System.out.print("catalog_number: ");
            int catalog_number = reader.nextInt();
            System.out.print("name: ");
            String name = reader.next();
            System.out.print("description: ");
            String description = reader.next();
            System.out.println("Please enter product_id: ");
            System.out.print("product_id: ");
            int product_id = reader.nextInt();

            preparedStatement.setInt(1,catalog_number);
            preparedStatement.setString(2,name);
            preparedStatement.setString(3,description);
            preparedStatement.setInt(4,product_id);

            int update = preparedStatement.executeUpdate();

            System.out.println(update+" updated products.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void deleteProduct(PreparedStatement preparedStatement) {
        try {
            System.out.print("Please enter product_id: ");
            int product_id = reader.nextInt();

            preparedStatement.setInt(1,product_id);

            preparedStatement.executeUpdate();

            System.out.println(" deleted product.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addOneProduct(PreparedStatement preparedStatement) {
        try {
            System.out.println("Please enter data: ");
            System.out.print("product_id: ");
            int product_id = reader.nextInt();
            System.out.print("catalog_number: ");
            int catalog_number = reader.nextInt();
            System.out.print("name: ");
            String name = reader.next();
            System.out.print("description: ");
            String description = reader.next();

            preparedStatement.setInt(1,product_id);
            preparedStatement.setInt(2,catalog_number);
            preparedStatement.setString(3,name);
            preparedStatement.setString(4,description);

            int update = preparedStatement.executeUpdate();

            System.out.println(update+" new products added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
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
}
