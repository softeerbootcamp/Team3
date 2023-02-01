package lightning.gathergo.repository;

import lightning.gathergo.model.Region;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer> {

    @Query("select * from region")
    public List<Region> findAll();
    @Query("select * from region where id = :id ")
    public Optional<Region> findOneById(Integer id);
    @Query("select * from region where name = :name ")
    public Optional<Region> findOneByName(String name);
    @Modifying
    @Query("INSERT INTO region (name) VALUES (:name)")
    public void save(String name);
    @Modifying
    @Query("UPDATE Region SET name = :name WHERE id = :id")
    void update(Integer id, String name);
    @Modifying
    @Query("DELETE FROM Region WHERE id = :id")
    void deleteById(Integer id);
    @Modifying
    @Query("DELETE FROM Region")
    public void deleteAll();
}
