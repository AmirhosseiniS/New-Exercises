import java.io.*;
import java.sql.*;
import java.util.Scanner;

import static java.lang.Class.forName;



public class PhoneBookConnection {
    
    static Scanner scanner = new Scanner(System.in);
    private static Connection connection;



    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection
                    ("jdbc:mysql://localhost:3306/phone_book",
                            "root", "password");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        displayMenu();
    }

    public static void displayMenu() throws SQLException {
        System.out.println("Choose one of the options bellow.");
        System.out.println("a. Add a contact");
        System.out.println("b. Delete a contact");
        System.out.println("c. Edit a contact");
        System.out.println("d. list contacts");
        System.out.println("x. Exit");

        String textContent = scanner.nextLine();
        switch (textContent) {
            case ("a") -> {
                System.out.println("Enter a phone number:");
                String contactNumber = scanner.nextLine();
                String sql = "SELECT * FROM contacts WHERE number="+
                        contactNumber +";";

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if (result.next()) {
                    System.out.println("The number's already been saved.");
                    displayMenu();
                } else {
                    System.out.println("Enter a first name:");
                    String contactFirstName = scanner.nextLine();
                    System.out.println("Enter a last name:");
                    String contactLastName = scanner.nextLine();
                    System.out.println("Enter the E-mail address:");
                    String emailAddress = scanner.nextLine();
                    String sqlAdd = "INSERT INTO contacts (number," +
                            " first_name, last_name, email) VALUES (?, ?, ?, ?)";

                    PreparedStatement addStatement = connection.prepareStatement(sqlAdd);
                    addStatement.setString(1, contactNumber);
                    addStatement.setString(2, contactFirstName);
                    addStatement.setString(3, contactLastName);
                    addStatement.setString(4, emailAddress);

                    int rowsUpdated = addStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("A new contact is inserted.");
                    }

                }
                displayMenu();
            }

            case ("b") -> {
                System.out.println("Enter the number you want to delete:");
                String numberToDelete = scanner.nextLine();
                String sql = "SELECT * FROM contacts WHERE number="+
                        numberToDelete +";";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if (result.next()) {
                    String sqlDelete = "DELETE FROM contacts WHERE number=?";
                    PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete);
                    deleteStatement.setString(1, numberToDelete);
                    int rowsDeleted = deleteStatement.executeUpdate();
                    if (rowsDeleted > 0) {
                        System.out.println("The contact is deleted.");
                    }
                }
                else{
                    System.out.println("The number is not saved in the phonebook.");
                }
                displayMenu();
            }

            case ("c") -> {
                System.out.println("Enter the number of the contact you want to edit:");
                String contactNumber = scanner.nextLine();
                String sql = "SELECT * FROM contacts WHERE number="+
                        contactNumber +";";

                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                if (!result.next()) {
                    System.out.println("The number is not saved in the phonebook.");
                    displayMenu();
                } else {
                    System.out.println("Enter the new first name for the contact:");
                    String contactFirstName = scanner.nextLine();
                    System.out.println("Enter the new last name for the contact:");
                    String contactLastName = scanner.nextLine();
                    System.out.println("Enter the new E-mail address:");
                    String emailAddress = scanner.nextLine();
                    String sqlAdd = "UPDATE contacts SET first_name=?, last_name=?," +
                            "email=? WHERE number =?";
                    PreparedStatement editStatement = connection.prepareStatement(sqlAdd);
                    editStatement.setString(1, contactFirstName);
                    editStatement.setString(2, contactLastName);
                    editStatement.setString(3, emailAddress);
                    editStatement.setString(4, contactNumber);

                    int rowsInserted = editStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("The contact information is edited.");
                    }
                }
                displayMenu();
            }

            case ("d") -> {
                String sql = "SELECT * FROM contacts";
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(sql);
                int count = 0;
                while (result.next()) {
                    String number = result.getString("number");
                    String firstName = result.getString("first_name");
                    String lastName = result.getString("last_name");
                    String email = result.getString("email");

                    System.out.println(number + ", " + firstName + " " + lastName + ", " + email);
                }

            displayMenu();
            }

            case ("x") -> {
            System.out.println("...");
            }
            default -> displayMenu();
        }
    }
}











