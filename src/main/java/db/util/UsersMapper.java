package db.util;

import model.User;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersMapper implements ResultSetMapper<User> {
    @Override
    public User map(int index, ResultSet r, StatementContext ctx) throws SQLException {

        User user = new User(
                r.getInt("id"),
                r.getTimestamp("createdAt"),
                r.getTimestamp("updatedAt"),
                r.getString("firstName"),
                r.getString("lastName"),
                r.getString("email"),
                r.getString("password"),
                r.getString("schoolName"),
                r.getString("companyName")
        );

        return user;
    }
}
