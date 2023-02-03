package lightning.gathergo.service;

import lightning.gathergo.exception.CustomGlobalException;
import lightning.gathergo.exception.ErrorCode;
import lightning.gathergo.model.Category;
import lightning.gathergo.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(Integer id){
        return Optional.ofNullable(categoryRepository.findOneById(id)).orElseThrow(() -> new CustomGlobalException(ErrorCode.NO_RESOURCE));
    }

    public Optional<Category> getCategoryByName(String name){
        return categoryRepository.findOneByName(name);
    }

    public Optional<Category> createCategory(String name){
        if(getCategoryByName(name).isPresent()) throw new CustomGlobalException(ErrorCode.NAME_DUPLICATED);
        categoryRepository.save(name);
        return categoryRepository.findOneByName(name);
    }

    public Optional<Category> updateCategory(Integer id, String name){
        if(getCategoryByName(name).isPresent()) throw new CustomGlobalException(ErrorCode.NAME_DUPLICATED);
        categoryRepository.update(id,name);
        return categoryRepository.findOneById(id);
    }

    public void deleteCategoryById(Integer id){
        categoryRepository.deleteById(id);
    }

    public void deleteCategories(){
        categoryRepository.deleteAll();
    }

}
