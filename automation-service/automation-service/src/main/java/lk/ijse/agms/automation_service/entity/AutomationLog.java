package lk.ijse.agms.automation_service.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class AutomationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;
    private String zoneId;
    private String action; //  TURN_FAN_ON or TURN_HEATER_ON
    private Double observedValue;
    private LocalDateTime timestamp;
}
