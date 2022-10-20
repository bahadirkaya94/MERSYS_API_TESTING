package Mersys.Model;

public class Discounts {

    private String id;
    private String description;
    private String code;
    private String priority;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "{\n" +
                (id == null ? "  \"id\": "+id+",\n":"  \"id\": \""+id+"\",\n") +
                "    \"description\": \""+description+"\",\n" +
                "    \"code\": \""+code+"\",\n" +
                "    \"active\": true,\n" +
                "    \"translateDescription\": [],\n" +
                "    \"priority\": "+priority+"\n" +
                "}";
    }
}