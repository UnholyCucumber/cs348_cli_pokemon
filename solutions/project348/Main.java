package project348;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.*; 


public class Main {
    private Scanner input = new Scanner(System.in);
    private Connection connection = null;

    public static int ID = -1;

    public Main(String[] args) {
        // loading the DBMS driver
        try {
            Class.forName("com.ibm.db2.jcc.DB2Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Missing DBMS driver.");
            e.printStackTrace();
        }

        try {
            // connecting to the a database
            connection = DriverManager.getConnection("jdbc:db2:CS348");
            System.out.println("Database connection open.\n");

            // setting auto commit to false
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("DBMS connection failed.");
            e.printStackTrace();
        }        
    }

    public static void main(String[] args) throws Exception {
        Main menu = new Main(args);
        menu.mainMenu();
        menu.exit();
    }

    public void exit() {
        try {
            // close database connection
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }        
    }

    public void mainMenu() throws SQLException {
        
        int isLoggedIn = 0;
        mainMenu: while (true) {

            switch (isLoggedIn) {
                case 1:
                    System.out.println("\n-- Actions --");
                    System.out.println("Select an feature: \n" + "\t1) Search Pokemon\n"
                            + "\t2) Create Trainer\n" + "\t3) EditPokemon\n" + "\t4) EncounterPokemon\n" + "\t5) Manage Party\n"+ "\t0) Exit\n ");
                    int selection = input.nextInt();
                    input.nextLine();

                    switch (selection) {
                        case 1:
                            SearchPokemon searchPokemon = new SearchPokemon(null);                        
                            searchPokemon.mainMenu();
                            searchPokemon.exit();
                            break;
                        case 2:                     
                            CreateTrainer createTrainer = new CreateTrainer(null);
                            ID = createTrainer.mainMenu();
                            createTrainer.exit();
                            break;
                        case 3:                         
                            EditPokemon editPokemon = new EditPokemon(null);
                            editPokemon.mainMenu(ID);
                            break;
                        case 4:                       
                            EncounterPokemon encounterPokemon = new EncounterPokemon(null);
                            encounterPokemon.mainMenu(ID);
                            encounterPokemon.exit();
                            break;
                        case 5:
                            ManageParty manageParty = new ManageParty(null);
                            manageParty.mainMenu(ID);
                            break;
                        case 0:
                            System.out.println("Thank you for playing!\n");
                            break mainMenu;
                        default:
                            System.out.println("Invalid action.");
                            break;
                    }
                    break;
                default:
                    System.out.println("\n-- Actions --");
                    System.out.println("Select an feature: \n" + "\t1) Log In\n"
                            + "\t2) Create Trainer\n" + "\t3) Search Pokemon\n" + "\t0) Exit\n ");
                    selection = input.nextInt();
                    input.nextLine();

                    switch (selection) {
                        case 1:
                            HashSet<Integer> validIds = getAllTrainers();

                            System.out.println("Log in as:");
                            int tmpID= input.nextInt();
                            input.nextLine();
                            if (validIds.contains(tmpID)){
                                ID = tmpID;
                                isLoggedIn = 1;
                                System.out.println("Logged in!");
                            } else {
                                System.out.println("Please input a valid user id!");
                            }
                            break;
                        case 2:
                            CreateTrainer createTrainer = new CreateTrainer(null);
                            ID = createTrainer.mainMenu();
                            createTrainer.exit();
                            isLoggedIn = 1;
                            break;
                        case 3:
                            SearchPokemon searchPokemon = new SearchPokemon(null);  
                            searchPokemon.mainMenu();
                            searchPokemon.exit();
                            break;
                        case 0:
                            System.out.println("Thank you for playing!\n");
                            break mainMenu;                            

                    }
                break;
            }
        }
    }

    private HashSet<Integer> getAllTrainers() throws SQLException{
        String getUser = "SELECT id, name, gender FROM Trainer";
        PreparedStatement getUserStmt = connection.prepareStatement(getUser);

        ResultSet trainers = getUserStmt.executeQuery();

        HashSet<Integer> validIds = new HashSet<Integer>();
        System.out.println("\n-- Existing Trainers --");
        while (trainers.next()) {
            System.out.println("==================================================");
            System.out.println("ID: " + trainers.getInt(1));
            validIds.add(trainers.getInt(1));
            System.out.println("Name: " + trainers.getString(2));
            System.out.println("Gender: " + trainers.getString(3));
            System.out.println("==================================================");
        }                            
        System.out.println("\n-----------------------");        

        connection.commit();
        getUserStmt.close();

        return validIds;
    }

}