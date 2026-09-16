package com.example;

import java.util.ArrayList;

// Cette stratégie permet de sélectionner deux profils différents
// parmi ceux qui n’ont pas encore été sélectionnés depuis l’activation du mode.
//
// La sélection favorise des candidats ayant un petit écart
// dans leur score de lettre de motivation (skill score).

public class SkillScoreStrategy implements ProfileSelectionStrategy{
    private static SkillScoreStrategy instance;
    private ArrayList<ApplicationProfile> allSelectedProfilesList;

    private SkillScoreStrategy()
    {
        allSelectedProfilesList = new ArrayList<>();
    }

    @Override
    public ArrayList<ApplicationProfile> getSelectedProfiles(ArrayList<ApplicationProfile> profilesList)
    {
        return ProfileSelectionMethods.selectBySkillScore(profilesList, allSelectedProfilesList);
    }

    // Implémentation du Singleton pour garantir une seule instance
    public static SkillScoreStrategy getInstance() {
        if (instance == null) {
            instance = new SkillScoreStrategy();
        }
        return instance;
    }
}