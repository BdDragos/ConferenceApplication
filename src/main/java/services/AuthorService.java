package services;

import model.Author;
import model.Conference;
import model.File;
import model.Sections;
import repository.AuthorsRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Dragos on 5/8/2017.
 */
public class AuthorService
{
    AuthorsRepository repo;
    public AuthorService(AuthorsRepository repo)
    {
         this.repo = repo;
    }

    public List<File> getAllFiles()
    {
        return repo.getAllFiles();
    }

    public List<Author> getAllAuthors() { return repo.getAllAuthor(); }

    public List<Conference> getAllConf()
    {
        return repo.getAllConferences();
    }

    public List<Sections> findByConfId(int id)
    {
        return repo.findByConfId(id);
    }

    public List<String> returnDeadline(int id)
    {
        return repo.returnDeadline(id);
    }

    public List<Author> getAfterFileId(int id)
    {
        return repo.getAfterFileId(id);
    }

    public List<Author> getAfterAuthotNOTid(List<Author> id)
    {
        return repo.getAfterAuthotNOTid(id);
    }

    public int uploadFile(String prop,String key,String top, String link,String abs,List<Author> autr, int idses,String deadline)
    {
        if (deadline.compareTo("0") == 0)
            return 2;
        Date got = new Date();
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            got = dtf.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Date currentDate = new Date();
        if (got.before(currentDate) || got.equals(currentDate))
            return repo.uploadFile(prop,key,top,link,abs,idses,autr);
        else
            return 3;
    }

    public int updateFile(String prop, String key, String top, String link, String abs, List<Author> autr, String deadline, int idses, int idf)
    {
        if (deadline.compareTo("0") == 0)
            return 2;
        Date got = new Date();
        DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            got = dtf.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
        Date currentDate = new Date();
        if (got.before(currentDate) || got.equals(currentDate))
            return repo.updateFile(prop,key,top,link,abs,idses,autr,idf);
        else
            return 3;
    }
}
