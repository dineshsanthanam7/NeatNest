package Firebaseutilc;

import java.io.Serializable;

public class Service implements Serializable {
    private String name;
    private String description;

    public Service() {
        // No-argument constructor required for Firestore
    }

    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
