package model;

/**
 * Created by Dragos on 5/8/2017.
 */
public class Sections {
    int idSection;
    int idConference;
    int sesChair;
    String name;
    String hour;
    String date;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sections{" +
                "idSection=" + idSection +
                ", idConference=" + idConference +
                ", sesChair=" + sesChair +
                ", name=" + name +
                '}';
    }

    public Sections() {}

    public int getIdSection() {
        return idSection;
    }

    public void setIdSection(int idSection) {
        this.idSection = idSection;
    }

    public int getIdConference() {
        return idConference;
    }

    public void setIdConference(int idConference) {
        this.idConference = idConference;
    }

    public int getSesChair() {
        return sesChair;
    }

    public void setSesChair(int sesChair) {
        this.sesChair = sesChair;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sections(int idSection, int idConference, int sesChair, String name, String hour, String date) {
        this.idSection = idSection;
        this.idConference = idConference;
        this.sesChair = sesChair;
        this.name = name;
        this.hour = hour;
        this.date = date;
    }

    public Sections(int idConference, int sesChair, String name, String hour, String date) {
        this.idConference = idConference;
        this.sesChair = sesChair;
        this.name = name;
        this.hour = hour;
        this.date = date;
    }
}
