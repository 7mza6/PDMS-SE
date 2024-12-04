/*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Componentes.Route;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class RouteCRUD {

    private static final String FILE_PATH = ".\\src\\Componentes\\Route\\Route.json";

    // Read routes from JSON file
    public static ArrayList<Route> readRoutes() {
        ArrayList<Route> routeList = new ArrayList<>();
        try (FileReader reader = new FileReader(FILE_PATH)) {
            Type type = new TypeToken<ArrayList<Route>>() {
            }.getType();
            Gson gson = new Gson();
            routeList = gson.fromJson(reader, type);
        } catch (IOException e) {
            System.out.println("Error reading the JSON file: " + e.getMessage());
        }
        return routeList;
    }

    // Write routes to JSON file
    public static void writeRoutes(ArrayList<Route> routeList) {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            Gson gson = new Gson();
            gson.toJson(routeList, writer);
        } catch (IOException e) {
            System.out.println("Error writing to the JSON file: " + e.getMessage());
        }
    }

    // Create new route
    public static void createRoute(Route route) {
        ArrayList<Route> routeList = readRoutes();
        routeList.add(route);
        writeRoutes(routeList);
        System.out.println("Route created successfully!");
    }

    // Read route by ID
    public static Route readRoute(int id) {
        ArrayList<Route> routeList = readRoutes();
        for (Route route : routeList) {
            if (route.getId() == id) {
                return route;
            }
        }
        return null;
    }

    // Update route by ID
    public static void updateRoute(int id, Route updatedRoute) {
        ArrayList<Route> routeList = readRoutes();
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).getId() == id) {
                routeList.set(i, updatedRoute);
                writeRoutes(routeList);
                System.out.println("Route updated successfully.");
                return;
            }
        }
        System.out.println("Route not found.");
    }

    // Delete route by ID
    public static void deleteRoute(int id) {
        ArrayList<Route> routeList = readRoutes();
        routeList.removeIf(route -> route.getId() == id);
        writeRoutes(routeList);
        System.out.println("Route deleted successfully.");
    }

    public static Route getRouteById(int routeId) {
        try {
            ArrayList<Route> routeList = readRoutes(); // Method to read all routes
            for (Route route : routeList) {
                if (route.getId() == routeId) {
                    return route;
                }
            }
            System.out.println("Route with ID " + routeId + " not found.");
        } catch (Exception e) {
            System.out.println("Error retrieving route by ID: " + e.getMessage());
        }
        return null; // Return null if route not found
    }

}
