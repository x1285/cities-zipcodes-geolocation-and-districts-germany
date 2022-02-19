package de.x1285.germany.citiesanddistricts.api.impl;

import de.x1285.germany.citiesanddistricts.api.GeoPlaceController;
import de.x1285.germany.citiesanddistricts.model.GeoPlace;
import de.x1285.germany.citiesanddistricts.service.GeoPlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GeoPlaceControllerImpl implements GeoPlaceController {

    @Autowired
    private GeoPlaceService service;

    @Override
    public ResponseEntity<Set<GeoPlace>> getSuggestionsForNameOrCode(String term) {
        Set<GeoPlace> result = service.find(term);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<GeoPlace>> getSuggestionsForName(String name) {
        List<GeoPlace> result = service.findForName(name);
        return ResponseEntity.ok(result);
    }

    @Override
    public ResponseEntity<List<GeoPlace>> getSuggestionsForCode(String code) {
        List<GeoPlace> result = service.findForCode(code);
        return ResponseEntity.ok(result);
    }
}
