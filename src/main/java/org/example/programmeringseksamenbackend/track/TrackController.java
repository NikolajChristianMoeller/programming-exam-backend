package org.example.programmeringseksamenbackend.track;

import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    private final TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getAllTracks() {
        List<TrackDTO> tracks = trackService.getAllTracks();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable Long id) {
        TrackDTO trackDTO = trackService.getTrackById(id)
                .orElseThrow(() -> new NotFoundException("Track not found, provided id: " + id));
        return ResponseEntity.ok(trackDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TrackDTO> deleteTrack (@PathVariable Long id) {
        return ResponseEntity.ok(trackService.deleteTrack(id));
    }
}
