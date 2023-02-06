package lightning.gathergo.mapper;

import lightning.gathergo.dto.CategoryDto;
import lightning.gathergo.model.Category;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class CategoryDtoMapper {
    private ModelMapper modelMapper;

    public CategoryDtoMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }
    //entity to dto
    public CategoryDto.Response toCategoryResponse(Category category){
        return modelMapper.map(category,CategoryDto.Response.class);
    }
    public List<CategoryDto.Response> toCategoryResponseList(List<Category> categories){
        return categories
                .stream()
                .map(category -> modelMapper.map(category, CategoryDto.Response.class))
                .collect(Collectors.toList());
    }
    //dto to entity
    public Category toCategory(CategoryDto.CreateRequest categoryRequest){
        return modelMapper.map(categoryRequest,Category.class);
    }

    public Category toCategory(CategoryDto.ModifyRequest categoryRequest){
        return modelMapper.map(categoryRequest,Category.class);
    }
}
