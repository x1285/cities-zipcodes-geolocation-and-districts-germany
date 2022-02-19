package de.x1285.germany.citiesanddistricts.data;

import de.x1285.germany.citiesanddistricts.Application;
import de.x1285.germany.citiesanddistricts.model.GeoPlace;
import de.x1285.germany.citiesanddistricts.model.GeoPlaceType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

public class GeoPlaceDataImporter {

    private static final String FILE_NAME = "raw-geo-data.txt";

    public static LinkedHashMap<Long, GeoPlace> readData() {
        try (InputStream inputStream = Application.class.getClassLoader().getResourceAsStream(FILE_NAME);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            final LinkedHashMap<Long, GeoPlace> all = new LinkedHashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                GeoPlace gp = new GeoPlace();
                String[] splittedLine = line.split("\\t");
                if (splittedLine[0].equals("#loc_id")) {
                    continue;
                }
                for (int i = 0, splittedLineLength = splittedLine.length; i < splittedLineLength; i++) {
                    String value = splittedLine[i].trim();
                    if (!value.isBlank()) {
                        switch (i) {
                            case 0:
                                gp.setId(Long.parseLong(value));
                                break;
                            case 1:
                                gp.setAgs(value);
                                break;
                            case 2:
                                gp.setAscii(value);
                                break;
                            case 3:
                                gp.setName(value);
                                break;
                            case 4:
                                gp.setLatitude(Double.parseDouble(value));
                                break;
                            case 5:
                                gp.setLongitude(Double.parseDouble(value));
                                break;
                            case 6:
                                gp.setAmt(value);
                                break;
                            case 7:
                                gp.setZipcodes(value);
                                break;
                            case 8:
                                gp.setPhoneAreaCode(value);
                                break;
                            case 9:
                                gp.setPopulation(Long.parseLong(value));
                                break;
                            case 10:
                                gp.setArea(Double.parseDouble(value));
                                break;
                            case 11:
                                gp.setCarLicensePlateCode(value);
                                break;
                            case 12:
                                gp.setInitialType(value);
                                gp.setType(GeoPlaceType.getFor(value));
                                break;
                            case 13:
                                gp.setLevel(Integer.parseInt(value));
                                break;
                            case 14:
                                gp.setParentId(Long.parseLong(value));
                                break;
                        }
                    }
                }
                if (all.get(gp.getId()) != null) {
                    throw new IllegalStateException("More than one entry found with id: " + gp.getId());
                }
                if (gp.getName() != null && !gp.getName().isBlank()) {
                    all.put(gp.getId(), gp);
                }
            }
            groupToParents(all);
            return all;
        } catch (IOException e) {
            throw new GeoPlaceDataImporterException("Failure during file import.", e);
        }
    }

    private static void groupToParents(LinkedHashMap<Long, GeoPlace> all) {
        for (GeoPlace gp : all.values()) {
            if (gp.getParentId() != null) {
                final Long longValue = gp.getParentId();
                if (longValue != null) {
                    if (104L == longValue) {
                        continue;
                    }
                    if (all.containsKey(longValue)) {
                        all.get(longValue).addChild(gp);
                    } else {
                        System.out.println("No parent found with id: " + longValue);
                    }
                }
            }
        }
        all.values().stream().filter(geoPostleitzahl -> geoPostleitzahl.getChildren().size() != 0)
                .collect(Collectors.toList());
    }

    public static class GeoPlaceDataImporterException extends RuntimeException {
        public GeoPlaceDataImporterException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
