package org.landm.repository;

import org.landm.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long>,
                                            JpaSpecificationExecutor<Shipment>{

        Optional<Shipment> findByTrackingNumber(String trackingNumber);

        boolean existsByTrackingNumber(String trackingNumber);
    }


