package com.example;

// Cette classe fille de la classe ApplicationProfile
// permet de définir le type du profil du candidat en Alpha.

public class ApplicationProfileAlpha extends ApplicationProfile {
    private final String ALPHA_PROFILE_PREFIX = "+ALPHA+";
    
    public ApplicationProfileAlpha(String name, String picture, String motivation, int distance, int distanceScore, int motivationScore)
    {
        super(name, picture, motivation, distance, distanceScore, motivationScore);
    }

    // permet de retouner le  nom du candidat avec le prefixe du type de profile pour l'affichage de celui-ci.
    @Override
    public String getDisplayedName(){return getName() + " " + ALPHA_PROFILE_PREFIX;}

    // permet de retourner le texte de motivation à afficher.
    @Override
    public String getDisplayedMotivationLetter(){return getMotivation();}
}