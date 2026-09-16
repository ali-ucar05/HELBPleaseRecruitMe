package com.example;

import java.util.ArrayList;

// Cette classe représente un match entre deux candidats.
//
// Elle stocke les profils concernés, le candidat sélectionné,
// ainsi que le calcul du delta de score entre les candidats.
//
// Le delta match permet de mesurer la différence de performance
// entre le candidat choisi et les autres candidats du match.

public class Match{
    public static int MIN_CANDIDATES_NUMBERS = 2;
    public static int MAX_CANDIDATES_NUMBERS = MIN_CANDIDATES_NUMBERS;
    private ArrayList<ApplicationProfile> candidatesProfilesList;
    private ApplicationProfile selectedProfile;
    private int deltaMatch = 0;
    private String matchColor;
    private boolean bonusScoreUsed = false;

    public Match(ArrayList<ApplicationProfile> candidatesProfilesList)
    {
        this.candidatesProfilesList = candidatesProfilesList;
    }

    public ArrayList<ApplicationProfile> getCandidatesProfilesList(){return this.candidatesProfilesList;}

    public ApplicationProfile getSelectedProfile(){return this.selectedProfile;}

    public int getDeltaMatch(){return this.deltaMatch;}

    public void setSelectedProfile(ApplicationProfile selectedProfile){this.selectedProfile = selectedProfile;}

    public void calculateDeltaMatch(){
        ApplicationProfile maxScoreCandidate = getMaxScoreCandidate();
        ApplicationProfile minScoreCandidate = getMinScoreCandidate();
        int avgScore = getAVGScoreCandidate();

        if(MAX_CANDIDATES_NUMBERS==MIN_CANDIDATES_NUMBERS)
        {
            if(this.selectedProfile.equals(maxScoreCandidate))
            {
                this.deltaMatch = this.selectedProfile.getScore() - minScoreCandidate.getScore();
            }
            else
            {
                this.deltaMatch = this.selectedProfile.getScore() - maxScoreCandidate.getScore();
            }
        }

        else
        {
            if(this.selectedProfile.equals(maxScoreCandidate))
            {
                this.deltaMatch = this.selectedProfile.getScore() - avgScore;
            }
            else
            {
                this.deltaMatch = this.selectedProfile.getScore() - maxScoreCandidate.getScore();
            }
        }
    }

    public int calculateDeltaMatchForBonusScore()
    {
        if(MAX_CANDIDATES_NUMBERS==MIN_CANDIDATES_NUMBERS)
        {
            return getMaxScoreCandidate().getScore() - getMinScoreCandidate().getScore();
        }
        return getMaxScoreCandidate().getScore() - getAVGScoreCandidate();
    }

    public String getMatchColor(){return this.matchColor;}

    public void setMatchColor(String color){this.matchColor = color;}

    public boolean isBonusScoreUsed(){return this.bonusScoreUsed;}

    public void setBonusScoreUsed(){this.bonusScoreUsed = true;}

    @Override
    public String toString()
    {
        String sign = "";
        String text = candidatesProfilesList.get(0).getName();
        String versusPrefix = "  Vs ";
        String postivePrefix = "+";
        String negativePrefix = "-";
        String scorePrefix = " : ";

        if(this.deltaMatch >= 0)
        {
            sign = postivePrefix;
        }
        else
        {
            sign = negativePrefix;
        }

        for (int index = 1; index < candidatesProfilesList.size(); index++)
        {
            text += versusPrefix + candidatesProfilesList.get(index).getName();
        }

        text +=  scorePrefix + sign + Math.abs(this.deltaMatch);

        return text;
    }

    private ApplicationProfile getMaxScoreCandidate()
    {
        ApplicationProfile maxScoreCandidate = candidatesProfilesList.get(0);

        for (int index = 1; index < candidatesProfilesList.size(); index++)
        {
            if(candidatesProfilesList.get(index).getScore() > maxScoreCandidate.getScore())
            {
                maxScoreCandidate = candidatesProfilesList.get(index);
            }
        }

        return maxScoreCandidate;
    }

    private ApplicationProfile getMinScoreCandidate()
    {
        ApplicationProfile minScoreCandidate = candidatesProfilesList.get(0);

        for (int index = 1; index < candidatesProfilesList.size(); index++)
        {
            if(candidatesProfilesList.get(index).getScore() < minScoreCandidate.getScore())
            {
                minScoreCandidate = candidatesProfilesList.get(index);
            }
        }

        return minScoreCandidate;
    }

    private int getAVGScoreCandidate()
    {
        int totalScore = 0;
        int avgScore = 0;

        for (int index = 0; index < candidatesProfilesList.size(); index++)
        {
            totalScore += candidatesProfilesList.get(index).getScore();
        }

        avgScore = totalScore / (candidatesProfilesList.size());
        return avgScore;
    }
}