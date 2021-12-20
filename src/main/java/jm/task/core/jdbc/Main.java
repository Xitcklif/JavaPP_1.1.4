package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        System.out.println("Создание БД:");
        userService.createUsersTable();

        System.out.println("\nЗаполнение БД:");
        userService.saveUser("Ivan", "Ivanov", (byte) 5);
        userService.saveUser("Petr", "Petrov", (byte) 12);
        userService.saveUser("Maxim", "Maximov", (byte) 45);
        userService.saveUser("Jane", "Janov", (byte) 24);

        System.out.println("\nСодержимое БД:");
        List<User> users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u.toString());
        }

        System.out.println("\nСодержимое БД после удаления записи:");
        userService.removeUserById(3);
        users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u.toString());
        }

        System.out.println("\nСодержимое БД после очистки таблицы:");
        userService.cleanUsersTable();
        users = userService.getAllUsers();
        for (User u : users) {
            System.out.println(u.toString());
        }

        System.out.println("\nУдаление БД:");
        userService.dropUsersTable();

        /*System.out.println("\nЗакрытие соединения:");
        userService.closeSession();*/
    }
}