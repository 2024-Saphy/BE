package saphy.saphy.global;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheck {

    @GetMapping
    public HealthStatus checkHealth(){
        return new HealthStatus("OK");
    }

    static class HealthStatus {
        private String status;

        public HealthStatus(String status) {
            this.status = status;
        }
    }
}
