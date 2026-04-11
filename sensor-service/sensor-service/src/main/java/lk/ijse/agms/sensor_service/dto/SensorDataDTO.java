package lk.ijse.agms.sensor_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorDataDTO {
    private String deviceId;
    private String zoneId;
    private TelemetryValue value; // The nested object from Postman
    private String capturedAt;

    @Data
    public static class TelemetryValue {
        private Double temperature;
        private String tempUnit;
        private Double humidity;
        private String humidityUnit;
    }

    // Helper methods to keep your Service code clean
    public Double getTemperature() {
        return value != null ? value.getTemperature() : null;
    }

    public Double getHumidity() {
        return value != null ? value.getHumidity() : null;
    }
}