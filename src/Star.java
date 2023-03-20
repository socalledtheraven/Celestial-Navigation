public class Star {
    private double GHA;
    private double declination;
    private double GP;
    private String name;

    public Star(String name) {
        this.declination = declinationFromAlmanac(name);
        this.GHA = GHAFromAlmanac(name);
    }

    private double declinationFromAlmanac(String star) {

    }

    private double GHAFromAlmanac(String star) {

    }

    public double GP() {

    }
}
