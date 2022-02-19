package de.x1285.germany.citiesanddistricts;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String CACHE_NAME_GEO_PLACES = "GeoPlaces";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(CACHE_NAME_GEO_PLACES);
    }
}
