package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Statement st;
    private Connection con;

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try {
            con = Util.getConnection();
            st = Util.getStatement(con);
            st.executeUpdate("create table if not exists Users " +
                    "(id int not null auto_increment, " +
                    "name VARCHAR(30) not null, " +
                    "lastName VARCHAR(30) not null, " +
                    "age int not null, " +
                    "PRIMARY KEY(id));");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            con = Util.getConnection();
            st = Util.getStatement(con);
            st.executeUpdate("drop table if exists Users;");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "insert into Users (name, lastName, age) values (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "delete from Users where id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user;
        try {
            con = Util.getConnection();
            st = Util.getStatement(con);
            ResultSet rs = st.executeQuery("select * from Users;");
            while (rs.next()) {
                user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            con = Util.getConnection();
            st = Util.getStatement(con);
            st.executeUpdate("truncate table Users;");
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}