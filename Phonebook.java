import org.apache.commons.io.FileUtils;
import java.io.*;
import java.util.Scanner;

public class Phonebook {

    private static final String PHONEBOOK = "Phonebook.txt";
    static Scanner scanner = new Scanner(System.in);
    public static File phoneBookFile;

    public static File fileCreator() {
        phoneBookFile = new File(PHONEBOOK);
        try {
            if (!phoneBookFile.exists()) {
                boolean fileCreated = phoneBookFile.createNewFile();
                if (fileCreated) {
                    System.out.println("Phonebook file created");
                } else {
                    System.err.println("Failed to create Phonebook file: ");
                }
            }
        } catch (IOException e) {
            System.err.println("Error creating Phonebook file: " + e.getMessage());
        }
        return phoneBookFile;
    }

    public static void main(String[] args) throws IOException {
        fileCreator();
        displayMenu();
    }

    public static void displayMenu() throws IOException {
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
                long contactNumber = scanner.nextLong();
                scanner.nextLine();
                if (findContact(contactNumber)) {
                    System.out.println("The number's already been saved.");
                    displayMenu();
                } else {
                    System.out.println("Enter a name:");
                    String contactName = scanner.nextLine();
                    String contact = "0" + contactNumber + "," + contactName;
                    addContact(contactNumber, contact);
                    displayMenu();
                }
            }

            case ("b") -> {
                System.out.println("Enter the number you want to delete:");
                long numberToDelete = scanner.nextLong();
                scanner.nextLine();
                if (findContact(numberToDelete)) {
                    try {
                        removeLineFromFile(phoneBookFile, String.valueOf(numberToDelete));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("The number is not saved in the phonebook.");
                }
                displayMenu();
            }

            case ("c") -> {
                System.out.println("Enter the number of the contact you want to edit:");
                long contactNumber = scanner.nextLong();
                scanner.nextLine();
                if (findContact(contactNumber)) {
                    System.out.println("Enter the new name for the contact:");
                    String contactName = scanner.nextLine();
                    String contact = "0" + contactNumber + "," + contactName;
                    try {
                        removeLineFromFile(phoneBookFile, String.valueOf(contactNumber));
                        addContact(contactNumber, ("\n" + contact));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    else {
                    System.out.println("The number is not saved in the Phonebook.");
                }
                displayMenu();
            }

            case ("d") -> {
                System.out.println("Contacts:");
                try (BufferedReader bufferedReader = new BufferedReader(new FileReader(phoneBookFile))) {
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        System.out.println(line);
                    }
                }
                displayMenu();
            }

            case ("x") -> {
                System.out.println("...");
            }
            default -> displayMenu();
        }
    }

    public static void addContact(long contactNumber, String contact) {
        try {
            FileWriter fileWriter = new FileWriter(phoneBookFile, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            if (findContact(contactNumber)) {
                System.out.println("The number's already been saved.");
            } else {
                printWriter.println(contact);
                printWriter.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean findContact(long contactNumber) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(phoneBookFile));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(String.valueOf(contactNumber))) {
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void removeLineFromFile(File targetFile, String searchTerm) throws IOException {
        StringBuffer fileContents =
                new StringBuffer(FileUtils.readFileToString(targetFile));
        String[] fileContentLines = fileContents.toString().split(System.lineSeparator());
        fileContents = new StringBuffer();
        for (int fileContentLinesIndex = 0;
             fileContentLinesIndex < fileContentLines.length;
             fileContentLinesIndex++) {
            if (fileContentLines[fileContentLinesIndex].contains(searchTerm)) {
                continue;
            }
            fileContents.append(fileContentLines[fileContentLinesIndex] + System.lineSeparator());
        }
        FileUtils.writeStringToFile(targetFile, fileContents.toString().trim());
    }
}
