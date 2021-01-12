package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.DrugViewDTO;
import CCM.cchira.medical.system.entity.Drug;

public class DrugViewBuilder {
    public DrugViewBuilder() {
    }

    public static DrugViewDTO generateDTOFromEntity(Drug drug) {
        return new DrugViewDTO(
                drug.getName(),
                drug.getSideEffects(),
                drug.getDosage()
        );
    }

    public static Drug generateEntityFromDTO(DrugViewDTO drugViewDTO) {
        return new Drug(
                drugViewDTO.getName(),
                drugViewDTO.getSideEffects(),
                drugViewDTO.getDosage()
        );
    }
}
