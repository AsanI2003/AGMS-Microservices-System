package lk.ijse.agms.sensor_service.service.impl;

import lk.ijse.agms.sensor_service.controller.SensorController;
import lk.ijse.agms.sensor_service.dto.AuthResponseDto;
import lk.ijse.agms.sensor_service.dto.LoginRequestDto;
import lk.ijse.agms.sensor_service.dto.SensorDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TelemetryServiceImpl {

    @Autowired
    private SensorController sensorController;

    private static final Logger logger = LoggerFactory.getLogger(TelemetryServiceImpl.class);

    // IoT Backend
    private final WebClient iotWebClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    // Zone Service
    private final WebClient zoneWebClient = WebClient.builder().baseUrl("http://localhost:8081").build();

    @Scheduled(fixedRate = 10000)
    public void fetchAndPushData() {
        System.out.println("\n--- [SYSTEM INTEGRATION] Starting 10s Data Cycle ---");


        loginToIotBackend()
                .flatMap(token ->
                        //  Dynamically get all zones from Zone Service
                        getAllZones().flatMapIterable(zones -> zones)
                                .flatMap(zone -> {
                                    String deviceId = (String) zone.get("deviceId");
                                    String zoneId = (String) zone.get("zoneID");

                                    if (deviceId == null) return Mono.empty();


                                    return fetchLatestTelemetry(token, deviceId)
                                            .flatMap(data -> {

                                                data.setZoneId(zoneId);
                                                sensorController.setLastReading(data);

                                                System.out.println("[FETCH] Zone: " + zoneId + " | Temp: " + data.getTemperature() + "°C");


                                                return pushToAutomationService(data);
                                            });
                                })
                                .then()
                )
                .subscribe(
                        success -> System.out.println("[SUCCESS] Cycle Complete.\n"),
                        error -> System.err.println("[ERROR] Flow Interrupted: " + error.getMessage() + "\n")
                );
    }

    private Mono<String> loginToIotBackend() {
        return iotWebClient.post()
                .uri("/api/auth/login")
                .bodyValue(new LoginRequestDto("username", "123456"))
                .retrieve()
                .bodyToMono(AuthResponseDto.class)
                .map(AuthResponseDto::getAccessToken);
    }

    private Mono<List<Map<String, Object>>> getAllZones() {
        return zoneWebClient.get()
                .uri("/api/v1/zones")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Map<String, Object>>>() {});
    }

    private Mono<SensorDataDTO> fetchLatestTelemetry(String token, String deviceId) {
        return iotWebClient.get()
                .uri("/api/devices/telemetry/" + deviceId)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(SensorDataDTO.class);
    }

    private Mono<Void> pushToAutomationService(SensorDataDTO data) {
        // Flatten the object so Automation Service can map it directly to its DTO
        Map<String, Object> payload = new HashMap<>();
        payload.put("zoneId", data.getZoneId());
        payload.put("temperature", data.getTemperature());
        payload.put("humidity", data.getHumidity());

        return WebClient.create("http://localhost:8083")
                .post()
                .uri("/api/automation/process")
                .bodyValue(payload)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}