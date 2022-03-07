package de.x1285.germany.citiesanddistricts.service;

import de.x1285.germany.citiesanddistricts.model.GeoPlace;
import de.x1285.germany.citiesanddistricts.model.GeoPlaceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class GeoPlaceService {

    @Autowired
    GeoPlaceCacheService cache;

    public GeoPlace findById(Long id) {
        return cache.getAll().get(id);
    }

    public Set<GeoPlace> find(String term) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Future<List<GeoPlace>> byName = executor.submit(() -> findForName(term));
        Future<List<GeoPlace>> byCode = executor.submit(() -> findForCode(term));
        Set<GeoPlace> geoPlaces = new HashSet<>();
        try {
            geoPlaces.addAll(byName.get());
            geoPlaces.addAll(byCode.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Failure during find", e);
        }
        return geoPlaces;
    }

    public List<GeoPlace> findForName(String name) {
        String lcName = name.toLowerCase();
        return cache.getAll().values().stream()
                .filter(x -> x.getName().toLowerCase().startsWith(lcName))
                .sorted(getGeoPlaceComparator())
                .collect(Collectors.toList());
    }

    public List<GeoPlace> findForCode(String code) {
        final String regex = "(^|,)(" + Pattern.quote(code) + "[a-zA-Z0-9]*?)(,?)";
        Pattern regexPattern = Pattern.compile(regex);
        return cache.getAll().values().stream()
                .filter(x -> x.getZipcodes() != null)
                .filter(x -> regexPattern.matcher(x.getZipcodes()).find())
                .sorted(getGeoPlaceComparator())
                .collect(Collectors.toList());
    }

    public static Comparator<? super GeoPlace> getGeoPlaceComparator() {
        return (Comparator<GeoPlace>) (o1, o2) -> {
            if (Objects.equals(o1.getId(), o2.getId())) {
                return 0;
            }
            int population = 0;
            if (o1.getPopulation() != null && o2.getPopulation() != null) {
                population = o2.getPopulation().compareTo(o1.getPopulation());
            } else if (o1.getPopulation() != null) {
                return -1;
            } else if (o2.getPopulation() != null) {
                return 1;
            }
            if (population == 0) {
                final GeoPlaceType o1Type = o1.getType();
                final GeoPlaceType o2Type = o2.getType();
                if (o1Type != o2Type) {
                    return Integer.compare(o1Type.getCardinality(), o2Type.getCardinality());
                }
                if (o1.getParentId() == null) {
                    return o2.getParentId() != null ? 1 : -1;
                }
                if (o2.getParentId() == null) {
                    return o1.getParentId() != null ? -1 : 1;
                }
                return o2.getLevel().compareTo(o1.getLevel());
            } else {
                return population;
            }
        };
    }
}
