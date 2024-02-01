package api.prog5.bookwel.service;

import api.prog5.bookwel.model.Category;
import api.prog5.bookwel.repository.CategoryRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

  private final CategoryRepository categoryRepository;

  public List<Category> getAllByCriteria(String name) {
    return name == null ? categoryRepository.findAll() : getAllByName(name);
  }

  public Category getById(String id) {
    return categoryRepository.findById(id).get();
  }

  public List<Category> getAllByName(String name) {
    return categoryRepository.findAllByName(name);
  }
}
