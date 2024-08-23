package org.example.programmeringseksamenbackend.track;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    TrackService trackService;

    public TrackController(TrackService trackService) {
        this.trackService = trackService;
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getAllTracks () {
        return ResponseEntity.ok(trackService.getAllTracks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable Long id) {
        return  ResponseEntity.of(trackService.getTrackById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<TrackDTO> deleteTrack (@PathVariable Long id) {
        return ResponseEntity.ok(trackService.deleteTrack(id));
    }
}
