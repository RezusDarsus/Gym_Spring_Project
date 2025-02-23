package dao;

import org.example.dao.TrainerDao;
import org.example.entity.Trainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.*;

public class TrainerDaoTest {
    private TrainerDao trainerDao;

    @BeforeEach
    public void setUp() {
        this.trainerDao = new TrainerDao();
        this.trainerDao.setTrainerStorage(new ConcurrentHashMap<>());
    }
    private Trainer createDummyTrainer() {
        return new Trainer(
                1L,
                "John",
                "Doe",
                "John.Doe",
                "password",
                "Cardio"
        );
    }

    @Test
    public void testSaveTrainer() {
        Trainer trainer = this.createDummyTrainer();

        trainerDao.saveTrainer(trainer);
        Optional<Trainer> retrievedTrainer = trainerDao.getTrainer(1L);

        assertTrue(retrievedTrainer.isPresent());
        assertEquals(trainer, retrievedTrainer.get());
    }

    @Test
    public void getTrainer_WhenTrainerDoesNotExist_ReturnsEmptyOptional() {
        Optional<Trainer> retrievedTrainer = trainerDao.getTrainer(1L);

        assertTrue(retrievedTrainer.isEmpty());
    }

    @Test
    public void getTrainer_WhenTrainerExists_ReturnsTrainer() {
        Trainer trainer = this.createDummyTrainer();
        trainerDao.saveTrainer(trainer);

        Optional<Trainer> retrievedTrainer = trainerDao.getTrainer(1L);

        assertTrue(retrievedTrainer.isPresent());
        assertEquals(trainer, retrievedTrainer.get());
    }

    @Test
    public void testUpdateTrainer_WhenTrainerExists_DoNotThrowException() {
        Trainer trainer = this.createDummyTrainer();
        trainerDao.saveTrainer(trainer);

        trainer.setSpecialization("Cardio");

        assertDoesNotThrow(() -> trainerDao.updateTrainer(trainer));

        Optional<Trainer> retrievedTrainer = trainerDao.getTrainer(1L);
        assertTrue(retrievedTrainer.isPresent());

        assertEquals("Cardio", retrievedTrainer.get().getSpecialization());
    }

    @Test
    public void testUpdateTrainer_WhenTrainerDoesNotExist_ThrowIllegalArgumentException() {
        Trainer trainer = this.createDummyTrainer();

        assertThrows(IllegalArgumentException.class, () -> trainerDao.updateTrainer(trainer));
    }
}