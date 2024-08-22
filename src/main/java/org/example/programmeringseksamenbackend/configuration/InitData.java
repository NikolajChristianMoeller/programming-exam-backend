package org.example.programmeringseksamenbackend.configuration;

import org.example.programmeringseksamenbackend.discipline.DisciplineDTO;
import org.example.programmeringseksamenbackend.discipline.DisciplineService;
import org.example.programmeringseksamenbackend.participant.ParticipantDTO;
import org.example.programmeringseksamenbackend.participant.ParticipantRepository;
import org.example.programmeringseksamenbackend.participant.ParticipantService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    private final DisciplineService disciplineService;
    private final ParticipantService participantService;
    private final ParticipantRepository participantRepository;

    public InitData(DisciplineService disciplineService, ParticipantService participantService, ParticipantRepository participantRepository) {
        this.disciplineService = disciplineService;
        this.participantService = participantService;
        this.participantRepository = participantRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (disciplineService.getAllDisciplines().isEmpty()) {
            System.out.println("Creating disciplines and events");
            for (int i = 1; i <= 50; i++) {
                //System.out.println("Creating discipline " + i);
                DisciplineDTO disciplineDTO = createDiscipline(i);
                int numberOfDisciplines = (int) (Math.random() * 10) + 10;
                //System.out.println("Creating " + numberOfRooms + " rooms in discipline " + disciplineDTO.getName());
                createParticipantsInDiscipline(numberOfDisciplines, disciplineDTO);
            }
            /* System.out.println("Done creating disciplines and rooms"); */
            System.out.println("Done creating disciplines");
        } else {
            /* System.out.println("Disciplines and rooms already exist"); */
            System.out.println("Disciplines already exist");

        }
    }

    private DisciplineDTO createDiscipline(int number){
        DisciplineDTO discipline = new DisciplineDTO(
                "Discipline " + number,
                "ApproxDuration " + number
        );

        DisciplineDTO nyDTO = disciplineService.createDiscipline(discipline);
        return nyDTO;
    }

    public void createParticipant(int number, DisciplineDTO discipline){
        ParticipantDTO participant = new ParticipantDTO(
                discipline.getId(),
                "Participant " + number
        );
        participantService.createParticipant(participant);
    }

    public void createParticipantsInDiscipline(int numberOfParticipants, DisciplineDTO discipline) {
        for (int number = 1; number <= numberOfParticipants; number++) {
            ParticipantDTO participant = new ParticipantDTO(
                    discipline.getId(),
                    "Participant " + number
            );
            participantService.createParticipant(participant);
        }
    }
}
