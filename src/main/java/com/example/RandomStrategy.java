package com.example;

import java.util.ArrayList;

// Cette stratégie permet de sélectionner deux profils aléatoirement
// parmi la liste des candidats.
//
// Chaque profil est choisi au hasard sans répétition, jusqu’à atteindre
// le nombre maximum de candidats défini dans un match.

public class RandomStrategy implements ProfileSelectionStrategy{
    private static RandomStrategy instance;

    private RandomStrategy()
    {}

    @Override
    public ArrayList<ApplicationProfile> getSelectedProfiles(ArrayList<ApplicationProfile> profilesList)
    {
        ArrayList<ApplicationProfile> selectedProfiles = new ArrayList<>();

        for(int counter = 0; counter < Match.MAX_CANDIDATES_NUMBERS; counter++)
        {
            int profileIndex = (int)(Math.random() * profilesList.size());
            
            while (selectedProfiles.contains(profilesList.get(profileIndex)))
            {
                profileIndex = (int)(Math.random() * profilesList.size());
            }

            selectedProfiles.add(profilesList.get(profileIndex));
        }
        
        return selectedProfiles;
    }

    // Implémentation du Singleton pour garantir une seule instance
    public static RandomStrategy getInstance() {
        if (instance == null) {
            instance = new RandomStrategy();
        }
        return instance;
    }
}