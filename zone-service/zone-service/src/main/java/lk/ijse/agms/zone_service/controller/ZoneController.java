package lk.ijse.agms.zone_service.controller;

import lk.ijse.agms.zone_service.dto.ZoneDTO;
import lk.ijse.agms.zone_service.entity.Zone;
import lk.ijse.agms.zone_service.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/zones")
public class ZoneController {

    @Autowired
    private ZoneService zoneService;

    @PostMapping
    public String saveZone(@RequestBody ZoneDTO zoneDTO) {
        return zoneService.saveZone(zoneDTO);
    }

    @GetMapping("/{id}")
    public ZoneDTO getZone(@PathVariable String id) {
        return zoneService.getZone(id);
    }

    @GetMapping
    public List<ZoneDTO> getAllZones() {
        return zoneService.getAllZones();
    }

    @PutMapping("/{id}")
    public void updateZone(@PathVariable String id, @RequestBody ZoneDTO zoneDTO) {
        zoneService.updateZone(id, zoneDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteZone(@PathVariable String id) {
        zoneService.deleteZone(id);
    }

}
