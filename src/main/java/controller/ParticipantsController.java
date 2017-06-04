package controller;

import model.Participants;
import repository.ParticipantsRepository;

import java.util.List;

/**
 * Created by Cosmin on 6/4/2017.
 */
public class ParticipantsController {
    private ParticipantsRepository participantsRepository;
    public ParticipantsController(ParticipantsRepository participantsRepository) {
        this.participantsRepository = participantsRepository;
    }
    public List<Participants> getAll() {
        return participantsRepository.getAll();
    }
}
