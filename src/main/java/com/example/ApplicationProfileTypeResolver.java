package com.example;

import java.util.Random;

// Cette Classe joue un rôle pour déterminer le type de profile de candidat.

public class ApplicationProfileTypeResolver
{
    private static Random random = new Random();
    public static final String PROFILE_TYPE_ALPHA = "Alpha";
    public static final String PROFILE_TYPE_BETA = "Beta";

    // Cette méthode détermine le type du profil à partir du nom du candidat.
    // Si la première lettre est une voyelle, le profil est de type Alpha.
    // Si la première lettre est une consonne, le profil est de type Beta.
    // Cas particulier : si la première lettre est 'R', le profil peut être Alpha ou Beta.
    public static String determineProfileType(String name)
    {
        char nameFirstLetter = name.charAt(0);
        String vowels = "AEUIOY";
        char letterR = 'R';

        if(vowels.indexOf(nameFirstLetter) != -1)
        {
            return PROFILE_TYPE_ALPHA;
        }

        else if (nameFirstLetter == letterR)
        {
            if (random.nextBoolean()) {
                return PROFILE_TYPE_ALPHA;
            } else {
                return PROFILE_TYPE_BETA;
            }
        }

        return PROFILE_TYPE_BETA;
    }
}