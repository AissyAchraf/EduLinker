package ensa.edulinker.backend.web.entities;

import java.io.Serializable;

public class Sector implements Serializable {

    private Long id;
    private String name;

    public Sector() {

    }

    public Sector(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sector [id= "+ id + ", name= " + name +"]";
    }
}
