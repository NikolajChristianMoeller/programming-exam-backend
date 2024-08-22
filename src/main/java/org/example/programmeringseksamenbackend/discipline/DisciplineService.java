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
        Discipline discipline = new Discipline();
        discipline.setDisciplineName(disciplineDTO.getDisciplineName());
        discipline.setApproxDuration(disciplineDTO.getApproxDuration());

        Discipline savedDiscipline = disciplineRepository.save(discipline);

        DisciplineDTO savedDisciplineDTO = new DisciplineDTO();
        savedDisciplineDTO.setId(savedDiscipline.getId());
        savedDisciplineDTO.setDisciplineName(savedDiscipline.getDisciplineName());
        savedDisciplineDTO.setApproxDuration(savedDiscipline.getApproxDuration());

        return savedDisciplineDTO;
    }

    /* public DisciplineDTO createDiscipline(DisciplineDTO disciplineDTO) {
        Discipline discipline = new Discipline();
        return toDTO(disciplineRepository.save(discipline));
    } */

}
