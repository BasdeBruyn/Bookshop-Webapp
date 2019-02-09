package nl.IPWRC.resources;

import io.dropwizard.hibernate.UnitOfWork;
import nl.IPWRC.Services.ItemService;
import nl.IPWRC.models.Item;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/item")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    private final ItemService itemService;

    public ItemResource(ItemService itemService) {
        this.itemService = itemService;
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
    public Integer save(@Valid Item item){
        return itemService.save(item).getId();
    }

    @PUT
    @UnitOfWork
    public Item update(@Valid Item item) {
        itemService.update(item);

        return item;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Integer id) {
        itemService.delete(itemService.getById(id));
    }
}
