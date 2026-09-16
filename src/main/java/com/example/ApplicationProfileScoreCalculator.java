package com.example;

import java.util.ArrayList;
import java.io.File;

// Cette Classe joue un rôle pour calculer le score du candidat.

public final class ApplicationProfileScoreCalculator
{
    private static final String DIRECTORY_PATH = "src/main/resources/DATA";
    private static final String KEYWORDS_FILE_NAME = "/keywords.txt";
    private static final int MIN_DISTANCE = 1;
    private static final int MAX_DISTANCE = 200;
    private static final int DISTANCE_MAX_SCORE = 50;
    private static final int MOTIVATION_MAX_SCORE = 50;
    private static final String SPACE_REGEX = "\\s+";
    private static String keyWordsContent = HELBPleaseRecruitMeParser.readDataFile(new File(DIRECTORY_PATH + KEYWORDS_FILE_NAME));
    private static String[] splitedKeywords = keyWordsContent.split(SPACE_REGEX);
    
    // Cette méthode prend en paramètre la distance du candidat.
    // On vérifie d’abord si la distance est inférieure ou égale
    // à la distance minimale afin de retourner le score maximal.
    // Si la distance est supérieure ou égale à la distance maximale,
    // le score retourné est 0.
    // Sinon, le score est calculé proportionnellement
    // selon la distance du candidat.
    public static int calculateDistanceScore(int distance)
    {
        if(distance <= MIN_DISTANCE){return DISTANCE_MAX_SCORE;}

        else if(distance >= MAX_DISTANCE){return 0;}

        return (int) Math.round((double) DISTANCE_MAX_SCORE * (MAX_DISTANCE-distance) / MAX_DISTANCE-1);
    }

    // Cette méthode calcule le score de motivation du candidat.
    // Elle compare les mots de la lettre de motivation
    // avec la liste des mots-clés définis.
    // Chaque mot-clé trouvé une seule fois augmente le compteur.
    // Le score final est calculé proportionnellement
    // au nombre de mots-clés trouvés par rapport
    // au nombre total de mots-clés disponibles.
    public static int calculateMotivationScore(String motivation)
    {
        ArrayList<String> keyWordsList = new ArrayList<>();
        int counterOfKeywords = 0;
        String[] candidateSplitedWords = motivation.split(SPACE_REGEX);

        for(String keyword : splitedKeywords)
        {
            for(String word : candidateSplitedWords)
            {
                if(keyword.equals(word))
                {
                    if(!keyWordsList.contains(keyword))
                    {
                        keyWordsList.add(keyword);
                        counterOfKeywords++;
                    }
                }
            }
        }

        return (int) Math.round((double) MOTIVATION_MAX_SCORE * counterOfKeywords / splitedKeywords.length);
    }
}