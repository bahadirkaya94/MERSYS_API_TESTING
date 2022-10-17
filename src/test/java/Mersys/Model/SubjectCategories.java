package Mersys.Model;

public class SubjectCategories {

    private String name;

    private String code;

    private boolean active;

    private String id;

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
//selam
    //aDASA
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SubjectCategories{" +
                "name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", active=" + active +
                ", id='" + id + '\'' +
                '}';
    }
}
