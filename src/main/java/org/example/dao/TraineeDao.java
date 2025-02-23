package org.example.dao;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Trainee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Repository
public class TraineeDao {
    private Map<Long, Trainee> traineeStorage;
    @Autowired
    public void setTraineeStorage(Map<Long, Trainee> traineeStorage) {
        this.traineeStorage = traineeStorage;
    }
    public Trainee saveTrainee(Trainee trainee) {
        if (traineeStorage.containsKey(trainee.getUserId())) {
            log.warn("Failed to save trainee: ID {} already exists", trainee.getUserId());
            throw new IllegalArgumentException("Trainee with ID " + trainee.getUserId() + " already exists");
        }
        traineeStorage.put(trainee.getUserId(), trainee);
        log.info("Trainee saved: ID={}, Username={}", trainee.getUserId(), trainee.getUsername());
        return trainee;
    }
    public Optional<Trainee> getTrainee(Long userId) {
        Optional<Trainee> trainee = Optional.ofNullable(traineeStorage.get(userId));
        if (trainee.isPresent()) {
            log.info("Fetched trainee: ID={}, Username={}", userId, trainee.get().getUsername());
        } else {
            log.warn("Trainee with ID {} not found", userId);
        }
        return trainee;
    }
    public boolean deleteTrainee(Long userId) {
        boolean removed = traineeStorage.remove(userId) != null;
        if (removed) {
            log.info("Trainee with ID {} successfully deleted", userId);
        } else {
            log.warn("Failed to delete trainee: ID {} does not exist", userId);
        }
        return removed;
    }
    public Trainee updateTrainee(Trainee trainee) {
        if (!traineeStorage.containsKey(trainee.getUserId())) {
            log.warn("Failed to update trainee: ID {} does not exist", trainee.getUserId());
            throw new IllegalArgumentException("Trainee with ID " + trainee.getUserId() + " does not exist");
        }

        traineeStorage.put(trainee.getUserId(), trainee);
        log.info("Trainee updated: ID={}, New Address={}", trainee.getUserId(), trainee.getAddress());
        return trainee;
    }
}
