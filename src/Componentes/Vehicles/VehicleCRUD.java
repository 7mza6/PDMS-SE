package Componentes.Vehicles;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class VehicleCRUD {
    private static final String FILE_PATH = ".\\src\\Componentes\\Vehicles\\Vehicles.json";

    // Read vehicles from JSON file
    public static ArrayList<Vehicle> readVehicles() {
        ArrayList<Vehicle> vehicleList = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<ArrayList<Vehicle>>() {}.getType();
            Gson gson = new Gson();
            vehicleList = gson.fromJson(reader, type);
        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
        return vehicleList;
    }

    public static void writeVehicles(ArrayList<Vehicle> vehicleList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(vehicleList, writer);
        } catch (IOException e) {
            System.out.println("Error writing to the JSON file: " + e.getMessage());
        }
    }

    // Create new vehicle
    public static void createVehicle(Vehicle vehicle) {
        ArrayList<Vehicle> vehicleList = readVehicles();
        vehicleList.add(vehicle);
        writeVehicles(vehicleList);
        System.out.println("Vehicle created successfully!");
    }

    // Read vehicle by ID
    public static Vehicle readVehicle(int id) {
        ArrayList<Vehicle> vehicleList = readVehicles();
        for (Vehicle vehicle : vehicleList) {
            if (vehicle.getId() == id) {
                return vehicle;
            }
        }
        return null;
    }

    // Update vehicle by ID
    public static void updateVehicle(int id, Vehicle updatedVehicle) {
        ArrayList<Vehicle> vehicleList = readVehicles();
        for (int i = 0; i < vehicleList.size(); i++) {
            if (vehicleList.get(i).getId() == id) {
                vehicleList.set(i, updatedVehicle);
                writeVehicles(vehicleList);
                System.out.println("Vehicle updated successfully.");
                return;
            }
        }
        System.out.println("Vehicle not found.");
    }

    // Delete vehicle by ID
    public static void deleteVehicle(int id) {
        ArrayList<Vehicle> vehicleList = readVehicles();
        vehicleList.removeIf(vehicle -> vehicle.getId() == id);
        writeVehicles(vehicleList);
        System.out.println("Vehicle deleted successfully.");
    }
}
