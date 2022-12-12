import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LookInnaBookApp {
    public static void main(String args[]) {
        Connection c = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LookInnaBook","postgres", "Password");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            System.out.println("*** Look Inna Book! ***");
            int selection = -1;
            Scanner scan = new Scanner(System.in);

            System.out.println("\nWelcome to Look Inna Book!\nStart by Creating the tables");
            while(selection != 0){
                System.out.println("\n**MAIN MENU**\n Please Make a Selection:\n 1. Create Tables \n 2. Drop All Tables\n 3. Exit Program");
                selection = Integer.parseInt(scan.nextLine());

                switch(selection){
                    case 1:
                        createTables(c);
                        break;
                    case 2:
                        dropAllTables(c);
                        break;
                    case 3:
                        dropAllTables(c);
                        c.close();
                        selection = 0;
                        System.out.println("EXITING...");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    static void dropAllTables(Connection c){
        try {
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE AUTHOR CASCADE;"+
                    "DROP TABLE BOOK CASCADE;"+
                    "DROP TABLE BOOK_ORDER CASCADE;"+
                    "DROP TABLE BOOK_USER CASCADE;"+
                    "DROP TABLE GENRE CASCADE;"+
                    "DROP TABLE OWNER CASCADE;"+
                    "DROP TABLE PHONE CASCADE;"+
                    "DROP TABLE PUBLISHER CASCADE;"+
                    "DROP TABLE SOLD CASCADE;";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            System.out.println("Tables Dropped");
        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
    static void createTables(Connection c){
        try{
            Statement stmt = c.createStatement();
            String sql = "CREATE TABLE Owner" +
                    "(password VARCHAR(20) NOT NULL," +
                    "id VARCHAR(20) NOT NULL," +
                    "first_name VARCHAR(15) NOT NULL,"+
                    "last_name VARCHAR(15) NOT NULL,"+
                    "PRIMARY KEY (id));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Book_User " +
                    "(password VARCHAR(20) NOT NULL,"+
                    "email VARCHAR(40) NOT NULL,"+
                    "card_expiration NUMERIC(4, 0) NOT NULL,"+
                    "card_number NUMERIC(20, 0) NOT NULL,"+
                    "card_cvv NUMERIC(10, 0) NOT NULL,"+
                    "phone NUMERIC(10, 0) NOT NULL,"+
                    "first_name VARCHAR(15) NOT NULL,"+
                    "last_name VARCHAR(15) NOT NULL,"+
                    "id VARCHAR(20) NULL,"+
                    "PRIMARY KEY (id));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Publisher"+
                    "(email VARCHAR(40) NOT NULL,"+
                    "account NUMERIC(7, 0) NOT NULL,"+
                    "transit NUMERIC(5, 0) NOT NULL,"+
                    "institution NUMERIC(3, 0) NOT NULL,"+
                    "id INT NOT NULL,"+
                    "first_name VARCHAR(15) NOT NULL,"+
                    "last_name VARCHAR(15) NOT NULL,"+
                    "house INT NOT NULL,"+
                    "street VARCHAR(20) NOT NULL,"+
                    "city VARCHAR(20) NOT NULL,"+
                    "postal VARCHAR(7) NOT NULL,"+
                    "store_contact VARCHAR(20) NOT NULL,"+
                    "PRIMARY KEY (id),"+
                    "FOREIGN KEY (store_contact) REFERENCES Owner(id));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Book_Order"+
                    "(number NUMERIC(15,0) NOT NULL,"+
                    "delivery_date DATE,"+
                    "ship_date DATE,"+
                    "total_price NUMERIC(12, 2) NOT NULL,"+
                    "shipping_house INT NOT NULL,"+
                    "shipping_street VARCHAR(30) NOT NULL,"+
                    "shipping_city VARCHAR(30) NOT NULL,"+
                    "shipping_postal VARCHAR(7) NOT NULL,"+
                    "billing_house INT NOT NULL,"+
                    "billing_street VARCHAR(30) NOT NULL,"+
                    "billing_city VARCHAR(30) NOT NULL,"+
                    "billing_postal VARCHAR(7) NOT NULL,"+
                    "user_id VARCHAR(20) NOT NULL,"+
                    "PRIMARY KEY (number),"+
                    "FOREIGN KEY (user_id) REFERENCES Book_User(id));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Phone"+
                    "(phone NUMERIC(10, 0) NOT NULL,"+
                    "publisher_id INT NOT NULL,"+
                    "PRIMARY KEY (phone, publisher_id),"+
                    "FOREIGN KEY (publisher_id) REFERENCES Publisher(id));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Book"+
                    "(name VARCHAR(100) NOT NULL,"+
                    "percentage NUMERIC(5, 2) NOT NULL,"+
                    "price NUMERIC(10, 2) NOT NULL,"+
                    "pages INT NOT NULL,"+
                    "isbn NUMERIC(13, 0) NOT NULL,"+
                    "stock INT NOT NULL,"+
                    "publisher_id INT NOT NULL,"+
                    "PRIMARY KEY (isbn),"+
                    "FOREIGN KEY (publisher_id) REFERENCES Publisher(id));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Genre"+
                    "(genre VARCHAR(30) NOT NULL,"+
                    "book_isbn NUMERIC(13, 0) NOT NULL,"+
                    "PRIMARY KEY (genre, book_isbn),"+
                    "FOREIGN KEY (book_isbn) REFERENCES Book(isbn));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE Author"+
                    "(first_name VARCHAR(15) NOT NULL,"+
                    "last_name VARCHAR(15) NOT NULL,"+
                    "book_isbn NUMERIC(13, 0) NOT NULL,"+
                    "PRIMARY KEY (first_name, last_name, book_isbn),"+
                    "FOREIGN KEY (book_isbn) REFERENCES Book(isbn));";
            stmt.executeUpdate(sql);
            sql = "CREATE TABLE sold"+
                    "(order_number NUMERIC(15,0) NOT NULL,"+
                    "book_isbn NUMERIC(13, 0) NOT NULL,"+
                    "PRIMARY KEY (order_number, book_isbn),"+
                    "FOREIGN KEY (order_number) REFERENCES Book_Order(number),"+
                    "FOREIGN KEY (book_isbn) REFERENCES Book(isbn));";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            System.out.println("Table ADDED");
        }
        catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
