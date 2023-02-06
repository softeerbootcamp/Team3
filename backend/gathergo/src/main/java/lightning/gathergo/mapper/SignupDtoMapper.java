package lightning.gathergo.mapper;

import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignupDtoMapper {
    private ModelMapper modelMapper;

    public SignupDtoMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public User toUser(SignupDto.SignupInput dto) {
        return modelMapper.map(dto, User.class);
    }
}
