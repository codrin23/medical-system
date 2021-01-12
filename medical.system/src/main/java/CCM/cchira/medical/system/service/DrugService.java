package CCM.cchira.medical.system.service;

import CCM.cchira.medical.system.dto.DrugDTO;
import CCM.cchira.medical.system.dto.DrugViewDTO;
import CCM.cchira.medical.system.dto.builders.DrugBuilder;
import CCM.cchira.medical.system.dto.builders.DrugViewBuilder;
import CCM.cchira.medical.system.entity.Drug;
import CCM.cchira.medical.system.exception.ResourceNotFoundException;
import CCM.cchira.medical.system.repository.DrugRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrugService {
    private final DrugRepository drugRepository;

    @Autowired
    public DrugService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    public Integer insert(DrugViewDTO drugViewDTODTO) {
        Drug drug = DrugViewBuilder.generateEntityFromDTO(drugViewDTODTO);

        return drugRepository.save(drug).getId();
    }

    public Integer update(DrugDTO drugDTO) {
        Optional<Drug> drug = drugRepository.findById(drugDTO.getId());

        if (drug.isEmpty()) {
            throw new ResourceNotFoundException("Drug", "name", drugDTO.getName());
        }

        Drug drugEntity = DrugBuilder.generateEntityFromDTO(drugDTO);

        drug.get().setName(drugDTO.getName());
        drug.get().setSideEffects(drugDTO.getSideEffects());
        drug.get().setDosage(drugDTO.getDosage());

        return drugRepository.save(drug.get()).getId();
    }

    public List<DrugDTO> findAll() {
        List<Drug> drugs = drugRepository.getAllOrdered();

        return drugs.stream()
                .map(DrugBuilder::generateDTOFromEntity)
                .collect(Collectors.toList());
    }

    public void delete(Integer drugId) {
        this.drugRepository.deleteById(drugId);
    }

    public DrugDTO getDrugById(Integer drugId) {
        Optional<Drug> patient = drugRepository.findById(drugId);

        return patient.map(DrugBuilder::generateDTOFromEntity).orElse(null);
    }

    public Drug getById(Integer drugId) {
        return drugRepository.getOne(drugId);
    }
}
