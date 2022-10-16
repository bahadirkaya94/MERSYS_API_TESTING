package Mersys.Model;

public class Attestations {

    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Attestations{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
