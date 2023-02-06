package lightning.gathergo.mapper;

import lightning.gathergo.dto.SignupDto;
import lightning.gathergo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SignupDtoMapper {
    private ModelMapper modelMapper;

    public SignupDtoMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public User toUser(SignupDto.SignupInput dto){
        return modelMapper.map(dto, User.class);
    }
}
