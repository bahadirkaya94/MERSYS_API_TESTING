package Mersys.Model;

public class Fields {
    private String NameFull;
    private String Code;
    private String id88;

    private boolean active;

    @Override
    public String toString() {
        return  "{\n"+
                (id88 == null ? "  \"id\": "+id88+",\n":"  \"id\": \""+id88+"\",\n")+
                "  \"name\": \"" +NameFull+"\",\n" +
                "  \"translateName\": [],\n" +
                "  \"Code\": \"" +Code+"\",\n" +
                "  \"type\": \"STRING\","+
                "  \"children\": [],"+
                "  \"systemField\": false,"+
                "  \"systemFieldName\": null,"+
                "  \"schoolId\": \"6343bf893ed01f0dc03a509a\""+"}";


    }


    public String getNameFull() {
        return NameFull;
    }

    public void setNameFull(String nameFull) {
        NameFull = nameFull;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getId88() {
        return id88;
    }

    public void setId88(String id88) {
        this.id88 = id88;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
