package lk.ijse.agms.sensor_service.service.impl;

import lk.ijse.agms.sensor_service.controller.SensorController;
import lk.ijse.agms.sensor_service.dto.AuthResponseDto;
import lk.ijse.agms.sensor_service.dto.LoginRequestDto;
import lk.ijse.agms.sensor_service.dto.SensorDataDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TelemetryServiceImpl {

    @Autowired
    private SensorController sensorController;

    private static final Logger logger = LoggerFactory.getLogger(TelemetryServiceImpl.class);

    // Use WebClient for Reactive/Non-blocking I/O
    private final WebClient webClient = WebClient.builder().baseUrl("http://localhost:8080").build();

    @Scheduled(fixedRate = 10000) //  10 seconds every
    public void fetchAndPushData() {
        System.out.println("\n--- [BRIDGE] Starting 10s Data Cycle ---");

        loginToIotBackend()
                .flatMap(token -> {
                    System.out.println("[BRIDGE] Step 1: JWT Obtained. Fetching Telemetry...");
                    return fetchLatestTelemetry(token);
                })
                .doOnNext(data -> {

                    System.out.println("[BRIDGE] Step 2: DATA RECEIVED!");
                    System.out.println(" >> Device: " + data.getDeviceId());
                    System.out.println(" >> Temperature: " + data.getTemperature() + "°C");
                    System.out.println(" >> Humidity: " + data.getHumidity() + "%");


                    sensorController.setLastReading(data);
                })
                .flatMap(data -> {
                    System.out.println("[BRIDGE] Step 3: Pushing to Automation Service...");
                    return pushToAutomationService(data);
                })
                .subscribe(
                        success -> System.out.println("[BRIDGE] SUCCESS: Cycle Complete.\n"),
                        error -> System.err.println("[BRIDGE] STATUS: Automation Service Offline (8083)\n")
                );
    }

    private Mono<String> loginToIotBackend() {
        return webClient.post()
                .uri("/api/auth/login")
                .bodyValue(new LoginRequestDto("username", "123456")) // Based on PDF [cite: 63, 65]
                .retrieve()
                .bodyToMono(AuthResponseDto.class)
                .map(AuthResponseDto::getAccessToken);
    }

    private Mono<SensorDataDTO> fetchLatestTelemetry(String token) {

        return webClient.get()
                .uri("/api/devices/telemetry/d918285e-870d-48f6-ad94-e5219b58d07c")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(SensorDataDTO.class);
    }

    private Mono<Void> pushToAutomationService(SensorDataDTO data) {

        return WebClient.create("http://localhost:8083")
                .post()
                .uri("/api/automation/process")
                .bodyValue(data)
                .retrieve()
                .toBodilessEntity()
                .then();
    }
}