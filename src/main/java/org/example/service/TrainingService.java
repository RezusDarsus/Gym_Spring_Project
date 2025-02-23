package org.example.service;
import lombok.extern.slf4j.Slf4j;
import org.example.dao.TrainingDao;
import org.example.entity.Training;
import org.example.entity.TrainingType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
public class TrainingService {
    private TrainingDao trainingDao;
    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
    public Training createTraining(Long traineeId, Long trainerId, String trainingName, TrainingType trainingTypeName, String trainingDate, Duration trainingDuration) {
        log.info("Creating training session: Name={}, Type={}, Date={}, Duration={} mins, TraineeID={}, TrainerID={}",
                trainingName, trainingTypeName.getTrainingTypeName(), trainingDate, trainingDuration.toMinutes(), traineeId, trainerId);

        Training training = new Training(traineeId, trainerId, trainingName, trainingTypeName, trainingDate, trainingDuration);
        Training savedTraining = trainingDao.saveTraining(training);

        log.info("Training session successfully created: ID={}, Name={}, Type={}, Date={}, Duration={} mins",
                savedTraining.getTraineeId() + "-" + savedTraining.getTrainerId(), savedTraining.getTrainingName(),
                savedTraining.getTrainingTypeName().getTrainingTypeName(), savedTraining.getTrainingDate(), savedTraining.getTrainingDuration().toMinutes());

        return savedTraining;
    }
    public Optional<Training> getTraining(Long traineeId, Long trainerId) {
        log.info("Fetching training session: TraineeID={}, TrainerID={}", traineeId, trainerId);

        Optional<Training> training = trainingDao.getTraining(traineeId, trainerId);

        if (training.isPresent()) {
            log.info("Training session found: ID={}, Name={}, Type={}, Date={}, Duration={} mins",
                    traineeId + "-" + trainerId, training.get().getTrainingName(),
                    training.get().getTrainingTypeName().getTrainingTypeName(), training.get().getTrainingDate(),
                    training.get().getTrainingDuration().toMinutes());
        } else {
            log.warn("No training session found for TraineeID={} and TrainerID={}", traineeId, trainerId);
        }

        return training;
    }
}
