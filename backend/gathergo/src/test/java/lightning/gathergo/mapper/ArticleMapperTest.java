package lightning.gathergo.mapper;

import lightning.gathergo.dto.GatheringDto;
import lightning.gathergo.model.Article;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;

import static java.time.LocalTime.now;

@ActiveProfiles("local")
@SpringBootTest
class ArticleMapperTest {
    GatheringDto.CreateRequest request;
    private ModelMapper modelMapper;
    @BeforeEach
    public void setUp() {
        modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        String text = "2011-10-02 18:48:05.123";
        Timestamp ts = Timestamp.valueOf(text);
        request = new GatheringDto.CreateRequest("1", "test", 1, "test", 1, 1, "test", ts);
    }

    @Test
    public void toUser() {
        // given
        SoftAssertions softly = new SoftAssertions();
        // when - signup request
        Article article = new Article();
        article =  modelMapper.map(request, Article.class);
        // then

        // id, hostId, title, thumbnail, curr,
        // total, isClosed, content, meetingDay, location,
        // regionId, categoryId, uuid
        softly.assertThat(article).hasFieldOrProperty("hostId").hasFieldOrProperty("title").hasFieldOrProperty("thumbnail")
                .hasFieldOrProperty("curr").hasFieldOrProperty("location");

        //RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder().withIgnoredFields("introduction").withIgnoredFields("uuid").withIgnoredFields("profilePath").build();

        //softly.assertThat(user).usingRecursiveComparison(configuration).isEqualTo(user);
    }
}