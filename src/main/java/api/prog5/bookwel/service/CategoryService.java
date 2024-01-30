package api.prog5.bookwel.service;

import api.prog5.bookwel.model.Category;
import api.prog5.bookwel.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category getById(String id){
        return categoryRepository.findById(id).get();
    }

    public List<Category> getByName(String name){
        return categoryRepository.findAllByName(name);
    }

}
