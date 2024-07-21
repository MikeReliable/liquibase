package ru.mike.liquibase.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "titanic")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean survived;
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private Pclass pclass;
    private String name;
    private String sex;
    private int age;

    @Column(name = "siblings_spouses_aboard")
    private int siblingsSpousesAboard;

    @Column(name = "parents_children_aboard")
    private int parentsChildrenAboard;

    private double fare;

    public Passenger() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pclass getPclass() {
        return pclass;
    }

    public void setPclass(Pclass pclass) {
        this.pclass = pclass;
    }

    public boolean isSurvived() {
        return survived;
    }

    public void setSurvived(boolean survived) {
        this.survived = survived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSiblingsSpousesAboard() {
        return siblingsSpousesAboard;
    }

    public void setSiblingsSpousesAboard(int siblingsSpousesAboard) {
        this.siblingsSpousesAboard = siblingsSpousesAboard;
    }

    public int getParentsChildrenAboard() {
        return parentsChildrenAboard;
    }

    public void setParentsChildrenAboard(int parentsChildrenAboard) {
        this.parentsChildrenAboard = parentsChildrenAboard;
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }
}
