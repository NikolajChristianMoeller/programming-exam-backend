package org.example.programmeringseksamenbackend.track;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
public class TrackController {

    TrackService test3Service;

    public TrackController(TrackService test3Service) {
        this.test3Service = test3Service;
    }

    @GetMapping
    public ResponseEntity<List<TrackDTO>> getAllTracks () {
        return ResponseEntity.ok(test3Service.getAllTracks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrackDTO> getTrackById(@PathVariable Long id) {
        return  ResponseEntity.of(test3Service.getTrackById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<TrackDTO> deleteTrack (@PathVariable Long id) {
        return ResponseEntity.ok(test3Service.deleteTrack(id));
    }
}
