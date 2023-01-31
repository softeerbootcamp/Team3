package lightning.gathergo.repository;

import lightning.gathergo.model.Region;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;



//@SpringBootTest
@ActiveProfiles("local")
@Transactional //테스트코드에 붙이면 테스트가 끝나고 나서 작성된 DB데이터를 지운다. 테스트 케이스마다 작동해 다음 테스트에 영향을 미치지 않는다.
class RegionRepositoryTest {

    RegionRepository regionRepository;

    public DataSource dataSource() {
        var dataSource = DataSourceBuilder.create()
                .url("jdbc:mysql://localhost:3306/gathergo?characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true")
                .username("root")
                .password("1234")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
        return dataSource;
    }


    @BeforeEach
    void setUp() {
        regionRepository = new RegionRepository(dataSource());
    }

    @Test
    @DisplayName("테이블 전체 조회")
    void findAll() {
        SoftAssertions softAssertions = new SoftAssertions();
        List<Region> regionList = regionRepository.findAll();

        softAssertions.assertThat(regionList.size()).isEqualTo(2);
        //softAssertions.assertThat(regionList.get(0).getName()).isEqualTo("서울시 강남구");
        softAssertions.assertThat(regionList.get(1).getName()).isEqualTo("대구시 수성구");
        softAssertions.assertAll();
    }

    @Test
    void findOneById() {
        Region region = regionRepository.findOneById(12);
        Assertions.assertThat(region.getName()).isEqualTo("대구시 수성구");
    }

    @Test
    void findOneByName() {
        Region region = regionRepository.findOneByName("대구시 수성구");
        Assertions.assertThat(region.getName()).isEqualTo("대구시 수성구");
    }

    @Test
    void updateRegion() {
        Region region = regionRepository.updateRegion(17,"대구광역시 수성구");
        Assertions.assertThat(region.getName()).isEqualTo("대구광역시 수성구");
    }

    @Test
    @DisplayName("테이블 삽입 테스트")
    void createRegion() {
        List<Region> regionList = regionRepository.findAll();
        int length = regionList.size();
        SoftAssertions softAssertions = new SoftAssertions();
        Region region = regionRepository.createRegion("대구시 수성구");
        softAssertions.assertThat(region.getName()).isEqualTo("대구시 수성구");
        softAssertions.assertThat(regionRepository.findAll().size()).isEqualTo(length+=1);

        softAssertions.assertAll();

    }

    //TODO 특정 로우 삭제 테스트 다시 진행

//    @Test
//    @DisplayName("특정 로우 삭제 테스트")
//    void deleteOneById() {
//        regionRepository.deleteOneById(16);
//        //assertThatNullPointerException().isThrownBy(() -> {  regionRepository.findOneById(12); });
//        //Assertions.assertThat(regionRepository.findOneById(16)).isEqualTo(0);
//    }

    @Test
    @DisplayName("전체 로우 삭제 테스트")
    void deleteAll() {
        regionRepository.deleteAll();
        Assertions.assertThat(regionRepository.findAll().size()).isEqualTo(0);

    }


}
