package com.example;

import java.util.ArrayList;
import java.util.Random;

// Cette stratégie permet de choisir deux profiles différent pas encore sélectionné depuis l'activation de ce mode.
// un critère est choisit au hasard soit la distance ou les compétences en fonction de cette critères les profiles sont 
// choisit avec un grand écart de distance(LocalScoreStrategy) ou avec un petit écart de score de lettre de motivation(SkillScoreStrategy).

public class AnyStrategy implements ProfileSelectionStrategy{
    private static AnyStrategy instance;
    private String criteria = "";
    private Random random = new Random();

    ArrayList<ApplicationProfile> allSelectedProfilesList = new ArrayList<>();

    private AnyStrategy()
    {
        allSelectedProfilesList = new ArrayList<>();
    }

    // Envoie aléatoirement les profils non encore sélectionnés
    // selon le critère choisi : distance ou lettre de motivation.
    // Paramètre : ArrayList<ApplicationProfile> profilesList
    // -> liste de tous les candidats.
    @Override
    public ArrayList<ApplicationProfile> getSelectedProfiles(ArrayList<ApplicationProfile> profilesList)
    {   
        setRandomCriteria();
        if(this.criteria.equals(ProfileSelectionStrategyFactory.LOCAL_SCORE))
        {
            return ProfileSelectionMethods.selectByLocalScore(profilesList, allSelectedProfilesList);
        }
        else
        {
            return ProfileSelectionMethods.selectBySkillScore(profilesList, allSelectedProfilesList);
        }
    }

    // Implémentation du pattern Singleton pour garantir une seule instance
    // de la stratégie AnyStrategy.
    public static AnyStrategy getInstance() {
        if (instance == null) {
            instance = new AnyStrategy();
        }
        return instance;
    }

    // Choisit aléatoirement le critère : distance ou localisation.
    private void setRandomCriteria()
    {
        if (random.nextBoolean()) {
            this.criteria = ProfileSelectionStrategyFactory.LOCAL_SCORE;
        } else {
            this.criteria = ProfileSelectionStrategyFactory.SKILL_SCORE;
        }
    }
}