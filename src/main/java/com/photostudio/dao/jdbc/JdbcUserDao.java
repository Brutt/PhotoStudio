package com.photostudio.dao.jdbc;

import com.photostudio.dao.UserDao;
import com.photostudio.dao.jdbc.mapper.UserRowMapper;
import com.photostudio.entity.user.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    private static final String GET_ALL_USERS = "SELECT Users.id, email, phoneNumber, firstName," +
            " lastName, genderName, roleName, passwordHash, salt, country, city, zip," +
            " address FROM photostudio.Users " +
            " INNER JOIN UserRole ON Users.userRoleId=UserRole.id" +
            " LEFT JOIN UserGender ON Users.genderId=UserGender.id;";

    private static final String ADD_NEW_USER = "INSERT INTO photostudio.Users (email,phoneNumber," +
            "firstName,lastName,genderId,userRoleId,passwordHash,salt, country,city,zip,address) " +
            "VALUES (?,?,?,?,(SELECT id FROM UserGender WHERE genderName=?)," +
            "( SELECT id FROM UserRole WHERE roleName ='user'),?,?,?,?,?,?,?)";

    private static final String GET_BY_ID = "SELECT u.id id, u.email email,u.phoneNumber phoneNumber," +
            " u.firstName firstName, u.lastName lastName, ur.roleName roleName, ug.genderName genderName, " +
            "u.passwordHash passwordHash, u.salt salt, u.country country," +
            " u.city city, u.zip zip, u.address address  FROM  Users u \n" +
            "INNER JOIN UserRole ur ON u.userRoleId=ur.id \n" +
            "LEFT JOIN UserGender ug ON u.genderId=ug.id\n" +
            "WHERE u.id=?;";

    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<User> getAllUsers() {
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {
            List<User> users = new ArrayList<>();

            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException("Can't show all users", e);
        }
    }

    @Override
    public void add(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER)) {
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setInt(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getFirstName());
            preparedStatement.setString(4, user.getLastName());

            preparedStatement.setObject(5, user.getGender());
            preparedStatement.setString(6, user.getPasswordHash());
            preparedStatement.setString(7, user.getSalt());
            preparedStatement.setString(8, user.getCountry());
            preparedStatement.setString(9, user.getCity());
            preparedStatement.setInt(10, user.getZip());
            preparedStatement.setString(11, user.getAddress());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Can't add new user to DB ", e);
        }
    }

    @Override
    public User getUserById(long id) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    throw new RuntimeException("User with id= " + id + "is missing");
                }
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                if (resultSet.next()) {
                    throw new RuntimeException("More than one users found");
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Can't found user where id=" + id, e);
        }
    }


    @Override
    public void edit(User user) {
    }

    @Override
    public void delete(long id) {

    }

}
