package lk.ijse.agms.crop_service.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "zone-service")
public interface ZoneClient {

    @GetMapping("/api/v1/zones/{id}")
    ResponseEntity<Object> getZoneById(@PathVariable("id") String id);
}
