package lk.ijse.agms.zone_service.service.impl;

import lk.ijse.agms.zone_service.dto.ZoneDTO;
import lk.ijse.agms.zone_service.entity.Zone;
import lk.ijse.agms.zone_service.repo.ZoneRepository;
import lk.ijse.agms.zone_service.service.ZoneService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public String saveZone(ZoneDTO zoneDTO) {
        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        return zoneRepository.save(zone).getZoneID();
    }

    @Override
    public void updateZone(String id, ZoneDTO zoneDTO) {
        if(zoneRepository.existsById(id)) {
            Zone zone = modelMapper.map(zoneDTO, Zone.class);
            zone.setZoneID(id);
            zoneRepository.save(zone);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found for update: " + id);
        }
    }

    @Override
    public void deleteZone(String id) {
        if(zoneRepository.existsById(id)) {
            zoneRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found for deletion: " + id);
        }
    }

    @Override
    public ZoneDTO getZone(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found for deletion: " + id));
        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public List<ZoneDTO> getAllZones() {
        List<Zone> zones = zoneRepository.findAll();
        // Use TypeToken to map a List of Entities to a List of DTOs
        return modelMapper.map(zones, new TypeToken<List<ZoneDTO>>() {}.getType());
    }
}
