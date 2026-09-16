package com.example;

import java.util.ArrayList;

// Cette classe joue un rôle pour créer les profiles des candidats.

public class ApplicationProfileFactory{
    private final String SPACE_REGEX = "\\s+";

    // Cette méthode envoie la liste de tout les profiles de candidats valide.
    public ArrayList<ApplicationProfile> createProfilesList()
    {
        ApplicationProfile applicationProfile;
        // Récupère les données de chaque candidats valides dans une liste.
        ArrayList<ApplicationProfileData> applicationProfilesDatasList = ApplicationProfileService.createProfilesDatasList();
        // Initialisation d'une nouvelle liste pour stocker tout les profiles de candidats.
        ArrayList<ApplicationProfile> profilesList = new ArrayList<>();

        // On créer par défaut un candidat de type alpha pour changer plus tard le type de profile.
        for (ApplicationProfileData applicationProfileData : applicationProfilesDatasList)
        {
            ApplicationProfile sourceProfile  = new ApplicationProfileAlpha(
                applicationProfileData.getName(),
                applicationProfileData.getPicture(),
                applicationProfileData.getMotivation(),
                applicationProfileData.getDistance(),
                ApplicationProfileScoreCalculator.calculateDistanceScore(applicationProfileData.getDistance()),
                ApplicationProfileScoreCalculator.calculateMotivationScore(applicationProfileData.getMotivation())
            );

            // On verifie le type du profile avec le nom du candidat on change si besoin.
            if(ApplicationProfileTypeResolver.determineProfileType(applicationProfileData.getName()).equals(ApplicationProfileTypeResolver.PROFILE_TYPE_ALPHA))
            {
                applicationProfile = sourceProfile;
            }
            else
            {
                applicationProfile = new ApplicationProfileBeta(sourceProfile);
            }
        
            profilesList.add(applicationProfile);
        }
        
        return profilesList;
    }
}