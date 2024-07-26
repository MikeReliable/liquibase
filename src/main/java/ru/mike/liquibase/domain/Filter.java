package ru.mike.liquibase.domain;

public class Filter {
    private boolean survived;
    private boolean adult;
    private boolean males;
    private boolean noRelatives;

    public Filter() {
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

}
