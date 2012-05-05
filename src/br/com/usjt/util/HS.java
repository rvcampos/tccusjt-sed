package br.com.usjt.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import br.com.usjt.jaxrs.JSPAttr;

/**
 * Hibernate Session
 */
public final class HS {

    private HS () {

    }

    private static SessionFactory sessionFactory;

    /**
     * @return openSession
     */
    public static Session getSession() {
        if (HS.sessionFactory == null) {
            synchronized (HS.class) {
                // Sim, depois de um sync a variavel pode nao estar nula nesta
                // logica
                if (HS.sessionFactory == null) {
                    HS.sessionFactory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
                }
            }
        }
        return HS.sessionFactory.openSession();
    }

    /**
     * Procura por valores
     * @return unique
     */
    @SuppressWarnings ("unchecked")
    public static <T, V> T searchByValueUnique(Class<T> bean, Map<String, V> valores) throws HibernateException, Exception {
        Session session = HS.getSession();
        try {
            Criteria crit = session.createCriteria(bean);
            for (Entry<String, V> ln : valores.entrySet()) {
                crit.add(Restrictions.eq(ln.getKey(), ln.getValue()));
            }
            return (T) crit.uniqueResult();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
    
    /**
     * Procura por valores
     * @return unique
     */
    @SuppressWarnings ("unchecked")
    public static <T> T searchById(Class<T> bean, Object id) throws HibernateException, Exception {
        Session session = HS.getSession();
        try {
            Criteria crit = session.createCriteria(bean);
            crit.add(Restrictions.idEq(id));
            return (T) crit.uniqueResult();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }
    
    /**
     * Procura por valores
     */
    @SuppressWarnings ("unchecked")
    public static <T> List<T> searchAll(Class<T> bean) throws HibernateException, Exception {
        Session session = HS.getSession();
        try {
            Criteria crit = session.createCriteria(bean);
            return crit.list();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }


    /**
     * Procura por valores
     */
    @SuppressWarnings ("unchecked")
    public static <T, V> List<T> searchByValue(Class<T> bean, Map<String, V> valores) throws HibernateException, Exception {
        Session session = HS.getSession();
        try {
            Criteria crit = session.createCriteria(bean);
            for (Entry<String, V> ln : valores.entrySet()) {
                crit.add(Restrictions.eq(ln.getKey(), ln.getValue()));
            }
            return crit.list();
        }
        finally {
            if (session.isOpen()) {
                session.close();
            }
        }
    }

}
