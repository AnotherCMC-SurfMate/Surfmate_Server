package cmc.surfmate.health;

import cmc.surfmate.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController.java
 *
 * @author jemlog
 */
@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<CommonResponse> getHealth()
    {
        return ResponseEntity.ok(new CommonResponse(200,"성공"));
    }
    @GetMapping("/")
    public String health()
    {
        return "health";
    }



}
