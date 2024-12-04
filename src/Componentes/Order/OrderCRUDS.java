package Componentes.Order;

import Componentes.Customers.Customer;
import Componentes.DeliveryStaff.DeliveryCRUDS;
import Componentes.DeliveryStaff.DeliveryStaff;
import Componentes.Packages.Package;

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
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import raven.toast.Notifications;



public class OrderCRUDS {
    private static final String FILE_PATH = ".\\src\\Componentes\\Order\\Order.json";
    public static ArrayList<Order> list;
    private static int i;

    public static ArrayList<Order> Read() {
        if (list == null) {
            list = new ArrayList<>();
        }
        try {
            FileReader read = new FileReader(FILE_PATH);
            Type OrderType = new TypeToken<ArrayList<Order>>() {
            }.getType();
            Gson gson = new Gson();
            list = gson.fromJson(read, OrderType);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrderCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    static void create(Customer customer, Package packagepdms, String status, String orderDate, String deliveryDate, float totalCost) {
        // Read existing orders
        ArrayList<Order> orders = Read();

        // Generate a unique order ID
        int newOrderID = orders.isEmpty() ? 1 : orders.get(orders.size() - 1).getOrderID() + 1;

        // Create a new order
        Order newOrder = new Order(newOrderID, customer, packagepdms, status, orderDate, totalCost);
        newOrder.setDeleveryDate(deliveryDate);

        orders.add(newOrder);

        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(orders, writer);
            
        } catch (IOException ex) {
            Logger.getLogger(OrderCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public static void Update(int ID, String[] data) {
    ArrayList<Order> OList = Read();
    boolean updated = false;

    for (Order order : OList) {
        if (order.getOrderID() == ID) {
            order.setStatus(data[0]);
            order.setOrderDate(data[1]);
            order.setDeleveryDate(data[2]);
            updated = true;

            // Update delivery staff if the order is assigned
            DeliveryStaff assignedStaff = DeliveryCRUDS.getStaffByOrderId(ID);
            if (assignedStaff != null) {
                DeliveryCRUDS.updateOrderDetails(ID, data[0], data[1], data[2]);
                                DeliveryCRUDS.updateOrderDetails(ID, data[0], data[1], data[2]);

            }
            break;
        }
    }

    if (updated) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(OList, writer);

            Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order updated successfully.");
        } catch (IOException ex) {
            Logger.getLogger(OrderCRUDS.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Order not found.");
    }
}


   public static void Remove(int orderId) {
    // Read the existing orders
    ArrayList<Order> orders = Read();

    // Find and remove the order with the matching ID
    orders.removeIf(order -> order.getOrderID() == orderId);

    // Save the updated list back to the JSON file
    try (FileWriter writer = new FileWriter(FILE_PATH)) {
        Gson gson = new Gson();
        gson.toJson(orders, writer);
    } catch (IOException ex) {
        Logger.getLogger(OrderCRUDS.class.getName()).log(Level.SEVERE, null, ex);
    }
}
    
    
    public static ArrayList<Order> Search(String data, JComboBox Type) {
        ArrayList<Order> OList = Read();  
        ArrayList<Order> result = new ArrayList<>();

        if (OList == null || OList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "There are no orders",
                    "No Orders", JOptionPane.WARNING_MESSAGE);
            return null;
        } else {
            for (Order o : OList) {
                if (Type.getSelectedIndex() == 0) {

                    if (o.getStatus().contains(data)) {
                        result.add(o);
                    }
                }
                if (Type.getSelectedIndex() == 1) {

                    if (o.getOrderDate().contains(data)) {
                        result.add(o);
                    }
                }
            }
            if (result.isEmpty()) {
                return null;  
            }
            return result;  
        }
    }
    public static Order getById(int orderID) {
        ArrayList<Order> OList = Read();
        for (Order o : OList) {
            if (o.getOrderID() == orderID) {
                return o;
            }
        }
        return null; 
    }
    public static ArrayList<Order> getOrdersByCustomerId(int customerId) {
    ArrayList<Order> allOrders = Read();
    ArrayList<Order> customerOrders = new ArrayList<>();
    
    for (Order order : allOrders) {
        if (order.getCustomer().getCustomerID() == customerId) {
            customerOrders.add(order);
        }
    }
    return customerOrders;
}
    
public static ArrayList<Long> getPackageIDsByOrderId(int orderId) {
    ArrayList<Long> packageIDs = new ArrayList<>();
    // Read all orders
    ArrayList<Order> orders = Read();
    
    // Loop through orders and check for matching orderId
    for (Order order : orders) {
        if (order.getOrderID() == orderId) {
            packageIDs.add(order.getPackagepdms().getPackageID());
        }
    }
    
    return packageIDs;
}


public static ArrayList<Order> getOrdersByPackageID(long packageID) {
        // This method retrieves all orders associated with a given package ID
        ArrayList<Order> orders = new ArrayList<>();
        // Logic to retrieve orders based on packageID
        // Example: Iterate through all orders and check the package ID
        for (Order order : OrderCRUDS.Read()) {
            if (order.getPackagepdms().getPackageID()== packageID) {
                orders.add(order);
            }
        }
        return orders;
    }

    public static void UpdatePackage(Package updatedPackage) {
        // Retrieve all orders
        ArrayList<Order> orders = Read(); 

        // Iterate through the orders and update the package for matching packageID
        for (Order order : orders) {
            if (order.getPackagepdms().getPackageID() == updatedPackage.getPackageID()) {
                // Update the package details in the order
                order.setPackagepdms(updatedPackage);

                // After updating the order, write the updated list back to the file
                try (FileWriter write = new FileWriter("path_to_orders_file")) {
                    Gson gson = new Gson();
                    gson.toJson(orders, write);
                } catch (IOException ex) {
                    Logger.getLogger(OrderCRUDS.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    
    
}
