package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    public void save(Order order) {
        entityManager.persist(order);
    }

    public Order findOne(Long id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> searchOrder(OrderSearch orderSearch) {

        String jpql = "select o from Order o join o.member m";

        boolean isFIrstCondition = true;
        if (orderSearch.getOrderStatus() != null) {
            if (isFIrstCondition) {
                jpql += " where";
                isFIrstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }


        return entityManager.createQuery(jpql, Order.class)
                .setMaxResults(1000) // 최대 1000건을 가져온다.
                .getResultList();
    }

    /**
     * JPA Criteria
     */
    public List<Order> findALlByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = criteriaBuilder.createQuery(Order.class);
        Root<Order> root = query.from(Order.class);
        Join<Object, Object> member = root.join("member", JoinType.INNER);

        List<Predicate> predicates = new ArrayList<>();
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = criteriaBuilder.equal(root.get("status"), orderSearch.getOrderStatus());
            predicates.add(status);
        }

        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Order> results = entityManager.createQuery(query)
                .setMaxResults(1000);
        return results.getResultList();
    }

}
