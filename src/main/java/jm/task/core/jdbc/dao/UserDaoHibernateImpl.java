package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.*;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private SessionFactory sf;

    public UserDaoHibernateImpl() {
        sf = Util.getSingleCon().getSessionFactory();
    }

    @Override
    public void createUsersTable() {
        Transaction tr = null;
        Session session = null;
        try {
            session = sf.openSession();
            tr = session.beginTransaction();
            session.createSQLQuery(
                            "create table if not exists User " +
                                    "(id int not null auto_increment, " +
                                    "name VARCHAR(30) not null, " +
                                    "lastName VARCHAR(30) not null, " +
                                    "age int not null, " +
                                    "PRIMARY KEY(id))")
                    .addEntity(User.class)
                    .executeUpdate();
            tr.commit();
        } catch (HibernateException e) {
            try {
                tr.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Transaction tr = null;
        Session session = null;
        try {
            session = sf.openSession();
            tr = session.beginTransaction();
            session.createSQLQuery("drop table if exists User").executeUpdate();
            tr.commit();
        } catch (HibernateException e) {
            try {
                tr.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tr = null;
        Session session = null;
        try {
            session = sf.openSession();
            tr = session.beginTransaction();
            session.save(new User(name, lastName, age));
            tr.commit();
        } catch (HibernateException e) {
            try {
                tr.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tr = null;
        Session session = null;
        try {
            session = sf.openSession();
            tr = session.beginTransaction();
            Query query = session.createSQLQuery("delete from User where id = :id");
            query.setParameter("id", id).executeUpdate();
            tr.commit();
        } catch (HibernateException e) {
            try {
                tr.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction tr = null;
        Session session = null;
        List<User> users = null;
        try {
            session = sf.openSession();
            tr = session.beginTransaction();
            CriteriaQuery cq = session.getCriteriaBuilder().createQuery(User.class);
            Root<User> root = cq.from(User.class);
            cq.select(root);
            users = session.createQuery(cq).getResultList();
            tr.commit();
        } catch (HibernateException e) {
            try {
                tr.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Transaction tr = null;
        Session session = null;
        try {
            session = sf.openSession();
            tr = session.beginTransaction();
            session.createSQLQuery("truncate table User").executeUpdate();
            tr.commit();
        } catch (HibernateException e) {
            try {
                tr.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                session.close();
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        }
    }
}
