package org.example.dao;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Trainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Repository
public class TrainerDao {
    private Map<Long, Trainer> trainerStorage;
    @Autowired
    public void setTrainerStorage(Map<Long, Trainer> trainerStorage) {
        this.trainerStorage = trainerStorage;
    }
    public Trainer saveTrainer(Trainer trainer) {
        if (trainerStorage.containsKey(trainer.getUserId())) {
            log.warn("Failed to save trainer: ID {} already exists", trainer.getUserId());
            throw new IllegalArgumentException("Trainer with ID " + trainer.getUserId() + " already exists");
        }
        trainerStorage.put(trainer.getUserId(), trainer);
        log.info("Trainer saved: ID={}, Username={}, Specialization={}", trainer.getUserId(), trainer.getUsername(), trainer.getSpecialization());
        return trainer;
    }
    public Optional<Trainer> getTrainer(Long userId) {
        Optional<Trainer> trainer = Optional.ofNullable(trainerStorage.get(userId));
        if (trainer.isPresent()) {
            log.info("Fetched trainer: ID={}, Username={}, Specialization={}", userId, trainer.get().getUsername(), trainer.get().getSpecialization());
        } else {
            log.warn("Trainer with ID {} not found", userId);
        }
        return trainer;
    }
    public Trainer updateTrainer(Trainer trainer) {
        if (!trainerStorage.containsKey(trainer.getUserId())) {
            log.warn("Failed to update trainer: ID {} does not exist", trainer.getUserId());
            throw new IllegalArgumentException("Trainer with ID " + trainer.getUserId() + " does not exist");
        }

        trainerStorage.put(trainer.getUserId(), trainer);
        log.info("Trainer updated: ID={}, New Specialization={}", trainer.getUserId(), trainer.getSpecialization());
        return trainer;
    }
}
