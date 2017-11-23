package com.driviz.stopwatch1;

/**
 * Created by Driviz on 16-07-2017.
 */

public class Laps_class {
    String lap_number,lap_Time;

    public Laps_class(String lap_number, String lap_Time) {
       setLap_number(lap_number);
       setLap_Time(lap_Time);
    }

    public String getLap_number() {
        return lap_number;
    }

    public void setLap_number(String lap_number) {
        this.lap_number = lap_number;
    }

    public String getLap_Time() {
        return lap_Time;
    }

    public void setLap_Time(String lap_Time) {
        this.lap_Time = lap_Time;
    }
}
