package com.example;

import java.util.ArrayList;

// Cette stratégie permet de sélectionner deux profils différents
// parmi les candidats non encore sélectionnés depuis l’activation de ce mode.
//
// La sélection se fait selon un critère de score local,
// favorisant un grand écart de distance entre les profils.

public class LocalScoreStrategy implements ProfileSelectionStrategy{
    private static LocalScoreStrategy instance;
    private ArrayList<ApplicationProfile> allSelectedProfilesList;

    private LocalScoreStrategy()
    {
        allSelectedProfilesList = new ArrayList<>();
    }

    // Retourne une liste de profils sélectionnés selon le score local (distance),
    // en excluant ceux déjà sélectionnés auparavant.
   @Override
    public ArrayList<ApplicationProfile> getSelectedProfiles(ArrayList<ApplicationProfile> profilesList)
    {
        return ProfileSelectionMethods.selectByLocalScore(profilesList, allSelectedProfilesList);
    }

    // Implémentation du pattern Singleton pour garantir une seule instance
    // de la stratégie LocalScore.
    public static LocalScoreStrategy getInstance() {
        if (instance == null) {
            instance = new LocalScoreStrategy();
        }
        return instance;
    }
}