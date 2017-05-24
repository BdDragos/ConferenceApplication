package services;

import model.Sections;
import repository.SectionRepository;

import java.util.List;

/**
 * Created by stefanvacareanu on 13/05/2017.
 */
public class SectionService {
    SectionRepository repo;

    public SectionService(SectionRepository repo){
        this.repo = repo;
    }

    public List<Sections> getAll(){
        return repo.getAll();
    }

    public Integer addSection(int idConf, int sesC, String name,String h,String d){
        return repo.addSection(idConf,sesC,name,h,d);
    }

    public void updateSection(Integer SectionID, int sesC, String name,String h,String d ){
        repo.updateSection(SectionID,sesC,name,h,d);
    }

    public void deleteSection(Integer SectionID){
        repo.deleteSection(SectionID);
    }

    public List<Sections> getAllSectionByConfId(int confid){
        return repo.getAllSectionByConfId(confid);
    }
}
