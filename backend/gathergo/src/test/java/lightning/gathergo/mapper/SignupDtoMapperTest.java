package lightning.gathergo.mapper;

import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.model.User;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SignupDtoMapperTest {

    private ModelMapper modelMapper = new ModelMapper();
    private SignupDtoMapper signupDtoMapper = new SignupDtoMapper(modelMapper);
    private SignupDto.SignupInput signupInput;

    @BeforeEach
    public void setUp() {
        signupInput = new SignupDto.SignupInput("asdf", "gildong", "12345678", "gildong@gmail.com");
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Test
    @DisplayName("SignupInput(회원가입 요청) User 클래스로 변경하는 mapper 테스트")
    public void toUser() {
        // given
        SoftAssertions softly = new SoftAssertions();

        // when - signup request
        User mappedUser = signupDtoMapper.toUser(signupInput);
        User expectedUser = new User(null, mappedUser.getUserId(), mappedUser.getUserName(), mappedUser.getPassword(), mappedUser.getEmail(), null, null);

        // then
        softly.assertThat(mappedUser).hasFieldOrProperty("userId")
                .hasFieldOrProperty("userName")
                .hasFieldOrProperty("password")
                .hasFieldOrProperty("email");

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("introduction")
                .withIgnoredFields("id")
                .withIgnoredFields("uuid")
                .withIgnoredFields("profilePath")
                .build();

        softly.assertThat(mappedUser).usingRecursiveComparison(configuration).isEqualTo(expectedUser);
        softly.assertAll();
    }
}
