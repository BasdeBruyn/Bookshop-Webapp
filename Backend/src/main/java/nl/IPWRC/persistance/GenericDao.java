package nl.IPWRC.persistance;

import com.google.common.collect.ImmutableList;
import io.dropwizard.hibernate.AbstractDAO;
import nl.IPWRC.models.Customer;
import nl.IPWRC.models.Item;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class GenericDao<T> extends AbstractDAO<T> {

    private static final ImmutableList<Class> entities = ImmutableList.of(
            Item.class,
            Customer.class
    );
    
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

    protected abstract Class<T> getTypeClass();

    public static ImmutableList<Class> getEntities() { return entities; }
}
