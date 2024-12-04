package Componentes.Staff;

import Componentes.Users.User;
import Componentes.Users.UserCRUDS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class StaffCRUDS {

    private static final String FILE_PATH = ".\\src\\Componentes\\Staff\\Staff.json";

    public static ArrayList<Staff> readStaff() {
    ArrayList<Staff> staffList = new ArrayList<>();
    File file = new File(FILE_PATH);

    if (!file.exists()) {
        try {
            file.createNewFile();  
        } catch (IOException e) {
            System.out.println("Error creating the JSON file: " + e.getMessage());
            return staffList;  
        }
    }

    try (FileReader reader = new FileReader(FILE_PATH)) {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Staff>>() {}.getType();
        staffList = gson.fromJson(reader, type);

        if (staffList == null) {
            staffList = new ArrayList<>();
        }

    } catch (IOException e) {
        System.out.println("Error reading the JSON file: " + e.getMessage());
    }

    return staffList;
}

    public static void writeStaff(ArrayList<Staff> staffList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(staffList, writer);
        } catch (IOException e) {
            System.out.println("Error writing to the JSON file: " + e.getMessage());
        }
    }

    public static void addStaff(Staff newStaff) {
        ArrayList<Staff> staffList = readStaff();
        staffList.add(newStaff);
        writeStaff(staffList);

        // If the new user is a Staff member, add to Users list
        if ("Staff".equalsIgnoreCase(newStaff.getRole())) {
            User newUser = new User(newStaff.getId(), newStaff.getFirstName(), newStaff.getLastName(),
                newStaff.getDateOfBirth(), newStaff.getGender(), newStaff.getAddress(),
                newStaff.getCelPhone(), newStaff.getTelPhone(), newStaff.getEmail(),
                newStaff.getUsername(), newStaff.getPassword(), newStaff.getRole());

            if (!UserCRUDS.isDuplicateUser(newUser.getId())) {
                UserCRUDS.addUser(newUser);  // Add to User list
            }
        }

        System.out.println("Staff added successfully!");
    }


    public static void updateStaffById(int id, Staff updatedStaff) {
    ArrayList<Staff> staffList = readStaff();
    for (int i = 0; i < staffList.size(); i++) {
        if (staffList.get(i).getId() == id) {
            staffList.set(i, updatedStaff);
            writeStaff(staffList);
            System.out.println("Staff updated successfully.");

            // Update corresponding user in Users list
            User updatedUser = new User(updatedStaff.getId(), updatedStaff.getFirstName(), 
                updatedStaff.getLastName(), updatedStaff.getDateOfBirth(), updatedStaff.getGender(), 
                updatedStaff.getAddress(), updatedStaff.getCelPhone(), updatedStaff.getTelPhone(), 
                updatedStaff.getEmail(), updatedStaff.getUsername(), updatedStaff.getPassword(), 
                updatedStaff.getRole());
            UserCRUDS.updateUserById(updatedStaff.getId(), updatedUser);
            return; // Exit the method after updating
        }
    }
    System.out.println("Staff with ID " + id + " not found.");
}


    public static Staff getStaffById(int id) {
    ArrayList<Staff> staffList = readStaff();
    for (Staff staff : staffList) {
        if (staff.getId() == id) {
            return staff;
        }
    }
    return null; // Return null if no staff with the given ID is found
}
    
    public static void deleteStaffById(int id) {
        ArrayList<Staff> staffList = readStaff();
        Staff staffToDelete = null;

        for (Staff staff : staffList) {
            if (staff.getId() == id) {
                staffToDelete = staff;
                break;
            }
        }

        if (staffToDelete != null) {
            staffList.remove(staffToDelete);
            writeStaff(staffList);
            System.out.println("Staff with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Staff with ID " + id + " not found.");
        }
    }
    

}
