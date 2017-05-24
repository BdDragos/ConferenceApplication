package repository;

import model.File;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mikha on 5/23/2017.
 */
public class FileRepositoryTest {

    @Test
    public void saveFile() {
        FileRepository fileRepo = new FileRepository();
        int currentSize = fileRepo.fileList.size();
        fileRepo.save(new File());
        assertEquals( currentSize+1, fileRepo.fileList.size());
    }

    @Test
    public void getAllFiles(){
        FileRepository fileRepo = new FileRepository();
        int cSize = fileRepo.fileList.size();
        int i = fileRepo.getAll().size();
        assertEquals(cSize, i);
    }

    @Test
    public void getById(){
        FileRepository fileRepo = new FileRepository();
        File f = fileRepo.getById(1);
        assertEquals(f.getIdF(), 1);
    }

    @Test
    public void updateFile(){
        FileRepository fileRepo = new FileRepository();
        File existingFile = fileRepo.getById(1);
        File newFile = existingFile;
        int reviewCount = existingFile.getReviewCount();
        newFile.setReviewCount(++reviewCount);
        fileRepo.updateFile(existingFile, newFile);
        File updatedFile = fileRepo.getById(1);
        assertEquals(updatedFile.getReviewCount(), reviewCount);
    }
}