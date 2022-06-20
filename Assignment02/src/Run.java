import java.sql.*;
import java.util.Scanner;

public class Run {
    private static Scanner input = new Scanner(System.in);

    public static  void menu(){
        System.out.println("========== Actions=========");
        System.out.println("1. Add product");
        System.out.println("2. Edit product");
        System.out.println("3. Delete product");
        System.out.println("4. View all product (default sort by Name of product)");
        System.out.println("5. Search product by id");
        System.out.println("6. Search product by name");
        System.out.println("7. End");
    }

    public static void main(String[] args) throws SQLException{
        Connection connection = getConnection();
        menu();
        while(true) {
            int choice;
            System.out.println("Your choice: ");
            choice = input.nextInt();
            if (choice == 1){
                createProduct();
                menu();
            } else if (choice == 2) {
                updateProduct();
                menu();
            } else if (choice == 3) {
                deleteProduct();
                menu();
            } else if (choice == 4) {
                readProductData();
                menu();
            } else if (choice == 5) {
                searchById();
                menu();
            } else if (choice == 6) {
                searchByName();
                menu();
            } else if (choice == 7) {
                System.exit(0);
            }
        }
    }
    public static Connection getConnection()  throws SQLException{
        String dbURl = "jdbc:mysql://localhost:3306/dbtest";
        String username = "root";
        String password = "";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void createProduct()  throws SQLException{
        int id;
        String name;
        String desc;
        double price;
        try {
            Connection connection = getConnection();
            String query = "insert into product values(?,?,?,?)";
            PreparedStatement preparedStatement = null;
            preparedStatement = connection.prepareStatement(query);

            System.out.println("Enter id:");
            id = input.nextInt();
            preparedStatement.setInt(1,id);

            System.out.println("Enter product name:");
            name = input.next();
            preparedStatement.setString(2,name);

            System.out.println("Enter product desc:");
            desc = input.next();
            preparedStatement.setString(3,desc);

            System.out.println("Enter product price:");
            price = input.nextDouble();
            preparedStatement.setDouble(4,price);

            int rowInserted = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void deleteProduct()  throws SQLException{
        int id;
        Connection connection = getConnection();
        String query = "DELETE FROM product WHERE id = ?";
        PreparedStatement statement = null;
        statement = connection.prepareStatement(query);
        System.out.println("Nhập id sản phẩm mà bạn muốn xoá: ");
        id = input.nextInt();
        statement.setInt(1,id);
        int rowsDelete = statement.executeUpdate();
        if (rowsDelete>0){
            System.out.println("Delete thành công");
        }
    }
    public static void updateProduct()  throws SQLException{
        int id;
        String name;
        String desc;
        double price;

        Connection connection = getConnection();
        String query = "UPDATE product SET proName = ?, proDesc = ?, price = ? WHERE id = ?;";
        PreparedStatement statement = connection.prepareStatement(query);

        System.out.println("Nhập id sản phẩm bạn muốn update: ");
        id = input.nextInt();
        statement.setInt(4,id);

        System.out.println("Nhập tên mới:");
        name = input.next();
        statement.setString(1,name);

        System.out.println("Nhập mô tả mới:");
        desc = input.next();
        statement.setString(2,desc);

        System.out.println("Nhập giá mới:");
        price = input.nextDouble();
        statement.setDouble(3,price);

        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated>0){
            System.out.println("Update thành công");
        }
    }
    public static void readProductData()throws SQLException {
        Connection connection = getConnection();
        String query = "select * from product order by proName";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString("proName");
            String desc = resultSet.getString(3);
            double price = resultSet.getDouble("price");
            System.out.println(id + " " + name + " " + desc + " " + price);
        }
    }
    public static void searchById()throws SQLException {
        int id;
        Connection connection = getConnection();
        String query = "SELECT * from product where id = ?";

        PreparedStatement statement = connection.prepareStatement(query);

        System.out.println("Enter student id: ");
        id = input.nextInt();
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int idp = resultSet.getInt(1);
            String name = resultSet.getString("proName");
            String desc = resultSet.getString("proDesc");
            double price = resultSet.getDouble("price");
            System.out.println(idp + " " + name + " " + desc + " " + price);
        }
    }
    public static void searchByName()throws SQLException {
        String name;
        Connection connection = getConnection();
        String query = "SELECT * from product where proName = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        System.out.println("Enter student name: ");
        name = input.next();
        statement.setString(1,name);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()){
            int idp = resultSet.getInt(1);
            String namep = resultSet.getString("proName");
            String desc = resultSet.getString("proDesc");
            double price = resultSet.getDouble("price");
            System.out.println(idp + " " + name + " " + desc + " " + price);
        }
    }


}
