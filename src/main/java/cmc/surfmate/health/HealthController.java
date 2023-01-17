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

    @GetMapping("/")
    public String health()
    {
        return "health";
    }

    @GetMapping("/health")
    public String getHealth()
    {
        return "health check";
    }

}
