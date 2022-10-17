package Mersys.Model;

public class Departments {

    private String id;
    private String name;
    private String code;
    private boolean active;
    private String schoolId;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "{\n" +
                (id == null ? "  \"id\": "+id+",\n":"  \"id\": \""+id+"\",\n") +
                "    \"name\": \"" + name + "\",\n" +
                "    \"code\": \"" + code + "\",\n" +
                "    \"active\": " + active + ",\n" +
                "    \"school\": {\n" +
                "    \"id\": \""+schoolId+"\"\n" +
                "    },\n" +
                "    \"sections\": [],\n" +
                "    \"constants\": []\n" +
                "}";
    }

}
