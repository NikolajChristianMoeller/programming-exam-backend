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
        participantDTO.setDisciplineId(participant.getDiscipline().getId());
        participantDTO.setParticipantNumber(participant.getParticipantNumber());
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
        Discipline discipline = disciplineRepository.findById(participantDTO.getId())
                .orElseThrow(() -> new NotFoundException("Discipline not found, provided id: " + participantDTO.getDisciplineId()));

        Participant participant = participantRepository.save(toEntity(participantDTO));
        discipline.getParticipants().add(participant);
        disciplineRepository.save(discipline);

        return toDTO(participant);
    }

    public List<ParticipantDTO> createParticipantsInDiscipline(List<ParticipantDTO> participantDTOs, DisciplineDTO disciplineDTO) {
        Discipline discipline = disciplineRepository.findById(disciplineDTO.getId())
                .orElseThrow(() -> new NotFoundException("Discipline not found, provided id: " + disciplineDTO.getId()));
        // Går igennem listen af ParticipantDTOer, hvert ParticipantDTO skal laves til en participant entity

        List<Participant> participants = participantDTOs.stream().map(this::toEntity).toList();

        return participantRepository.saveAll(participants).stream().map(this::toDTO).toList();
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
