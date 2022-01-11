package marketplace.persistence.hibernateimpl;

import marketplace.dto.persistence.UserPersistenceDto;
import marketplace.entities.Role;
import marketplace.entities.User;
import marketplace.persistence.UserDao;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.Optional;

import static marketplace.queries.UserQueriesConstants.COUNT_USER_BY_EMAIL;
import static marketplace.queries.UserQueriesConstants.SELECT_USER_BY_EMAIL;

/**
 * Data access object layer to implements working with database.
 */
@Component("UserDaoHibernateImpl")
public class UserDaoHibernateImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Create and save user in database.
     *
     * @param userPersistenceDto which need to save in database.
     */
    @Override
    @Transactional
    public void saveUser(UserPersistenceDto userPersistenceDto) {
        final User user = assembleEntity(userPersistenceDto);
        entityManager.persist(user);
    }

    /**
     * Find user by it's email.
     *
     * @param email of the user.
     * @return user wrapped in <code>Optional</code> class.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        final User user = entityManager.createQuery(SELECT_USER_BY_EMAIL, User.class)
                .setParameter(1, email)
                .getSingleResult();
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    /**
     * Count user by certain email.
     *
     * @param email by which need to find.
     * @return count of id of users.
     */
    @Override
    public long countUserByEmail(String email) {
        final BigInteger singleResult = (BigInteger) entityManager.createNativeQuery(COUNT_USER_BY_EMAIL)
                .setParameter(1, email).getSingleResult();
        return singleResult.longValue();
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