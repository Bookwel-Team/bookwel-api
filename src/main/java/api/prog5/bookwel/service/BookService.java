package api.prog5.bookwel.service;

import api.prog5.bookwel.endpoint.rest.exception.NotFoundException;
import api.prog5.bookwel.model.Book;
import api.prog5.bookwel.repository.BookRepository;
import api.prog5.bookwel.repository.dao.BookDao;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final BookRepository bookRepository;

    public List<Book> getBooksByCriteria(String author, String category, Integer page, Integer pageSize){
        Pageable pageable =
                PageRequest.of(page - 1, pageSize, Sort.by(DESC, "author"));
        return bookDao.findByCriteria(author, category, pageable);
    }

    public Book getBookById(String id){
        return bookRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Book with id: " + id + " not found"));
    }

}
