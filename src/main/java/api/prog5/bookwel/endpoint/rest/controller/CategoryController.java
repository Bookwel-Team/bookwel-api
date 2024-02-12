package api.prog5.bookwel.endpoint.rest.controller;

import api.prog5.bookwel.endpoint.rest.mapper.CategoryMapper;
import api.prog5.bookwel.endpoint.rest.model.Category;
import api.prog5.bookwel.endpoint.rest.security.model.Principal;
import api.prog5.bookwel.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CategoryController {
  private final CategoryService categoryService;
  private final CategoryMapper mapper;

  @GetMapping("/categories")
  public List<Category> getAllCategories(
          @RequestParam(required = false) String name,
          @AuthenticationPrincipal Principal principal) {
    var currentUser = principal == null ? null : principal.getUser();
    return categoryService.getAllByCriteria(name).stream().map(c -> mapper.toRest(c, currentUser)).toList();
  }
}
