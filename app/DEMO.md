# Demo: release log (Spring Boot)

**Framework:** Spring Boot 3.5 on Java 21, with Maven. **Moving parts locally:** none. Releases are held in memory and vanish on restart.

Small but real enough to push through Artifact Registry, Binary Authorization and Cloud Deploy. `/version` returns the commit the image was built from, so after a deploy you can check the right build is running.

## Run it

```bash
cd app
mvn test                  # first run downloads dependencies, which takes a few minutes
mvn spring-boot:run       # http://localhost:8080
```

## Try it

```bash
curl -X POST localhost:8080/releases -H 'Content-Type: application/json' \
     -d '{"service":"api","version":"1.2.0"}'
curl localhost:8080/releases                    # [{"service":"api","version":"1.2.0"}]
curl localhost:8080/version                     # local
GIT_SHA=abc1234 mvn spring-boot:run             # /version now returns abc1234
```

## Read the code in this order

1. `ReleasesApplication`: the entry point.
2. `ReleaseController`: three endpoints, a `List` as storage, `@Value("${GIT_SHA:local}")` for the build-time setting.
3. `ReleaseControllerTest`: posts a release then reads it back.

## Docker

```bash
docker build -t releases . && docker run -p 8080:8080 -e GIT_SHA=abc1234 releases
```

In the pipeline, pass the commit as a build/deploy env var so `/version` matches the attested image.
