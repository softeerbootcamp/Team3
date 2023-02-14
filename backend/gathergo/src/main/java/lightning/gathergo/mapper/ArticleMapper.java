package lightning.gathergo.mapper;

import com.amazonaws.services.cloudformation.model.AccountGateResult;
import lightning.gathergo.dto.GatheringDto;
import lightning.gathergo.model.Article;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ArticleMapper {
    private ModelMapper modelMapper;

    ArticleMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // entity to dto
    public GatheringDto.ArticlePartialDto toPartialDto(Article entity){
        return modelMapper.map(entity, GatheringDto.ArticlePartialDto.class);
    }

    public List<GatheringDto.ArticlePartialDto> toArticlePartialDtoList(List<Article> entities){
        return entities
                .stream()
                .map(entity->
                    modelMapper.map(entity, GatheringDto.ArticlePartialDto.class)
                )
                .collect(Collectors.toList());
    }

    public GatheringDto.ArticleFullDto toArticleFullDto(Article entity){
        return modelMapper.map(entity, GatheringDto.ArticleFullDto.class);
    }

    // dto to entity
    public Article toArticle(GatheringDto.UpdateRequest dto){
        return modelMapper.map(dto, Article.class);
    }

    public Article toArticle(GatheringDto.CreateRequest dto){
        return modelMapper.map(dto, Article.class);
    }
}
