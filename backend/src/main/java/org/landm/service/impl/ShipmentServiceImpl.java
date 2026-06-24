package org.landm.service.impl;

import jakarta.transaction.Transactional;
import org.landm.dto.shipment.CreateShipmentRequestDto;
import org.landm.dto.shipment.ShipmentDto;
import org.landm.dto.shipment.ShipmentStatusHistoryDto;
import org.landm.dto.shipment.UpdateShipmentStatusRequestDto;
import org.landm.entity.Shipment;
import org.landm.entity.ShipmentStatusHistory;
import org.landm.entity.User;
import org.landm.entity.enums.ShipmentStatus;
import org.landm.helper.ShipmentNumberGenerator;
import org.landm.mapper.ShipmentMapper;
import org.landm.mapper.ShipmentStatusHistoryMapper;
import org.landm.repository.ShipmentRepository;
import org.landm.repository.ShipmentStatusHistoryRepository;
import org.landm.repository.UserRepository;
import org.landm.service.ShipmentService;
import org.landm.specification.ShipmentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShipmentServiceImpl implements ShipmentService {
    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;
    private final ShipmentMapper shipmentMapper;
    private final ShipmentNumberGenerator shipmentNumberGenerator;
    private final ShipmentStatusHistoryRepository historyRepository;

    private final ShipmentStatusHistoryMapper shipmentStatusHistoryMapper;

    public ShipmentServiceImpl(ShipmentRepository shipmentRepository,
                               UserRepository userRepository,
                               ShipmentMapper shipmentMapper,
                               ShipmentNumberGenerator shipmentNumberGenerator,
                               ShipmentStatusHistoryRepository historyRepository,
                               ShipmentStatusHistoryMapper shipmentStatusHistoryMapper) {
        this.shipmentRepository = shipmentRepository;
        this.userRepository = userRepository;
        this.shipmentMapper = shipmentMapper;
        this.shipmentNumberGenerator = shipmentNumberGenerator;
        this.historyRepository = historyRepository;

        this.shipmentStatusHistoryMapper = shipmentStatusHistoryMapper;
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

        shipment.getStatusHistory().add(firstEntry);
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
    public ShipmentDto getByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new RuntimeException("Posiljka sa tracking brojem ne postoji: " + trackingNumber));

        return shipmentMapper.toDto(shipment);
    }


    @Override
    @Transactional
    public Page<ShipmentDto> getAll(Long userId,
                                    ShipmentStatus status,
                                    LocalDateTime dateFrom,
                                    LocalDateTime dateTo,
                                    Pageable pageable) {

        org.springframework.data.jpa.domain.Specification<Shipment> spec =
                ShipmentSpecification.filter(userId, status, dateFrom, dateTo);

        Page<Shipment> page = shipmentRepository.findAll(spec, pageable);

        return page.map(shipmentMapper::toDto);
    }
        @Override
        @Transactional
        public ShipmentDto updateStatus(Long id, UpdateShipmentStatusRequestDto request) {

            Shipment shipment = shipmentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Pošiljka ne postoji: " + id));

            validateTransition(shipment.getCurrentStatus(), request.getStatus());

            shipment.setCurrentStatus(request.getStatus());

            ShipmentStatusHistory historyEntry = ShipmentStatusHistory.builder()
                    .shipment(shipment)
                    .status(request.getStatus())
                    .note(request.getNote())
                    .build();

            shipment.getStatusHistory().add(historyEntry);

            Shipment saved = shipmentRepository.save(shipment);

            return shipmentMapper.toDto(saved);
        }

    @Override
    @Transactional
    public List<ShipmentStatusHistoryDto> getHistoryForShipment(Long shipmentId) {
        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(() -> new RuntimeException("nema shipmenta u bazi"));

        List<ShipmentStatusHistory> entries = historyRepository.findByShipmentIdOrderByChangedAtAsc(shipmentId);

        List<ShipmentStatusHistoryDto> result = new ArrayList<>();
        for (ShipmentStatusHistory entry : entries) {
            result.add(shipmentStatusHistoryMapper.toDto(entry));
        }
        return result;
    }

    private void validateTransition(ShipmentStatus current, ShipmentStatus next) {

        if (current == next) {
          throw new RuntimeException("Posiljka je vec u statusu: " + next);
      }
        if (current == ShipmentStatus.DELIVERED || current == ShipmentStatus.CANCELLED) {
          throw new RuntimeException(
                  "Posiljka je u finalnom statusu (" + current + ") i ne moze se menjati"
          );
      }
        if (current == ShipmentStatus.CREATED) {
          if (next != ShipmentStatus.IN_TRANSIT && next != ShipmentStatus.CANCELLED) {
              throw new RuntimeException("Iz CREATED je dozvoljen samo prelaz u IN_TRANSIT ili CANCELLED");
          }
      }
        if (current == ShipmentStatus.IN_TRANSIT) {
          if (next != ShipmentStatus.DELIVERED && next != ShipmentStatus.CANCELLED) {
              throw new RuntimeException("Iz IN_TRANSIT je dozvoljen samo prelaz u DELIVERED ili CANCELLED");
          }
      }
        }

}
