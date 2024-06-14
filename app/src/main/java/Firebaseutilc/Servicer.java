package Firebaseutilc;

public class Servicer {
    private String name;
    private String rating;

    public Servicer() {
        // No-argument constructor required for Firestore
    }

    public Servicer(String name, String rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
