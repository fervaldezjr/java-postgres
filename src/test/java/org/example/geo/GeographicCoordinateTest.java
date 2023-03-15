package org.example.geo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

public class GeographicCoordinateTest {

    @Test
    public void canCreateLocation() {
        new GeographicCoordinate(0, 0);
        new GeographicCoordinate(90, 180, 0);
        new GeographicCoordinate(-90, -180, 0);
        try {
            new GeographicCoordinate(91, 0, 0);
            fail("Expected max latitude failure");
        } catch(IllegalArgumentException e) {}
        try {
            new GeographicCoordinate(-91, 0, 0);
            fail("Expected max latitude failure");
        } catch(IllegalArgumentException e) {}
        try {
            new GeographicCoordinate(0, 181, 0);
            fail("Expected max latitude failure");
        } catch(IllegalArgumentException e) {}
        try {
            new GeographicCoordinate(0, -181, 0);
            fail("Expected max latitude failure");
        } catch(IllegalArgumentException e) {}
    }

    @Test
    public void canParseLocation() {
        record TestCase (String location, float expectedLatitude, float expectedLongitude, float expectedAltitude){}
        List.of(
            new TestCase("lat:40.45646,long:-4.56565", 40.45646f, -4.56565f, 0f),
            new TestCase("lat:40.45646,long:-4.56565,alt:10", 40.45646f, -4.56565f, 10f),
            new TestCase(" lat : 40.45646 , long: -4.56565 , alt : 10 ", 40.45646f, -4.56565f, 10f),
            new TestCase("latitude:40.45646, longitude:-4.56565, altitude:10", 40.45646f, -4.56565f, 10f)
        )
        .stream()
        .forEach(testCase -> {
            GeographicCoordinate location = GeographicCoordinate.parse(testCase.location);

            assertEquals(testCase.expectedLatitude,  location.latitude(),  0);
            assertEquals(testCase.expectedLongitude, location.longitude(), 0);
            assertEquals(testCase.expectedAltitude,  location.altitude(),  0);
        });
    }

    @Test
    public void cantParseLocation() {
        record TestCase (String location){}
        List.of(
            new TestCase(""),                             // nothing to parse
            new TestCase("lat:0"),                        // only one parameter
            new TestCase("lat:a,long:1,alt:8,another:7"), // more than 3 parameters
            new TestCase("lat:a,long:1"),                 // wrong parameter value
            new TestCase("lat,long"),                     // parameters without values
            new TestCase("lat:10:12,long=10"),            // two value separators in one key
            new TestCase("lat=10,long=10")                // wrong value separator
            // TODO add more parsing failures
        )
        .stream()
        .forEach(testCase -> {
            try {
                GeographicCoordinate.parse(testCase.location);
                fail(String.format("Expected error parsing %s but was OK", testCase.location));
            } catch(RuntimeException e) {}
        });
    }
}
