package org.example.beginningClasses;

import java.io.Serializable;

public class Car implements Serializable {
    private String name; //Поле может быть null
    private boolean cool;

    public Car() {
    }

    public Car(String name, boolean cool) {
        this.name = name;
        this.cool = cool;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCool(boolean cool) {
        this.cool = cool;
    }

    public String getName() {
        return name;
    }

    public boolean isCool() {
        return cool;
    }

    @Override
    public String toString() {
        return name + ",\t" + cool;
    }

}
