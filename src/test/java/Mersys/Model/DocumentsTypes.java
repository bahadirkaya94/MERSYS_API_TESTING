package Mersys.Model;

import java.util.Arrays;

public class DocumentsTypes {


    private String id;
    private String name;
    private String description;
    private String[] attachmentStages;
    private boolean active;
    private boolean required;
    private boolean useCamera;
    private String[] translateName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getAttachmentStages() {
        return attachmentStages;
    }

    public void setAttachmentStages(String[] attachmentStages) {
        this.attachmentStages = attachmentStages;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public boolean isUseCamera() {
        return useCamera;
    }

    public void setUseCamera(boolean useCamera) {
        this.useCamera = useCamera;
    }

    public String[] getTranslateName() {
        return translateName;
    }

    public void setTranslateName(String[] translateName) {
        this.translateName = translateName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }


    @Override
    public String toString() {
        return "DocumentsTypes{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", attachmentStages=" + Arrays.toString(attachmentStages) +
                ", active=" + active +
                ", required=" + required +
                ", useCamera=" + useCamera +
                ", translateName=" + Arrays.toString(translateName) +
                ", schoolId='" + schoolId + '\'' +
                '}';
    }
}
