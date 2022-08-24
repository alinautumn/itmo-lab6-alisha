package com.alisha.common.data;

import java.io.Serializable;
import java.util.Objects;

public class LocationFrom implements Serializable {
    private final Double x; //Поле не может быть null
    private final Double y; //Поле не может быть null
    private final String name; //Строка не может быть пустой, Поле не может быть null


    public LocationFrom(Double x, Double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }


    @Override
    public int hashCode() {
        return Objects.hash(x, y, name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationFrom locationFrom = (LocationFrom) o;
        return Double.compare(locationFrom.x, x) == 0 && Objects.equals(y, locationFrom.y) && Objects.equals(name, locationFrom.name);
    }

    @Override
    public String toString() {
        return "LocationFrom{"
                + "x=" + x
                + ", y=" + y
                + ", name='" + name + '\''
                + '}';
    }
}
