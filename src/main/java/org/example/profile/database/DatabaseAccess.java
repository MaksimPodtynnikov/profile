package org.example.profile.database;

import org.example.profile.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {

    private final String driverName ="com.mysql.cj.jdbc.Driver";
    private final String connectionString = "jdbc:mysql://127.0.0.1:3306/profiles";
    private final String login = "root";
    private final String password = "root";//пароль свой
    private final Connection connection;
    private static Statement stmt;
    public DatabaseAccess() throws SQLException {
        connection = DriverManager.getConnection(connectionString, login, password);
    }

    /**
     * @return list of nicknames of users
     */
    public List<String> getNames() throws SQLException {
        List<String> names = new ArrayList<>();
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select nickname from users");
        while (rs.next()) {
           names.add(rs.getString("nickname"));
        }
        stmt.close();
        rs.close();
        return names;
    }

    /**
     * @param login nickname
     * @param password user password
     * @return current user
     */
    public User getUser(String login, String password) throws SQLException {
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users where nickname='"+login+"' and password='"+password+"'");
        User user=null;
        if (rs.next()) {
            user =new User(rs.getInt("id"),rs.getString("firstname"),login,rs.getString("lastname"),
                    rs.getString("patronymic"),password,rs.getString("hobbies"),rs.getInt("enters"));
            user.photoStream = rs.getBinaryStream("photo");
        }
        stmt.close();
        rs.close();
        return user;
    }

    /**
     * @param id of user
     * @return current user
     */
    public User getUser(int id) throws SQLException {
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from users where id="+id);
        User user=null;
        if (rs.next()) {
            user =new User(rs.getInt("id"),rs.getString("firstname"),login,rs.getString("lastname"),
                    rs.getString("patronymic"),password,rs.getString("hobbies"),rs.getInt("enters"));
            user.photoStream = rs.getBinaryStream("photo");
        }
        stmt.close();
        rs.close();
        return user;
    }

    /**
     * @param enters count
     * @param id of user
     */
    public void addUserEnters(int enters,int id) throws SQLException {
        stmt = connection.createStatement();
        stmt.execute("UPDATE users SET enters = "+(enters+1)+
                " WHERE id = "+id);
    }

    /**
     * @param id current user
     * @return list of friends of current user
     */
    public List<String> getFriends(int id) throws SQLException {
        List<String> friends=new ArrayList<>();
        stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select * from friends where id="+id);
        while (rs.next()) {
            friends.add("http://localhost:8080/profile?id="+rs.getInt("id_friend"));
        }
        return friends;
    }

    /**
     * @param user current user
     */
    public void editUser(User user) throws SQLException {
        if(user.photoStream!=null) {
            stmt = connection.prepareStatement("UPDATE users SET firstname = '" + user.firstName + "'," +
                    " lastname = '" + user.lastName + "'," +
                    " patronymic = '" + user.patronymic + "'," +
                    " hobbies = '" + user.hobbies + "', " +
                    " photo = ? " +
                    " WHERE id = " + user.id);
            ((PreparedStatement) stmt).setBlob(1, user.photoStream);
        }
        else stmt = connection.prepareStatement("UPDATE users SET firstname = '" + user.firstName + "'," +
                " lastname = '" + user.lastName + "'," +
                " patronymic = '" + user.patronymic + "'," +
                " hobbies = '" + user.hobbies + "' " +
                " WHERE id = " + user.id);
        ((PreparedStatement)stmt).execute();
    }

    /**
     * @param id current user
     * @param idF id of friend
     */
    public void addFriend(int id,int idF) throws SQLException {
        stmt = connection.prepareStatement("INSERT INTO friends (id,id_friend) values (?,?)");
        ((PreparedStatement)stmt).setInt(1, id);
        ((PreparedStatement)stmt).setInt(2, idF);
        ((PreparedStatement)stmt).execute();
        stmt.close();
    }

    /**
     * @param user created user
     * @return new user
     */
    public int addUser(User user) throws SQLException {
        stmt = connection.prepareStatement("INSERT INTO users (nickname,firstname,lastname,patronymic,hobbies,password,photo,enters)" +
                " values('"+user.nickname+"','"+user.firstName+"','"+user.lastName+"','"+user.patronymic
                +"','"+user.hobbies+"','"+user.password+"',?,"+0 +");");
        ((PreparedStatement)stmt).setBlob(1, user.photoStream);
        ((PreparedStatement)stmt).execute();
        stmt.close();
        return getUser(user.nickname, user.password).id;
    }
}
