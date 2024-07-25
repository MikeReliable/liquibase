package ru.mike.liquibase.domain;

import org.springframework.data.domain.Page;

public class Filter {
    boolean survived;
    boolean adult;
    boolean males;
    boolean noRelatives;
    Page<Passenger> passengers;

    public Filter() {
    }

    public Filter(boolean survived, boolean adult, boolean males, boolean noRelatives, Page<Passenger> passengers) {
        this.survived = survived;
        this.adult = adult;
        this.males = males;
        this.noRelatives = noRelatives;
        this.passengers = passengers;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public boolean isMales() {
        return males;
    }

    public void setMales(boolean males) {
        this.males = males;
    }

    public boolean isNoRelatives() {
        return noRelatives;
    }

    public void setNoRelatives(boolean noRelatives) {
        this.noRelatives = noRelatives;
    }

    public Page<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(Page<Passenger> passengers) {
        this.passengers = passengers;
    }
}
