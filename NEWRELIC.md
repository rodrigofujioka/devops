# New Relic Configuration

This guide explains how to set up and configure New Relic APM for the DevOps application.

## Prerequisites

1. A New Relic account (sign up at [https://newrelic.com/](https://newrelic.com/))
2. Java 17 or later
3. Maven

## Setup Instructions

### 1. Get Your New Relic License Key

1. Log in to your New Relic account
2. Go to **Account settings** > **API keys**
3. Copy your **License key**

### 2. Configure New Relic

1. Open the configuration file: `src/main/resources/newrelic/newrelic.yml`
2. Replace `YOUR_NEW_RELIC_LICENSE_KEY` with your actual New Relic license key
3. (Optional) Update the application name and other settings as needed

### 3. Build and Run the Application

```bash
# Build the application
mvn clean install

# Run the application with New Relic agent
mvn spring-boot:run
```

### 4. Verify the Installation

1. Log in to your New Relic account
2. Go to **APM** > **Applications**
3. You should see your application listed with the name you specified in the configuration

## Key Features

- **Performance Monitoring**: Track response times, throughput, and error rates
- **Distributed Tracing**: See the full request flow across services
- **Error Analytics**: Get detailed error reports and stack traces
- **Database Monitoring**: Monitor SQL query performance
- **JVM Metrics**: Track memory usage, garbage collection, and thread activity

## Custom Instrumentation

The application includes custom instrumentation for:

- Slow database queries
- Performance-intensive operations
- Error tracking

## Troubleshooting

If you don't see your application in New Relic:

1. Verify your license key is correct
2. Check the logs in `logs/newrelic_agent.log`
3. Ensure your firewall allows outbound connections to New Relic's servers
4. Verify that the New Relic Java agent is being loaded (check the application startup logs)

## Additional Resources

- [New Relic Java Agent Documentation](https://docs.newrelic.com/docs/agents/java-agent/)
- [Troubleshooting the Java Agent](https://docs.newrelic.com/docs/agents/java-agent/troubleshooting/)
- [New Relic Support](https://support.newrelic.com/)
