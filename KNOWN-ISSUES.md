#

Known issues that have been confirmed by testing but not yet fixed. Each entry has enough detail to reproduce and fix without re-investigating.

---

## 1. `PUT /api/v1/users/{userId}` clears fields absent from the request body

See ishtech-springboot-jwtauth [`KNOWN-ISSUES.md`, issue 1](https://github.com/IshTech/ishtech-springboot-jwtauth/blob/dev/KNOWN-ISSUES.md).

In this application the update fails with `500` instead of the silent `200` described there: H2 rejects the cleared `default_lang` (`NULL not allowed for column "DEFAULT_LANG"`).
