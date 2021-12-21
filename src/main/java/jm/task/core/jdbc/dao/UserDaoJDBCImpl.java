package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = Util.getConnection();
            st = con.createStatement();
            st.executeUpdate("create table if not exists Users " +
                    "(id int not null auto_increment, " +
                    "name VARCHAR(30) not null, " +
                    "lastName VARCHAR(30) not null, " +
                    "age int not null, " +
                    "PRIMARY KEY(id));");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropUsersTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = Util.getConnection();
            st = con.createStatement();;
            st.executeUpdate("drop table if exists Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection con = null;
        try {
            con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "insert into Users (name, lastName, age) values (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, lastName);
            ps.setInt(3, age);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        Connection con = null;
        try {
            con = Util.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "delete from Users where id = ?");
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        Connection con = null;
        Statement st = null;
        List<User> users = new ArrayList<>();
        User user;
        try {
            con = Util.getConnection();
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from Users;");
            while (rs.next()) {
                user = new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                user.setId(rs.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    public void cleanUsersTable() {
        Connection con = null;
        Statement st = null;
        try {
            con = Util.getConnection();
            st = con.createStatement();;
            st.executeUpdate("truncate table Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}