package com.example;

// Cette classe joue le rôle de factory pour créer les stratégies
// de sélection de profils des candidats en fonction du choix du recruteur.
//
// Elle retourne une instance de stratégie adaptée (Random, LocalScore,
// SkillScore ou Any) selon le paramètre fourni.

public class ProfileSelectionStrategyFactory{
    public static final String RANDOM = "Random";
    public static final String LOCAL_SCORE = "LocalScore";
    public static final String SKILL_SCORE = "SkillScore";
    public static final String ANY = "Any";

    public static ProfileSelectionStrategy createStrategy(String strategyChoice)
    {
          switch (strategyChoice) {
            case RANDOM:
                return RandomStrategy.getInstance();
            case LOCAL_SCORE:
                return LocalScoreStrategy.getInstance();
            case SKILL_SCORE:
                return SkillScoreStrategy.getInstance();
            case ANY:
                return AnyStrategy.getInstance();
        }
        return RandomStrategy.getInstance();
    }
}