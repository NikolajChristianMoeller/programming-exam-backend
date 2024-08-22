package org.example.programmeringseksamenbackend.timeslot;

import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {
    TimeSlotRepository timeSlotRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository) {
        this.timeSlotRepository = timeSlotRepository;
    }

    private TimeSlotDTO toDTO(TimeSlot timeSlot) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setId(timeSlot.getId());
        timeSlotDTO.setDate(timeSlot.getDate());
        timeSlotDTO.setStartTime(timeSlot.getStartTime());
        timeSlotDTO.setEndTime(timeSlot.getEndTime());
        return timeSlotDTO;
    }

    private TimeSlot toEntity(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotDTO.getId());
        timeSlot.setDate(timeSlotDTO.getDate());
        timeSlot.setStartTime(timeSlotDTO.getStartTime());
        timeSlot.setEndTime(timeSlotDTO.getEndTime());
        return timeSlot;
    }

    public List<TimeSlotDTO> getAllTimeSlots() {
        return timeSlotRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<TimeSlotDTO> getTimeSlotById(Long id) {
        if (id == null || id < 0) {
            throw new NotFoundException("Id must be provided");
        }

        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(id);

        if (timeSlotOptional.isEmpty()) {
            throw new NotFoundException("TimeSlot not found, provided id: " + id);
        }

        return timeSlotOptional.map(this::toDTO);
    }

    /*public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = new TimeSlot();
        return toDTO(timeSlotRepository.save(timeSlot));
    }*/

    public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = toEntity(timeSlotDTO);
        return toDTO(timeSlotRepository.save(timeSlot));
    }

    public TimeSlotDTO updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO) {
        return timeSlotRepository.findById(id).map(timeSlot -> {
            timeSlot.setDate(timeSlotDTO.getDate());
            timeSlot.setStartTime(timeSlotDTO.getStartTime());
            timeSlot.setEndTime(timeSlotDTO.getEndTime());
            return toDTO(timeSlotRepository.save(timeSlot));
        }).orElse(null);
    }

    public TimeSlotDTO deleteTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time slot not found, provided id: " + id));
        TimeSlotDTO timeSlotDTO = toDTO(timeSlot);
        timeSlotRepository.deleteById(id);
        return timeSlotDTO;
    }

}
