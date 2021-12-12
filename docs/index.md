[![Build](https://github.com/fintecy/oxr-client/actions/workflows/gradle.yml/badge.svg?branch=main)](https://github.com/fintecy/oxr-client/actions/workflows/gradle.yml)

# Open Exchange Rates (OXR) Client

Async client with CompletableFutures based on new HttpClient (java 11+)

## Usage
### Simple client creation
```
OxrApi client = OxrClient.authorize("YOUR_APP_TOKEN");
```
### Complex client configuration
```
var client = oxrClient()
    .useClient(HttpClient.newBuilder()
        .followRedirects(HttpClient.Redirect.ALWAYS)
        .priority(10)
        .connectTimeout(Duration.ofMillis(500))
        .executor(Executors.newSingleThreadExecutor())
        .build())
    .with(CircuitBreaker.ofDefaults())
    .with(RateLimiter.smoothBuilder(Duration.ofMillis(100))
        .build())
    .with(RetryPolicy.ofDefaults())
    .with(Timeout.of(Duration.ofMillis(400)))
    .useAuthHeader(true) -- use Auth header vs query param
    .mapper(new ObjectMapper()-- to be able to configure mapper
        .registerModule(new JavaTimeModule())) 
    .authWith("YOUR_APP_TOKEN")
    .rootPath("https://openexchangerates.org/api") -- just to use stub in tests
    .build();
```

### Get remaining requests count
```
var client = OxrClient.authorize("YOUR_APP_TOKEN");
int requestsRemaining = client
    .usage()
    .thenApply(UsageResponse::getData)
    .thenApply(UsageData::getUsage)
    .thenApply(Usage::getRequestsRemaining)
    .get(5, TimeUnit.SECONDS);
```

### Get currency name
```
var client = OxrClient.authorize("YOUR_APP_TOKEN");
int currencyName = client
    .currencies()
    .thenApply(c -> c.getCurrencyName("GBP")) // British Pound Sterling
    .get(200, TimeUnit.MILLISECONDS);
```

### Convert balance
```
var amount = 100.5;
var from = Currency.getInstance("GBP");
var to = Currency.getInstance("EUR");
var client = OxrClient.authorize("YOUR_APP_TOKEN");
int eurBalance = client
//    .convert(amount, from, to)
    .convert(convertAmount(amount)
            .from(from)
            .to(to)
            .enablePrettyPrint(true)
            .build())
    .get(200, TimeUnit.MILLISECONDS);
```

## Dependencies
- Java 11+
- FailSafe
- Slf4j api
- Jackson (databind, datatype-jsr310)
- WireMock (tests)
- Junit5 (tests)

## Author
Anton Batiaev <anton@batiaev.com>
