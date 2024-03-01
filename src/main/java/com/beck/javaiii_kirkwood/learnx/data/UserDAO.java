package com.beck.javaiii_kirkwood.learnx.data;

import com.beck.javaiii_kirkwood.learnx.Models.User;
import com.beck.javaiii_kirkwood.shared.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.beck.javaiii_kirkwood.learnx.data.Database.getConnection;
import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

public class UserDAO {
  public static void main(String[] args) throws SQLException {
    getConnection();
    try {
      System.out.println(getUserByPrimaryKey("fizzle@aol.com"));
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    ArrayList Users = (ArrayList) getAllUsers();
    Users.forEach(System.out::println);
  }

  public static User getUserByPrimaryKey(String __email) throws SQLException {
    User result = null;
    try (Connection connection = getConnection()) {
      try (CallableStatement statement = connection.prepareCall("{CALL sp_get_user(?)}")) {
        statement.setString(1, __email);

        try (ResultSet resultSet = statement.executeQuery()) {
          if (resultSet.next()) {

            int ID = resultSet.getInt("id");
            String first_name = resultSet.getString("first_name");
            String last_name = resultSet.getString("last_name");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            char[] password = resultSet.getString("password").toCharArray();
            String status = resultSet.getString("status");
            String privileges = resultSet.getString("privileges");
            Instant created_at = resultSet.getTimestamp("created_at").toInstant();
            Instant last_logged_in = resultSet.getTimestamp("last_logged_in").toInstant();
            Instant updated_at = resultSet.getTimestamp("updated_at").toInstant();
            String language = resultSet.getString("language");
            result = new User(ID, first_name, last_name, email, phone, password, status, privileges, created_at, last_logged_in, updated_at, language);
          }
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getSQLState());
    }
    return result;
  }

  public static List<User> getAllUsers() throws SQLException {
    List<User> results = new ArrayList<>();
    try (Connection connection = getConnection()) {
      try (CallableStatement statement = connection.prepareCall("{CALL sp_get_all_users()}")) {

        try (ResultSet resultSet = statement.executeQuery()) {
          while (resultSet.next()) {

            int ID = resultSet.getInt("id");
            String first_name = resultSet.getString("first_name");
            if (resultSet.wasNull()) {
              first_name = "";
            }
            String last_name = resultSet.getString("last_name");
            if (resultSet.wasNull()) {
              last_name = "";
            }
            String email = resultSet.getString("email");
            if (resultSet.wasNull()) {
              email = "";
            }
            String phone = resultSet.getString("phone");
            if (resultSet.wasNull()) {
              phone = "";
            }
            char[] password = resultSet.getString("password").toCharArray();
            //if(resultSet.wasNull()){password="";}
            String status = resultSet.getString("status");
            if (resultSet.wasNull()) {
              status = "";
            }
            String privileges = resultSet.getString("privileges");
            if (resultSet.wasNull()) {
              privileges = "";
            }
            Instant created_at = resultSet.getTimestamp("created_at").toInstant();
            if (resultSet.wasNull()) {
              created_at = new Date().toInstant();
            }
            Instant last_logged_in = resultSet.getTimestamp("last_logged_in").toInstant();
            if (resultSet.wasNull()) {
              last_logged_in = new Date().toInstant();
              ;
            }
            Instant updated_at = resultSet.getTimestamp("updated_at").toInstant();
            if (resultSet.wasNull()) {
              updated_at = new Date().toInstant();
              ;
            }
            String language = resultSet.getString("language");
            if (resultSet.wasNull()) {
              language = "";
            }
            User result = new User(ID, first_name, last_name, email, phone, password, status, privileges, created_at, last_logged_in, updated_at, language);
            results.add(result);
          }
        }
      }
    } catch (SQLException e) {
      System.out.println(e.getSQLState());
    }
    return results;
  }
  public static void update(User user) {
    try(Connection connection = getConnection();
        CallableStatement statement = connection.prepareCall("{CALL sp_update_user(?,?,?,?,?,?,?,?,?)}")
    ) {
      statement.setInt(1, user.getID());
      statement.setString(2, user.getFirst_name());
      statement.setString(3, user.getLast_name());
      statement.setString(4, user.getEmail());
      statement.setString(5, user.getPhone());
      statement.setString(6, user.getLanguage());
      statement.setString(7, user.getStatus());
      statement.setString(8, user.getPrivileges());
      statement.setTimestamp(9, Timestamp.from(user.getLast_logged_in()));
      statement.executeUpdate();
      // To do: Return the rows affected and display an error if the user not updated.
    } catch (SQLException e) {
      System.out.println("Likely bad SQL query");
      System.out.println(e.getMessage());
    }
  }

  public static List<String> addUSer(User user) {
    List<String> results = new ArrayList<>();
    try (Connection connection = getConnection();
         CallableStatement statement = connection.prepareCall("{CALL sp_add_user(?, ?)}")
    ) {
      statement.setString(1, user.getEmail());
      String hashPassword = BCrypt.hashpw(String.valueOf(user.getPassword()), BCrypt.gensalt(12));
      statement.setString(2, hashPassword);
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected == 1) {
        try (CallableStatement statement2 = connection.prepareCall("{CALL sp_get_2fa_code(?)}")
        ) {
          statement2.setString(1, user.getEmail());
          try (ResultSet resultSet = statement2.executeQuery()) {
            if (resultSet.next()) {
              String code = resultSet.getString("code");
              String created_at = resultSet.getTimestamp("created_at").toString();
              results.add(code);
              results.add(created_at);
            }
          }
        }
      }
    } catch (SQLException e) {
      System.out.println("Likely bad SQL query");
      System.out.println(e.getMessage());
    }
    return results;
  }
  public static boolean passwordReset(String email, HttpServletRequest req) throws SQLException {
    User userFromDatabase = getUserByPrimaryKey(email);
    if(userFromDatabase != null) {
      try(Connection connection = getConnection()) {
        String uuid = String.valueOf(UUID.randomUUID());
        // To do: check database if uuid already exists
        try(CallableStatement statement = connection.prepareCall("{CALL sp_add_password_reset(?, ?)}")) {
          statement.setString(1, email);
          statement.setString(2, uuid);
          statement.executeUpdate();
        }
        return EmailService.sendPasswordResetEmail(email, uuid, req);
      } catch (SQLException e) {
        System.out.println("Likely bad SQL query");
        System.out.println(e.getMessage());
      }
    }
    return false;
  }
}



