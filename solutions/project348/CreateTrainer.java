package project348;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;
import java.math.BigDecimal;

public class CreateTrainer {
    private Scanner input = new Scanner(System.in);
    private Connection connection = null;

    public CreateTrainer(String[] args) {

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
        CreateTrainer menu = new CreateTrainer(args);
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

    public int mainMenu() throws SQLException {
        
        int userID = -1;

        mainMenu: while (true) {

            System.out.println(
                "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMMmmMMMNMMMMMMMMMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMNNd/.++:-+++/ymdNMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMmd+../+/. ...-.`+hNMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMNds+s++/+/so:/s+-dMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMNdd+..-:-  .:dsmMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMNy:ydhs++syshdh+dMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMdsh:/sy-.oo:.o+mMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMMMMs`/yo/--..dMMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMMMMNdy+..odmNMMMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMNhs-:hNdso:y++mMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMmooss:+Mdooooo- +-+/hMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMNdhoohdooydddNhs+.  +dMMMMMMMMMM\n"
                +"MMMMMMMMMMMMh/:Nhsh::hhyyyym/ -:`::mMMMMMMMMMM\n"
                +"MMMMMMMMMMMmdosNmo:-ooyhhyym/`+. d-/dMMMMMMMMM\n"
                +"MMMMMMMMMMh:   :d:. -yNhhhdm/+.  d- mMMMMMMMMM\n"
                +"MMMMMMMMMMm-   .sMmsoo//. so+-   d- :NMMMMMMMM\n"
                +"MMMMMMMMMMMNhhyoshhmd+. .:hs.    d/.-NMMMMMMMM\n"
                +"MMMMMMMMMMMMMMh `:.+NNdodNN+   .-mooNMMMMMMMMM\n"
                +"MMMMMMMMMMMMNy-::. +mhdhhhdm: /-.-hMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMd  -+ +mhmdyyym/ +-  dMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMd  -+ +myhmmhym/ +-  dMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMm::++ +mydMMdym/ +/::mMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMmo/``/ms+yMMdym/ ``+oNMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMNdddy+yMMMMdhddddNMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMmyys+/yMMMMdsoyhsmMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMNdmmmdhhmMMdshdhhhdmMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMm+//oyydMMMMMdo//-+NMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"
                +"MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM\n"
            );
            System.out.print("\nOAK: Hello there! Welcome to the world of Pokémon!"); input.nextLine();
            System.out.print("\nOAK: My name is Oak! People call me the Pokémon Prof!"); input.nextLine();
            System.out.print("\nOAK: This world is inhabited by creatures called Pokémon!"); input.nextLine();
            System.out.print("\nOAK: For some people, Pokémon are pets. Others use them for fights."); input.nextLine();
            System.out.print("\nOAK: Myself...I study Pokémon as a profession."); input.nextLine();
            System.out.print("\nOAK: But first, tell me a little bit about yourself"); input.nextLine();
            System.out.print("\nOAK: Are you a boy or a girl: \n" 
                                + "  1) Boy\n"
                                + "  2) Girl\n" 
                                + "  3) Other\n" 
                                + "  4) I choose not to identify\n");
            int genderSelection = 1;
            while (true) {
                genderSelection = input.nextInt();
                input.nextLine();
                if (genderSelection >= 1 && genderSelection <= 4) break;
                System.out.println("OAK: ...Sorry, could you repeat that?");
            }
            String gender = "";
            if (genderSelection == 1) gender = "M";
            else if (genderSelection == 2) gender = "F";
            else if (genderSelection == 3) gender = "O";
            else if (genderSelection == 4) gender = "*";


            String name = "";
            System.out.println("OAK: ...And what is your name?"); 
            while (true) {
                name = input.nextLine().trim();
                if (!name.isEmpty() && name.length() <= 12) break;
                System.out.println("OAK: ...Sorry, could you repeat that? (Name must be non empty and max 12 characters)");
            }
            System.out.print("\nOAK: Right...so your name is " + name + "."); input.nextLine();
            System.out.print("\nOAK: This is my grandson. He's been your rival since you were both babies."); input.nextLine();
            System.out.println("                                   ___\n"
                                + "                               ,-''   `.\n"
                                + "                             ,'  _   e )`-._\n"
                                + "                            /  ,' `-._<.===-'\n"
                                + "                           /  /\n"
                                + "                          /  ;\n"
                                + "              _          /   ;\n"
                                + " (`._    _.-'' ''--..__,'    |\n"
                                + " <_  `-''                     \\\n"
                                + "  <`-                          :\n"
                                + "   (__   <__.                  ;\n"
                                + "     `-.   '-.__.      _.'    /\n"
                                + "        \\      `-.__,-'    _,'\n"
                                + "         `._    ,    /__,-'\n"
                                + "            ''._\\__,'< <____\n"
                                + "                 | |  `----.`.\n"
                                + "                 | |        \\ `.\n"
                                + "                 ; |___      \\-``\n"
                                + "                 \\   --<\n"
                                + "                  `.`.<\n"
                                + "                    `-'\n"
                                );
            System.out.print("\nOAK: Erm...what was his name now?\n"); input.nextLine();
            System.out.print("\nOAK: That's right! I remember now! His name is Mr. Goose!"); input.nextLine();
            
            String getPkm = "SELECT * FROM Pokemon WHERE ID IN (1, 4, 7, 25)";
            PreparedStatement getPkmStmt = connection.prepareStatement(getPkm);
            
            System.out.print("\nOAK: Now " + name + ", it's time to choose your very own partner pokemon!"); input.nextLine();
            ResultSet sPkmRs = getPkmStmt.executeQuery();
            while (sPkmRs.next()) {
                System.out.println("\nOAK: " + sPkmRs.getString(2) + "! The " + sPkmRs.getString(5) +  "! It's a " + sPkmRs.getString(3) + " type!");
                System.out.println(parseAscii(sPkmRs.getString(9)));
                input.nextLine();
            }
            connection.commit();
            getPkmStmt.close();
            
            System.out.print("\nOAK: Now, which one will you choose?: \n" 
                                + "  1) Bulbasaur\n"
                                + "  2) Charmander\n" 
                                + "  3) Squirtle\n" 
                                + "  4) Pikachu\n");
            int starterSelection = 1;
            while (true) {
                starterSelection = input.nextInt();
                input.nextLine();
                if (starterSelection >= 1 && starterSelection <= 4) break;
                System.out.println("OAK: ...Sorry, could you repeat that?");
            }

            System.out.print("\nOAK: Excellent choice!"); input.nextLine();
            System.out.print("\nOAK: " + name + "!"); input.nextLine();
            System.out.print("\nOAK: Your very own Pokémon adventure is about to unfold!"); input.nextLine();
            System.out.print("\nOAK: A world of dreams and adventures with Pokémon awaits! Let's go!"); input.nextLine();

            String insertTrainer = "INSERT INTO Trainer (name, gender) VALUES (?, ?)";
            PreparedStatement insertTrainerStmt = connection.prepareStatement(insertTrainer, Statement.RETURN_GENERATED_KEYS);
            insertTrainerStmt.setString(1, name);
            insertTrainerStmt.setString(2, gender);
            int affectedRows = insertTrainerStmt.executeUpdate();
        
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            int user = -1;
            try (ResultSet generatedKeys = insertTrainerStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user = generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

            connection.commit();
            insertTrainerStmt.close();

            int pkmn_id = 0;
            if (starterSelection == 1) pkmn_id = 1;
            else if (starterSelection == 2) pkmn_id = 4;
            else if (starterSelection == 3) pkmn_id = 7;
            else if (starterSelection == 4) pkmn_id = 25;

            gender = "f";

            if (Math.random() < 0.5){
                gender = "m";
            }
            
            // generate up to four valid moves
            String getLearnableMoves = "SELECT * FROM LearnableMove WHERE can_be_learned_by_pkmn_id = " + String.valueOf(pkmn_id);
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
            String getLearnableAbility = "SELECT * FROM LearnableAbility WHERE can_be_learned_by_pkmn_id = " + String.valueOf(pkmn_id);
            PreparedStatement getLearnableAbilityStmt = connection.prepareStatement(getLearnableAbility);
            ResultSet learnableAbilityResultSet = getLearnableAbilityStmt.executeQuery();
            
            String ability = "";
            if (learnableAbilityResultSet.next()){
                ability = learnableAbilityResultSet.getString(1);
            }
            
            connection.commit();
            getLearnableAbilityStmt.close();
            
            // add captured Pokemon to storage
            String insertPkmn = "INSERT INTO ownedpokemon (trainer_id, pkmn_id, gender, in_team, m1, m2, m3, m4, ability) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPkmnStmt = connection.prepareStatement(insertPkmn, Statement.RETURN_GENERATED_KEYS);
            insertPkmnStmt.setInt(1, user);
            insertPkmnStmt.setInt(2, pkmn_id);
            insertPkmnStmt.setString(3, gender);
            insertPkmnStmt.setBoolean(4, true);
            insertPkmnStmt.setString(5, move1);
            insertPkmnStmt.setString(6, move2);
            insertPkmnStmt.setString(7, move3);
            insertPkmnStmt.setString(8, move4);
            insertPkmnStmt.setString(9, ability);
            
            affectedRows = insertPkmnStmt.executeUpdate();
        
            if (affectedRows == 0) {
                throw new SQLException("Creating OwnedPokemon failed, no rows affected.");
            }

            try (ResultSet generatedKeys = insertPkmnStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                }
                else {
                    throw new SQLException("Creating OwnedPokemon failed, no ID obtained.");
                }
            }

            connection.commit();
            insertPkmnStmt.close();            

            System.out.print("\n(Your trainer with ID: " + String.valueOf(user) + " has now been created. Press enter to exit.)"); input.nextLine();
            userID = user;
            break mainMenu;
    
    }
    return userID;
    }
    
    private String parseAscii(String ascii) {
        return ascii.replace('|', '\n');
    }

}

