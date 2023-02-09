package lightning.gathergo.mapper;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lightning.gathergo.dto.CommentDto;
import lightning.gathergo.model.Article;
import lightning.gathergo.model.Comment;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Configuration
public class CommentMapper {
    private ModelMapper modelMapper;

    CommentMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // entity to dto
    public CommentDto.Response toCommentResponse(Article entity){
        return modelMapper.map(entity, CommentDto.Response.class);
    }

    public List<CommentDto.Response> toCommentResponseList(List<Comment> entities){
        return entities
                .stream()
                .map(entity->
                    modelMapper.map(entity, CommentDto.Response.class)
                )
                .collect(Collectors.toList());
    }

    // dto to entity
    public Comment toComment(CommentDto.UpdateReqeust dto){
        return modelMapper.map(dto, Comment.class);
    }

    public Comment toComment(CommentDto.CreateRequest dto){
        return modelMapper.map(dto, Comment.class);
    }
}
