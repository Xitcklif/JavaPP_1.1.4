package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private Session session;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery(
                        "create table if not exists User " +
                                "(id int not null auto_increment, " +
                                "name VARCHAR(30) not null, " +
                                "lastName VARCHAR(30) not null, " +
                                "age int not null, " +
                                "PRIMARY KEY(id));")
                .addEntity(User.class)

                .executeUpdate();
        tr.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("drop table if exists User;").executeUpdate();
        tr.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tr.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("delete from User where id = " + id + ";")
                .executeUpdate();
        tr.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        CriteriaQuery cq = session.getCriteriaBuilder().createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        List<User> users = session.createQuery(cq).getResultList();
        tr.commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session = Util.getSessionFactory().openSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("delete from User").executeUpdate();
        tr.commit();
        session.close();
    }
}
