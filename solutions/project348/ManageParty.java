package project348;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
import java.util.*; 
import java.util.ArrayList;

public class ManageParty {
    private Scanner input = new Scanner(System.in);
    private Connection connection = null;

    public ManageParty(String[] args) {
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
        ManageParty menu = new ManageParty(args);
        menu.mainMenu(-1);
    }

    public void mainMenu(int ID) throws SQLException {
        this.editPartyByTrainerID(String.valueOf(ID));
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

    private void editPartyByTrainerID(String userID) throws SQLException {
        ArrayList<ArrayList<String>> party = new ArrayList<ArrayList<String>>();

        Boolean done = false;
        while (!done) {
            party = this.getParty(userID);
            System.out.println("Editing Trainer " + userID + "'s current party");
            this.printParty(party, userID);
            System.out.println("\n-- Actions --");
            System.out.println("Select an option: \n" 
                                + "  1) Add pokemon to party\n"
                                + "  2) Remove pokemon to party\n"
                                + "  3) Replace pokemon in party\n"
                                + "  0) Return\n ");
            int selection = input.nextInt();
            input.nextLine();
            System.out.println("selection " + selection);
            switch(selection) {
                case 1:
                    if (party.size() == 6) {
                        System.out.println("Sorry your party is full.");
                        break;
                    }
                    this.addPokemonToParty(party, userID);
                    break;
                case 2:
                    if (party.size() == 0) {
                        System.out.println("Sorry your party is empty.");
                        break;
                    }
                    this.removePokemonInParty(party, userID);
                    break;
                case 3:
                    if (party.size() == 0) {
                        System.out.println("Sorry your party is empty.");
                        break;
                    }
                    this.replacePokemonInParty(party, userID);
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

    private void addPokemonToParty(ArrayList<ArrayList<String>> party, String userID) throws SQLException {
        String getPokemon = "SELECT * FROM OwnedPokemon Join Pokemon on OwnedPokemon.pkmn_id=Pokemon.id WHERE in_team = 0 AND trainer_id = " + userID;
        PreparedStatement getPokemonStmt = connection.prepareStatement(getPokemon);
        ResultSet pokemon = getPokemonStmt.executeQuery();
        System.out.println("Pick a pokemon to add: ");
        ArrayList<String> replacementPokemons = new ArrayList<String>();
        int i = 0;
        while (pokemon.next()) {
            replacementPokemons.add(pokemon.getString("id"));
            System.out.println("  " + String.valueOf(i) + ") id: " + pokemon.getString("id") + ", name: " + pokemon.getString("name") + ", nickname: " + pokemon.getString("nickname"));
            i++;
        }
        if (i == 0) {
            System.out.println("Sorry you have no other pokemon.");
            return;
        }
        int selectionNew = 0;
        while(true) {
            selectionNew = input.nextInt();
            input.nextLine();

            if (selectionNew >= 0 && selectionNew < replacementPokemons.size()) {
                break;
            }
            System.out.println("Invalid Input. Try Again: \n");
        }
        this.addPokemonToParty(replacementPokemons.get(selectionNew));
    }

    private void removePokemonInParty(ArrayList<ArrayList<String>> party, String userID) throws SQLException {
        System.out.println("Pick a pokemon to be removed: ");
        this.printParty(party, userID);
        int selectionCurr = 0;
        while(true) {
            selectionCurr = input.nextInt();
            input.nextLine();

            if (selectionCurr >= 0 && selectionCurr < party.size()) {
                break;
            }
            System.out.println("Invalid Input. Try Again: \n");
        }
        this.removePokemonFromParty(party.get(selectionCurr).get(0));
    }

    private void replacePokemonInParty(ArrayList<ArrayList<String>> party, String userID) throws SQLException {
        String getPokemon = "SELECT * FROM OwnedPokemon Join Pokemon on OwnedPokemon.pkmn_id=Pokemon.id WHERE in_team = 0 AND trainer_id = " + userID;
        PreparedStatement getPokemonStmt = connection.prepareStatement(getPokemon);
        ResultSet pokemon = getPokemonStmt.executeQuery();
        System.out.println("Choose a pokemon to be replaced: ");
        this.printParty(party, userID);
        int selectionCurr = 0;
        while(true) {
            selectionCurr = input.nextInt();
            input.nextLine();

            if (selectionCurr >= 0 && selectionCurr < party.size()) {
                break;
            }
            System.out.println("Invalid Input. Try Again: \n");
        }
        System.out.println("Pick a pokemon as a replacement: ");
        ArrayList<String> replacementPokemons = new ArrayList<String>();
        int i = 0;
        while (pokemon.next()) {
            replacementPokemons.add(pokemon.getString("id"));
            System.out.println("  " + String.valueOf(i) + ") id: " + pokemon.getString("id") + ", name: " + pokemon.getString("name") + ", nickname: " + pokemon.getString("nickname"));
            i++;
        }
        if (i == 0) {
            System.out.println("Sorry you have no other pokemon to use as a replacement.");
            return;
        }
        int selectionNew = 0;
        while(true) {
            selectionNew = input.nextInt();
            input.nextLine();

            if (selectionNew >= 0 && selectionNew < replacementPokemons.size()) {
                break;
            }
            System.out.println("Invalid Input. Try Again: \n");
        }

        this.removePokemonFromParty(party.get(selectionCurr).get(0));
        this.addPokemonToParty(replacementPokemons.get(selectionNew));
    }

    private ArrayList<ArrayList<String>> getParty(String userID) throws SQLException {
        ArrayList<ArrayList<String>> party = new ArrayList<ArrayList<String>>(); 

        String getParty = "SELECT * FROM OwnedPokemon Join Pokemon on OwnedPokemon.pkmn_id=Pokemon.id WHERE trainer_id = ? AND in_team = 1";
        PreparedStatement getPartyStmt = connection.prepareStatement(getParty);
        getPartyStmt.setString(1, userID);
        
        ResultSet partyPokemon = getPartyStmt.executeQuery();
        while (partyPokemon.next()) {
            ArrayList<String> temp = new ArrayList<String>();
            temp.add(partyPokemon.getString("id"));
            temp.add(partyPokemon.getString("name"));
            temp.add(partyPokemon.getString("nickname"));
            party.add(temp);
        }
        getPartyStmt.close();
        return party;
    }

    private void printParty(ArrayList<ArrayList<String>> party, String userID) {
        for (int i = 0; i < party.size(); i++) {
            System.out.println("  " + String.valueOf(i) + ") id: " + party.get(i).get(0) + ", name: " + party.get(i).get(1) +", nickname: " + party.get(i).get(2));
        }
    }

    private void removePokemonFromParty (String pokemonID) throws SQLException {
        String updatePokemon = "UPDATE OwnedPokemon Set in_team = 0 WHERE id = " + pokemonID;
        PreparedStatement updatePokemonStmt = connection.prepareStatement(updatePokemon);
        updatePokemonStmt.executeUpdate();
        connection.commit();
        updatePokemonStmt.close();
    }

    private void addPokemonToParty (String pokemonID) throws SQLException {
        String updatePokemon = "UPDATE OwnedPokemon Set in_team = 1 WHERE id = " + pokemonID;
        PreparedStatement updatePokemonStmt = connection.prepareStatement(updatePokemon);
        updatePokemonStmt.executeUpdate();
        connection.commit();
        updatePokemonStmt.close();
    }
}