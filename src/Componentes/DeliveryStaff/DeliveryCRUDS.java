package Componentes.DeliveryStaff;

import Componentes.DeliveryStaff.DeliveryStaff;
import Componentes.Order.Order;
import Componentes.Users.User;
import Componentes.Users.UserCRUDS;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import raven.toast.Notifications;

public class DeliveryCRUDS {

    private static final String FILE_PATH = ".\\src\\Componentes\\DeliveryStaff\\DeliveryStaff.json";

    // Read delivery staff from the DeliveryStaff.json file
    public static ArrayList<DeliveryStaff> readDeliveryStaff() {
        ArrayList<DeliveryStaff> deliveryList = new ArrayList<>();
        File file = new File(FILE_PATH);

        // Check if the file exists, if not, create an empty file
        if (!file.exists()) {
            try {
                file.createNewFile();  // Create an empty file if it doesn't exist
            } catch (IOException e) {
                System.out.println("Error creating the JSON file: " + e.getMessage());
                return deliveryList;  // Return empty list in case of error
            }
        }

        // If the file exists, try reading from it
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<DeliveryStaff>>() {
            }.getType();
            deliveryList = gson.fromJson(reader, type);

            // If the file is empty, gson will return null. In that case, we initialize the list
            if (deliveryList == null) {
                deliveryList = new ArrayList<>();
            }

        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }

        return deliveryList;
    }

    // Write the list of delivery staff to the DeliveryStaff.json file
    public static void writeDeliveryStaff(ArrayList<DeliveryStaff> deliveryList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(deliveryList, writer);
        } catch (IOException e) {
            System.out.println("Error writing to the JSON file: " + e.getMessage());
        }
    }

    // Add a new delivery staff and update the Users list
    public static void addDeliveryStaff(DeliveryStaff newDeliveryStaff) {
        ArrayList<DeliveryStaff> deliveryStaffList = readDeliveryStaff();
        deliveryStaffList.add(newDeliveryStaff);
        writeDeliveryStaff(deliveryStaffList);

        if ("Delivery Staff".equalsIgnoreCase(newDeliveryStaff.getRole())) {
            User newUser = new User(newDeliveryStaff.getId(), newDeliveryStaff.getFirstName(), newDeliveryStaff.getLastName(),
                    newDeliveryStaff.getDateOfBirth(), newDeliveryStaff.getGender(), newDeliveryStaff.getAddress(),
                    newDeliveryStaff.getCelPhone(), newDeliveryStaff.getTelPhone(), newDeliveryStaff.getEmail(),
                    newDeliveryStaff.getUsername(), newDeliveryStaff.getPassword(), newDeliveryStaff.getRole());

            if (!UserCRUDS.isDuplicateUser(newUser.getId())) {
                UserCRUDS.addUser(newUser);
            }
        }

        System.out.println("Delivery Staff added successfully!");
    }

    // Update an existing delivery staff
    public static void updateDeliveryStaffById(int id, DeliveryStaff updatedDeliveryStaff) {
        ArrayList<DeliveryStaff> deliveryList = readDeliveryStaff();
        for (int i = 0; i < deliveryList.size(); i++) {
            if (deliveryList.get(i).getId() == id) {
                deliveryList.set(i, updatedDeliveryStaff);
                writeDeliveryStaff(deliveryList);
                System.out.println("Delivery staff updated successfully.");

                // Update corresponding user in Users list
                User updatedUser = new User(updatedDeliveryStaff.getId(), updatedDeliveryStaff.getFirstName(),
                        updatedDeliveryStaff.getLastName(), updatedDeliveryStaff.getDateOfBirth(),
                        updatedDeliveryStaff.getGender(), updatedDeliveryStaff.getAddress(),
                        updatedDeliveryStaff.getCelPhone(), updatedDeliveryStaff.getTelPhone(),
                        updatedDeliveryStaff.getEmail(), updatedDeliveryStaff.getUsername(),
                        updatedDeliveryStaff.getPassword(), updatedDeliveryStaff.getRole());
                UserCRUDS.updateUserById(updatedDeliveryStaff.getId(), updatedUser);
                return;
            }
        }

        System.out.println("Delivery staff with id " + id + " not found.");
    }

    // Get delivery staff by ID
    public static DeliveryStaff getDeliveryStaffById(int id) {
        ArrayList<DeliveryStaff> deliveryList = readDeliveryStaff();
        for (DeliveryStaff delivery : deliveryList) {
            if (delivery.getId() == id) {
                return delivery;
            }
        }
        return null;
    }

    public static void addOrderToAssignedList(int deliveryStaffId, Order order) {
        ArrayList<DeliveryStaff> deliveryList = readDeliveryStaff();

        for (DeliveryStaff deliveryStaff : deliveryList) {
            if (deliveryStaff.getId() == deliveryStaffId) {
                // Initialize the assignedOrders list if it's null
                if (deliveryStaff.getAssignedOrders() == null) {
                    deliveryStaff.setAssignedOrders(new ArrayList<>());
                }

                // Check if the order is already assigned
                if (!deliveryStaff.getAssignedOrders().contains(order)) {
                    deliveryStaff.getAssignedOrders().add(order);
                    writeDeliveryStaff(deliveryList);

                    System.out.println("Order added successfully to Delivery Staff ID: " + deliveryStaffId);
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_CENTER, "Order assigned successfully.");
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_CENTER, "This order is already assigned to the delivery staff.");
                }
                return;
            }
        }

        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "Delivery staff with ID " + deliveryStaffId + " not found.");
    }

    public static void deleteDeliveryStaffById(int id) {
        ArrayList<DeliveryStaff> deliveryStaffList = readDeliveryStaff();
        DeliveryStaff deliveryStaffToDelete = null;

        for (DeliveryStaff deliveryStaff : deliveryStaffList) {
            if (deliveryStaff.getId() == id) {
                deliveryStaffToDelete = deliveryStaff;
                break;
            }
        }

        if (deliveryStaffToDelete != null) {
            deliveryStaffList.remove(deliveryStaffToDelete);
            writeDeliveryStaff(deliveryStaffList);
            System.out.println("Delivery Staff with ID " + id + " deleted successfully.");
        } else {
            System.out.println("Delivery Staff with ID " + id + " not found.");
        }
    }

    public static boolean isOrderAssigned(int orderId) {
        ArrayList<DeliveryStaff> deliveryList = readDeliveryStaff();

        for (DeliveryStaff deliveryStaff : deliveryList) {
            ArrayList<Order> assignedOrders = deliveryStaff.getAssignedOrders();
            if (assignedOrders != null) {
                for (Order order : assignedOrders) {
                    if (order.getOrderID() == orderId) {
                        return true;
                    }
                }
            }
        }

        return false; // Order is not assigned
    }

    public static void updateOrderDetails(int orderId, String status, String orderDate, String deliveryDate) {
        // Read all delivery staff records
        ArrayList<DeliveryStaff> staffList = readDeliveryStaff();

        // Loop through the staff list to find the assigned order
        for (DeliveryStaff staff : staffList) {
            for (Order assignedOrder : staff.getAssignedOrders()) {
                if (assignedOrder.getOrderID() == orderId) {
                    // Update order details
                    assignedOrder.setStatus(status);
                    assignedOrder.setOrderDate(orderDate);
                    assignedOrder.setDeleveryDate(deliveryDate);

                    // Save updated staff record back to the storage                    
                    updateDeliveryStaffById(staff.getId(), staff);

                    return;
                }
            }
        }

        Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_CENTER, "No delivery staff found for order ID: " + orderId);

    }

    public static DeliveryStaff getStaffByOrderId(int orderId) {
        ArrayList<DeliveryStaff> staffList = readDeliveryStaff();
        for (DeliveryStaff staff : staffList) {

            for (Order assignedOrder : staff.getAssignedOrders()) {
                if (assignedOrder.getOrderID() == orderId) {
                    return staff;
                }

            }
        }
        return null;

    }
}
