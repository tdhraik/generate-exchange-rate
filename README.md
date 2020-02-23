# generate-exchange-rate-service

##### Steps to run locally - 

-   Just execute `docker-compose up` and it should do !!
-   To access end points please read https://github.com/tdhraik/get-exchange-rate

##### What the service does ?

This service is a type of command service [CQRS]

- On startup the application calls external exchange rate api to fetch historical rates for the last 30 days.

- Fetches the exchange rates from external api (http://api.coinlayer.com/api) for latest time and historical dates as well.
  This api (in free version) doesn't take a range of date (for historical usecase) as parameter instead takes one date and return the exchange rate on
  that day.
  
- There are two jobs running to fetch the latest and historical data. The latest job is completely configurable from external
  [git repo](https://github.com/tdhraik/configurations). 

- So a cron expression could be set on the fly and the scheduler will consider the updated cron from next execution. There 
  is a spring cloud configuration server artifact that reads the configurations from the git repo.
  
- The historical data job runs every night at 3 AM and fetches the data for the last day.

- Both the jobs uses *kafka* for updating the other systems ( [get-exchange-rate-service](https://github.com/tdhraik/get-exchange-rate) ).

- *exchange_rate_latest* and *exchange_rate_historic* are the two separate topics to push latest and historical messages.     
  

