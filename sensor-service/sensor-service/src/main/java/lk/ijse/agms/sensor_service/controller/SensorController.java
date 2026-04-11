package lk.ijse.agms.sensor_service.controller;

import lk.ijse.agms.sensor_service.dto.SensorDataDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {


    // Store the last fetched reading in memory for the debug view
    private SensorDataDTO lastReading;

    @GetMapping("/latest")
    public SensorDataDTO getLatest() {
        return lastReading;
    }

    public void setLastReading(SensorDataDTO data) {
        this.lastReading = data;
    }
}
