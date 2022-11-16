package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import java.util.Properties;

public class Util {
    private Util() {
    }

    public static Util getInstance() {
        if (instance == null) {
            instance = new Util();
        }
        return instance;
    }

    private static Util instance;
    private static SessionFactory sessionFactory;
    private static final Environment environment = null;

    public static org.hibernate.SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration config = new Configuration();
                Properties prop = new Properties();
                prop.put(environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                prop.put(environment.URL, "jdbc:mysql://localhost:3306/mydb");
                prop.put(environment.USER, "root");
                prop.put(environment.PASS, "LWQn2vCC");
                prop.put(environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                prop.put(environment.SHOW_SQL, "true");
                prop.put(environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                prop.put(Environment.HBM2DDL_AUTO, "create");
                prop.put(Environment.AUTOCOMMIT,"off");
                config.setProperties(prop);
                config.addAnnotatedClass(User.class);

                StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                sessionFactory = config.buildSessionFactory(serviceRegistry);

            } catch (Throwable ex) {
                System.err.println("Ошибка создания SessionFactory" + ex);
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }
}