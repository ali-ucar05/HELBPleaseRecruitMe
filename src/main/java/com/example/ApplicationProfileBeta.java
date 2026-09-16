package com.example;

// Cette classe fille de la classe ApplicationProfile
// permet de définir le type du profil du candidat en Beta.

public class ApplicationProfileBeta extends ApplicationProfile{
    private final String BETA_PROFILE_PREFIX = "-BETA-";

    public ApplicationProfileBeta(String name, String picture, String motivation, int distance, int distanceScore, int motivationScore)
    {
        super(name, picture, motivation, distance, distanceScore, motivationScore);
    }

    public ApplicationProfileBeta(ApplicationProfile applicationProfile){
        super(applicationProfile.getName(), 
        applicationProfile.getPicture(), 
        applicationProfile.getMotivation(), 
        applicationProfile.getDistance(), 
        applicationProfile.getDistanceScore(), 
        applicationProfile.getMotivationScore());
    }

    // permet de retouner le  nom du candidat avec le prefixe du type de profile pour l'affichage de celui-ci.
    @Override
    public String getDisplayedName(){return getName();}

    // Permet de retourner le texte de motivation avec le prefixe -BETA-
    @Override
    public String getDisplayedMotivationLetter(){return BETA_PROFILE_PREFIX + " " + getMotivation();}
}