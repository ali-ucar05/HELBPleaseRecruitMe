package com.example;

import java.util.ArrayList;

// Cette classe contient les informations et l’état de la session du recruteur.
//
// Elle gère le déroulement de la session : sélection des profils,
// création des matchs, calcul des scores, bonus, et statistiques.
//
// Elle permet également de générer les données nécessaires
// à la fin de la session pour produire un rapport complet.

public class TrainingSession{
    private ApplicationProfileFactory applicationProfileFactory;
    private ProfileSelectionStrategy currentStrategy;
    private ApplicationProfile viewedProfile;
    private double workingRate;
    private int score;
    private int bonusScore;
    private ArrayList<ApplicationProfile> profilesList;
    private ArrayList<Match> matchesList;
    private HELBPleaseRecruitMeObserver observer;
    private Match currentMatch;
    private double elapsedTime;
    private ProfileSelectionStrategyFactory profileSelectionStrategyFactory;

    public TrainingSession(HELBPleaseRecruitMeObserver observer, String initialStrategy){
        this.applicationProfileFactory = new ApplicationProfileFactory();
        this.profileSelectionStrategyFactory = new ProfileSelectionStrategyFactory();
        this.workingRate = 0;
        this.score = 0;
        this.bonusScore = 0;
        this.profilesList = applicationProfileFactory.createProfilesList();
        this.matchesList = new ArrayList<>();
        this.observer = observer;
        setCurrentStrategy(initialStrategy);
        changeProfiles();
        updateRecruiterStatistics();
    }

    public double getWorkingRate(){return this.workingRate;}

    public int getScore(){return this.score;}

    public int getBonusScore(){return this.bonusScore;}

    public ArrayList<ApplicationProfile> getProfilesList(){return this.profilesList;}

    public ArrayList<Match> getMatchesList(){return this.matchesList;}

    public Match getCurrentMatch(){return this.currentMatch;}

    public void setViewedProfile(ApplicationProfile viewedProfile){this.viewedProfile = viewedProfile;}

    // Cette va changer les candidats lorsque un match n'a pas commencer ou un match à été terminé.
    public void changeProfiles()
    {
        ArrayList<ApplicationProfile> selectedProfileList = currentStrategy.getSelectedProfiles(profilesList);

        if(selectedProfileList.size() == Match.MAX_CANDIDATES_NUMBERS)
        {
            this.currentMatch = new Match(selectedProfileList);
        }
        else
        {
            this.observer.onNoCandidatesAvailable();
        }

        this.observer.updateProfiles(currentMatch);
    }

    public void confirmMatchEnd(ApplicationProfile selectedProfile, String color)
    {
        this.currentMatch.setSelectedProfile(selectedProfile);
        this.currentMatch.calculateDeltaMatch();
        this.currentMatch.setMatchColor(color);
        this.matchesList.add(currentMatch);
        changeProfiles();
        updateRecruiterStatistics();
        // Mise à jour de la vue.
        this.observer.updateMatchesList(matchesList);
        this.observer.updateRecruiterStatistics(this.score, this.bonusScore, this.workingRate);
    }

    public void setCurrentStrategy(String strategyChoice){
        this.currentStrategy = profileSelectionStrategyFactory.createStrategy(strategyChoice);
    }

    // méthode est appeler chaque une seconde par la time line pour mettre à jour le temps écoulé.
    public void updateSessionElapsedTime(double time){this.elapsedTime += time;}

    public void updateRecruiterStatistics()
    {
        calculateRecruiterScore();
        calculateWorkingRate();
    }

    // Accorde 20% de point bonus avec le delta match si l'estmiation du delta match se situe dans la marge d'erreur 15% du score du candidat. 
    public void calculateScoreBonus(int estimatedScore){
        double allowedErrorPercent = BonusScoreConfig.ALLOWED_ERROR_PERCENT;
        double bonusPercent = BonusScoreConfig.BONUS_PERCENT;

        int candidateScore = this.viewedProfile.getScore();
        int deltaMatch = this.currentMatch.calculateDeltaMatchForBonusScore();

        int minEstimatedScore = (int) Math.round(candidateScore - (candidateScore * allowedErrorPercent));
        int maxEstimatedScore = (int) Math.round(candidateScore + (candidateScore * allowedErrorPercent));

        if (estimatedScore >= minEstimatedScore && estimatedScore <= maxEstimatedScore)
        {
            this.bonusScore += (int) Math.round(deltaMatch * bonusPercent);
            this.observer.updateRecruiterStatistics(this.score, this.bonusScore, this.workingRate);
        }
    }

    // Aditionne chaque delta match pour calculer le score du recruteur
    private void calculateRecruiterScore()
    {
        this.score = 0;
        for (Match match : matchesList)
        {
            this.score += match.getDeltaMatch();
        }
    }

    // Calcule le working rate en divisant par le temps ecoulé avec le nombre de match réalisé depuis le lancement de l'applciation.
    private void calculateWorkingRate()
    {
        if(this.elapsedTime <= 0)
        {
            this.workingRate = 0;
            return;
        }
        double millisDivider = 1000.0;
        this.workingRate = this.matchesList.size() / (this.elapsedTime / millisDivider);
    }
}