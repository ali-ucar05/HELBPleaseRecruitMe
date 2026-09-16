package com.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// Cette classe permet de lire les fichiers et d’écrire le fichier de session.
//
// Elle fournit des méthodes pour parser les données des candidats
// ainsi que pour générer un rapport de session contenant les matchs
// et les scores de l’utilisateur.

public final class HELBPleaseRecruitMeParser{
    private static final String FILENAME = "session_report.txt";
    private static final String DELIMETER = "--------------------";
    private static final String CURRENT_SCORE_TEXT = "Current Score : ";
    private static final String BONUS_SCORE_TEXT = "Bonus Score : ";
    private static final String FINAL_SCORE_TEXT = "Final Score : ";
    private static final String ERROR_TXT = "An error occurred.";
    private static final String SUCCESS_TXT = "Successfully wrote to the file.";
    private static final String SPACE_PREFIX = " ";
    private static final String RETURN_LINE_PREFIX = "\n";
    private static final String START_SELECTED_PREFIX = "(";
    private static final String END_SELECTED_PREFIX = ")";

    private HELBPleaseRecruitMeParser(){}

    // lit le fichier de données et retourne son contenu sous forme de String
    // en concaténant les lignes.
    public static String readDataFile(File file)
    {
        String data = "";
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                if(!data.isEmpty())
                {
                    data += SPACE_PREFIX;
                }
                data += myReader.nextLine();
            }
        } catch (FileNotFoundException e) {    
            System.out.println(ERROR_TXT);
            e.printStackTrace();
        }

        return data.trim();
    }

    // Cette méthode prend la liste des matchs réalisés par l’utilisateur,
    // son score actuel et son score bonus.
    // Elle écrit un fichier de session contenant l’historique des matchs,
    // les choix effectués, le score de delta match ainsi que les résultats
    // finaux de l’utilisateur.
    public static void writeSessionFile(ArrayList<Match> matchesList, int currentScore, int bonusScore)
    {
        try {
            FileWriter myWriter = new FileWriter(FILENAME);
            for (Match match : matchesList)
            {
                myWriter.write(match.toString().replace(match.getSelectedProfile().getName(), START_SELECTED_PREFIX+match.getSelectedProfile().getName()+END_SELECTED_PREFIX)+RETURN_LINE_PREFIX);
            }

            myWriter.write(DELIMETER + RETURN_LINE_PREFIX);
            myWriter.write(CURRENT_SCORE_TEXT +  String.valueOf(currentScore) + RETURN_LINE_PREFIX);
            myWriter.write(BONUS_SCORE_TEXT +  String.valueOf(bonusScore) + RETURN_LINE_PREFIX);
            myWriter.write(FINAL_SCORE_TEXT +  String.valueOf(currentScore + bonusScore) + RETURN_LINE_PREFIX);
            myWriter.close();  
            System.out.println(SUCCESS_TXT);
            readSessionFile(new File(FILENAME));
        } catch (IOException e) {
            System.out.println(ERROR_TXT);
            e.printStackTrace();
        }
    }

    private static void readSessionFile(File file)
    {
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                System.out.println(myReader.nextLine());
            }
        } catch (FileNotFoundException e) {    
            System.out.println(ERROR_TXT);
            e.printStackTrace();
        }
    }
}