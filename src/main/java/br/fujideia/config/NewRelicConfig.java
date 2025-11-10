package br.fujideia.config;

import com.newrelic.api.agent.NewRelic;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;



@Configuration
public class NewRelicConfig {

    @PostConstruct
    public void init() {
        // Initialize New Relic agent
        try {
            NewRelic.getAgent().getConfig().getValue("app_name");
        } catch (Exception e) {
            System.err.println("Failed to initialize New Relic: " + e.getMessage());
        }
    }
}
