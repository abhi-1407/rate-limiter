# Distributed Rate Limiter using Redis Sorted Set + Lua Script

## Overview

This project implements a distributed sliding window rate limiter using:

- Java 21
- Spring Boot
- Redis
- Lua Scripting

The rate limiter allows a configurable number of requests within a sliding time window.

Example:

- Limit: 100 requests
- Window: 60 seconds

text Request 1   -> Allowed Request 2   -> Allowed ... Request 100 -> Allowed Request 101 -> Rejected

---

## Why Sliding Window?

A Fixed Window rate limiter suffers from burst traffic issues.

Example:

text 10:00:59 -> 100 requests 10:01:00 -> 100 requests

Effectively:

text 200 requests within 1 second

Sliding Window solves this by considering only requests within the last N seconds.

---

## Why Redis Sorted Set?

Redis Sorted Sets provide:

- Timestamp-based ordering
- O(log N) insertion
- Efficient cleanup of expired requests

Example:

text rate_limit:user:123  1717910000001 1717910002000 1717910005000 1717910010000

Each request is stored as:

text Score  = Timestamp Member = Timestamp:UUID

---

## Why Lua Script?

Without Lua:

java count = redis.zcard(key);  if(count < limit){     redis.zadd(...); }

Multiple application instances can cause race conditions.

Example:

text Server A -> count = 99 Server B -> count = 99

Both requests are allowed.

Result:

text 101 requests

Limit exceeded.

Lua executes atomically inside Redis.

text Cleanup Count Insert

All happen as a single operation.

---

## High Level Design

text Client    |    v Spring Boot API    |    v RateLimiterService    |    v Lua Script    |    v Redis Sorted Set

---

## Request Flow

1. User sends request.
2. Lua script removes expired timestamps.
3. Current request count is calculated.
4. If count >= limit:
    - Reject request.
5. Else:
    - Insert current timestamp.
    - Allow request.

---

## Redis Data Structure

Key:

text rate_limit:user:123

Sorted Set:

text Score              Member  1717910000001      1717910000001:uuid1 1717910005000      1717910005000:uuid2 1717910010000      1717910010000:uuid3

---

## Time Complexity

Insertion:

text O(log N)

Cleanup:

text O(log N + removedElements)

Count:

text O(1)

Overall:

text O(log N)

---

## Technologies Used

- Java 21
- Spring Boot 3
- Redis 7
- Lua
- Docker

---

## Future Improvements

- Token Bucket implementation
- Leaky Bucket implementation
- Per API limits
- Dynamic limits from database
- User tiers (Free/Premium)
- Redis Cluster support
- Metrics using Prometheus
- Grafana dashboards

---

## Running Locally

Start Redis:

bash docker-compose up -d

Run Spring Boot:

bash mvn spring-boot:run

Test:

bash curl "http://localhost:8080/api/rate-limit?userId=123"

---

## Sample Response

Allowed:

json {   "allowed": true }

Rejected:

json {   "allowed": false,   "message": "Rate limit exceeded" } 