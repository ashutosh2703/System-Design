# Sugarfit architecture notes (context for the System Design journey)

Surveyed on 2026-09-04 from the repos on ~/Desktop. Use this as the "live example" map while reading Alex Xu Vol 1.

## The big picture

```
Browsers / mobile app
        |
   DNS + CDN (CloudFront for images, static Astro sites)
        |
   Istio ingress (GKE, sf-prod-gke-cluster)
        |
   API gateways (BFF layer)
     - curefit-api        Node/TS + InversifyJS   (legacy, min 3 / max 55 pods)
     - curefit-api-java   Java/Spring, "cfapi v2" (min 5 / max 65 pods)
        |
   Domain microservices (all *.prod.sugarfit.internal / *.production.cure.fit.internal)
     - central-health-store (CHS)  Java  health data, CGM ingestion
     - ambrosia                    Java  fitness / medicine / sleep / logging
     - telematia (albus)           Java  consults, bookings, orders
     - sugarfit-subscription-manager (sms) Java  subscriptions, coach tasks
     - lms                         Java  leads, webinars, support
     - synapse                     Node  consult flow, video, PDF generation
     - xray                        Node  internal admin console
        |
   Data + async
     - MySQL primary + read replica (CHS, sms, telematia, lms)
     - MongoDB (ambrosia, lms, curefit-api)
     - Redis clusters (carefit-cache, platforms-segmentation-cache, ~10 named clusters in cfapi)
     - AWS SQS / SNS everywhere for async (CGM readings, leads, payouts, PDF jobs)
     - S3 (health files, invoices, consult recordings)
   Observability: Datadog APM + RUM, Prometheus via Spring Actuator, Rollbar, Sentry, Grafana/Loki
   Deploy: Dockerfile -> Jenkins -> Helm values-{env}.yaml -> ArgoCD ApplicationSet -> GKE, KEDA autoscaling
```

## Repo cheat sheet

| Repo | What | Stack | Data | Async |
|---|---|---|---|---|
| curefit-api | API gateway to all upstream services | Node/TS | MySQL, Mongo, ~10 Redis | SQS, SNS, in-process cron |
| curefit-api-java | Same gateway, v2, Node to Java migration in progress | Java 21 / Spring Boot 2.7 | MySQL, Mongo, ~10 Redis | SQS, SNS |
| central-health-store | Health data aggregation, CGM readings + alerts | Java 21 | MySQL + replica, Redis (Redisson), S3 | ~10 SQS queues, 3 SNS topics |
| ambrosia | Fitness, medicine, sleep, logging domains | Java 21 | Mongo, Redis, MySQL (logging) | none found |
| telematia | Teleconsults, bookings, orders, agent assignment | Java 11 / Spring Boot 2.1 | MySQL + replica, Redis + Redisson locks, S3 | SQS incl. FIFO |
| sugarfit-subscription-manager | Subscriptions, coach tasks, master filter engine | Java 11 | MySQL + replica, Redis | 6 SQS consumers, cron |
| lms | Leads, webinars, Freshdesk support | Java 21 | MySQL, Mongo Atlas, Redis | SQS incl. FIFO leads queue |
| synapse | Consults, 100ms video, PDF via Playwright | Node/TS | Redis, S3 | SQS PDF queues, separate CRON worker pod |
| xray | Internal admin UI | Node 12 / React | Redis session store, S3 | none |
| cfapi-auth | Tiny shared CORS library | Java | none | none |
| carefit-configs | git-crypt encrypted configs for ~35 services | none | none | none |
| sf-web, sugarfit-glp-website, weightloss-care, sf-care, zencare, sf-web-bau | Static Astro marketing / storefront sites hitting api.prod.sugarfit.com | Astro + React | none | none |
| sugarfit-website | Legacy Next.js 11 site | Next.js | none | Firebase Functions |
| loki | Webinar checkout micro-frontend on Vercel | Next.js 13 | none | none |
| sugarfit-chat-app | React chat widget SDK, polling every 5s | React lib | none | none |

Personal / learning repos, not Sugarfit: subscription-system, docker-testapp, journalApp, quiz-app, astro-*, Java, TypeScript, ts-react, git-crash-course.

## Files worth opening per Chapter 1 concept

- Read replica: `central-health-store/central-health-store-core/src/main/resources/application-prod.properties` line 9, `sugarfit-subscription-manager/values-sf-prod.yaml` lines 75-77
- Cache with TTL: same CHS properties file, `redis.ttl=14400` (4h) and `spring.redis.ttl=300`
- Rate limiter: `curefit-api-java/curefit-api-core/src/main/java/com/curefit/cfapi/interceptor/ApiRateLimitInterceptor.java`, `curefit-api/src/config/middlewares/ipRateLimitMiddleware.ts`
- Autoscaling (horizontal scaling): `synapse/values-sf-prod.yaml` lines 190-207, `ambrosia/values-prod.yaml` lines 72-84
- Stateless vs stateful web tier: `xray/src/server/server.ts` line 308 (session moved out of the pod into Redis)
- CDN: `sugarfit-glp-website/src/utilities/s3.ts`, `zencare/src/utils/CloudfrontUtils.ts`
- Message queues: CHS `application-prod.properties` SQS section, `sugarfit-subscription-manager/.../consumers/`
- Observability: `central-health-store/.datadog/service.datadog.yaml`, `curefit-api-java/.datadog/service.datadog.yaml`
- Multi-region config: `curefit-api-java/curefit-api-core/src/main/resources/region/ap-south-1/`

## Progress log

- 2026-09-04: Chapter 1 taught with live examples. Homework: find where CHS routes reads to the replica (not in chs code, likely curefit-commons), read one SQS consumer end to end, draw the architecture from memory.
  Findings to raise at work: merge-conflict markers left in central-health-store/values-sf-prod.yaml (~line 231); hardcoded admin token in 'Loki migration script/migrate_webinars.js'.

## Resources in this folder (checked 2026-09-04)

- `System Design Interview by Alex Xu.pdf` — Volume 1, 269 pages, text-searchable. Primary textbook.
- `ByteByteGo-Big-Archive-System-Design-2023.pdf` — 344 pages of ByteByteGo newsletter one-pagers. Supplement only, use for quick visual refreshers per topic.
- `system-design-interview-an-insiders-guide-volume-2.pdf` — NOT the real book. 30 scanned pages with a watermark, no text. Real Vol 2 is ~440 pages. Replace before month 6.
- Still missing: Designing Data-Intensive Applications (Kleppmann), needed from month 2.
- 2026-09-06: Chapter 2 (estimation) done with a CGM ingestion worked example. Open question for work: where do BG readings actually land? CHS consumes prod-sugarfit-chs-bg-readings but no readings table exists in CHS MySQL migrations; likely forwarded to metrics.production.cure.fit.internal or nest. Homework: confirm active CGM user count and sensor sync cadence, then redo the numbers.
