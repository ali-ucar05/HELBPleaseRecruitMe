package com.example;

import java.util.ArrayList;


// Cette interface permet de mettre à jour l’interface du recruteur
// lorsqu’il y a des changements dans les profils des candidats,
// dans les matches ou dans les scores.
//
// Elle suit le principe de l’Observer pour notifier la vue
// des modifications effectuées dans la session.

public interface HELBPleaseRecruitMeObserver {
    public abstract void updateProfiles(Match match);

    public abstract void updateMatchesList(ArrayList<Match> matchesList);

    public abstract void updateRecruiterStatistics(int score, int bonusScore, double workingRate);

    public abstract void onNoCandidatesAvailable();
}