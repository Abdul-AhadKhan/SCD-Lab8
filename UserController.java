package Controllers;

import Models.Database;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    Database database;

    public UserController() throws ClassNotFoundException {
        database = new Database();
    }

    public boolean checkUser(String username, String password) throws SQLException {
        return database.checkUser(username, password);
    }

    public boolean registerUser(String username, String password) throws SQLException {
        return database.registerUser(username, password);
    }

    public String[] getAllUsers() throws SQLException {
        return database.getAllUsers();
    }

    public ArrayList<String> getInboxMessages(String sender, String receiver) throws SQLException {
        return database.getInboxMessages(sender, receiver);
    }

    public ArrayList<String> getSenderNames(String username) throws SQLException {
        return database.getSendersNames(username);
    }

    public ArrayList<String> getReceiverNames(String username) throws SQLException {
        return database.getReceiverNames(username);
    }

    public boolean sendMessage(String sender, String receiver, String message) throws SQLException {
        return database.sendMessage(sender, receiver, message);
    }
}
