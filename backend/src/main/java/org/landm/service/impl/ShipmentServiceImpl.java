package org.landm.service.impl;

import jakarta.transaction.Transactional;
import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.entity.Shipment;
import org.landm.entity.ShipmentStatusHistory;
import org.landm.entity.User;
import org.landm.entity.enums.ShipmentStatus;
import org.landm.helper.ShipmentNumberGenerator;
import org.landm.mapper.ShipmentMapper;
import org.landm.repository.ShipmentRepository;
import org.landm.repository.UserRepository;
import org.landm.service.ShipmentService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final ShipmentMapper shipmentMapper;
    private final ShipmentNumberGenerator shipmentNumberGenerator;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                               UserRepository userRepository,
                               ShipmentMapper shipmentMapper,
                               ShipmentNumberGenerator shipmentNumberGenerator) {
        this.shipmentRepository = shipmentRepository;
        this.userRepository = userRepository;
        this.shipmentMapper = shipmentMapper;
        this.shipmentNumberGenerator = shipmentNumberGenerator;
    }
    @Override
    @Transactional
    public ShipmentDto createShipment(CreateShipmentRequestDto req) {

        User user = userRepository.findById(req.getUserId())
                .orElseThrow(()-> new RuntimeException("User ne postoji u bazi"));

        String trackingNumber = shipmentNumberGenerator.generate();

        Shipment shipment = Shipment.builder()
                .trackingNumber(trackingNumber)
                .user(user)
                .weight(req.getWeight())
                .description(req.getDescription())
                .receiverName(req.getReceiverName())
                .receiverAddress(req.getReceiverAddress())
                .senderAddress(req.getSenderAddress())
                .currentStatus(ShipmentStatus.CREATED)
                .build();

        ShipmentStatusHistory firstEntry = ShipmentStatusHistory.builder()
                .shipment(shipment)
                .status(ShipmentStatus.CREATED)
                .note("Posiljka je kreirana")
                .build();

        Shipment saved = shipmentRepository.save(shipment);
        return shipmentMapper.toDto(saved);
    }
    @Override
    @Transactional
    public ShipmentDto getShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shipment not found"));

        ShipmentDto dto = shipmentMapper.toDto(shipment);


        return dto;
    }
    @Override
    @Transactional
    public List<ShipmentDto> getAll() {
        List<Shipment> shipments = shipmentRepository.findAll();

        List<ShipmentDto> result = new ArrayList<>();

        for (Shipment shipment : shipments) {
            result.add(shipmentMapper.toDto(shipment));
        }
        return result;
    }
}
