package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    Transaction transaction = null;
    private final SessionFactory sessionFactory = Util.getInstance().getSessionFactory();

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
                            "name VARCHAR(20), lastName VARCHAR(20), age TINYINT)")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");

        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS user")
                    .executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");

        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            System.out.println("Пользователь " + name + " " + lastName + " " + age + " Добавлен в базу данных");

        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return sessionFactory.openSession().createQuery("SELECT u FROM User u", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createSQLQuery("truncate table user");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}