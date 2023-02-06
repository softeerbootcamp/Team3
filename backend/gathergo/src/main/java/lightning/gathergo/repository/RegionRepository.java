package lightning.gathergo.repository;

import lightning.gathergo.model.Region;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer> {

    @Query("select * from region")
    public List<Region> findAll();
    @Query("select * from region where id = :id ")
    public Optional<Region> findOneById(@Param("id")Integer id);
    @Query("select * from region where name = :name ")
    public Optional<Region> findOneByName(@Param("name") String name);
    @Modifying
    @Query("INSERT INTO region (name) VALUES (:name)")
    public void save(@Param("name")String name);
    @Modifying
    @Query("UPDATE region SET name = :name WHERE id = :id")
    void update(@Param("id")Integer id, @Param("name")String name);
    @Modifying
    @Query("DELETE FROM region WHERE id = :id")
    void deleteById(@Param("id")Integer id);
    @Modifying
    @Query("DELETE FROM region")
    public void deleteAll();
}
