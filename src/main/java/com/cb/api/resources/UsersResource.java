package com.cb.api.resources;

import com.cb.business.services.UsersService;
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
    public UsersResource(UsersService usersService) {
        this.usersService = usersService;
    }

    @GET()
    public Response getUsers() {
        return Response.ok().entity(usersService.getUsers()).build();
    }
    @GET()
    @Path("/test")
    public Response test() {
        return Response.ok().entity("ishaan").build();
    }
    @GET()
    @Path("/{id}")
    public Response getUser(@PathParam("id") final int id) {
        return Response.ok().entity(usersService.getUser(id)).build();
    }

    @POST()
    public Response addUser(final @NotNull User user) {
        this.usersService.addUser(user);
        return Response.ok().entity(usersService.getUser(user.getId())).build();
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
