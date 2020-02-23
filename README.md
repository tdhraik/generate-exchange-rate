# Calculate exchange rate for 1 Bitcoin to USD

* curl -H "Content-Type: application/json" -X POST -d '{"name":"cron.job.exchange.rate.latest", "value":"0 */10 * * * *"}' http://localhost:8193/actuator/env
    // curl -X POST localhost:8080/actuator/refresh -d {} -H "Content-Type: application/json"


## generate-exchange-rate-service 

-   This service is responsible for 