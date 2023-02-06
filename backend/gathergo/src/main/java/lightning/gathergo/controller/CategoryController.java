package lightning.gathergo.controller;

import lightning.gathergo.dto.CategoryDto;
import lightning.gathergo.mapper.CategoryDtoMapper;
import lightning.gathergo.model.Category;
import lightning.gathergo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryDtoMapper categoryDtoMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, CategoryDtoMapper categoryDtoMapper) {
        this.categoryService = categoryService;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String,Object>> getCategories() {
        List<CategoryDto.Response> categories = categoryDtoMapper.toCategoryResponseList(categoryService.getCategories());
        System.out.println(System.getProperty("user.dir"));
        Map<String,Object> result = new HashMap<>();
        result.put("data",categories);
        result.put("count",categories.size());

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategoryById(@PathVariable Integer categoryId) {
        CategoryDto.Response categoryResponse;
        categoryResponse = categoryDtoMapper.toCategoryResponse(categoryService.getCategoryById(categoryId).get());
        return new ResponseEntity<CategoryDto.Response>(categoryResponse, HttpStatus.FOUND);
    }

    @PostMapping
    public ResponseEntity<CategoryDto.Response> createCategory(@RequestBody CategoryDto.CreateRequest in) {
        Category category = categoryService.createCategory(in.getName()).get();
        CategoryDto.Response response = categoryDtoMapper.toCategoryResponse(category);
        return ResponseEntity.ok().body(response);
    }


    @PutMapping
    public ResponseEntity<CategoryDto.Response> modifyCategory(@RequestBody CategoryDto.ModifyRequest in) {
        Category category = categoryDtoMapper.toCategory(in);
        Optional<Category> modified = categoryService.updateCategory(in.getId(), in.getName());
        CategoryDto.Response response = categoryDtoMapper.toCategoryResponse(modified.get());
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return ResponseEntity.ok().body("deleted");
    }


    @DeleteMapping
    public ResponseEntity<String> deleteCategories() {
        categoryService.deleteCategories();
        return ResponseEntity.ok().body("all deleted");
    }

}
