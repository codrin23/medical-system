package CCM.cchira.medical.system.dto;

public class DrugViewDTO {
    private String name;
    private String sideEffects;
    private String dosage;

    public DrugViewDTO() {
    }

    public DrugViewDTO(String name, String sideEffects, String dosage) {
        this.name = name;
        this.sideEffects = sideEffects;
        this.dosage = dosage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
