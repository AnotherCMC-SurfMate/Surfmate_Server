package cmc.surfmate.health;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HealthController.java
 *
 * @author jemlog
 */
@RestController
public class HealthController {

    @GetMapping("health")
    public String health()
    {
        return "health";
    }

}
