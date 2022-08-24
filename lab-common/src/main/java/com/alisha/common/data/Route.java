package com.alisha.common.data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class Route implements Comparable<Route>, Serializable {

    @Min(1)
    private int id; //�������� ���� ������ ���� ������ 0, �������� ����� ���� ������ ���� ����������, �������� ����� ���� ������ �������������� �������������
    @NotNull
    private final String name; //���� �� ����� ���� null, ������ �� ����� ���� ������
    @NotNull
    private final Coordinates coordinates; //���� �� ����� ���� null
    @NotNull
    private final java.time.LocalDate creationDate; //���� �� ����� ���� null, �������� ����� ���� ������ �������������� �������������
    @NotNull
    private final LocationFrom from; //���� �� ����� ���� null
    @NotNull
    private final LocationTo to; //���� �� ����� ���� null
    @Min(2)
    private final long distance; //�������� ���� ������ ���� ������ 1


    public Route(String name, Coordinates coordinates, LocationFrom from, LocationTo to, long distance) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = java.time.LocalDate.now();
        this.from = from;
        this.to = to;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public long getDistance() {
        return distance;
    }


    @Override
    public int hashCode() {
        return Objects.hash(id, name, distance, creationDate, coordinates, to, from, distance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route that = (Route) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(to, that.to) && Objects.equals(from, that.from) && distance == that.distance;
    }

    @Override
    public String toString() {
        return "Route{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate
                + ", from=" + from
                + ", to=" + to
                + ", distance=" + distance
                + '}';
    }

    @Override
    public int compareTo(Route o) {
        Integer oValue = o.getId();
        Integer thisValue = this.getId();


        if (oValue == null) {
            oValue = -1;
        }
        if (thisValue == null) {
            thisValue = -1;
        }

        if (oValue - thisValue != 0) {
            return thisValue - oValue;
        } else {
            return this.getId() - o.getId();
        }
    }
}
