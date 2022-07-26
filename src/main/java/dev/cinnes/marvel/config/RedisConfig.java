package dev.cinnes.marvel.config;

import dev.cinnes.marvel.model.MarvelCharacter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public ReactiveRedisTemplate<String, MarvelCharacter> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        final var serializer = new Jackson2JsonRedisSerializer<>(MarvelCharacter.class);
        RedisSerializationContext.RedisSerializationContextBuilder<String, MarvelCharacter> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        final var context = builder.value(serializer).build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
