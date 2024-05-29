package Firebaseutilc;

public class Service {
    private String title;
    private String type;
    private String description;

    public Service(String title, String type, String description) {
        this.title = title;
        this.type = type;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }
}

