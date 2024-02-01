package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.endpoint.rest.mapper.CategoryMapper;
import api.prog5.bookwel.endpoint.rest.model.Category;
import api.prog5.bookwel.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper mapper;

  @GetMapping("/categories")
  public List<Category> getAllCategories(@RequestParam(required = false) String name) {
    return categoryService.getAllByCriteria(name).stream().map(mapper::toRest).toList();
  }
}
