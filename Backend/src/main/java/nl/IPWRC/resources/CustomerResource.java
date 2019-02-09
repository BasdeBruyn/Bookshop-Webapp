package nl.IPWRC.resources;

import io.dropwizard.hibernate.UnitOfWork;
import nl.IPWRC.Services.CustomerService;
import nl.IPWRC.models.Customer;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GET
    @UnitOfWork
    public List<Customer> getAll(){
        return customerService.getAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Customer getById(@PathParam("id") Integer id){
        return customerService.getById(id);
    }

    @POST
    @UnitOfWork
    public Integer save(@Valid Customer customer){
        return customerService.save(customer).getId();
    }

    @PUT
    @UnitOfWork
    public Customer update(@Valid Customer customer) {
        customerService.update(customer);

        return customer;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Integer id) {
        customerService.delete(customerService.getById(id));
    }
}
