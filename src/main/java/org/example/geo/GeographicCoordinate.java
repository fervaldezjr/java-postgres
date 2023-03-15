package org.example.geo;

public final class GeographicCoordinate {
    private static final String FRAGMENT_SEPARATOR = ",";
    private static final String ASSINGMENT_SEPARATOR = ":";

    // Latitude limits
    private static final float NORTH_POLE    =   90f; 
    private static final float SOUTH_POLE    =  -90f;
    // Longitude limits
    private static final float MAX_LONGITUDE =  180f; 
    private static final float MIN_LONGITUDE = -180f;

    private final float latitude;
    private final float longitude;
    private final float altitude;

    public GeographicCoordinate(float latitude, float longitude) {
        this(latitude, longitude, 0f);
    }

    public GeographicCoordinate(float latitude, float longitude, float altitude) {
        if (latitude > NORTH_POLE || latitude < SOUTH_POLE) {
            throw new IllegalArgumentException(String.format("Latitude must be between %dº and %dº", NORTH_POLE, SOUTH_POLE));
        }
        if (longitude > MAX_LONGITUDE || longitude < MIN_LONGITUDE) {
            throw new IllegalArgumentException(String.format("Longitude must be between %dº and %dº", MAX_LONGITUDE, MIN_LONGITUDE));
        }
        this.latitude  = latitude;
        this.longitude = longitude;
        this.altitude  = altitude;
    }

    /**
     * Parse an location String.
     * 
     * Valid formats:
     *   "lat:40.45646,long:-4.56565[,alt:790]"
     *   "latitude:40.45646,longitude:-4.56565[,altitude:790]"
     */
    public static GeographicCoordinate parse(String location) {
        String[] fragments = location.split(FRAGMENT_SEPARATOR);
        if (fragments.length < 2 || fragments.length > 3) {
            throw new IllegalArgumentException(String.format("Wrong number of location fragments: %d", fragments.length)); // TODO
        }
        float latitude  = Float.MAX_VALUE;
        float longitude = Float.MAX_VALUE;
        float altitude  = 0;
        for (String fragment: fragments) {
            if (fragment.indexOf(ASSINGMENT_SEPARATOR) == -1) {
                throw new IllegalArgumentException(String.format("No assignation separator '%s'", ASSINGMENT_SEPARATOR));
            }
            switch (parseKey(fragment)) {
            case "lat":
            case "latitude":
                latitude = parseValue(fragment);
                break;
            case "long": 
            case "longitude":
                longitude = parseValue(fragment);
                break; 
            case "alt":
            case "altitude":
                altitude = parseValue(fragment);
                break; 
            default:
                throw new IllegalArgumentException(String.format("Unexpected location fragment key: %s", parseKey(fragment)));
            }
        }
        if (latitude == Float.MAX_VALUE || longitude == Float.MAX_VALUE) {
            throw new IllegalArgumentException("No se ha pasado latitude o longitude");
        }
        return new GeographicCoordinate(latitude, longitude, altitude);
    }

    public float latitude()  { return latitude; }
    public float longitude() { return longitude; }
    public float altitude()  { return altitude; }

    private static String parseKey(String fragment) {
        return fragment.substring(0, fragment.indexOf(ASSINGMENT_SEPARATOR))
                       .trim()
                       .toLowerCase();
    }

    private static float parseValue(String fragment) {
        try {
            String value = fragment.substring(fragment.indexOf(ASSINGMENT_SEPARATOR) + 1);
            return Float.parseFloat(value.toLowerCase());
        } catch(NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("Error parsing longitude");
        }
    }
}
