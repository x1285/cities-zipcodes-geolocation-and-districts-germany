package de.x1285.germany.citiesanddistricts.model;

import lombok.Getter;

@Getter
public enum GeoPlaceType {

    ORT("Ort", "Stadt", "kreisfreie Stadt", "ehem. Stadt", "ehem. Gemeinde", "Dom- und Kaiserstadt",
            "Kurort, Stadt", "Höhenluftkurort", "Bergstadt", "Freie und Hansestadt", "Schöfferstadt", "Liebenbachstadt",
            "Hansestadt", "Sickingenstadt", "Universitätsstadt", "Loreleystadt", "ehem. Ort", "Brüder-Grimm-Stadt",
            "Wissenschaftsstadt", "Gemeinde", "aufgelöste Gemeinde", "Dorf", "Markt", "Kreisangehörige Gemeinde",
            "Kurort", "Landeshauptstadt", "Karolingerstadt", "Ortschaft", "Kreisfrei Stadt", "Gemeinde", "Lutherstadt", "Gneisenaustadt, Stadt", "documenta-Stadt"),
    KREIS("Kreis", "Landkreis", "Amt", "Kreisstadt", "Stadtkreis"),
    ORTSTEIL("Ortsteil", "Stadtviertel", "Stadtteil (ehem. Stadt)", "Stadtteil", "Gemarkung",
            "Verwaltungsbezirk", "Quartier", "Flecken", "Ortsbezirk", "äußerer Stadtbezirk", "Stadtteil von Roßlau",
            "Statistischer Bezirk", "Schachdorf"),
    UNBEKANNT(),
    BUNDESLAND("Bundesland");

    private final String name;
    private final String[] aliases;

    GeoPlaceType() {
        this.name = null;
        this.aliases = new String[]{};
    }

    GeoPlaceType(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public static GeoPlaceType getFor(String nameOrAlias) {
        for (GeoPlaceType value : values()) {
            if (value.is(nameOrAlias)) {
                return value;
            }
        }
        return GeoPlaceType.UNBEKANNT;
    }

    public boolean is(String nameOrAlias) {
        if (nameOrAlias.equals(name)) {
            return true;
        }
        for (String alias : aliases) {
            if (nameOrAlias.equals(alias)) {
                return true;
            }
        }
        return false;
    }

    public int getCardinality() {
        return ordinal();
    }
}
