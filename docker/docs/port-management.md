# Fluent port management docs

## Dev

| Service           | Port  |
|-------------------|-------|
| —> Microservices  | ————  |
| api-entrypoint    | 17000 |
| auth-service      | 17001 |
| —> Databases      | ————  |
| api-entrypoint-pg | 17100 |
| auth-service-pg   | 17101 |
| —> Cache          | ————  |
| redis             | 17200 |


## Prod

| Service           | Port |
|-------------------|------|
| —> Microservices  | ———— |
| api-entrypoint    | 7000 |
| auth-service      | 7001 |
| —> Databases      | ———— |
| api-entrypoint-pg | 7100 |
| auth-service-pg   | 7101 |
| —> Cache          | ———— |
| redis             | 7200 |
