package nl.IPWRC.persistance;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class GenericDao<T> extends AbstractDAO<T> {
    
    public GenericDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<T> getAll() {
        CriteriaBuilder cb = currentSession().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(getTypeClass());
        Root<T> rootEntry = cq.from(getTypeClass());
        CriteriaQuery<T> all = cq.select(rootEntry);

        TypedQuery<T> allQuery = currentSession().createQuery(all);
        return allQuery.getResultList();
    }

    public T getById(Integer id) {
        return currentSession().get(getTypeClass(), id);
    }

    public T save(T t) {
        return persist(t);
    }

    public void update(T t) {
        currentSession().saveOrUpdate(t);
    }

    public void delete(T t) {
        currentSession().delete(t);
    }

    public void delete(Integer id) {
        String query = "delete from " + getClassName() + " t where t.id = :id";
        Query<T> queryObj = currentSession().createQuery(query, getTypeClass());
        queryObj.setParameter("id", id);
        queryObj.executeUpdate();
    }

    private String getClassName() {
        return getTypeClass().getSimpleName();
    }

    protected abstract Class<T> getTypeClass();
}
