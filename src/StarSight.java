public class StarSight {
    private double GHA;
    private double declination;
    private double angularHeight;
    private GeographicPosition GP;
    private String name;

    public StarSight(String name, double angularHeight) {
        this.declination = declinationFromAlmanac(name);
        this.GHA = GHAFromAlmanac(name);
    }

    private double declinationFromAlmanac(String star) {
        // acquire from almanac, consider using the python library or the pdf thing
    }

    private double GHAFromAlmanac(String star) {
        // acquire from almanac, consider using the python library or the pdf thing
    }

    private double angularHeightToObservedHeight() {
        // perform adjustments, treat ah as Ho for now
        return angularHeight;
    }
}
