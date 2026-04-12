package lk.ijse.agms.automation_service.controller;


import lk.ijse.agms.automation_service.dto.SensorDataDTO;
import lk.ijse.agms.automation_service.entity.AutomationLog;
import lk.ijse.agms.automation_service.repo.AutomationLogRepository;
import lk.ijse.agms.automation_service.service.impl.AutomationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/automation")
public class AutomationController {
    @Autowired
    private AutomationServiceImpl automationService;

    @Autowired
    private AutomationLogRepository logRepository;

    @PostMapping("/process") // Internal endpoint for Telemetry Service
    public void receiveData(@RequestBody SensorDataDTO data) {
        automationService.processEnvironmentalData(data);
    }

    @GetMapping("/logs") // API for Farmer to see logs
    public List<AutomationLog> getLogs() {
        return logRepository.findAll();
    }
}
