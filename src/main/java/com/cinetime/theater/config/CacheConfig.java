package com.cinetime.theater.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SocketOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class CacheConfig implements CachingConfigurer {

//    @Value("${spring.cache.redis.time-to-live}")
//    private long redisTimeToLive;
//
//    @Value("${spring.data.redis.timeout}")
//    private Duration redisCommandTimeout;
//
//    @Autowired
//    private  RedisProperties redisProperties;
//
//    @Bean
//    protected LettuceConnectionFactory redisConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .master(redisProperties.getSentinel().getMaster());
//        redisProperties.getSentinel().getNodes().forEach(s -> sentinelConfig.sentinel(s, redisProperties.getPort()));
//        sentinelConfig.setPassword(RedisPassword.of(redisProperties.getPassword()));
//
//        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
//                .commandTimeout(redisCommandTimeout).readFrom(ReadFrom.REPLICA_PREFERRED).build();
//        return new LettuceConnectionFactory(sentinelConfig, clientConfig);
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashKeySerializer(new GenericToStringSerializer<>(Object.class));
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//        return redisTemplate;
//    }
//
//    @Override
//    @Bean
//    public RedisCacheManager cacheManager() {
//        return RedisCacheManager.builder(this.redisConnectionFactory()).cacheDefaults(this.cacheConfiguration())
//                .build();
//    }
//
//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofMinutes(redisTimeToLive))
//                .disableCachingNullValues()
//                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));
//    }
//
//    @Override
//    public CacheErrorHandler errorHandler() {
//        return new CacheErrorHandler() {
//            @Override
//            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
//                log.info("Failure getting from cache: " + cache.getName() + ", exception: " + exception.toString());
//            }
//
//            @Override
//            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
//                log.info("Failure putting into cache: " + cache.getName() + ", exception: " + exception.toString());
//            }
//
//            @Override
//            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
//                log.info("Failure evicting from cache: " + cache.getName() + ", exception: " + exception.toString());
//            }
//
//            @Override
//            public void handleCacheClearError(RuntimeException exception, Cache cache) {
//                log.info("Failure clearing cache: " + cache.getName() + ", exception: " + exception.toString());
//            }
//        };
//    }

    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory()
    {
        final SocketOptions socketOptions = SocketOptions.builder().connectTimeout(Duration.ofSeconds(10)).build();
        ClientOptions clientOptions = ClientOptions.builder()
                .socketOptions(socketOptions)
                .autoReconnect(true)
                .disconnectedBehavior(ClientOptions.DisconnectedBehavior.REJECT_COMMANDS)
                .build();

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(10))
                .clientOptions(clientOptions).build();

        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
        return new LettuceConnectionFactory(redisStandaloneConfiguration, clientConfig);

    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Configure the default expiration time for cache 'theaters' to 1 day
        Map<String, Duration> expires = new HashMap<>();
        expires.put("theaters", Duration.ofDays(1));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(RedisCacheConfiguration.defaultCacheConfig())

                .withInitialCacheConfigurations(expires.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                entry -> RedisCacheConfiguration.defaultCacheConfig().entryTtl(entry.getValue()))))
                .build();
    }
}
