package CCM.cchira.medical.system.service;

import CCM.cchira.medical.system.dto.DoctorDTO;
import CCM.cchira.medical.system.dto.builders.DoctorBuilder;
import CCM.cchira.medical.system.entity.Doctor;
import CCM.cchira.medical.system.entity.Patient;
import CCM.cchira.medical.system.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService (DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDTO> getAllDoctorsOrdered() {
        List<Doctor> patients = doctorRepository.getAllOrdered();

        return patients.stream()
                .map(DoctorBuilder::generateDTOFromEntity)
                .collect(Collectors.toList());
    }

    public Doctor getById(Integer doctorId) {
        return doctorRepository.getOne(doctorId);
    }
}
