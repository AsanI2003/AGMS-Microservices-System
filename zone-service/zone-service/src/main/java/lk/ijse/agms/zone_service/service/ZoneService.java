package lk.ijse.agms.zone_service.service;

import lk.ijse.agms.zone_service.dto.ZoneDTO;
import lk.ijse.agms.zone_service.entity.Zone;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ZoneService {
    String saveZone(ZoneDTO zoneDTO,String token);
    void updateZone(String id, ZoneDTO zoneDTO);
    void deleteZone(String id);
    ZoneDTO getZone(String id);
    List<ZoneDTO> getAllZones();
}
