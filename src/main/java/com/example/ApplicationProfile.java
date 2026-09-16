package com.example;

// Cette classe stocke les informations du profil d’un candidat :
// nom, image, distance, lettre de motivation et score.

public abstract class ApplicationProfile {
    private String name;
    private String picture;
    private String motivation;
    private int distance;
    private int distanceScore;
    private int motivationScore;
    public static int MAX_PROFILE_SCORE = 100;
 
    public ApplicationProfile(String name, String picture, String motivation, int distance, int distanceScore, int motivationScore){
        this.name = name;
        this.picture = picture;
        this.motivation = motivation;
        this.distance = distance;
        this.distanceScore = distanceScore;
        this.motivationScore = motivationScore;
    }
 
    public String getName(){return this.name;}

    public String getPicture(){return this.picture;}

    public String getMotivation(){return this.motivation;}

    public int getDistance(){return this.distance;}

    public int getDistanceScore(){return this.distanceScore;}

    public int getMotivationScore(){return this.motivationScore;}

    public int getScore(){return this.distanceScore + this.motivationScore;}

    public abstract String getDisplayedName();

    public abstract String getDisplayedMotivationLetter();
}