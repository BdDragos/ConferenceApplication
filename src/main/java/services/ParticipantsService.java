package services;

import controller.ParticipantsController;
import model.Participants;

import java.util.List;

/**
 * Created by Cosmin on 6/4/2017.
 */
public class ParticipantsService {
    private ParticipantsController participantsController;
    public ParticipantsService(ParticipantsController participantsController) {
        this.participantsController = participantsController;
    }
    public List<Participants> getAll() {
        return participantsController.getAll();
    }
}
