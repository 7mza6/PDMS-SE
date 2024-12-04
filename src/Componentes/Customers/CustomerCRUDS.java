package Componentes.Customers;

import Componentes.Customers.Customer;
import Componentes.Packages.PackageCRUDS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class CustomerCRUDS {

    private static ArrayList<Customer> list = new ArrayList<>();
    private static final String FILE_PATH = "./src/Componentes/Customers/Customers.json";

    public static ArrayList<Customer> Read() {
        try (FileReader read = new FileReader(FILE_PATH)) {
            Type customerType = new TypeToken<ArrayList<Customer>>() {}.getType();
            list = new Gson().fromJson(read, customerType);
            if (list == null) {
                list = new ArrayList<>();
            }
        } catch (IOException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error reading customer file", ex);
        }
        return list;
    }

    public static Customer ReadOne(int customerID) {
        ArrayList<Customer> customerList = Read();
        if (customerList == null || customerList.isEmpty()) {
            System.out.println("Customer list is empty or could not be loaded.");
            return null;
        }
        for (Customer customer : customerList) {
            if (customer.getCustomerID() == customerID) {
                return customer;
            }
        }
        return null;
    }

    public static void Create(String[] data) {
        Read();
        int newID = list.stream().mapToInt(Customer::getCustomerID).max().orElse(0) + 1;
        String phone = data[3];
        if (phone.length() == 10 && IsInt(phone)) {
            list.add(new Customer(newID, data[0], data[1], data[2], phone, data[4], data[5]));
        } else {
            System.out.println("The phone number is not valid");
            return;
        }
        WriteToFile();
    }

    public static void Update(int customerID, Customer updatedCustomer) {
    ArrayList<Customer> customerList = Read();
    boolean updated = false;

    for (int i = 0; i < customerList.size(); i++) {
        if (customerList.get(i).getCustomerID() == customerID) {
            customerList.set(i, updatedCustomer);
            updated = true;
            
            // Update associated packages
            ArrayList<Componentes.Packages.Package> packageList = PackageCRUDS.Read();
            if (packageList != null) {
                for (Componentes.Packages.Package pkg : packageList) {
                    if (pkg.getCustomer().getCustomerID() == customerID) {
                        pkg.setCustomer(updatedCustomer); // Update the package with the new customer data
                        PackageCRUDS.Update(pkg); // Save the updated package
                    }
                }
            }
            break;
        }
    }

    if (updated) {
        WriteToFile();
        System.out.println("Customer and associated packages updated successfully.");
    } else {
        System.out.println("Customer not found.");
    }
}


   public static void Remove(int customerID) {
    ArrayList<Customer> customerList = Read();
    boolean removed = customerList.removeIf(customer -> customer.getCustomerID() == customerID);

    if (removed) {
        // Remove associated packages
        ArrayList<Componentes.Packages.Package> packageList = PackageCRUDS.Read();
        if (packageList != null) {
            packageList.removeIf(pkg -> pkg.getCustomer().getCustomerID() == customerID);
            PackageCRUDS.Write();
        }

        WriteToFile();
        System.out.println("Customer and associated packages removed successfully.");
    } else {
        System.out.println("Customer not found.");
    }
}


    private static void WriteToFile() {
        try (FileWriter write = new FileWriter(FILE_PATH)) {
            new Gson().toJson(list, write);
        } catch (IOException ex) {
            Logger.getLogger(CustomerCRUDS.class.getName()).log(Level.SEVERE, "Error writing to customer file", ex);
        }
    }

    public static boolean IsInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Customer SearchByID(int customerID) {
        return ReadOne(customerID);
    }
    
    public static ArrayList<Customer> Search(String data, JComboBox Type) {
        ArrayList<Customer> CList = Read();
        ArrayList<Customer> result = new ArrayList<>();

        if (CList == null || CList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There is no Users",
                    "No Users", JOptionPane.WARNING_MESSAGE);     
            return null;
        } else {
            for (Customer c : CList) {
                if (Type.getSelectedIndex() == 0) {
                    if (String.valueOf(c.getCustomerID()).contains(data)) {
                        result.add(c);
                    }
                }
                if (Type.getSelectedIndex() == 1) {
                    if (c.getFirstName().contains(data)) {
                        result.add(c);
                    }
                }
                if (Type.getSelectedIndex() == 2) {
                    if (c.getAddress().contains(data)) {
                        result.add(c);
                    }

                }
            }
            if (result.isEmpty()) {
                return null;
            }
            return result;
        }
    }

}
