package com.IA;

public class StarSight {
    private Star star;
    private Degree angularHeight;
    private Degree indexCorrection;
    private boolean indexCorrectionOn;
    private Direction hemisphere;

    public StarSight(String name, Degree angularHeight, Degree indexCorrection, boolean indexCorrectionOn, Direction hemisphere) {
        star = new Star(name);
        this.angularHeight = angularHeight;
        this.indexCorrection = indexCorrection;
        this.indexCorrectionOn = indexCorrectionOn;
        this.hemisphere = hemisphere;
    }


}