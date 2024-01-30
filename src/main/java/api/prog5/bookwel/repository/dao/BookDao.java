package api.prog5.bookwel.repository.dao;

import api.prog5.bookwel.model.Book;
import api.prog5.bookwel.model.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class BookDao {

    private final EntityManager entityManager;

    public List<Book> findByCriteria(String author, String category, Pageable pageable){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> query = builder.createQuery(Book.class);
        Root<Book> root = query.from(Book.class);
        Join<Book, Category> join = root.join("category");

        List<Predicate> predicates = new ArrayList<>();

        if (author != null){
            predicates.add(
                    builder.or(
                        builder.like(builder.lower(root.get("author")), "%" + author + "%"),
                        builder.like(root.get("author"), "%" + author + "%")));
        }

        if (category != null){
            predicates.add(
                    builder.or(
                            builder.like(builder.lower(join.get("name")), "%" + category + "%"),
                            builder.like(join.get("name"), "%" + category + "%")));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0])).distinct(true);
        }

        query.orderBy(QueryUtils.toOrders(pageable.getSort(), root, builder));

        return entityManager
                .createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

}
