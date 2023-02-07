package lightning.gathergo.repository;

import lightning.gathergo.model.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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

    @Modifying
    @Query(value="insert into user (uuid, userId, userName, password, email, introduction, profilePath) values(:uuid, :userId, :userName, :password, :email, :introduction,  :profilePath)")
    void save(@Param("uuid") String uuid, @Param("userId") String userId, @Param("userName") String userName, @Param("password") String password, @Param("email") String email, @Param("introduction") String introduction, @Param("profilePath") String profilePath);
}
