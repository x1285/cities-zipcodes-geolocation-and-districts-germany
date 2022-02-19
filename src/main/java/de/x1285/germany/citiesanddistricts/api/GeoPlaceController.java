package de.x1285.germany.citiesanddistricts.api;

import de.x1285.germany.citiesanddistricts.model.GeoPlace;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@RestController
public interface GeoPlaceController {

    @GetMapping(value = "/geo/search/{term}", produces = "application/json")
    ResponseEntity<Set<GeoPlace>> getSuggestionsForNameOrCode(@NotNull @PathVariable("term") String term);

    @GetMapping(value = "/geo/name/{name}", produces = "application/json")
    ResponseEntity<List<GeoPlace>> getSuggestionsForName(@NotNull @PathVariable("name") String name);

    @GetMapping(value = "/geo/code/{code}", produces = "application/json")
    ResponseEntity<List<GeoPlace>> getSuggestionsForCode(@NotNull @PathVariable("code") String code);

}
