package com.example.rentservice.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AddressLevel {
        RF_SUBJECT("rfSubject"),
        ADMINISTRATIVE_DISTRICT("administrativeDistrict"),
        MUNICIPAL_DISTRICT("municipalDistrict"),
        URBAN_SETTLEMENT("urbanSettlement"),
        CITY("city"),
        LOCALITY("locality"),
        PLANNING_STRUCTURE_ELEMENT("planningStructureElement"),
        ROAD_NETWORK_ELEMENT("roadNetworkElement"),
        LAND_PLOT("landPlot"),
        BUILDING("building"),
        ROOM("room"),
        ROOM_IN_ROOM("roomInRoom"),
        AUTONOMOUS_OKRUG("autonomousOkrug"),
        INTRACITY_LEVEL("intracityLevel"),
        ADDITIONAL_TERRITORY("additionalTerritory"),
        OBJECTS_IN_AT("objectsInAt"),
        PARKING_SPACE("parkingSpace");

        private final String value;

        AddressLevel(String value) {
                this.value = value;
        }

        @JsonValue
        public String getValue() {
                return value;
        }
}
