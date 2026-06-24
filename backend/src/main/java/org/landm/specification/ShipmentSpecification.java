package org.landm.specification;

import jakarta.persistence.criteria.Predicate;
import org.landm.entity.Shipment;
import org.landm.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShipmentSpecification {

    public static org.springframework.data.jpa.domain.Specification<Shipment> filter(Long userId, ShipmentStatus status, LocalDateTime dateFrom, LocalDateTime dateTo){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(userId != null){
                predicates.add(criteriaBuilder.equal(root.get("user").get("id"), userId));
            }
            if(status != null){
                predicates.add(criteriaBuilder.equal(root.get("currentStatus"), status));
            }
            if(dateFrom != null){
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dateFrom));
            }
            if(dateTo != null){
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), dateTo));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
