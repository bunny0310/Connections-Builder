package db;

import db.util.UsersMapper;
import model.User;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(UsersMapper.class)
public interface UsersDAO {

    @SqlQuery("SELECT * FROM users WHERE id = :id")
    public User getUser(@Bind("id") final int id);

    @SqlQuery("SELECT * FROM users")
    public List<User> getUsers();

    @SqlUpdate("Insert into users (firstName, lastName, email, password, schoolName, companyName) VALUES (:firstName, :lastName, :email, :password, :schoolName, :companyName)")
    public void addUser(@BindBean User user);

    @SqlUpdate("Update users SET firstName = :firstName, lastName = :lastName, email = :email, password = :password, schoolName = :schoolName, companyName = :companyName WHERE id = :id")
    public void updateUser(@BindBean User user, @Bind("id") final int id);

    @SqlUpdate("Delete FROM users WHERE id = :id")
    public void deleteUser(@Bind("id") final int id);

    @SqlQuery("SELECT * FROM users ORDER BY id DESC LIMIT 1")
    public User getRecentlyAddedUser();

}
