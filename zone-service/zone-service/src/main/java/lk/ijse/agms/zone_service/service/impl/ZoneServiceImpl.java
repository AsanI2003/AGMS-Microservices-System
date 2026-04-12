package lk.ijse.agms.zone_service.service.impl;

import lk.ijse.agms.zone_service.client.IotClient;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    private ZoneRepository zoneRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private IotClient iotClient; // OpenFeign

    @Override
    public String saveZone(ZoneDTO zoneDTO, String authHeader) {

        if (zoneDTO.getMinTemp() == null || zoneDTO.getMaxTemp() == null ||
                zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Min Temp must be strictly less than Max Temp");
        }


        validateHumidity(zoneDTO.getMinHumidity(), zoneDTO.getMaxHumidity());


        Map<String, String> payload = new HashMap<>();
        payload.put("name", zoneDTO.getZoneName() + "-Sensor");
        payload.put("zoneId", zoneDTO.getZoneID());

        String externalDeviceId;
        try {

            Map<String, Object> response = iotClient.registerDevice(authHeader, payload);
            externalDeviceId = response.get("deviceId").toString();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "IoT External Provider is currently offline");
        }


        Zone zone = modelMapper.map(zoneDTO, Zone.class);
        zone.setDeviceId(externalDeviceId);

        return zoneRepository.save(zone).getZoneID();
    }

    @Override
    public void updateZone(String id, ZoneDTO zoneDTO) {
        Zone existingZone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found: " + id));


        if (zoneDTO.getMinTemp() >= zoneDTO.getMaxTemp()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Min Temp must be less than Max Temp");
        }

        validateHumidity(zoneDTO.getMinHumidity(), zoneDTO.getMaxHumidity());


        existingZone.setZoneName(zoneDTO.getZoneName());
        existingZone.setZoneLocation(zoneDTO.getZoneLocation());
        existingZone.setZoneSize(zoneDTO.getZoneSize());
        existingZone.setMinTemp(zoneDTO.getMinTemp());
        existingZone.setMaxTemp(zoneDTO.getMaxTemp());
        existingZone.setMinHumidity(zoneDTO.getMinHumidity());
        existingZone.setMaxHumidity(zoneDTO.getMaxHumidity());


        zoneRepository.save(existingZone);
    }

    @Override
    public void deleteZone(String id) {
        if(zoneRepository.existsById(id)) {
            zoneRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found: " + id);
        }
    }

    @Override
    public ZoneDTO getZone(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Zone not found: " + id));
        return modelMapper.map(zone, ZoneDTO.class);
    }

    @Override
    public List<ZoneDTO> getAllZones() {
        List<Zone> zones = zoneRepository.findAll();
        return modelMapper.map(zones, new TypeToken<List<ZoneDTO>>() {}.getType());
    }


    private void validateHumidity(Double min, Double max) {
        if (min == null || max == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Humidity values cannot be null");
        }
        if (min < 0 || max > 100) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Humidity must be between 0 and 100 PERCENTAGE");
        }
        if (min >= max) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Min Humidity must be strictly less than Max Humidity");
        }
    }
}