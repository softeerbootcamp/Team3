package lightning.gathergo.mapper;

import lightning.gathergo.dto.ArticleDto;
import lightning.gathergo.model.Article;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ArticleMapper {
    private ModelMapper modelMapper;

    ArticleMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    // entity to dto
    public ArticleDto.Response toArticleResponse(Article entity){
        return modelMapper.map(entity, ArticleDto.Response.class);
    }

    public List<ArticleDto.Response> toArticleResponseList(List<Article> entities){
        return entities
                .stream()
                .map(entity->
                    modelMapper.map(entity, ArticleDto.Response.class)
                )
                .collect(Collectors.toList());
    }

    // dto to entity
    public Article toArticle(ArticleDto.UpdateRequest dto){
        return modelMapper.map(dto, Article.class);
    }

    public Article toArticle(ArticleDto.CreateRequest dto){
        return modelMapper.map(dto, Article.class);
    }

    public Article toArticle(ArticleDto.CloseRequest dto){
        return modelMapper.map(dto, Article.class);
    }

    public Article toArticle(ArticleDto.ReadRequest dto) { return modelMapper.map(dto, Article.class); }
}
