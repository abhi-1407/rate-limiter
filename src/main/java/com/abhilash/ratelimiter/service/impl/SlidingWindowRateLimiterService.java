@Service
public class SlidingWindowRateLimiterService
        implements RateLimiterService {

    private static final long WINDOW_SIZE_MS =
            60_000;

    private static final long LIMIT =
            100;

    private final RateLimiterRepository repository;

    public SlidingWindowRateLimiterService(
            RateLimiterRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public void validateRequest(
            String userId
    ) {

        String key =
                RedisKeys.RATE_LIMIT_PREFIX
                        + userId;

        long currentTime =
                Instant.now()
                        .toEpochMilli();

        String requestId =
                currentTime
                        + ":"
                        + UUID.randomUUID();

        boolean allowed =
                repository.allowRequest(
                        key,
                        currentTime,
                        WINDOW_SIZE_MS,
                        LIMIT,
                        requestId
                );

        if (!allowed) {
            throw new RateLimitExceededException();
        }
    }
}