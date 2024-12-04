package Componentes.Users;

import Componentes.Staff.Staff;
import Componentes.Staff.StaffCRUDS;
import Componentes.DeliveryStaff.DeliveryStaff;
import Componentes.DeliveryStaff.DeliveryCRUDS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class UserCRUDS {

    private static final String FILE_PATH = ".\\src\\Componentes\\Users\\Users.json";

    public static ArrayList<User> readUsers() {
        ArrayList<User> userList = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<ArrayList<User>>() {}.getType();
            Gson gson = new Gson();
            userList = gson.fromJson(reader, type);
        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
        return userList;
    }

    public static void writeUsers(ArrayList<User> userList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(userList, writer);
        } catch (IOException e) {
            System.out.println("Error writing to the JSON file: " + e.getMessage());
        }
    }

    // Add user and also handle "Staff" and "DeliveryStaff" roles
    public static void addUser(User newUser) {
        ArrayList<User> userList = readUsers();

        newUser.setId(userList.size() > 0 ? userList.get(userList.size() - 1).getId() + 1 : 1); // Assign the ID based on the last ID in the list
        userList.add(newUser);
        writeUsers(userList);

        // Ensure the same ID for both User and Staff / DeliveryStaff
        if ("Staff".equalsIgnoreCase(newUser.getRole())) {
            // Create the corresponding Staff with the same ID
            if (StaffCRUDS.getStaffById(newUser.getId()) == null) {
                Staff staff = new Staff(newUser.getId(), newUser.getFirstName(), newUser.getLastName(),
                                        newUser.getDateOfBirth(), newUser.getGender(), newUser.getAddress(),
                                        newUser.getCelPhone(), newUser.getTelPhone(), newUser.getEmail(),
                                        newUser.getUsername(), newUser.getPassword(), newUser.getRole(), "SomeBranch"); // Set branch accordingly
                StaffCRUDS.addStaff(staff);  // Ensure Staff list is updated
            }
        }

        // Handle DeliveryStaff similarly
        if ("Delevery Staff".equalsIgnoreCase(newUser.getRole())) {
            // Create the corresponding DeliveryStaff with the same ID
            if (DeliveryCRUDS.getDeliveryStaffById(newUser.getId()) == null) {
                DeliveryStaff deliveryStaff = new DeliveryStaff(newUser.getId(), newUser.getFirstName(),
                                                               newUser.getLastName(), newUser.getDateOfBirth(),
                                                               newUser.getGender(), newUser.getAddress(),
                                                               newUser.getCelPhone(), newUser.getTelPhone(),
                                                               newUser.getEmail(), newUser.getUsername(),
                                                               newUser.getPassword(), newUser.getRole());
                DeliveryCRUDS.addDeliveryStaff(deliveryStaff); 
                System.out.println("User added successfully!");
            }
        }

        System.out.println("User added successfully!");
    }



    public static void updateUserById(int id, User newUser) {
        ArrayList<User> userList = readUsers();

        boolean userFound = false;
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getId() == id) {
                // Replace the user at this position
                userList.set(i, newUser);
                userFound = true;
                break;
            }
        }

        if (userFound) {
            // Write the updated list back to the file
            writeUsers(userList);
            System.out.println("User with ID " + id + " updated successfully.");
        } else {
            System.out.println("User with ID " + id + " not found.");
        }
    }

    // Get user by their unique ID
    public static User getUserById(int id) {
        ArrayList<User> userList = readUsers();
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;  // User not found
    }

    // Get the role of the user by their username
    public static String getRoleByName(String username) {
        ArrayList<User> userList = readUsers();
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user.getRole();
            }
        }
        return null; 
    }

    public static boolean isDuplicateUserName(String username) {
        ArrayList<User> userList = readUsers();
        for (User user : userList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return true; 
            }
        }
        return false; 
    }
    public static boolean isDuplicateUser(int id) {
        ArrayList<User> userList = readUsers();
        for (User user : userList) {
            if (user.getId() == (id)) {
                return true; 
            }
        }
        return false; 
    }
    
    public static void deleteUserById(int id) {
    ArrayList<User> userList = readUsers();
    User userToDelete = null;

    // Find the user to delete
    for (User user : userList) {
        if (user.getId() == id) {
            userToDelete = user;
            break;
        }
    }

    if (userToDelete != null) {
        // Remove the user from the user list
        userList.remove(userToDelete);
        writeUsers(userList); // Save the updated list back to the JSON file

        // Check the role and delete from the corresponding list
        if ("Staff".equalsIgnoreCase(userToDelete.getRole())) {
            // Delete from Staff list
            StaffCRUDS.deleteStaffById(id);
        } else if ("DeliveryStaff".equalsIgnoreCase(userToDelete.getRole())) {
            // Delete from DeliveryStaff list
            DeliveryCRUDS.deleteDeliveryStaffById(id);
        }

        System.out.println("User with ID " + id + " deleted successfully.");
    } else {
        System.out.println("User with ID " + id + " not found.");
    }
}

    
}
