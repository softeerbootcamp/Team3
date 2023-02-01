package lightning.gathergo.repository;

import lightning.gathergo.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.sql.In;
import org.springframework.data.repository.CrudRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface RegionRepository extends CrudRepository<Region, Integer> {

    public List<Region> findAll();
    public Optional<Region> findOneById(Integer id);
    public Optional<Region> findOneByName(String name);
    public Region save(Region region);
    public void deleteById(Integer id);
    public void deleteAll();



}
