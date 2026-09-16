package com.example;

// Ce modèle est utilisé dans la factory pour stocker
// les informations des candidats valides lors de la lecture du dossier data.

public class ApplicationProfileData {
    private String name;
    private String picture;
    private String motivation;
    private int distance;

    public ApplicationProfileData(String name, String picture, String motivation, int distance)
    {
        this.name = name;
        this.picture = picture;
        this.motivation = motivation;
        this.distance = distance;
    }

    public String getName(){return this.name;}

    public String getPicture(){return this.picture;}
    
    public String getMotivation(){return this.motivation;}
    
    public int getDistance(){return this.distance;}
}