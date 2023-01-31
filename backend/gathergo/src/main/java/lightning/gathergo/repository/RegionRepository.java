package lightning.gathergo.repository;

import lightning.gathergo.model.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RegionRepository {

    // 데이터베이스 테이블을 삭제하고, 생성하는 DDL 쿼리문을 실행하고자 할 때는
    // jdbcTemplate.execute() 함수를 이용하고,
    // select 쿼리문은 jdbcTemplate.query(), insert문은 jdbcTemplate.batchUpdate()를 활용합니다

    private static final Logger logger = LoggerFactory.getLogger(RegionRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public RegionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /**
     * select all rows
     * @return
     */
    public List<Region> findAll(){
        String sql = "select * from region";
        logger.debug("query : {}", sql);

        RowMapper<Region> regionMapper = (rs, rowNum) -> {
            Region region = new Region(rs.getInt("id"),rs.getString("Name"));
            return region;
        };

        return jdbcTemplate.query(sql,regionMapper);
    }

    public Region findOneById(Integer id){
        String SQL = "select * from region where id = ?";

        RowMapper<Region> regionMapper = (rs, rowNum) -> {
            Region region = new Region(rs.getInt("id"),rs.getString("Name"));
            return region;
        };

        return jdbcTemplate.queryForObject(SQL,new Object[] { id },regionMapper);
    }


    public Region findOneByName(String name){

        String SQL = "select * from region where name = ?";

        RowMapper<Region> regionMapper = (rs, rowNum) -> {
            Region region = new Region(rs.getInt("id"),rs.getString("Name"));
            return region;
        };

        return jdbcTemplate.queryForObject(SQL,new Object[] { name },regionMapper);
    }

    public Region createRegion(String name) {

        //TODO 같은 지역명이 있으면 그냥 무시하게

        //JdbcTemplate에서 insert는 따로 sql문 작성할 필요없이 SimpleJdbcInsert를 이용하면 바로 가능하다.
        // jdbcTemplate로 SimpleJdbcInsert 객체를 생성해준다음 아래 코드와 같이 Map 자료형을 바로 insert 가능하다
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("region").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", name);
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        Region region = new Region(Integer.parseInt(String.valueOf(key)), name);
        return region;
    }

    public Region updateRegion(Integer id, String name){
        String SQL = "update region set name = ? where id = ?";
        jdbcTemplate.update(SQL, new Object[]{name, id});

        return findOneById(id);

    }

    public void deleteOneById(Integer id){
        String SQL = "delete from region where id = ?";
        jdbcTemplate.update(SQL, new Object[]{id});
    }

    public void deleteAll(){
        jdbcTemplate.update("delete from region");
    }



}
