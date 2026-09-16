package com.example;

import java.util.ArrayList;
import java.io.File;

// Cette classe joue un rôle pour récuperer les données des candidats dans le dossier Data.

public final class ApplicationProfileService{
    private static final String DIRECTORY_NAME = "/DATA/";
    private static final String DIRECTORY_PATH = "src/main/resources/DATA";
    private static final String IMAGE_FILE_NAME = "image.png";
    private static final String MOTIVATION_FILE_NAME = "motivation.txt";
    private static final String DISTANCE_FILE_NAME = "distance.dist";
    private static final String SEPARATOR_PREFIX = "/";

    // Cette méthode crée une liste contenant les données
    // des candidats valides en parcourant le dossier Data en lecture.
    // Pour chaque sous-dossier candidat, elle récupère :
    // le nom, l’image, la lettre de motivation et la distance.
    // Seuls les candidats avec une lettre de motivation valide
    // sont ajoutés dans la liste retournée.
    public static ArrayList<ApplicationProfileData> createProfilesDatasList()
    {
        ArrayList<ApplicationProfileData> profilesDatasList = new ArrayList<>();

        File directory = new File(DIRECTORY_PATH);
        File[] subDirectories = directory.listFiles();

        ApplicationProfile applicationProfile;
      
        for (File subDirectory : subDirectories) {

            if(!subDirectory.isDirectory())
            {
                continue;
            }

            File profileImageFile = new File(subDirectory, IMAGE_FILE_NAME);
            File profileMotivationFile = new File(subDirectory, MOTIVATION_FILE_NAME);
            File profileDistanceFile = new File(subDirectory, DISTANCE_FILE_NAME);

            String candidateName = subDirectory.getName();
            String candidatePicture = DIRECTORY_NAME + candidateName + SEPARATOR_PREFIX + IMAGE_FILE_NAME;
            String candidateMotivation = HELBPleaseRecruitMeParser.readDataFile(profileMotivationFile);

            if(!ApplicationProfileValidator.isValidMotivation(candidateMotivation))
            {
                continue;
            }

            int candidateDistance = Integer.valueOf(HELBPleaseRecruitMeParser.readDataFile(profileDistanceFile));

            profilesDatasList.add(new ApplicationProfileData(candidateName, candidatePicture, candidateMotivation, candidateDistance));
        }

        return profilesDatasList;
    }
}