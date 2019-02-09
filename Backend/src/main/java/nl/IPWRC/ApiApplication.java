package nl.IPWRC;

import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import nl.IPWRC.Services.ItemService;
import nl.IPWRC.Services.CustomerService;
import nl.IPWRC.models.Customer;
import nl.IPWRC.models.Item;
import nl.IPWRC.persistance.GenericDao;
import nl.IPWRC.persistance.ItemDao;
import nl.IPWRC.persistance.CustomerDao;
import nl.IPWRC.resources.ItemResource;
import nl.IPWRC.resources.CustomerResource;

public class ApiApplication extends Application<APiConfiguration> {

    public static void main(String[] args) throws Exception {
        new ApiApplication().run(args);
    }

    private final HibernateBundle<APiConfiguration> hibernate
            = new HibernateBundle<APiConfiguration>(Item.class) {
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
        final ItemDao itemDao = new ItemDao(hibernate.getSessionFactory());
        final CustomerDao customerDao = new CustomerDao(hibernate.getSessionFactory());
        environment.jersey().register(new ItemResource(new ItemService(itemDao)));
        environment.jersey().register(new CustomerResource(new CustomerService(customerDao)));
    }
}
