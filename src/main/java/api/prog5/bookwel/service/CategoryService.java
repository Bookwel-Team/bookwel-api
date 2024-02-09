package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.repository.CategoryRepository;
import api.prog5.bookwel.repository.model.Category;
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
    return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category.id = "+ id + " not found"));
  }

  public List<Category> getAllByName(String name) {
    return categoryRepository.findAllByName(name);
  }

  public Category getByName(String name){
    return categoryRepository.findByName(name).orElseThrow(() -> new NotFoundException("Category.name = "+ name + " not found"));
  }
}
