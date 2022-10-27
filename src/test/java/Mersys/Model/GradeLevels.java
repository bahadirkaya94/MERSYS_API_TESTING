package Mersys.Model;

public class GradeLevels {

    private String name99;
    private String shorname99;

    private boolean active;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {

       return "{\n"+
               (id == null ? "  \"id\": "+id+",\n":"  \"id\": \""+id+"\",\n")+
               "  \"name\": \"" +name99+"\",\n" +
               "  \"order\": null, \n" +
               "  \"shortName\": \"" +shorname99+"\",\n" +
               "  \"nextGradeLevel\": null, \n" +
               "  \"active\": " + active + ",\n" +
               "  \"translateName\": [],\n" +
               "  \"translateShortName\": []\n" +
               "}";




    }

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName99() {
        return name99;
    }

    public void setName99(String name99) {
        this.name99 = name99;
    }

    public String getShorname99() {
        return shorname99;
    }

    public void setShorname99(String shorname99) {
        this.shorname99 = shorname99;
    }

}
