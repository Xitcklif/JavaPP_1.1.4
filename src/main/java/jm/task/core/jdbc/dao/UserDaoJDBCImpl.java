package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Util db;
    private Statement st;
    private Connection con;

    public UserDaoJDBCImpl() {
        con = Util.getConnection();
        st = db.getStatement(con);
    }

    public void createUsersTable() {
        try {
            st.executeUpdate("create table if not exists Users " +
                    "(id int not null auto_increment, " +
                    "name VARCHAR(30) not null, " +
                    "lastName VARCHAR(30) not null, " +
                    "age int not null, " +
                    "PRIMARY KEY(id));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try {
            st.executeUpdate("drop table if exists Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            st.executeUpdate("insert into Users (name, lastName, age)" +
                    "values ('" + name + "', '" + lastName + "', '" + age + "');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try {
            st.executeUpdate("delete from Users where id = " + id + ";");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        User user;
        try {
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
        }
        return users;
    }

    public void cleanUsersTable() {
        try {
            st.executeUpdate("delete from Users;");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}