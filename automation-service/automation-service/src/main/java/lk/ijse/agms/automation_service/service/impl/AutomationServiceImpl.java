package lk.ijse.agms.automation_service.service.impl;

import lk.ijse.agms.automation_service.client.ZoneClient;
import lk.ijse.agms.automation_service.dto.SensorDataDTO;
import lk.ijse.agms.automation_service.dto.ZoneThresholdDTO;
import lk.ijse.agms.automation_service.entity.AutomationLog;
import lk.ijse.agms.automation_service.repo.AutomationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AutomationServiceImpl {
    @Autowired
    private ZoneClient zoneClient;

    @Autowired
    private AutomationLogRepository logRepository;

    public void processEnvironmentalData(SensorDataDTO data) {

        ZoneThresholdDTO thresholds = zoneClient.getThresholds(data.getZoneId());

        //  Logic
        if (data.getTemperature() > thresholds.getMaxTemp()) {
            saveActionLog(data.getZoneId(), "TURN_FAN_ON", data.getTemperature());
        } else if (data.getTemperature() < thresholds.getMinTemp()) {
            saveActionLog(data.getZoneId(), "TURN_HEATER_ON", data.getTemperature());
        }
    }

    private void saveActionLog(String zoneId, String action, Double value) {
        AutomationLog log = new AutomationLog();
        log.setZoneId(zoneId);
        log.setAction(action);
        log.setObservedValue(value);
        log.setTimestamp(LocalDateTime.now());
        logRepository.save(log);
    }
}
