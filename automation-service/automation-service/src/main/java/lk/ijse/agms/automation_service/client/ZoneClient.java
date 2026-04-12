package lk.ijse.agms.automation_service.client;

import lk.ijse.agms.automation_service.dto.ZoneThresholdDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneClient {
    @GetMapping("/api/v1/zones/{id}")
    ZoneThresholdDTO getThresholds(@PathVariable("id") String id);
}
