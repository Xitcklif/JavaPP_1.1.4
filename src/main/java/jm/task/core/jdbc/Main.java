package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class Main {
    private static final UserService userService = new UserServiceImpl();

    public static void main(String[] args) throws SQLException {
        userService.createUsersTable();
        saveUser("Ivan", "Ivanov", (byte) 5);
        saveUser("Petr", "Petrov", (byte) 12);
        saveUser("Maxim", "Maximov", (byte) 45);
        saveUser("Jane", "Janov", (byte) 24);
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u.toString());
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    public static void saveUser(String name, String lastName, byte age) {
        userService.saveUser(name, lastName, age);
        System.out.println("User с именем - " + name + " добавлен в базу данных");
    }
}