package org.landm.repository;

import org.landm.entity.ShipmentStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipmentStatusHistoryRepository extends JpaRepository<ShipmentStatusHistory, Long> {

        List<ShipmentStatusHistory> findByShipmentIdOrderByChangedAtAsc(Long shipmentId);
    }

