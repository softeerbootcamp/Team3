package lightning.gathergo.repository;

import lightning.gathergo.model.Category;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category,Integer> {
    @Query("select * from category")
    public List<Category> findAll();
    @Query("select * from category where id = :id ")
    public Optional<Category> findOneById(@Param("id")Integer id);
    @Query("select * from category where name = :name ")
    public Optional<Category> findOneByName(@Param("name") String name);
    @Modifying
    @Query("INSERT INTO category (name) VALUES (:name)")
    public void save(@Param("name")String name);
    @Modifying
    @Query("UPDATE category SET name = :name WHERE id = :id")
    void update(@Param("id")Integer id, @Param("name")String name);
    @Modifying
    @Query("DELETE FROM category WHERE id = :id")
    void deleteById(@Param("id")Integer id);
    @Modifying
    @Query("DELETE FROM category")
    public void deleteAll();
}
