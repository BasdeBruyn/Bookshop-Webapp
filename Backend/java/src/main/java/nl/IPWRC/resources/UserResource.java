package nl.IPWRC.resources;

import com.fasterxml.jackson.annotation.JsonView;
import io.dropwizard.auth.Auth;
import io.dropwizard.hibernate.UnitOfWork;
import nl.IPWRC.services.UserService;
import nl.IPWRC.exceptions.InvalidInputException;
import nl.IPWRC.models.User;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserService userService;

    public UserResource() {
        userService = UserService.getInstance();
    }

    @GET
    @UnitOfWork
    @RolesAllowed("admin")
    @JsonView(User.Public.class)
    public List<User> getAll(){
        return userService.getAll();
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @JsonView(User.Public.class)
    public User getById(@PathParam("id") Integer id, @Auth User authUser){
        return userService.getById(id, authUser);
    }

    @GET
    @Path("/email/{email}")
    @UnitOfWork
    @JsonView(User.Public.class)
    public User getByEmail(@PathParam("email") String  email, @Auth User authUser){
        return userService.getByEmail(email, authUser);
    }

    @POST
    @UnitOfWork
    @JsonView(User.Public.class)
    public User save(@Valid User user) throws InvalidInputException {
        return userService.save(user);
    }

    @PUT
    @UnitOfWork
    @JsonView(User.Public.class)
    public User update(@Valid User user, @Auth User authUser) throws InvalidInputException {
        userService.update(user, authUser);

        return user;
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    public void delete(@PathParam("id") Integer id, @Auth User authUser) {
        userService.delete(id, authUser);
    }

    @GET
    @Path("/authenticate")
    @JsonView(User.Public.class)
    public User authenticate(@Auth User user) {
        return user;
    }
}
