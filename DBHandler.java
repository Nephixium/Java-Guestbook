package com.javadatabase;

import javax.swing.*;
import java.sql.*;

/** Hanterar anslutningen till databasen och tillhörande metoder som hanterar tabellerna i den.
 *  @author Nephixium
 *  @version 1.0
 */

public class DBHandler {

    private Connection dbConnection;

    public DBHandler() {
        this.dbConnection = connectDb();
    }

    /** Skapar en SQL förfrågan med hjälp av en textsträng och ett PreparedStatement objekt som anger metoder för att välja index i tabellen och dess respektive kolumns värde.
     *  Metoden använder sig av författaren (Author) och dennes kommentar (Comment) för att fylla i värdena.
     *  @param author Författaren till inlägget
     *  @param comment Den skrivna kommentaren
     *  */
    public void postComment(Author author, Comment comment) {

        System.out.println("Adding comment to database!");
        System.out.println(author.getName()+"\n" + author.getEmail() + "\n" + author.getWebsite() + "\n" + comment.getComment());
        String sqlQuery = "INSERT INTO" + " " + "Guestbook" + " " + "VALUES (?, ?, ?, ?)";

        try {

            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, htmlValidate(author.getName()));
            preparedStatement.setString(2, htmlValidate(author.getEmail()));
            preparedStatement.setString(3, htmlValidate(author.getWebsite()));
            preparedStatement.setString(4, htmlValidate(comment.getComment()));
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not parse SQL query");
            System.exit(1);
        }
    }

    /** Formaterar en SQL förfrågan med hjälp av en textsträng i en PreparedStatement som sedan används för att återge resultat från tabellen så länge det finns mer data att hämta.
     * @param textField Textfältet som innehåller kommentarerna från databsen */
    public void getAllEntries(JTextArea textField) {
        String sqlQuery = "SELECT * FROM Guestbook";

        try {
            PreparedStatement preparedStatement = this.dbConnection.prepareStatement(sqlQuery);
            ResultSet allResults = preparedStatement.executeQuery(sqlQuery);

            while(allResults.next()) {
                textField.append("Name: " + allResults.getString("Name") + "\n"); /** Hämtar kolumen "Name" och skriver ut den i textfältet */
                textField.append("Email: " + allResults.getString("Email") + "\n");
                textField.append("Website: " + allResults.getString("Website") + "\n");
                textField.append("Comment: " + allResults.getString("Comment") + "\n");
                textField.append("\n");
            }

            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not retrieve database entries");
            System.exit(1);
        }
    }

    /** Anslut till databasen och returnera en anslutning om det gick att ansluta korrekt, annars null */
    public Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        /** Databasens namn, dess host och det användarnamn samt lösenord som används för att logga in på den */
        String url = "example:mysql://" + "somehost.example" + "/" + "databasename";
        String username = "username";
        String password = "password";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Could not connect to database");
            return null;
        }
    }

    /** Matchar en uppsättning tecken mot en annan sträng och kontrollerar om den innehåller HTML kod.
     * @param unformattedString En oformaterad sträng som omformateras om HTML-kod hittas
     * @return "censored" om strängen innehöll HTML-kod eller den oförändrade strängen om ingen HTML-kod fanns */
    public String htmlValidate(String unformattedString) {

        if(unformattedString.matches("<.*>")) {
            return "censored";
        } else {
            return unformattedString;
        }
    }
}
