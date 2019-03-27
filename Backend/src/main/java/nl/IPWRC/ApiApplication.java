package nl.IPWRC;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.hibernate.UnitOfWorkAwareProxyFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.IPWRC.exceptions.ClientExceptionHandler;
import nl.IPWRC.models.CartItem;
import nl.IPWRC.models.Order;
import nl.IPWRC.models.User;
import nl.IPWRC.persistance.CartDao;
import nl.IPWRC.persistance.OrderDao;
import nl.IPWRC.resources.CartResource;
import nl.IPWRC.resources.OrderResource;
import nl.IPWRC.models.Item;
import nl.IPWRC.persistance.ItemDao;
import nl.IPWRC.persistance.UserDao;
import nl.IPWRC.resources.ItemResource;
import nl.IPWRC.resources.UserResource;
import nl.IPWRC.services.AuthService;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.hibernate.SessionFactory;

public class ApiApplication extends Application<APiConfiguration> {

    public static void main(String[] args) throws Exception {
        new ApiApplication().run(args);
    }

    private final HibernateBundle<APiConfiguration> hibernate
            = new HibernateBundle<APiConfiguration>(Item.class, User.class, Order.class, CartItem.class) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(APiConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<APiConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(APiConfiguration config,
                    Environment environment) throws ClassNotFoundException {
        initDaos(hibernate.getSessionFactory());

        registerResources(environment);

        environment.jersey().register(new ClientExceptionHandler());

        setupAuthentication(environment);
    }

    private void initDaos(SessionFactory sessionFactory) {
        CartDao.initInstance(sessionFactory);
        ItemDao.initInstance(sessionFactory);
        OrderDao.initInstance(sessionFactory);
        UserDao.initInstance(sessionFactory);
    }

    private void registerResources(Environment environment) {
        environment.jersey().register(new ItemResource());
        environment.jersey().register(new UserResource());
        environment.jersey().register(new OrderResource());
        environment.jersey().register(new CartResource());
    }

    private void setupAuthentication(Environment environment){
        AuthService authService =
                new UnitOfWorkAwareProxyFactory(hibernate)
                .create(AuthService.class);
        environment.jersey().register(new AuthDynamicFeature(
                new BasicCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(authService)
                        .setAuthorizer(authService)
                        .setRealm("Basic")
                        .buildAuthFilter())
        );
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
    }
}
