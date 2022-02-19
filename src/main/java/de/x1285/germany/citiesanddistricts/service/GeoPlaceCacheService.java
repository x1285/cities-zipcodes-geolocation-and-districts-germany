package de.x1285.germany.citiesanddistricts.service;

import de.x1285.germany.citiesanddistricts.CacheConfiguration;
import de.x1285.germany.citiesanddistricts.data.GeoPlaceDataImporter;
import de.x1285.germany.citiesanddistricts.model.GeoPlace;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class GeoPlaceCacheService {

    @Cacheable(CacheConfiguration.CACHE_NAME_GEO_PLACES)
    public LinkedHashMap<Long, GeoPlace> getAll() {
        return GeoPlaceDataImporter.readData();
    }
}
