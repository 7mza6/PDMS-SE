package Componentes.Packages;

import Componentes.Order.Order;
import Componentes.Order.OrderCRUDS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class PackageCRUDS {

    private static final String FILE_PATH = ".\\src\\Componentes\\Packages\\Package.json";

    public static ArrayList<Package> list;

    public static ArrayList<Package> Read() {
        try {
            FileReader read = new FileReader(FILE_PATH);
            Type packageType = new TypeToken<ArrayList<Package>>() {
            }.getType();
            Gson gson = new Gson();
            list = gson.fromJson(read, packageType);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public static void Write() {
    try (FileWriter writer = new FileWriter(FILE_PATH)) {
        Gson gson = new Gson();
        gson.toJson(list, writer);
        System.out.println("Package data saved successfully.");
    } catch (IOException ex) {
        Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, "Error writing to the package file", ex);
    }
}

    
    public static boolean Create(Package newPackage) {

        ArrayList<Package> PList = Read();
        if (PList == null) {
            PList = new ArrayList<>();
        }

        long packageID = newPackage.getPackageID();
        for (Package p : PList) {
            if (p.getPackageID() == packageID) {
                JOptionPane.showMessageDialog(null, "The ID already exists",
                        "ID Exist", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        String phone = newPackage.getReciverPhone();
        if (phone.length() != 10 || !IsInt(phone)) {
            JOptionPane.showMessageDialog(null, "Phone number is not valid",
                    "Phone Validation", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        PList.add(newPackage);

        try (FileWriter write = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(PList, write);
        } catch (IOException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public static ArrayList<Package> SearchByID(long ID) {
        ArrayList<Package> PList = Read();
        ArrayList<Package> result = new ArrayList<>();
        for (Package p : PList) {
            if (p.getPackageID() == ID) {
                result.add(p);
            }
        }
        return result;
    }

    public static boolean Update(Package updatedPackage) {
        ArrayList<Package> pList = Read();
        if (pList == null) {
            return false;
        }

        String phone = updatedPackage.getReciverPhone();
        if (phone.length() != 10 || !IsInt(phone)) {
            return false;
        }

        // Find the package by ID and update it
        for (int i = 0; i < pList.size(); i++) {
            if (pList.get(i).getPackageID() == updatedPackage.getPackageID()) {
                pList.set(i, updatedPackage);

                // Now, update the associated orders (if any)
                ArrayList<Order> orders = OrderCRUDS.getOrdersByPackageID(updatedPackage.getPackageID());
                for (Order order : orders) {
                    order.setPackagepdms(updatedPackage); // Update the order with the new package details

                    String[] data = {
                        order.getStatus(), // Updated status
                        order.getOrderDate(), // Existing order date
                        order.getDeleveryDate() // Existing delivery date
                    };
                    OrderCRUDS.UpdatePackage(updatedPackage);
                }

                // Save updated list to file
                try (FileWriter write = new FileWriter(FILE_PATH)) {
                    Gson gson = new Gson();
                    gson.toJson(pList, write);
                } catch (IOException ex) {
                    Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }

                return true;
            }
        }

        return false;
    }

    public static void Remove(long packageID) {
        ArrayList<Package> pList = Read();

        // Remove the package
        pList.removeIf(p -> p.getPackageID() == packageID);

        // Now, delete or update the associated orders (if any)
        ArrayList<Order> orders = OrderCRUDS.getOrdersByPackageID(packageID);
        for (Order order : orders) {
            // Either delete the order or update it (depending on your requirements)
            OrderCRUDS.Remove(order.getOrderID()); // Assuming you have a method to remove orders
        }

        try {
            FileWriter write = new FileWriter(FILE_PATH);
            Gson gson = new Gson();
            gson.toJson(pList, write);
            write.close();

        } catch (IOException ex) {
            Logger.getLogger(PackageCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean IsInt(String s) {
        boolean isValid;
        try {
            Integer.valueOf(s);
            isValid = true;
        } catch (NumberFormatException e) {
            isValid = false;
        }
        return isValid;

    }

    public static Package ReadOne(long packageID) {
        ArrayList<Package> PList = Read();
        for (Package p : PList) {
            if (p.getPackageID() == packageID) {
                return p;
            }
        }
        return null;
    }

    public static ArrayList<Package> getPackageByCity(String city) {
        ArrayList<Package> filteredPackages = new ArrayList<>();
        try {
            ArrayList<Package> packageList = Read(); // Method to read all packages
            for (Package pkg : packageList) {
                if (pkg.getBranch().getLocationdata().getCity().equalsIgnoreCase(city)) {
                    filteredPackages.add(pkg);
                }
            }
        } catch (Exception e) {
            System.out.println("Error retrieving packages by city: " + e.getMessage());
        }
        return filteredPackages;
    }

    

    
}
