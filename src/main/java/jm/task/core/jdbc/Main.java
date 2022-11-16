package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Andrey", "Ivanov", (byte) 25);
        userService.saveUser("Ivan", "Sidorov", (byte) 61);
        userService.saveUser("Masha", "Petrova", (byte) 47);
        userService.saveUser("Vika", "Matveev", (byte) 30);
        userService.removeUserById(1);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
    }
}