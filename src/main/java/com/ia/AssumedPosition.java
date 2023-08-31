package com.ia;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AssumedPosition {
    private Latitude assumedLatitude;
    private Longitude assumedLongitude;
    private double expectedHeight;
    private Degree azimuth;
    private static final Logger logger = LogManager.getLogger();

    public AssumedPosition(DRPosition dPos, Star star) {
        // assumed latitude is just the degrees of dr position
        assumedLatitude = new Latitude(dPos.getLatitude().getDegrees(), dPos.getLatitude().getDirection());
        assumedLongitude = calculateAssumedLongitude(dPos);
        expectedHeight = calculateExpectedHeight(dPos, star);
        azimuth = calculateAzimuth(star);
    }

    private Longitude calculateAssumedLongitude(DRPosition dPos) {
        // assumed longitude is the degrees of dr position & the minutes of aries, for some reason
        Direction aLonDir = dPos.getLongitude().getDirection();
        double dLonDegs = dPos.getLongitude().getDegrees();
        double GHAMins = FileHandler.getAriesGHA().getMinutes();

        if (aLonDir == Direction.WEST) {
            return new Longitude((int) dLonDegs, GHAMins, aLonDir);
        } else {
            return new Longitude((int) dLonDegs, 60-GHAMins, aLonDir);
        }
    }

    private double calculateExpectedHeight(DRPosition drPos, Star star) {
        // this is for the expected height (through sextant, like the angular height) at the assumed position
        double DRlat = drPos.getLatitude().toDouble();
        double LHA = calculateLHA(star).toDouble();
        double dec = star.getDeclination().toDouble();

        // using the standard formula for calculating expected height
        double realValue = (Utilities.sin(dec) * Utilities.sin(DRlat)) + (Utilities.cos(dec) * Utilities.cos(DRlat) * Utilities.cos(LHA));
        logger.info("expected height: " + realValue);
        return Utilities.asin(realValue);
    }

    private Degree calculateLHA(Star star) {
        // calculates the Local Hour Angle
        Degree LHA;
        Degree GHA = star.getGreenwichHourAngle();
        if (assumedLongitude.getDirection() == Direction.WEST) {
            if (assumedLongitude.getDegrees() > GHA.getDegrees()) {
                LHA = new Degree((star.getGreenwichHourAngle().getDegrees() + 360) - assumedLongitude.getDegrees());
            } else {
                LHA = new Degree(star.getGreenwichHourAngle().getDegrees() - assumedLongitude.getDegrees());
            }
        } else {
            // eastern longitudes
            double GHADiff = 60 - GHA.getMinutes();
            LHA = Degree.add(GHA, new Degree(assumedLongitude.getDegrees(), GHADiff));
        }

        return LHA;
    }

    private Degree calculateAzimuth(Star star) {
        // calculates the azimuth (the angle from the assumed position to the GP)
        double dec = star.getDeclination().toDouble();
        double alat = assumedLatitude.toDouble();

        // uses standard azimuth formula
        double Zn =
                Utilities.acos((Utilities.sin(dec) - Utilities.sin(alat) * Utilities.sin(expectedHeight)) / (Utilities.cos(alat) * Utilities.cos(expectedHeight)));

        double Z;
        if (assumedLatitude.getDirection() == Direction.NORTH) {
            if (calculateLHA(star).getDegrees() > 180) {
                Z = Zn;
            } else {
                Z = Zn - 360;
            }
        } else {
            if (calculateLHA(star).getDegrees() > 180) {
                Z = Zn + 180;
            } else {
                Z = Zn - 180;
            }
        }

        return new Degree(Z);
    }

    public Latitude getAssumedLatitude() {
        return assumedLatitude;
    }

    public Longitude getAssumedLongitude() {
        return assumedLongitude;
    }

    public double getExpectedHeight() {
        return expectedHeight;
    }

    public Degree getAzimuth() {
        return azimuth;
    }
}