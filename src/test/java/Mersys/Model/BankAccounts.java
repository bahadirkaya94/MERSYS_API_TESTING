package Mersys.Model;

public class BankAccounts {
    private boolean active;
    private String currency;
    private String iban;
    private String id;
    private String integrationCode;
    private String name;
    private String schoolId;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntegrationCode() {
        return integrationCode;
    }

    public void setIntegrationCode(String integrationCode) {
        this.integrationCode = integrationCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "BankAccounts{" +
                "active=" + active +
                ", currency='" + currency + '\'' +
                ", iban='" + iban + '\'' +
                ", id='" + id + '\'' +
                ", integrationCode='" + integrationCode + '\'' +
                ", name='" + name + '\'' +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
