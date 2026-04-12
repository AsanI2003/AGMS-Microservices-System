package lk.ijse.agms.zone_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "iot-provider", url = "http://localhost:8080/api")
public interface IotClient {
    @PostMapping("/devices")
    Map<String, Object> registerDevice(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> payload
    );
}
