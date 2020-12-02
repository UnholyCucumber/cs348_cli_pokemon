package project348;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;



public class EncounterPokemon {
    private Scanner input = new Scanner(System.in);
    private Connection connection = null;

    private String parseAscii(String ascii){
        return ascii.replace('|', '\n');
    }

    public EncounterPokemon(String[] args) {

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
        EncounterPokemon menu = new EncounterPokemon(args);
        menu.mainMenu(-1);
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

    public void mainMenu(int trainer_id) throws SQLException {
            /*System.out.println("please enter your trainer ID (the ID will be automatically passed in final production):");
            int trainer_id = input.nextInt();
            input.nextLine();*/
            
        mainMenu: while (true) {


            
            System.out.println("Encounter a Pokemon (Y/N)?");
            
            //
            // let users select map (potentially)
            //
            
            String selection = input.nextLine();            
            String selection_l = selection.toLowerCase();
            switch (selection_l) {
                case "y":
                
                    // find number of Pokemons in the Pokemons table and generate a Pokemon with a random valid ID
                    int min = 1;
                    String getMaxIDQ = "SELECT COUNT(ID) FROM POKEMON";
                    PreparedStatement getMaxID = connection.prepareStatement(getMaxIDQ);
                    ResultSet getMaxIDr = getMaxID.executeQuery();
                    getMaxIDr.next();
                    int max = getMaxIDr.getInt(1);


                    try {
                        System.out.println("\n      Rustle... \n");
                        Thread.sleep(1000);
                        System.out.println("Rustle... \n");
                        Thread.sleep(1000);
                        System.out.println("    Rustle... \n");
                        Thread.sleep(1000);
                    }
                        catch(InterruptedException ex)
                    {
                        Thread.currentThread().interrupt();
                    }
                    int rand_id = (int)(Math.random() * ((max-min)+1))+min;
                    this.EncounterPokemonByID(rand_id, trainer_id);
                    break;
                case "n":
                    System.out.println("Returning...\n");
                    break mainMenu;
                
                default:
                    System.out.println("Invalid action. Please select y or n");
                    break;
            }
            // connection.commit();
            // connection.close();
        }
    }



    private void EncounterPokemonByID(int rand_id, int trainer_id) throws SQLException {


        String getPkm = "SELECT * FROM Pokemon WHERE UPPER(id) = UPPER(?)";
        PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
        getPkmStmt.setInt(1, rand_id);

        ResultSet sPkmRs = getPkmStmt.executeQuery();

        sPkmRs.next();
        
        System.out.println(parseAscii(sPkmRs.getString(9)));
        
        System.out.println("\n\nA wild " + sPkmRs.getString(2) + " appeared!");
        System.out.println("ID: " + sPkmRs.getInt(1));
        System.out.println("Types: " + sPkmRs.getString(3) + " " + sPkmRs.getString(4));
        System.out.println("Category: " + sPkmRs.getString(5));
        System.out.println("Capture Rate: " + sPkmRs.getInt(6));
        System.out.println("HP: " + sPkmRs.getInt(7));
        System.out.println("Attack: " + sPkmRs.getInt(8) + "\n"); 
        
        
        System.out.print("\nWhat will you do?: \n" 
                            + "  1) Throw Poké Ball\n"
                            + "  2) Throw Great Ball\n" 
                            + "  3) Throw Ultra Ball\n" 
                            + "  4) Throw Master Ball\n"
                            + "  5) Run away\n");

        int selection = input.nextInt();            
        
        String nickname = "";

        switch(selection){
            case 1:
            case 2:
            case 3:
            case 4:
                double p = Math.random();
                if (selection == 1) p *= 255;
                else if (selection == 2) p *= 200;
                else if (selection == 3) p *= 100;
                else if (selection == 4) p *= 0;
                if (p <= sPkmRs.getInt(6)){
                    System.out.println("\nYou have captured " + sPkmRs.getString(2) + " !\n");
                    // get nickname
                    System.out.println("Would you like to name " + sPkmRs.getString(2) + " (Y/N)?");
                    input.nextLine();
                    while (true) {
                        String name = input.nextLine().toLowerCase();

                        if (name.equals("y")) {
                            System.out.println("\nPlease enter a nickname (Name must be non empty and max 12 characters).\n");
                            while (true) {
                                nickname = input.nextLine().trim();
                                if (!nickname.isEmpty() && nickname.length() <= 12) break;
                                System.out.println("(Name must be non empty and max 12 characters)");
                            }
                            System.out.println("\nThat's a great name!\n");
                            break;
                        } else if (name.equals("n")) {
                            System.out.println("\nThat's ok.\n");
                            break;
                        } else {
                            System.out.println("\nInvalid action. Please select y or n\n");
                        }
                    }
                    
                    // get trainer ID
                    // int trainer_id = 1;
                    
                    // generate gender
                    String gender = "f";

                    if (Math.random() < 0.5){
                        gender = "m";
                    }
                    
                    // set Pokemon in storage
                    boolean in_team = false;
                    
                    // generate up to four valid moves
                    String getLearnableMoves = "SELECT * FROM LearnableMove WHERE can_be_learned_by_pkmn_id = " + sPkmRs.getInt(1);
                    PreparedStatement getLearnableMovesStmt = connection.prepareStatement(getLearnableMoves);
                    ResultSet learnableMovesResultSet = getLearnableMovesStmt.executeQuery();
                    
                    String move1 = "";
                    String move2 = "";
                    String move3 = "";
                    String move4 = "";
                    if (learnableMovesResultSet.next()){
                        move1 = learnableMovesResultSet.getString(1);
                    }
                    if (learnableMovesResultSet.next()){
                        move2 = learnableMovesResultSet.getString(1);
                    }
                    if (learnableMovesResultSet.next()){
                        move3 = learnableMovesResultSet.getString(1);
                    }
                    if (learnableMovesResultSet.next()){
                        move4 = learnableMovesResultSet.getString(1);
                    }
                    
                    connection.commit();
                    getLearnableMovesStmt.close();
                    
                    // generate an ability
                    String getLearnableAbility = "SELECT * FROM LearnableAbility WHERE can_be_learned_by_pkmn_id = " + sPkmRs.getInt(1);
                    PreparedStatement getLearnableAbilityStmt = connection.prepareStatement(getLearnableAbility);
                    ResultSet learnableAbilityResultSet = getLearnableAbilityStmt.executeQuery();
                    
                    String ability = "";
                    if (learnableAbilityResultSet.next()){
                        ability = learnableAbilityResultSet.getString(1);
                    }
                    
                    connection.commit();
                    getLearnableAbilityStmt.close();
                    
                    // add captured Pokemon to storage
                    String insertPkm = "INSERT INTO ownedpokemon (trainer_id, pkmn_id, nickname, gender, in_team, m1, m2, m3, m4, ability) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertPkmStmt = connection.prepareStatement(insertPkm);
                    insertPkmStmt.setInt(1, trainer_id);
                    insertPkmStmt.setInt(2, sPkmRs.getInt(1));
                    insertPkmStmt.setString(3, nickname);
                    insertPkmStmt.setString(4, gender);
                    insertPkmStmt.setBoolean(5, in_team);
                    insertPkmStmt.setString(6, move1);
                    insertPkmStmt.setString(7, move2);
                    insertPkmStmt.setString(8, move3);
                    insertPkmStmt.setString(9, move4);
                    insertPkmStmt.setString(10, ability);
                    int insertResult = insertPkmStmt.executeUpdate();
                    connection.commit();
                    insertPkmStmt.close();
                    
                    /* testing insert
                    String testPkm = "SELECT * FROM ownedpokemon WHERE UPPER(id) = UPPER(?)";
                    PreparedStatement testPkmStmt = connection.prepareStatement(testPkm);
                    testPkmStmt.setInt(1, sPkmRs.getInt(1));

                    ResultSet testPkmR = getPkmStmt.executeQuery();
                    
                    testPkmR.next();
                    System.out.println(testPkmR.getString(2));
                    */
                    
                    if (nickname != ""){
                        System.out.println(nickname + " has been sent to storage.\n");
                    } else {
                        System.out.println(sPkmRs.getString(2) + " has been sent to storage.\n");
                    }
                } else {
                    System.out.println("\nUh oh, " + sPkmRs.getString(2) + " got away!\n");
                    input.nextLine();
                }
                break;
            case 5:
                System.out.println("Safely fled!\n"); input.nextLine();
                break;
            default:
                System.out.println("Oak's words echoed... There's a time and place for everything, but not now."); input.nextLine();
                break;
        }
        
        connection.commit();
        getPkmStmt.close();
        return;
    }

    
}