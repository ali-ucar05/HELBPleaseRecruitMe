package com.example;

// Cette classe contient la configuration utilisée pour le calcul du score bonus
// en fonction du score de delta match.
// Elle définit la marge d’erreur autorisée ainsi que le pourcentage de bonus
// appliqué selon l’écart (delta) du matching.
public class BonusScoreConfig {
    public static final double ALLOWED_ERROR_PERCENT = 0.15;
    public static final double BONUS_PERCENT = 0.20;
}