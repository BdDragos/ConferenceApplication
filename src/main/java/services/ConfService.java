package services;

import model.Conference;
import repository.ConfRepository;

import java.util.List;

/**
 * Created by stefanvacareanu on 13/05/2017.
 */
public class ConfService {
    ConfRepository repo;

    public ConfService(ConfRepository repo){
        this.repo = repo;
    }

    public List<Conference> getAll(){
        return repo.getAll();
    }

    public Integer addConference(int noPart, String name, String deadlineprop,String deadlineabs){
        return repo.addConference(noPart,name,deadlineprop, deadlineabs);
    }

    public void updateConference(Integer ConfID, int noPart, String name, String deadlineprop,String deadlineabs ){
        repo.updateConference(ConfID,noPart,name,deadlineprop, deadlineabs);
    }

    public void deleteConference(Integer ConfID){
        repo.deleteConference(ConfID);
    }
}
