package marketplace.persistence.jdbcimpl;

import marketplace.dto.persistence.UserPersistenceDto;
import marketplace.entities.Role;
import marketplace.entities.User;
import marketplace.persistence.UserDao;
import marketplace.persistence.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static marketplace.constants.ApplicationConstants.USER;
import static marketplace.queries.UserQueriesConstants.*;

/**
 * Data access object layer to implements working with database.
 */
@Component("UserDaoJdbcImpl")
public class UserDaoJdbcImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataSource dataSource;

    /**
     * Create and save user in database.
     *
     * @param userPersistenceDto which need to save in database.
     * @throws SQLException will be thrown if occur.
     */
    @Override
    public void saveUser(UserPersistenceDto userPersistenceDto) throws SQLException {
        final User user = assembleEntity(userPersistenceDto);
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatementUser
                     = connection.prepareStatement(INSERT_USER);
             final PreparedStatement preparedStatementRoleUser
                     = connection.prepareStatement(INSERT_ROLE_USER)) {
            preparedStatementUser.setString(1, user.getFirstName());
            preparedStatementUser.setString(2, user.getLastName());
            preparedStatementUser.setString(3, user.getEmail());
            preparedStatementUser.setString(4, user.getPassword());
            preparedStatementUser.setString(5, String.valueOf(user.getGender()));
            preparedStatementUser.execute();
            preparedStatementRoleUser.setString(1, user.getEmail());
            preparedStatementRoleUser.setString(2, USER);
            preparedStatementRoleUser.execute();
        }
    }

    /**
     * Find user by it's email.
     *
     * @param email of the user.
     * @return user wrapped in <code>Optional</code> class.
     * @throws SQLException will be thrown if occur.
     */
    @Override
    public Optional<User> findByEmail(String email) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(SELECT_FROM_USER_EMAIL)) {
            preparedStatement.setString(1, email);
            final ResultSet resultSet = preparedStatement.executeQuery();
            final User user = userMapper.mapUser(resultSet);
            if (user.getId() == 0) {
                return Optional.empty();
            }
            return Optional.of(user);
        }
    }

    /**
     * Count user by certain email.
     *
     * @param email by which need to find.
     * @return count of id of users.
     */
    @Override
    public long countUserByEmail(String email) throws SQLException {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection
                     .prepareStatement(COUNT_USER_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            final ResultSet resultSet = preparedStatement.executeQuery();
            long count = 0;
            while (resultSet.next()) {
                count = resultSet.getLong(1);
            }
            return count;
        }
    }

    private User assembleEntity(UserPersistenceDto userPersistenceDto) {
        final User user = new User();
        user.setId(userPersistenceDto.getId());
        user.setFirstName(userPersistenceDto.getFirstName());
        user.setLastName(userPersistenceDto.getLastName());
        user.setEmail(userPersistenceDto.getEmail());
        user.setPassword(userPersistenceDto.getPassword());
        user.setGender(userPersistenceDto.getGender());
        user.getAuthorities().add(Role.USER);
        return user;
    }

}