package Models;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private final String url = "jdbc:mysql://localhost:3306/lab8";
    private final String username = "root";
    private final String password = "ahad@123";
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;

    public Database() throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ?  AND password = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        resultSet = statement.executeQuery();
        if (resultSet.next()){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean registerUser(String username, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE username = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        resultSet = statement.executeQuery();
        if (resultSet.next()){
            return false;
        }
        else{
            query = "INSERT INTO Users VALUES (?, ?)";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            int result = statement.executeUpdate();
            if (result > 0){
                return true;
            }
            else{
                return false;
            }
        }
    }

    public String[] getAllUsers() throws SQLException {
        String query = "SELECT username FROM Users";
        statement = connection.prepareStatement(query);
        resultSet = statement.executeQuery();
        ArrayList<String> usernames = new ArrayList<>();
        while (resultSet.next()){
            usernames.add(resultSet.getString("username"));
        }
        String[] username = new String[usernames.size()];
        username = usernames.toArray(username);
        return username;
    }

    public ArrayList<String> getInboxMessages(String sender, String receiver) throws SQLException {
        String query = "SELECT * FROM Messages WHERE sender = ? AND receiver = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1,sender);
        statement.setString(2, receiver);
        resultSet = statement.executeQuery();
        ArrayList<String> messages = new ArrayList<>();
        while (resultSet.next()){
            messages.add(resultSet.getString("message"));
        }
        return messages;
    }

    public ArrayList<String> getSendersNames(String username) throws SQLException {
        String query = "SELECT DISTINCT(sender) FROM Messages WHERE receiver = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        resultSet = statement.executeQuery();
        ArrayList<String> senderNames = new ArrayList<>();
        while (resultSet.next()){
            senderNames.add(resultSet.getString("sender"));
        }
        return senderNames;
    }

    public ArrayList<String> getReceiverNames(String username) throws SQLException {
        String query = "SELECT DISTINCT(receiver) FROM Messages WHERE sender = ?";
        statement = connection.prepareStatement(query);
        statement.setString(1, username);
        resultSet = statement.executeQuery();
        ArrayList<String> receiverNames = new ArrayList<>();
        while (resultSet.next()){
            receiverNames.add(resultSet.getString("receiver"));
        }
        return receiverNames;
    }

    public boolean sendMessage(String sender, String receiver, String message) throws SQLException {
        String query = "INSERT INTO Messages VALUES(?, ?, ?)";
        statement = connection.prepareStatement(query);
        statement.setString(1, sender);
        statement.setString(2, receiver);
        statement.setString(3, message);
        int status = statement.executeUpdate();
        if (status >= 1){
            return true;
        }
        return false;
    }

}
