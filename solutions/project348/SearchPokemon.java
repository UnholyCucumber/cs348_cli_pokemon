package project348;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
import java.math.BigDecimal;

public class SearchPokemon {
    private Scanner input = new Scanner(System.in);
    private Connection connection = null;

    public SearchPokemon(String[] args) {

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
        SearchPokemon menu = new SearchPokemon(args);
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

        mainMenu: while (true) {

            System.out.println("\n-- Actions --");
            System.out.println("Select an option: \n" + "  1) Search Pokemon by [Name]\n"
                    + "  2) Search Pokemon by [ID]\n" + "  3) Search Pokemon have [Type]\n" + "  0) Exit\n ");
            int selection = input.nextInt();
            input.nextLine();

            switch (selection) {
                case 1:
                    System.out.println("Please provide the Pokemon Name: ");
                    String userID = input.nextLine().trim();
                    this.searchPokemonByName(userID);
                    break;
                case 2:
                    System.out.println("Please provide the Pokemon ID: ");
                    String id = input.nextLine().trim();
                    this.searchPokemonByID(id);
                    break;
                case 3:
                    System.out.print("Please provide the Pokemon Type: ");
                    String myclass = input.nextLine();
                    this.searchPokemonByType(myclass);
                    break;
                case 0:
                    System.out.println("Returning...\n");
                    break mainMenu;
                default:
                    System.out.println("Invalid action.");
                    break;
            }
        }
    }

    private void searchPokemonByName(String pkmName) throws SQLException {

        System.out.println("Searching Pokemon with Name: " + pkmName);

        String getPkm = "SELECT * FROM Pokemon WHERE UPPER(name) = UPPER(?)";
        PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
        getPkmStmt.setString(1, pkmName);

        ResultSet sPkmRs = getPkmStmt.executeQuery();

        int count = 0;
        while (sPkmRs.next()) {
            System.out.println("==================================================");
            System.out.println("Name: " + sPkmRs.getString(2));
            System.out.println("ID: " + sPkmRs.getInt(1));
            System.out.println("Types: " + sPkmRs.getString(3) + " " + sPkmRs.getString(4));
            System.out.println("Category: " + sPkmRs.getString(5));
            System.out.println("Capture Rate: " + sPkmRs.getInt(6));
            System.out.println("HP: " + sPkmRs.getInt(7));
            System.out.println("Attack: " + sPkmRs.getInt(8));
            System.out.println(parseAscii(sPkmRs.getString(9)));
            System.out.println("==================================================");
            count++;
        }
        if (count == 0) {
            System.out.println("Pokemon NOT FOUND !!!! Please check yoru input.");

        }
        connection.commit();
        getPkmStmt.close();

    }

    private String parseAscii(String ascii) {
        return ascii.replace('|', '\n');
    }

    private void searchPokemonByID(String ID) throws SQLException {
        System.out.println("Searching Pokemon with ID: " + ID);

        String getPkm = "SELECT * FROM Pokemon WHERE ID = ?";
        PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
        getPkmStmt.setString(1, ID);

        ResultSet sPkmRs = getPkmStmt.executeQuery();
        int count = 0;
        while (sPkmRs.next()) {
            System.out.println("==================================================");
            System.out.println("Name: " + sPkmRs.getString(2));
            System.out.println("ID: " + sPkmRs.getInt(1));
            System.out.println("Types: " + sPkmRs.getString(3) + " " + sPkmRs.getString(4));
            System.out.println("Category: " + sPkmRs.getString(5));
            System.out.println("Capture Rate: " + sPkmRs.getInt(6));
            System.out.println("HP: " + sPkmRs.getInt(7));
            System.out.println("Attack: " + sPkmRs.getInt(8));
            System.out.println(parseAscii(sPkmRs.getString(9)));
            System.out.println("==================================================");
            count++;
        }
        if (count == 0) {
            System.out.println("Pokemon NOT FOUND !!!! Please check yoru input.");

        }

        connection.commit();
        getPkmStmt.close();

    }

    private void searchPokemonByType(String type) throws SQLException {
        System.out.println("Searching Pokemon with Type: " + type);

        String getPkm = "SELECT * FROM Pokemon WHERE UPPER(type1) = UPPER(?) or UPPER(type2) = UPPER(?)";
        PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
        getPkmStmt.setString(1, type);
        getPkmStmt.setString(2, type);

        ResultSet sPkmRs = getPkmStmt.executeQuery();

        int row_count = 0;

        while (sPkmRs.next()) {
            System.out.println("==================================================");
            System.out.println("Name: " + sPkmRs.getString(2));
            System.out.println("ID: " + sPkmRs.getInt(1));
            System.out.println("Types: " + sPkmRs.getString(3) + " " + sPkmRs.getString(4));
            System.out.println("Category: " + sPkmRs.getString(5));
            System.out.println("Capture Rate: " + sPkmRs.getInt(6));
            System.out.println("HP: " + sPkmRs.getInt(7));
            System.out.println("Attack: " + sPkmRs.getInt(8));
            row_count++;
        }

        if (row_count == 0) {
            System.out.println("There is no Pokemon with Type: " + type);
        } else {
            System.out.println("==================================================");
            System.out.println(row_count + " Record(s) selected.");
        }

        connection.commit();
        getPkmStmt.close();
    }
}