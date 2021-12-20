package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sf;
    private Session session;

    public UserDaoHibernateImpl() {
        sf = Util.getSessionFactory();
        session = sf.openSession();
    }

    @Override
    public void createUsersTable() {
        session = sf.getCurrentSession();
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
    }

    @Override
    public void dropUsersTable() {
        session = sf.getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("drop table if exists User;").executeUpdate();
        tr.commit();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        session = sf.getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.save(new User(name, lastName, age));
        tr.commit();
    }

    @Override
    public void removeUserById(long id) {
        session = sf.getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("delete from User where id = " + id + ";")
                .executeUpdate();
        tr.commit();
    }

    @Override
    public List<User> getAllUsers() {
        session = sf.getCurrentSession();
        Transaction tr = session.beginTransaction();
        CriteriaQuery cq = session.getCriteriaBuilder().createQuery(User.class);
        Root<User> root = cq.from(User.class);
        cq.select(root);
        List<User> users = session.createQuery(cq).getResultList();
        tr.commit();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        session = sf.getCurrentSession();
        Transaction tr = session.beginTransaction();
        session.createSQLQuery("delete from User").executeUpdate();
        tr.commit();
    }

    public void closeSession() {
        session.close();
    }
}
