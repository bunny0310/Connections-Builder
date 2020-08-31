package com.cb;

import com.cb.api.resources.UsersResource;
import com.cb.business.services.UsersService;
import db.UsersDAO;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class CBUsersService extends Application<DatabaseConfiguration> {
    @Override
    public void initialize(Bootstrap<DatabaseConfiguration> bootstrap) {

    }

    @Override
    public void run(DatabaseConfiguration configuration, Environment environment) throws Exception {

        final DBIFactory dbiFactory = new DBIFactory();
        final DBI dbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "mysql");
        final UsersDAO usersDAO = dbi.onDemand(UsersDAO.class);
        UsersService usersService = new UsersService(usersDAO);
        UsersResource usersResource = new UsersResource(usersService);
        environment.jersey().register(usersResource);
    }

    public static void main(String ...args) throws  Exception{
        new CBUsersService().run(args);
    }
}
