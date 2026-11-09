package edu.farmingdale.csc311.clubdirectory.club;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/clubs")
public class ClubController {

    private final ClubRepository repository;

    public ClubController(ClubRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Club> all(@RequestParam(required = false) String category) {
        if (category == null || category.isBlank()) {
            return repository.findAll();
        }
        return repository.findByCategoryIgnoreCase(category);
    }

    @GetMapping("/{id}")
    public Club one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No club with id " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Club create(@Valid @RequestBody Club club) {
        Club newClub = new Club(club.getName(), club.getCategory(),
                club.getMeetingDay(), club.getRoom());
        return repository.save(newClub);
    }
}