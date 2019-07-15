package nl.IPWRC.resources;

import io.dropwizard.hibernate.UnitOfWork;
import nl.IPWRC.services.ItemService;
import nl.IPWRC.exceptions.InvalidInputException;
import nl.IPWRC.models.Item;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    private final ItemService itemService;

    public ItemResource() {
        itemService = ItemService.getInstance();
    }

    @GET
    @UnitOfWork
    public List<Item> getAll(){
        return itemService.getAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    public Item getById(@PathParam("id") Integer id){
        return itemService.getById(id);
    }

    @POST
    @UnitOfWork
    @RolesAllowed("admin")
    public Integer save(@Valid Item item) throws InvalidInputException {
        return itemService.save(item).getId();
    }

    @PUT
    @UnitOfWork
    @RolesAllowed("admin")
    public Item update(@Valid Item item) throws InvalidInputException {
        itemService.update(item);

        return item;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @RolesAllowed("admin")
    public void delete(@PathParam("id") Integer id) {
        itemService.delete(itemService.getById(id));
    }
}
