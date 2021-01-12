package CCM.cchira.medical.system.dto.builders;

import CCM.cchira.medical.system.dto.DrugDTO;
import CCM.cchira.medical.system.entity.Drug;

public class DrugBuilder {
    public DrugBuilder() {
    }

    public static DrugDTO generateDTOFromEntity(Drug drug) {
        return new DrugDTO(
                drug.getId(),
                drug.getName(),
                drug.getSideEffects(),
                drug.getDosage()
        );
    }

    public static Drug generateEntityFromDTO(DrugDTO drugDTO) {
        return new Drug(
                drugDTO.getName(),
                drugDTO.getSideEffects(),
                drugDTO.getDosage()
        );
    }
}
