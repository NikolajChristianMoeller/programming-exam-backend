package org.example.programmeringseksamenbackend.discipline;

import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DisciplineService {
    DisciplineRepository disciplineRepository;

    public DisciplineService(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    private DisciplineDTO toDTO(Discipline discipline) {
        DisciplineDTO disciplineDTO = new DisciplineDTO();
        disciplineDTO.setId(discipline.getId());
        disciplineDTO.setDisciplineName(discipline.getDisciplineName());
        disciplineDTO.setApproxDuration(discipline.getApproxDuration());
        return disciplineDTO;
    }

    private Discipline toEntity(DisciplineDTO disciplineDTO) {
        Discipline discipline = new Discipline();
        discipline.setDisciplineName(disciplineDTO.getDisciplineName());
        discipline.setApproxDuration(disciplineDTO.getApproxDuration());
        return discipline;
    }

    public List<DisciplineDTO> getAllDisciplines() {
        return disciplineRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<DisciplineDTO> getDisciplineById(Long id) {
        if (id == null || id < 0) {
            throw new NotFoundException("Id must be provided");
        }

        Optional<Discipline> disciplineOptional = disciplineRepository.findById(id);

        if (disciplineOptional.isEmpty()) {
            throw new NotFoundException("Discipline not found, provided id: " + id);
        }

        return disciplineOptional.map(this::toDTO);
    }

    public DisciplineDTO createDiscipline(DisciplineDTO disciplineDTO) {
        Discipline discipline = toEntity(disciplineDTO);
        return toDTO(disciplineRepository.save(discipline));
    }

    /* public DisciplineDTO createDiscipline(DisciplineDTO disciplineDTO) {
        Discipline discipline = new Discipline();
        return toDTO(disciplineRepository.save(discipline));
    } */

}
