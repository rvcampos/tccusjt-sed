package br.com.usjt.ead;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

public class EntityDAO<T>
{

    private static Logger LOG = Logger.getLogger(EntityDAO.class);

    public T searchID(Number id, Session session, Class<T> classe) {
        T bean = null;
        try {
            bean = (T) session.get(classe, id);
        }
        catch (Exception e) {
            LOG.error("Erro ao recuperar entidade [" + classe + "] com id [" + id + "]", e);
        }

        return bean;
    }

    public boolean insert(Session session, T bean) {
        Transaction tx = session.beginTransaction();
        try {
            session.save(bean);
            tx.commit();
            LOG.info("Registro " + bean + " inserido com sucesso");
            return true;
        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Erro ao inserir entidade [" + bean + "]", e);
        }

        return false;
    }

    public boolean delete(Session session, Number id, Class<T> classe) {
        Transaction tx = session.beginTransaction();
        try {
            session.delete(searchID(id, session, classe));
            tx.commit();
            return true;
        }
        catch (Exception e) {
            tx.rollback();
            LOG.error("Erro ao deletar entidade [" + classe + "] com id [" + id + "]", e);
        }

        return false;
    }

    public List<T> searchByValue(Session session, Class<T> classe, Map<String, Object> valores) {
        Criteria c = session.createCriteria(classe);
        for (Map.Entry<String, Object> entry : valores.entrySet()) {
            c.add(Restrictions.eq(entry.getKey(), entry.getValue()));
        }
        return c.list();
    }
    
    public List<T> searchAll(Session session, Class<T> classe)
    {
        return session.createCriteria(classe).list();
    }
}
