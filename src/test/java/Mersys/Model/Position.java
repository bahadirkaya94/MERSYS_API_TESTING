package Mersys.Model;

public class Position {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return "{\n" +
                (id == null ? "  \"id\": "+id+",\n":"  \"id\": \""+id+"\",\n") +
                "  \"name\": \""+name+"\",\n" +
                "  \"translateName\": []\n" +
                "}";
    }
}
