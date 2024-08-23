package org.example.programmeringseksamenbackend.participant;

import org.example.programmeringseksamenbackend.discipline.Discipline;
import org.example.programmeringseksamenbackend.discipline.DisciplineDTO;
import org.example.programmeringseksamenbackend.discipline.DisciplineRepository;
import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParticipantService {
    private final DisciplineRepository disciplineRepository;
    ParticipantRepository participantRepository;

    public ParticipantService(ParticipantRepository participantRepository, DisciplineRepository disciplineRepository) {
        this.participantRepository = participantRepository;
        this.disciplineRepository = disciplineRepository;
    }

    private ParticipantDTO toDTO(Participant participant) {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(participant.getId());
        participantDTO.setFullName(participant.getFullName());
        participantDTO.setParticipantNumber(participant.getParticipantNumber());
        participantDTO.setDisciplineId(participant.getDiscipline() != null ? participant.getDiscipline().getId() : null);
        participantDTO.setGender(participant.getGender());
        participantDTO.setAgeGroup(participant.getAgeGroup());
        return participantDTO;
    }


    private Participant toEntity(ParticipantDTO participantDTO) {
        Participant participant = new Participant();
        participant.setId(participantDTO.getId());
        Discipline discipline = disciplineRepository.findById(participantDTO.getDisciplineId()).orElse(null);
        participant.setDiscipline(discipline);
        participant.setParticipantNumber(participantDTO.getParticipantNumber());
        participant.setGender(participantDTO.getGender());
        participant.setAgeGroup(participantDTO.getAgeGroup());
        return participant;
    }

    public List<ParticipantDTO> getAllParticipants() {
        return participantRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<ParticipantDTO> getParticipantById(Long id) {
        if (id == null || id < 0) {
            throw new NotFoundException("Id must be provided");
        }

        Optional<Participant> participantOptional = participantRepository.findById(id);

        if (participantOptional.isEmpty()) {
            throw new NotFoundException("Participant not found, provided id: " + id);
        }

        return participantOptional.map(this::toDTO);
    }

    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        Discipline discipline = disciplineRepository.findById(participantDTO.getDisciplineId())
                .orElseThrow(() -> new NotFoundException("Discipline not found with id: " + participantDTO.getDisciplineId()));

        Participant participant = toEntity(participantDTO);
        participant.setDiscipline(discipline);

        // Save participant
        Participant savedParticipant = participantRepository.save(participant);

        // Update discipline's participants list
        discipline.getParticipants().add(savedParticipant);
        disciplineRepository.save(discipline);

        return toDTO(savedParticipant);
    }

    public ParticipantDTO updateParticipant(Long id, ParticipantDTO participantDTO) {
        return participantRepository.findById(id).map(participant -> {
            participant.setParticipantNumber(participantDTO.getParticipantNumber());
            participant.setGender(participantDTO.getGender());
            participant.setAgeGroup(participantDTO.getAgeGroup());
            return toDTO(participantRepository.save(participant));
        }).orElse(null);
    }

    public ParticipantDTO deleteParticipant(Long id) {
        Participant participant = participantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Participant not found, provided id: " + id));
        ParticipantDTO participantDTO = toDTO(participant);
        participantRepository.deleteById(id);
        return participantDTO;
    }

}
