local key = KEYS[1]

local currentTime = tonumber(ARGV[1])

local windowSize = tonumber(ARGV[2])

local limit = tonumber(ARGV[3])

local requestId = ARGV[4]

local minTime = currentTime - windowSize

redis.call(
    'ZREMRANGEBYSCORE',
    key,
    '-inf',
    minTime
)

local currentCount =
    redis.call(
        'ZCARD',
        key
    )

if currentCount >= limit then
    return 0
end

redis.call(
    'ZADD',
    key,
    currentTime,
    requestId
)

redis.call(
    'PEXPIRE',
    key,
    windowSize
)

return 1