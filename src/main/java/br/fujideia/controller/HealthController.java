package br.fujideia.controller;

import com.newrelic.api.agent.NewRelic;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/health")
public class HealthController {

    @GetMapping
    public Map<String, Object> health() {
        Map<String, Object> healthInfo = new HashMap<>();
        healthInfo.put("status", "UP");
        
        try {
            // Check if New Relic agent is available
            com.newrelic.api.agent.NewRelic.getAgent();
            
            // Get configuration
            com.newrelic.api.agent.Config config = NewRelic.getAgent().getConfig();
            
            // Check if agent is enabled
            Boolean agentEnabled = config.getValue("agent_enabled", Boolean.FALSE);
            
            if (agentEnabled != null && agentEnabled) {
                String appName = config.getValue("app_name");
                String agentVersion = config.getValue("version");
                
                Map<String, Object> newRelicInfo = new HashMap<>();
                newRelicInfo.put("status", "ACTIVE");
                newRelicInfo.put("appName", appName);
                newRelicInfo.put("agentVersion", agentVersion);
                newRelicInfo.put("configFile", config.getValue("config_file_path"));
                
                healthInfo.put("newRelic", newRelicInfo);
            } else {
                Map<String, Object> errorInfo = new HashMap<>();
                errorInfo.put("status", "AGENT_DISABLED");
                errorInfo.put("configFile", config.getValue("config_file_path"));
                errorInfo.put("agentEnabled", agentEnabled);
                healthInfo.put("newRelic", errorInfo);
            }
        } catch (Exception e) {
            Map<String, Object> errorInfo = new HashMap<>();
            errorInfo.put("status", "ERROR");
            errorInfo.put("message", e.getMessage());
            healthInfo.put("newRelic", errorInfo);
        }
        
        return healthInfo;
    }
}
