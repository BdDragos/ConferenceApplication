package model;

import java.util.Date;

/**
 * Created by Dragos on 5/8/2017.
 */
public class Conference
{
    private int idConference;
    private String name;
    private int noParticipants;
    private String deadlineProposal;
    private String deadlineAbstract;


    public String getDeadlineProposal() {
        return deadlineProposal;
    }

    public void setDeadlineProposal(String deadlineProposal) {
        this.deadlineProposal = deadlineProposal;
    }

    public String getDeadlineAbstract() {
        return deadlineAbstract;
    }

    public void setDeadlineAbstract(String deadlineAbstract) {
        this.deadlineAbstract = deadlineAbstract;
    }

    public Conference(int idConference, String name, int noParticipants, String deadlineProposal) {
        this.idConference = idConference;
        this.name = name;
        this.noParticipants = noParticipants;
        this.deadlineProposal = deadlineProposal;
        this.deadlineAbstract = deadlineAbstract;

    }

    public Conference(String name, int noParticipants, String deadlineProposal, String deadlineAbstract) {
        this.name = name;
        this.noParticipants = noParticipants;
        this.deadlineProposal = deadlineProposal;
        this.deadlineAbstract = deadlineAbstract;

    }

    public Conference() {}

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdConference() {
        return idConference;
    }

    public void setIdConference(int idConference) {
        this.idConference = idConference;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }
}
