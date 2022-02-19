package de.x1285.germany.citiesanddistricts.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(of = {"id", "name"})
public class GeoPlace {

    private Long id;
    private String ags;
    private String ascii;
    private String name;
    private Double latitude;
    private Double longitude;
    private String amt;
    private String zipcodes;
    private String phoneAreaCode;
    private Long population;
    private Double area;
    private String carLicensePlateCode;
    private String initialType;
    private GeoPlaceType type = GeoPlaceType.UNBEKANNT;
    private Integer level;
    private Long parentId;

    private final List<GeoPlace> children = new ArrayList<>();

    public void addChild(GeoPlace child) {
        children.add(child);
    }

}
