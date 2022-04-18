package com.endava.specification;

import com.endava.model.entity.Author;
import com.endava.model.entity.Author_;
import com.endava.model.entity.Book;
import com.endava.model.entity.Book_;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.endava.model.entity.Status.ACTIVE;
import static com.endava.model.entity.Status.INACTIVE;
import static com.endava.specification.SpecificationFieldParam.AUTHORS;
import static com.endava.util.Constants.PERCENTAGE_SYMBOL;

@Component
public class BookSpecification {

    public static Specification<Book> getSearchSpecification(SpecificationParams request) {

        return ((root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();

            if (request.getStatus() == null || Objects.equals(request.getStatus(), SpecificationStatusFieldParam.ACTIVE)) {
                predicates.add(criteriaBuilder.equal(root.get(Book_.status), ACTIVE));
            } else if (Objects.equals(request.getStatus(), SpecificationStatusFieldParam.INACTIVE)) {
                predicates.add(criteriaBuilder.equal(root.get(Book_.status), INACTIVE));
            }

            String searchText = PERCENTAGE_SYMBOL + request.getBookSearchText() + PERCENTAGE_SYMBOL;

            if (request.getBookSearchType() != null) {
                if (request.getBookSearchType().equals(AUTHORS)) {
                    ListJoin<Book, Author> authorJoin = root.join(Book_.authors);
                    predicates.add(criteriaBuilder.like(authorJoin.get(Author_.fullName), searchText));
                } else {
                    predicates.add(criteriaBuilder.like(
                            root.get(request.getBookSearchType().getEntityFieldName()), searchText)
                    );
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }
}