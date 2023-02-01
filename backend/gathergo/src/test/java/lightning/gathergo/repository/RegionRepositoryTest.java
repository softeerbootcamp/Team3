package lightning.gathergo.repository;

import lightning.gathergo.model.Region;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


//@SpringBootTest
@ActiveProfiles("local")
@DataJdbcTest //@Transactional 포함됨
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RegionRepositoryTest {

    @Autowired
    RegionRepository regionRepository;


    @Test
    @DisplayName("테이블 전체 조회")
    void findAll() {
        SoftAssertions softAssertions = new SoftAssertions();
        Region region1 = new Region("서울시 강남구");
        regionRepository.save(region1);
        Region region2 = new Region("부산시 해운대구");
        regionRepository.save(region2);


        List<Region> regionList = regionRepository.findAll();

        softAssertions.assertThat(regionList.size()).isEqualTo(3);
        softAssertions.assertThat(regionList.get(0).getName()).isEqualTo("대구광역시 수성구");
        softAssertions.assertThat(regionList.get(1).getName()).isEqualTo("서울시 강남구");
        softAssertions.assertThat(regionList.get(2).getName()).isEqualTo("부산시 해운대구");
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("아이디로 특정 로우 조회")
    void findOneById() {
        Optional<Region> region = regionRepository.findOneById(17);
        Optional<Region> region2 = regionRepository.findOneById(18);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(region.get().getName()).isEqualTo("대구광역시 수성구");
        softAssertions.assertThat(region2.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("이름으로 특정 로우 조회")
    void findOneByName() {
        Optional<Region> region = regionRepository.findOneByName("대구광역시 수성구");
        Optional<Region> region2 = regionRepository.findOneByName("부산광역시 해운대구");

        SoftAssertions softAssertions = new SoftAssertions();
        Assertions.assertThat(region.get().getId()).isEqualTo(17);
        Assertions.assertThat(region2.isEmpty()).isTrue();
        softAssertions.assertAll();;
    }

    @Test
    void updateRegion() {
        Region modified = new Region(17,"대구광역시 북구");
        Region region = regionRepository.save(modified);
        Assertions.assertThat(region.getName()).isEqualTo("대구광역시 북구");
    }

    @Test
    @DisplayName("테이블 삽입 테스트")
    void createRegion() {
        List<Region> regionList = regionRepository.findAll();
        int length = regionList.size();

        SoftAssertions softAssertions = new SoftAssertions();
        Region region = new Region("대구시 수성구");
        Region res = regionRepository.save(region);
        softAssertions.assertThat(res.getName()).isEqualTo("대구시 수성구");
        softAssertions.assertThat(regionRepository.findAll().size()).isEqualTo(length+=1);

        softAssertions.assertAll();

    }

    @Test
    @DisplayName("특정 로우 삭제 테스트")
    void deleteOneById() {
        regionRepository.deleteById(17);
        Optional<Region> region = regionRepository.findOneById(17);
        Assertions.assertThat(region.isEmpty()).isTrue();
    }

    @Test
    @DisplayName("전체 로우 삭제 테스트")
    void deleteAll() {
        regionRepository.deleteAll();
        Assertions.assertThat(regionRepository.findAll().size()).isEqualTo(0);
    }


}
