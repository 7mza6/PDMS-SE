# PDMS-SE

A Java-based desktop application providing a modern dashboard experience with light and dark mode support, built using Java Swing and FlatLaf. This project appears to be a component of a larger Package Delivery Management System (PDMS), focusing on user interface and potentially data management capabilities.

## Key Features & Benefits

*   **Modern User Interface**: Leverages the FlatLaf look and feel to provide a sleek, modern, and customizable UI for Java Swing applications.
*   **Dynamic Theming**: Integrated light and dark mode functionality allows users to switch between themes based on preference or environment.
*   **Flexible Layouts**: Utilizes MigLayout for powerful and flexible UI component arrangement, making the interface responsive and adaptable.
*   **Toast Notifications**: Provides user-friendly feedback through unobtrusive toast notifications for events and actions.
*   **SVG Support**: Renders Scalable Vector Graphics (SVG) through the jsvg library, allowing for high-quality, resolution-independent icons and graphics.
*   **JSON Data Handling**: Incorporates Gson for efficient serialization and deserialization of Java objects to/from JSON, crucial for data persistence and exchange (as seen with `Package.json`).
*   **Package Management Integration (Potential)**: The structure observed in `src/Componentes/Packages/Package.json` suggests a robust system for managing package details, recipient information, branch data, and customer profiles, hinting at its role within a broader PDMS.

## Prerequisites & Dependencies

To run or develop this project, you will need:

### Languages
*   **Java**: The project is written primarily in Java. A Java Development Kit (JDK) is required.

### Tools & Technologies
*   **Java Development Kit (JDK)**: Version 8 or higher.
*   **Apache Ant**: Recommended for building the project using the provided `build.xml` file.
*   **Node.js**: As specified in the technologies, though its direct role in the Java Swing application context is not immediately apparent from the provided project structure, it may be used for supplementary tooling or processes.

### Libraries
The project bundles its dependencies within the `lib/` and `library/` directories. Key libraries include:

*   **FlatLaf (v3.4.1)**: Modern open-source Look and Feel for Java Swing desktops.
    *   `flatlaf-3.4.1.jar`
    *   `flatlaf-extras-3.4.1.jar`
    *   `flatlaf-fonts-roboto-2.137.jar` (Roboto font integration for FlatLaf)
*   **jsvg (v1.4.0)**: A Java SVG renderer.
    *   `jsvg-1.4.0.jar`
*   **MigLayout**: Powerful and flexible layout manager for Swing and SWT.
    *   `miglayout-core.jar`
    *   `miglayout-swing.jar`
*   **Swing Toast Notifications (v1.0.2)**: A lightweight library for displaying toast notifications in Swing applications.
    *   `swing-toast-notifications-1.0.2.jar`
*   **Gson (v2.8.2)**: A Java library to serialize and deserialize Java objects to/from JSON.
    *   `gson-2.8.2.jar`

## Installation & Setup Instructions

Follow these steps to get a local copy of the project up and running.

1.  **Clone the Repository**
    Start by cloning the PDMS-SE repository to your local machine:
    ```bash
    git clone https://github.com/7mza6/PDMS-SE.git
    cd PDMS-SE
    ```

2.  **Install Java Development Kit (JDK)**
    Ensure you have JDK 8 or a newer version installed. You can download it from Oracle or use OpenJDK. Verify your installation with:
    ```bash
    java -version
    javac -version
    ```

3.  **Install Apache Ant (Optional, for Building)**
    If you plan to build the project using the `build.xml` file, you'll need Apache Ant.
    *   Download Ant from the [official Apache Ant website](https://ant.apache.org/bindownload.cgi).
    *   Follow the installation instructions for your operating system.
    *   Verify your installation:
        ```bash
        ant -version
        ```

4.  **Set Up in an IDE (e.g., IntelliJ IDEA, Eclipse)**
    *   Import the project as an existing Java project.
    *   Ensure that all `.jar` files in the `lib/` and `library/` directories are added to the project's classpath. Most IDEs will do this automatically if you import it as an Ant project or a standard Java project with a correct project structure.

## Usage Examples & API Documentation

This section provides examples of how to interact with key parts of the application.

### Launching the Application Form

The application typically starts by displaying a main form or dashboard. The provided snippet shows how to launch a `PanelForm`:

```java
// Assuming 'Application' class exists in 'raven.application' package
// And 'PanelForm' is a custom Swing JPanel
import raven.application.Application;
import yourpackage.PanelForm; // Adjust package as necessary

public class Main {
    public static void main(String[] args) {
        // Display a custom panel as the main application form
        Application.showForm(new PanelForm());
    }
}
```

### Setting Menu Selection

If the dashboard includes a menu system (e.g., a sidebar navigation), you can programmatically set the selected item:

```java
// Set a specific menu item as selected
// (e.g., to navigate to a particular section upon launch or user action)
Application.setSelectedMenu(0, 0); // Selects the first main menu item (index 0), and its first sub-item (index 0)
// The parameters depend on the internal structure of the menu component.
```

### Package Data Structure Example

The `src/Componentes/Packages/Package.json` file reveals the JSON structure used for package data. This indicates how package information, recipient details, branch data, and customer profiles are organized.

```json
[
  {
    "PackageID": 102,
    "weight": 1.0,
    "ContentDescription": "Glassware",
    "ReciverName": "Jane Smith",
    "ReciverPhone": "0987654321",
    "branch": {
      "BranchNumber": 12312321,
      "BranchName": "khaders",
      "Locationdata": {
        "City": "Nablus",
        "street": "weqe"
      }
    },
    "Location": "Bethlehem",
    "Reciveraddress": "456 Pine Street",
    "fragile": true,
    "customer": {
      "CustomerID": 2,
      "FirstName": "lol",
      "LastName": "121",
      "Gender": "Male",
      "Phone": "0569974804",
      "DateOfBirth": "1212121",
      "Address": "07-11-2024"
    }
  },
  {
    "PackageID": 103,
    "weight": 3.2,
    "ContentDescription": "Electronics",
    "ReciverName": "John Doe",
    "ReciverPhone": "1234567890",
    "branch": {
      "BranchNumber": 98765432,
      "BranchName": "Main Branch",
      "Locationdata": {
        "City": "Hebron",
        "street": "Central St"
      }
    },
    "Location": "Ramallah",
    "Reciveraddress": "789 Oak Avenue",
    "fragile": false,
    "customer": {
      "CustomerID": 3,
      "FirstName": "Alice",
      "LastName": "Smith",
      "Gender": "Female",
      "Phone": "0598765432",
      "DateOfBirth": "1990-05-15",
      "Address": "101 Pine Lane"
    }
  }
]
```
This JSON structure showcases the data model for packages, including nested objects for branch and customer information.

## Configuration Options

*   **Application Theme**: The primary configuration for the UI is the ability to switch between light and dark modes. This functionality is typically exposed through the application's graphical interface.
*   **Build Configuration**: The `build.xml` file, used with Apache Ant, allows developers to customize various aspects of the build process, including:
    *   Compilation settings (source/target Java versions, debug options)
    *   Packaging (JAR file creation, manifest adjustments)
    *   Dependency management (classpath references)
    *   Distribution targets

## Contributing Guidelines

We welcome contributions to the PDMS-SE project! If you're interested in improving this application, please follow these guidelines:

1.  **Fork the Repository**: Start by forking the `7mza6/PDMS-SE` repository to your GitHub account.
2.  **Create a New Branch**: Create a new branch for your feature, bug fix, or enhancement. Use descriptive names like `feature/add-new-dashboard-widget` or `bugfix/resolve-login-issue`.
    ```bash
    git checkout -b feature/your-feature-name
    ```
3.  **Make Your Changes**: Implement your changes, ensuring they adhere to the existing coding style and best practices for Java Swing development.
4.  **Test Thoroughly**: Before submitting, ensure that your changes are well-tested and do not introduce any regressions.
5.  **Commit Your Changes**: Write clear and concise commit messages that explain the purpose of your changes.
    ```bash
    git commit -m "feat: Add new user profile editing functionality"
    ```
6.  **Push to Your Fork**: Push your new branch to your forked repository on GitHub.
    ```bash
    git push origin feature/your-feature-name
    ```
7.  **Open a Pull Request**: Go to the original `7mza6/PDMS-SE` repository and open a Pull Request (PR) from your branch. Provide a detailed description of your changes, including any relevant screenshots or testing notes.

## License Information

The license for this project has **not been specified**. Please contact the repository owner (`7mza6`) for more information regarding licensing and usage terms.

## Acknowledgments

This project is made possible and enhanced by the following excellent open-source libraries:

*   **FlatLaf**: [https://www.formdev.com/flatlaf/](https://www.formdev.com/flatlaf/)
*   **jsvg**: [https://github.com/weisJ/jsvg](https://github.com/weisJ/jsvg)
*   **MigLayout**: [https://www.miglayout.com/](https://www.miglayout.com/)
*   **Swing Toast Notifications**: [https://github.com/shchogolev/swing-toast-notifications](https://github.com/shchogolev/swing-toast-notifications)
*   **Gson**: [https://github.com/google/gson](https://github.com/google/gson)
