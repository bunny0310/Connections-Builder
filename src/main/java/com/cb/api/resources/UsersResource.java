package com.cb.api.resources;

import com.cb.business.services.UsersService;
import com.cb.cache.LRUCache;
import model.User;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/v1/users")
@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
    private UsersService usersService;
    private LRUCache<User> cache= new LRUCache<User>(10);

    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    @GET()
    public Response getUsers() {
        return Response.ok().entity(usersService.getUsers()).build();
    }
    @GET()
    @Path("/{id}")
    public Response getUser(@PathParam("id") final int id) {
        if(cache.get(id) != null) {

            return Response.ok().entity(cache.get(id)).build();
        }
        User ret = usersService.getUser(id);
        if(ret != null) {
            cache.put(id, ret);
        }
        return Response.ok().entity(usersService.getUser(id)).build();
    }

    @POST()
    public Response addUser(final @NotNull User user) {
        User newUser = null;
        try{
            newUser = this.usersService.addUser(user);
        }
        catch(Error error) {
            return Response.status(500).entity(error).build();
        }

        //store in cache
        if(newUser != null) {
            cache.put(newUser.getId(), newUser);
        }
        return Response.ok().entity(newUser).build();
    }

    @PUT()
    @Path("/{id}")
    public Response updateUser(final @NotNull User user, @PathParam("id") final int id){
        this.usersService.updateUser(user, id);
        return Response.ok().entity("{\"message\" : \"user updated!\"}").build();
    }

    @DELETE()
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") final int id){
        this.usersService.deleteUser(id);
        return Response.ok().entity("{\"message\" : \"user deleted!\"}").build();
    }
}
