package lightning.gathergo.mapper;

import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.model.User;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SignupDtoMapperTest {

    private ModelMapper modelMapper = new ModelMapper();;
    private SignupDtoMapper signupDtoMapper = new SignupDtoMapper(modelMapper);
    private SignupDto.SignupInput signupInput;

    @BeforeEach
    public void setUp() {
        signupInput = new SignupDto.SignupInput("asdf", "gildong", "12345678", "gildong@gmail.com");
    }

    @Test
    @DisplayName("SignupInput(회원가입 요청) User 클래스로 변경하는 mapper 테스트")
    public void toUser() {
        // given
        SoftAssertions softly = new SoftAssertions();
        // when - signup request
        User user = signupDtoMapper.toUser(signupInput);
        // then
        softly.assertThat(user).hasFieldOrProperty("userId")
                .hasFieldOrProperty("userName")
                .hasFieldOrProperty("password")
                .hasFieldOrProperty("email");

        RecursiveComparisonConfiguration configuration = RecursiveComparisonConfiguration.builder()
                .withIgnoredFields("introduction")
                .withIgnoredFields("uuid")
                .withIgnoredFields("profilePath")
                .build();

        softly.assertThat(user).usingRecursiveComparison(configuration).isEqualTo(user);
        softly.assertAll();
    }
}
