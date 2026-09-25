#

Known issues that have been confirmed by testing but not yet fixed. Each entry has enough detail to reproduce and fix without re-investigating.

---

## 1. Wrong-port requests to `/api/v1/books` or `/api/v1/users` return a raw `500` with a leaked stack trace

**Status:** Open
**Impact:** High — this is the project's core feature (port-based routing), and its failure mode is currently the worst possible one: an unhandled exception leaking internals to the client.
**Affects:** `PortFilter`, when `fi.ishtech.practice.springboot.multiport.additional-ports=true`

### Description

When additional ports are enabled, `/api/v1/books/**` is only supposed to be reachable on the book port and `/api/v1/users/**` only on the user port. `PortFilter` enforces this with `Assert.isTrue(...)`, which throws `IllegalArgumentException` on a mismatch. That exception is never caught, so it propagates all the way to Spring Boot's default error handler and comes back as `500 Internal Server Error` with a full stack trace in the response body — instead of a clean `404 Not Found` (the endpoint genuinely doesn't exist on that port) or similar.

Confirmed both via local `./mvnw spring-boot:run` and the built Docker image.

There is currently **no test coverage at all** for `PortFilter` — no test file exists for it, which is presumably why this was never caught.

`README.md`'s "Ports" section documents which port each API group uses, but says nothing about what happens on a wrong-port request.

### Steps to reproduce

```sh
# with additional-ports=true, book-port=8081, user-port=8082
curl http://localhost:8080/api/v1/books -H "Authorization: Bearer <ACCESS_TOKEN>"
```

Expected: a clean `404` (or similar), no stack trace.
Actual: `500`, body contains `"Invalid Port 8080, use 8081"` and a full Java stack trace.

### Suggested fix

1. In `PortFilter`, reject wrong-port requests by writing a clean error response directly (or by throwing something mapped to `404` by an `@ExceptionHandler`) instead of letting `IllegalArgumentException` propagate.
2. Add tests for `PortFilter`: correct port allowed, wrong port rejected with the new clean status code, other endpoints (e.g. `/api/v1/auth/**`) unaffected by the restriction.
3. Once fixed, document the expected wrong-port response in `README.md` or `CURL-INFO.md`.

---

## 2. A GitHub release with a JDK-variant tag (`vx.y.z-jdkNN`) doesn't publish a Docker image

**Status:** Open. Recorded in full in springboot-books-app: [`KNOWN-ISSUES.md`, issue 1](https://github.com/muneer2ishtech/springboot-books-app/blob/dev/KNOWN-ISSUES.md).
**Impact:** Medium — a JDK-variant release publishes no Docker image, and nothing fails visibly.
**Affects:** `.github/workflows/cicd.yml`, trigger `on.push.tags: ['v[0-9]+.[0-9]+.[0-9]+']`, the same as in springboot-books-app.

The description, steps to reproduce, likely cause and suggested fix are in the linked entry; apply the same fix here, and verify it as described there, with this repo's image.
