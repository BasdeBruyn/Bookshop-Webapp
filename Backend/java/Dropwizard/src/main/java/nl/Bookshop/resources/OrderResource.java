package nl.Bookshop.resources;

import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.Bookshop.exceptions.InvalidInputException;
import nl.Bookshop.models.Order;
import nl.Bookshop.models.User;
import nl.Bookshop.services.OrderService;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/order")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderService orderService;

    public OrderResource() {
        orderService = OrderService.getInstance();
    }

    @GET
    @UnitOfWork
    @RolesAllowed("admin")
    public List<Order> getAll(){
        return orderService.getAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Order getById(@PathParam("id") Integer id, @Auth User authUser){
        return orderService.getById(id, authUser);
    }

    @GET
    @Path("/latest/{userId}")
    @UnitOfWork
    public Order getLatest(@PathParam("userId") Integer userId, @Auth User authUser) {
        return orderService.getLatestOrder(userId, authUser);
    }

    @POST
    @UnitOfWork
    public Order save(@Valid Order order, @Auth User authUser) throws InvalidInputException {
        return orderService.save(order, authUser);
    }

    @PUT
    @UnitOfWork
    public Order update(@Valid Order order, @Auth User authUser) throws InvalidInputException {
        orderService.update(order, authUser);

        return order;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Integer id, @Auth User authUser) {
        orderService.delete(id, authUser);
    }
}
