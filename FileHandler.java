import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileHandler {
    private static final String ACCOUNT_FILE = "accounts.ser";
    private static final String LAST_ACCOUNT_ID_FILE = "lastAccountId.txt";
    
    public static int loadLastAccountId() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LAST_ACCOUNT_ID_FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            // Default to 1000 if the file doesn't exist or is invalid
            return 1000;
        }
    }

    public static void saveLastAccountId(int lastAccountId) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LAST_ACCOUNT_ID_FILE))) {
            writer.write(String.valueOf(lastAccountId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save all accounts to a file
    public static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNT_FILE))) {
            oos.writeObject(BankAccount.getAccountRegistry());
            System.out.println("Accounts saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all accounts from a file
    public static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNT_FILE))) {
            Map<Integer, BankAccount> accounts = (Map<Integer, BankAccount>) ois.readObject();
            BankAccount.setAccountRegistry(accounts);
            System.out.println("Accounts loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No previous accounts found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
