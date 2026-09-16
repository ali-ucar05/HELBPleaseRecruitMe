package com.example;

import java.util.ArrayList;

// Cette interface définit une méthode commune aux différentes stratégies
// de sélection de profils.
//
// Elle permet aux classes de stratégie d’implémenter leur propre logique
// de sélection des candidats à partir d’une liste de profils.

public interface ProfileSelectionStrategy{
    public abstract ArrayList<ApplicationProfile> getSelectedProfiles(ArrayList<ApplicationProfile> profilesList);
}