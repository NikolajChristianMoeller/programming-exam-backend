package org.example.programmeringseksamenbackend.discipline;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/disciplines")
public class DisciplineController {

    DisciplineService disciplineService;

    public DisciplineController(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @GetMapping
    public ResponseEntity<List<DisciplineDTO>> getAllDiscipline () {
        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DisciplineDTO> getDisciplineById(@PathVariable Long id) {
        return  ResponseEntity.of(disciplineService.getDisciplineById(id));
    }

    @PostMapping
    public ResponseEntity<DisciplineDTO> createDiscipline (@RequestBody DisciplineDTO disciplineDTO) {
        return ResponseEntity.status(404).body(disciplineService.createDiscipline(disciplineDTO));
    }
}
