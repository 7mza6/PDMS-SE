    package Componentes.Branchs;

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

    public class BranchsCRUDS {

        public static ArrayList<BranchClass> list;
        private static final String FILE_PATH = "./src/Componentes/Branchs\\branchs.json";


        public static ArrayList<BranchClass> Read() {
            if (list == null) {
                list = new ArrayList<>();
            }
            try {
                FileReader read = new FileReader(FILE_PATH);
                Type BranchType = new TypeToken<ArrayList<BranchClass>>() {
                }.getType();
                Gson gson = new Gson();
                list = gson.fromJson(read, BranchType);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, null, ex);
            }
            return list;
        }

        public static void Create(String[] data) {
            ArrayList<BranchClass> BList = Read();
            LocationData Ldata = new LocationData(data[2], data[3]);
            BList.add(new BranchClass(Integer.parseInt(data[0]), data[1], Ldata));
            try {
                FileWriter write = new FileWriter(FILE_PATH);
                Gson gson = new Gson();
                gson.toJson(BList, write);
                write.close();
            } catch (IOException ex) {
                Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        public static ArrayList<BranchClass> Search(String CityName) {
            ArrayList<BranchClass> BList = Read();
            ArrayList<BranchClass> result = new ArrayList<>();
            for (BranchClass branch : BList) {
                if (branch.getLocationdata().getCity().equals(CityName)) {
                    result.add(branch);
                }
            }

            return result;
        }

        public static Boolean Update(int BI, String[] data) {
            ArrayList<BranchClass> BList = Read(); 
        for (int i = 0; i < BList.size(); i++) {
            if (BList.get(i).getBranchNumber() == BI) {
                BList.get(i).setBranchName(data[0]);
                BList.get(i).getLocationdata().setCity(data[1]);
                BList.get(i).getLocationdata().setStreet(data[2]);

                try {
                    // Writing the updated list to the JSON file
                    FileWriter write = new FileWriter(FILE_PATH);
                    Gson gson = new Gson();
                    gson.toJson(BList, write);
                    write.close();
                } catch (IOException ex) {
                    Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, null, ex);
                }

                return true;
            }
        }

        JOptionPane.showMessageDialog(null, "Branch not found",
                "Branch Update", JOptionPane.WARNING_MESSAGE);
        return false;
    }
        
        public static boolean Delete(int branchNumber) {
            ArrayList<BranchClass> BList = Read();
            boolean isDeleted = false;

            // Find and remove the branch with the given branch number
            for (int i = 0; i < BList.size(); i++) {
                if (BList.get(i).getBranchNumber() == branchNumber) {
                    BList.remove(i);
                    isDeleted = true;
                    break;
                }
            }

            // Write the updated list back to the JSON file
            if (isDeleted) {
                try {
                    FileWriter write = new FileWriter(FILE_PATH);
                    Gson gson = new Gson();
                    gson.toJson(BList, write);
                    write.close();
                    JOptionPane.showMessageDialog(null, "Branch deleted successfully!",
                            "Branch Deletion", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    Logger.getLogger(BranchsCRUDS.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Branch not found!",
                        "Branch Deletion", JOptionPane.WARNING_MESSAGE);
            }

            return isDeleted;
        }
    
    }
