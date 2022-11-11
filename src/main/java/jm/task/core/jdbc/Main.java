package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        SessionFactory sessionFactory = Util.getInstance().getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.getTransaction();
        userService.createUsersTable();

        try {
            transaction.begin();
            userService.saveUser("Andrey", "Ivanov", (byte) 25);
            userService.saveUser("Ivan", "Sidorov", (byte) 61);
            userService.saveUser("Masha", "Petrova", (byte) 47);
            userService.saveUser("Vika", "Matveev", (byte) 30);
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();

        } finally {
            userService.removeUserById(1);
            System.out.println(userService.getAllUsers());
            userService.cleanUsersTable();
            session.close();
            sessionFactory.close();
        }
    }
}