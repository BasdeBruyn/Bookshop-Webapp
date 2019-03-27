package nl.IPWRC.persistance;

import nl.IPWRC.models.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserDao extends GenericDao<User> {

    private static UserDao mInstance;

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public User getByEmail(String email) {
        String query = "from User u where u.email = :email";
        Query<User> queryObj = currentSession().createQuery(query, User.class);
        queryObj.setParameter("email", email);
        List<User> users = queryObj.list();
        if (users.size() == 0) {
            return null;
        } else
            return users.get(0);
    }

    public static UserDao getInstance() {
        return mInstance;
    }

    public static void initInstance(SessionFactory sessionFactory){
        mInstance = new UserDao(sessionFactory);
    }

    @Override
    protected Class<User> getTypeClass() {
        return User.class;
    }
}
