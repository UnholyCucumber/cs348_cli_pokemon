package project348;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
import java.util.ArrayList;

public class EditPokemon {
    private Scanner input = new Scanner(System.in);
    private Connection connection = null;

    public EditPokemon(String[] args) {
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
        EditPokemon menu = new EditPokemon(args);
        menu.mainMenu(-1);
    }

    public void mainMenu(int ID) throws SQLException {
        Boolean done = false;
        mainMenu: while (!done) {
            System.out.println("\n-- Actions --");
            System.out.println("Select an option: \n" 
                                + "  1) [ID] of Pokemon to edit\n"
                                + "  0) Exit\n ");
            int selection = input.nextInt();
            input.nextLine();
            System.out.println("selection " + selection);
            switch(selection) {
                case 1:
                    System.out.println("Pokemons you own:");
                    int count = printOwnedPokemons(ID);
                    if (count == 0) break;
                    System.out.println("Please provide the Pokemon ID");
                    
                    String pokemonID = input.nextLine().trim();
                    this.editPokemonByID(pokemonID);
                    break;
                case 0:
                    done = true;
                    System.out.println("Returning...\n");
                    break;
                default:
                    System.out.println("Invalid action.");
                    break;
            }
        }
    }

    private int printOwnedPokemons(int ID) throws SQLException{
        // SELECT trainer_id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id
        String getPkm = "SELECT OwnedPokemon.id, nickname, Pokemon.name as name, m1, ability FROM OwnedPokemon JOIN Pokemon ON OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id = ?";
        PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
        getPkmStmt.setString(1, String.valueOf(ID));

        ResultSet sPkmRs = getPkmStmt.executeQuery();

        int count = 0;
        while (sPkmRs.next()) {
            System.out.println("==================================================");
            System.out.println("Name: " + sPkmRs.getString(3));
            System.out.println("ID: " + sPkmRs.getInt(1));
            count++;
        }

        if (count == 0) {
            System.out.println("You do not have any pokemons");

        } else{
            System.out.println("==================================================");
            
        }
        connection.commit();
        getPkmStmt.close();
        return count;
    }

    public void exit() {
        try {
            connection.commit();
            connection.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void editPokemonByID(String ID) throws SQLException {
        System.out.println("Editing Pokemon with Name: " + ID);

        String getPkm = "SELECT * FROM OwnedPokemon WHERE ID = ?";
        PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
        getPkmStmt.setString(1, ID);
        
        ResultSet pkmToEdit = getPkmStmt.executeQuery();

        while (pkmToEdit.next()) {
            Boolean done = false;
            while (!done) {
                System.out.println("\n-- Actions --");
                System.out.println("Select an option: \n" 
                                    + "  1) Edit Pokemon moves\n"
                                    + "  2) Edit Pokemon ability\n" 
                                    + "  3) Edit Pokemon nickname\n" 
                                    + "  4) Release Pokemon from storage\n"
                                    + "  0) Return\n ");
                int selection = input.nextInt();
                input.nextLine();
    
                switch(selection) {
                    case 1:
                        this.editPokemonMove(pkmToEdit);
                        break;
                    case 2:
                        this.editPokemonAbility(pkmToEdit);
                        break;
                    case 3:
                        this.editPokemonNickname(pkmToEdit);
                        break;
                    case 4:
                        System.out.println("Are you sure?\n"
                                        + "   1) Yes\n"
                                        + "   0) No\n");
                        int choice = input.nextInt();
                        input.nextLine();
                        switch(choice) {
                            case 1:
                                this.deletePokemon(pkmToEdit);
                                done = true;
                                pkmToEdit.close();
                                return;
                            case 0:
                                break;
                        }
                        
                        break;
                    case 0:
                        done = true;
                        break;
                }
                getPkmStmt = connection.prepareStatement(getPkm);
                getPkmStmt.setString(1, ID);
                pkmToEdit = getPkmStmt.executeQuery();
                pkmToEdit.next();
            }
        }
        pkmToEdit.close();
    }

    private void editPokemonMove(ResultSet pkm) throws SQLException {
        System.out.println("Select a move to replace: \n"
                            + "  1) " + pkm.getString("m1")
                            + "  2) " + pkm.getString("m2")
                            + "  3) " + pkm.getString("m3")
                            + "  4) " + pkm.getString("m4"));
        int selectionCurr = 0;
        while(true) {
            selectionCurr = input.nextInt();
            input.nextLine();

            if (selectionCurr >= 1 && selectionCurr <= 4) {
                    break;
                }
            System.out.println("Invalid Input. Try Again: \n");
        }
        String getLearnableMoves = "SELECT * FROM LearnableMove WHERE can_be_learned_by_pkmn_id = " + pkm.getString("pkmn_id");
        PreparedStatement getLearnableMovesStmt = connection.prepareStatement(getLearnableMoves);
        ResultSet learnableMovesResultSet = getLearnableMovesStmt.executeQuery();
        ArrayList<String> movesArr = new ArrayList<String>();
        System.out.println("Select the replacement move: ");
        int i = 0;
        while (learnableMovesResultSet.next()) {
            System.out.println("  " + String.valueOf(i) + ") " + learnableMovesResultSet.getString(1));
            movesArr.add(learnableMovesResultSet.getString(1));
            i++;
        }

        int selectionNew = 0;

        while(true) {
            selectionNew = input.nextInt();
            input.nextLine();

            if (selectionNew >= 0 && selectionNew < i) {
                break;
            }
            System.out.println("Invalid Input. Try Again: \n");
        }

        String updateMove = "UPDATE OwnedPokemon Set m" +
                            String.valueOf(selectionCurr) +
                            " = '" + movesArr.get(selectionNew) +
                            "' WHERE id = " + pkm.getString(1);
        PreparedStatement updateMoveStmt = connection.prepareStatement(updateMove);
        updateMoveStmt.executeUpdate();
        connection.commit();
        updateMoveStmt.close();
    }

    private void editPokemonAbility(ResultSet pkm) throws SQLException {
        System.out.println("Choose an ability to replace " + pkm.getString("ability"));
        String getLearnableAbility = "SELECT * FROM LearnableAbility WHERE can_be_learned_by_pkmn_id = " + pkm.getString("pkmn_id");
        PreparedStatement getLearnableAbilityStmt = connection.prepareStatement(getLearnableAbility);
        ResultSet learnableAbilityResultSet = getLearnableAbilityStmt.executeQuery();
        ArrayList<String> abilitiesArr = new ArrayList<String>();
        int i = 0;
        System.out.println("Select the replacement ability: ");
        while (learnableAbilityResultSet.next()) {
            System.out.println("  " + String.valueOf(i) + ") " + learnableAbilityResultSet.getString(1));
            abilitiesArr.add(learnableAbilityResultSet.getString(1));
            i++;
        }
        int selection = 0;
        while (true) {
            selection = input.nextInt();
            input.nextLine();

            if (selection >= 0 && selection < i) {
                break;
            }    
            System.out.println("Invalid Input. Try Again: \n");               
        }
        String updateAbility = "UPDATE OwnedPokemon Set ability = '" +
                                abilitiesArr.get(selection) +
                                "' WHERE id = " + pkm.getString(1);
        PreparedStatement updateAbilityStmt = connection.prepareStatement(updateAbility);
        updateAbilityStmt.executeUpdate();  
        connection.commit();
        updateAbilityStmt.close();
    }

    private void editPokemonNickname(ResultSet pkm) throws SQLException {
        System.out.println("Enter a new nickname for " + pkm.getString("nickname") + ":");
        String newNickname = input.nextLine();
        String updateNickname = "UPDATE OwnedPokemon Set nickname = '" +
                                newNickname + "' WHERE id = " + pkm.getString(1);
        PreparedStatement updateNicknameStmt = connection.prepareStatement(updateNickname);
        updateNicknameStmt.executeUpdate(); 
        connection.commit();
        updateNicknameStmt.close();
    }
    
    private void deletePokemon(ResultSet pkm) throws SQLException {
        String removePokemon = "DELETE FROM OwnedPokemon WHERE id = " + pkm.getString("id");
        PreparedStatement stmt = connection.prepareStatement(removePokemon);
        stmt.executeUpdate();
        stmt.close();
    }
}
