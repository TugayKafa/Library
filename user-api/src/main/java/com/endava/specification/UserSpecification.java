package com.endava.specification;

import com.endava.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.endava.model.Status.ACTIVE;
import static com.endava.model.Status.INACTIVE;
import static com.endava.util.Constant.FIRST_NAME;
import static com.endava.util.Constant.LAST_NAME;
import static com.endava.util.Constant.PERCENTAGE_SYMBOL;
import static com.endava.util.Constant.STATUS;
import static com.endava.util.Constant.WHITE_SPACE;

@Component
public class UserSpecification {

    public static Specification<User> getSearchSpecification(SpecificationParams request) {
        return ((root, query, criteriaBuilder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (request.getStatus() == null || Objects.equals(request.getStatus(), SpecificationStatusFieldParam.ACTIVE)) {
                predicates.add(criteriaBuilder.equal(root.get(STATUS), ACTIVE));
            } else if (Objects.equals(request.getStatus(), SpecificationStatusFieldParam.INACTIVE)) {
                predicates.add(criteriaBuilder.equal(root.get(STATUS), INACTIVE));
            }

            String searchText = PERCENTAGE_SYMBOL + request.getUserSearchText() + PERCENTAGE_SYMBOL;
            if (request.getUserSearchType() != null) {
                if (Objects.equals(request.getUserSearchType(), SpecificationFieldParam.NAME)) {
                    Expression<String> fullNameExpression = criteriaBuilder.concat(
                            criteriaBuilder.concat(root.get(FIRST_NAME), WHITE_SPACE), root.get(LAST_NAME)
                    );
                    predicates.add(criteriaBuilder.like(
                            fullNameExpression,
                            searchText
                    ));
                } else {
                    predicates.add(criteriaBuilder.like(
                            root.get(request.getUserSearchType().getEntityFieldName()),
                            searchText
                    ));
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        });
    }

}