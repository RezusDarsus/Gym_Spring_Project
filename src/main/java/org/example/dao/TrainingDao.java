package org.example.dao;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Repository
public class TrainingDao {
    private Map<String, Training> trainingStorage;
    @Autowired
    public void setTrainingStorage(Map<String, Training> trainingStorage) {
        this.trainingStorage = trainingStorage;
    }

    public Training saveTraining(Training training) {
        String compositePrimaryKey = training.getTraineeId() + "-" + training.getTrainerId();

        if (trainingStorage.containsKey(compositePrimaryKey)) {
            log.warn("Failed to save training: Training with ID {} already exists", compositePrimaryKey);
            throw new IllegalArgumentException("Training with ID " + compositePrimaryKey + " already exists");
        }

        trainingStorage.put(compositePrimaryKey, training);
        log.info("Training saved: ID={}, Type={}, TraineeID={}, TrainerID={}",
                compositePrimaryKey, training.getTrainingTypeName().getTrainingTypeName(),
                training.getTraineeId(), training.getTrainerId());
        return training;
    }


    public Optional<Training> getTraining(Long traineeId, Long trainerId) {
        String compositePrimaryKey = traineeId + "-" + trainerId;
        Optional<Training> training = Optional.ofNullable(trainingStorage.get(compositePrimaryKey));

        if (training.isPresent()) {
            log.info("Fetched training: ID={}, Type={}, TraineeID={}, TrainerID={}",
                    compositePrimaryKey, training.get().getTrainingTypeName().getTrainingTypeName(),
                    traineeId, trainerId);
        } else {
            log.warn("Training with ID {} not found", compositePrimaryKey);
        }

        return training;
    }
}
