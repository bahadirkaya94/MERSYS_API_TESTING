package Mersys.Model;

public class HumanResourcePositions {

    private String id;
    private String name;
    private String shortName;
    private String tenantId;

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "{\n" +
                (id == null ? "  \"id\": "+id+",\n":"  \"id\": \""+id+"\",\n") +
                "    \"name\": \""+name+"\",\n" +
                "    \"shortName\": \""+shortName+"\",\n" +
                "    \"translateName\": [],\n" +
                "    \"tenantId\": \""+tenantId+"\",\n" +
                "    \"active\": true\n" +
                "}" ;
    }
}
