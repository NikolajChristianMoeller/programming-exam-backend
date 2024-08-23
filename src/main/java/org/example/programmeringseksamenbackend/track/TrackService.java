package org.example.programmeringseksamenbackend.track;

import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrackService {
    TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    private TrackDTO toDTO(Track track) {
        TrackDTO trackDTO = new TrackDTO();
        trackDTO.setId(track.getId());
        trackDTO.setTrackType(track.getTrackType());
        trackDTO.setTrackShape(track.getTrackShape());
        trackDTO.setTrackSurface(track.getTrackSurface());
        trackDTO.setTrackLength(track.getTrackLength());
        trackDTO.setLanes(track.getLanes());
        return trackDTO;
    }

    private Track toEntity(TrackDTO trackDTO) {
        Track track = new Track();
        track.setId(trackDTO.getId());
        track.setTrackType(trackDTO.getTrackType());
        track.setTrackShape(trackDTO.getTrackShape());
        track.setTrackSurface(trackDTO.getTrackSurface());
        track.setTrackLength(trackDTO.getTrackLength());
        track.setLanes(trackDTO.getLanes());
        return track;
    }

    public List<TrackDTO> getAllTracks() {
        return trackRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<TrackDTO> getTrackById(Long id) {
        if (id == null || id < 0) {
            throw new NotFoundException("Id must be provided");
        }

        Optional<Track> trackOptional = trackRepository.findById(id);

        if (trackOptional.isEmpty()) {
            throw new NotFoundException("Track not found, provided id: " + id);
        }

        return trackOptional.map(this::toDTO);
    }


    public TrackDTO deleteTrack(Long id) {
        Track track = trackRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Track not found, provided id: " + id));
        TrackDTO trackDTO = toDTO(track);
        trackRepository.deleteById(id);
        return trackDTO;
    }

}
