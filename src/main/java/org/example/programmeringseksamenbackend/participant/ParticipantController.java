package org.example.programmeringseksamenbackend.participant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAllParticipants() {
        return ResponseEntity.ok(participantService.getAllParticipants());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable Long id) {
        return  ResponseEntity.of(participantService.getParticipantById(id));
    }

    @PostMapping
    public ResponseEntity<ParticipantDTO> createParticipant (@RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.status(202).body(participantService.createParticipant(participantDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipantDTO> updateParticipant (@PathVariable Long id, @RequestBody ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.updateParticipant(id, participantDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParticipantDTO> deleteParticipant (@PathVariable Long id) {
        return ResponseEntity.ok(participantService.deleteParticipant(id));
    }
}
