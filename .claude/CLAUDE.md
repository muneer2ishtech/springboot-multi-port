<!-- Repo-specific instructions. The shared IshTech rules live in .claude/rules/ and are identical across repos; don't put repo-specific content there. -->
# springboot-multi-port

The owner's standing instructions are in `.claude/rules/` (`owner-workflow.md`, `git-and-branches.md`, `versions-and-releases.md`, `build-and-test.md`, `build-tooling.md`, `documentation.md`, `repositories.md`). They apply to every task in this repo. This file adds only what is specific to this repo.

## About this repo
- It's a runnable Spring Boot application, so it has test Levels 1, 2 and 3.
- It's built with Maven (`pom.xml`); use `./mvnw`.
- Nothing depends on `springboot-multi-port`, so dependent tests (`rules/build-and-test.md`, section "Dependent tests") don't apply.
- Upstream libraries: `ishtech-springboot-jwtauth` and `ishtech-validations-java`, and their own upstream libraries (`rules/repositories.md`). Their versions are declared in `pom.xml`; these are the upstream SNAPSHOTs that the test Level 3 precondition refers to.

## Read the doc before doing the thing
The docs are the source of truth. Don't guess commands: open the matching file and section first, and follow its links.

| Before you... | Read |
|---|---|
| work out what the application is and its tech stack | `README.md`, the introduction and sections "Tech stack", "Design" and "Ports" |
| run test Level 1 (build with tests) | `README.md`, section "Build and Run", subsection "Local Maven Build" |
| run test Level 2 (run the app with Maven) | `README.md`, section "Build and Run", subsection "Local Maven Run" (single port, or additional ports as section "Ports" describes). The database is H2: section "DB", subsection "Local" |
| run test Level 3 (run with Docker compose) | `DOCKER-BUILD.md`, section "Run with docker compose" |
| run the API tests (part of Levels 2 and 3) | `CURL-INFO.md` (every flow; the auth flows are in the ishtech-springboot-jwtauth doc linked under "Auth APIs"); endpoint list in `API-INFO.md`. With additional ports enabled, each API group has its own port: `README.md`, section "Ports" |
| touch the database for any other reason | `README.md`, section "DB" |
| change the version or anything release-related, or check what CI enforces | `.github/workflows/cicd.yml` |
| report or fix a bug | `KNOWN-ISSUES.md` first, it may already be recorded |

If a doc is missing, wrong or unclear, fix the doc (see `rules/documentation.md`) instead of working around it.
