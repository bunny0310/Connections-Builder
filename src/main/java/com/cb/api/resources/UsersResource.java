package com.cb.api.resources;

import com.cb.business.services.UsersService;
import com.cb.cache.LRUCache;
import model.User;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.JsonObject;

@Path("/api/v1/users")
@Consumes(MediaType.APPLICATION_JSON + "; charset=utf-8")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
    private UsersService usersService;
    private LRUCache<Integer, User> cache= new LRUCache<Integer, User>(10);
    private LRUCache<String, User> emailCache= new LRUCache<String, User>(10);

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
        return Response.ok().entity(ret).build();
    }

    @POST()
    @Path("/searchByEmail")
    public Response getUserFromEmail(final @NotNull User user) {
        String email = user.getEmail();
        if(email == "") {
            JsonObject object = new JsonObject();
            object.addProperty("message", "Invalid JSON or missing fields");
            return Response.status(422).entity(object.toString()).build();
        }
        if(emailCache.get(email) != null) {

            return Response.ok().entity(emailCache.get(email)).build();
        }
        User ret = usersService.getUserFromEmail(email);
        if(ret != null) {
            emailCache.put(email, ret);
        }
        return Response.ok().entity(ret).build();
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
        User updatedUser = null;
        updatedUser = this.usersService.updateUser(user, id);
        if(updatedUser != null) {
            cache.put(updatedUser.getId(), updatedUser);
        }
        return Response.ok().entity("{\"message\" : \"user updated!\"}").build();
    }

    @DELETE()
    @Path("/{id}")
    public Response deleteUser(@PathParam("id") final int id){
        this.usersService.deleteUser(id);
        return Response.ok().entity("{\"message\" : \"user deleted!\"}").build();
    }

    @POST()
    @Path("/login")
    public Response login(final @NotNull User user) {
        if(user == null) {
            return Response.status(422).entity("Empty or malformed JSON").build();
        }
        boolean authStatus = this.usersService.verifyUser(user);
        JsonObject object = new JsonObject();
        object.addProperty("authenticated", authStatus);
        object.addProperty("email", authStatus ? user.getEmail() : null);
        int status = authStatus ? 201 : 401;
        return Response.status(status).entity(object.toString()).build();
    }
}
