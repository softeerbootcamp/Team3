package lightning.gathergo.repository;

import lightning.gathergo.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query(value="select * from user u where u.userId = :userId")
    Optional<User> findUserByUserId(String userId);

    @Modifying
    @Query(value="delete from user u where u.userId = :userId")
    void deleteUserByUserId(String userId);

    @Query(value="select * from user u")
    List<User> findAll();
}
