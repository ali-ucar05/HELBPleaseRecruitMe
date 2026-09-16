package com.example;

// Cette classe permet de valider si la lettre de motivation d'un profile de candidat est valide.

public final class ApplicationProfileValidator{
    private static final String HTTPS_PREFIX = "https://";
    private static final String NUMBERS_REGEX = ".*\\d.*";

    private ApplicationProfileValidator(){}

    // Vérifie si la lettre de motivation contient le préfixe "https"
    // en utilisant une comparaison insensible à la casse.
    private static boolean containsHttps(String motivation){return motivation.toLowerCase().contains(HTTPS_PREFIX);}

    // Vérifie si la lettre de motivation contient des chiffres
    // en utilisant une expression régulière (regex).
    private static boolean containsNumbers(String motivation){return motivation.matches(NUMBERS_REGEX);}

    // Cette méthode permet de vérifier si le candidat est valide.
    // Un candidat est considéré comme invalide si sa lettre de motivation
    // contient des chiffres ou la chaîne "https".
    public static boolean isValidMotivation(String motivation){return !containsHttps(motivation) && !containsNumbers(motivation);}
}