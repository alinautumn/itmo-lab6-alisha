package com.alisha.common.data;

import java.io.Serializable;
import java.util.Objects;

public class LocationTo implements Serializable {

    private final double x; //Поле не может быть null
    private final Integer y; //Поле не может быть null
    private final String name; //Строка не может быть пустой, Поле не может быть null

    public  LocationTo(double x, Integer y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y, name);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationTo locationTo = (LocationTo) o;
        return Double.compare(locationTo.x, x) == 0 && Objects.equals(y, locationTo.y) && Objects.equals(name, locationTo.name);
    }

    @Override
    public String toString(){
        return "LocationTo{"
                + "x=" + x
                + ", y=" + y
                + ", name='" + name + '\''
                + '}';
    }
}
