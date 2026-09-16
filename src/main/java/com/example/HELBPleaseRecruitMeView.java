package com.example;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

// Cette classe gère l’affichage de l’application côté utilisateur.
//
// Elle construit l’interface graphique principale (JavaFX),
// affiche les profils des candidats, les matchs, les scores
// et les statistiques du recruteur.
//
// Elle implémente l’interface HELBPleaseRecruitMeObserver afin
// de mettre à jour la vue automatiquement lors des changements
// de profils, de matchs ou de statistiques.

public class HELBPleaseRecruitMeView implements HELBPleaseRecruitMeObserver
{
    // Les variables finals + les controls de la vue principale.
    private final String FRAME_TITLE = "HELBPleaseRecruitMe";
    private final String SUBMIT_BUTTON_TEXT = "Recruit ";
    private final String CURRENT_SCORE_LABEL_TEXT = "Current Score : ";
    private final String BONUS_SCORE_LABEL_TEXT = "Bonus Score : ";
    private final String WORKING_RATE_LABEL_TEXT = "Working Rate : ";
    private final String WORKING_RATE_PREFIX = "/s"; 
    private final String ZERO_PREFIX = "0";
    private final int MAIN_FRAME_WIDTH = 1920;
    private final int MAIN_FRAME_HEIGHT = 1080;

    private final int HORIZONTAL_SPACING_VALUE = 50;
    private final int VERTICAL_SPACING_VALUE = 25;
    private final int PROFILE_BUTTON_WIDTH = 200;
    private final int PROFILE_BUTTON_HEIGHT = 200;
    private final int SUBMIT_BUTTON_WIDTH = PROFILE_BUTTON_WIDTH;
    private final int SUBMIT_BUTTON_HEIGHT = 30;
    private final int MATCHES_SCROLLPANE_WIDTH = MAIN_FRAME_WIDTH;
    private final int MATCHES_SCROLLPANE_HEIGHT = 100; 
    private final int MATCHES_RECTANGLE_WINNER_WIDTH = 30;
    private final int MATCHES_RECTANGLE_WINNER_HEIGHT = MATCHES_RECTANGLE_WINNER_WIDTH;
    private final String BORDER_STYLE = "-fx-border-color: black;";

    private Label currentScoreLabel = new Label(CURRENT_SCORE_LABEL_TEXT + ZERO_PREFIX);
    private Label bonusScoreLabel = new Label(BONUS_SCORE_LABEL_TEXT + ZERO_PREFIX);
    private ScrollPane scrollPane = new ScrollPane();
    public Stage stage;
    private Button workingRateButton = new Button(WORKING_RATE_LABEL_TEXT + ZERO_PREFIX + WORKING_RATE_PREFIX);

    // Les controls utilisé dans dans le controlleur pour les évenements de l'utilisateur.
    public ArrayList<Button> profilesDetailsButtonList = new ArrayList();
    public ArrayList<Button> profilesSubmitButtonList = new ArrayList();

    public HashMap<Button, ApplicationProfile> profilesDetailsButtonMap = new HashMap<Button, ApplicationProfile>();
    public HashMap<Button, ApplicationProfile> profilesSubmitButtonMap = new HashMap<Button, ApplicationProfile>();

    private final String RANDOM_RADIO_BTN_TXT = "Random";
    private final String LOCAL_SCORE_RADIO_BTN_TXT = "Local Score (+)";
    private final String SKILL_SCORE_RADIO_BTN_TXT = "Skill Score (-)";
    private final String ANY_RADIO_BTN_TXT = "Any";
    private final String SUBMIT_BTN_TXT = "Submit Session";
    public RadioButton randomRadioButton = new RadioButton(RANDOM_RADIO_BTN_TXT);
    public RadioButton localScoreRadioButton = new RadioButton(LOCAL_SCORE_RADIO_BTN_TXT);
    public RadioButton skillScoreRadioButton = new RadioButton(SKILL_SCORE_RADIO_BTN_TXT);
    public RadioButton anyRadioButton = new RadioButton(ANY_RADIO_BTN_TXT);
    public ToggleGroup strategiesGroup = new ToggleGroup();
    public Button SubmitSessionButton= new Button(SUBMIT_BTN_TXT);

    public static final String BACKGROUND_PREFIX = "-fx-background-color: ";
    public static final String[] HEX_COLORS = {"#b7c5d9", "#ddb7b0", "#C1E1C1", "#FAC898"};

    // Les variables finals + les controls de la vue profile du candidat spécifique.
    private final int CANDIDATE_FRAME_HEIGHT = 400;
    private final int CANDIDATE_FRAME_WIDTH = 800;
    private final String MOTIVATION_LABEL_TEXT = "Motivation : ";
    private final String DISTANCE_LABEL_TEXT = "Distance : ";
    private final String DISTANCE_PREFIX = " kms";
    private final String FRAME_TITLE_TEXT = " profile";
    private final double PROFILE_TEXT_MAX_WIDTH = 700;
    private final double PROFILE_CONTENT_WIDTH = 800;
    private final String FONT_NAME = "Arial";
    private final int FONT_SIZE = 24;
    private final int PERCENT_MULTIPLIER = 100;

    private final String BONUS_LABEL = "Bonus : Try to guess with " + BonusScoreConfig.ALLOWED_ERROR_PERCENT * PERCENT_MULTIPLIER + "% error margin to win " + BonusScoreConfig.BONUS_PERCENT * PERCENT_MULTIPLIER+"% bonus";
    private final String EXPLENATION_LABEL_TXT = "Guess The score value [0:" + String.valueOf(ApplicationProfile.MAX_PROFILE_SCORE) +"]";
    private Label candidateNameLabel = new Label();
    private Label candidateMotivationLabel = new Label();
    private Label candidateDistanceLabel = new Label();
    private Label bonusLabel = new Label(BONUS_LABEL);
    private Label explanationLabel = new Label(EXPLENATION_LABEL_TXT);

    // Les controls utilisé dans dans le controlleur pour les évenements de l'utilisateur.
    private final String CONFIRM_BTN_TXT = "Confirm the guess";
    private final String ONLY_NUMBERS_REGEX = "\\d+";
    public TextField bonusTextField = new TextField();
    public Button confirmButton = new Button(CONFIRM_BTN_TXT);
    
    // Les variables finals + les controls de la vue confirmBox.
    private final String CONFIRM_BOX_TITLE = "End Session Confirm";
    private final String CONFIRM_BOX_LABEL_TEXT = "Are you sure ?";
    private static boolean answer = false;
    private static int minWidth = 250;
    
    private final String YES_BTN_TXT = "Yes";
    private final String NO_BTN_TXT = "No";
    public Stage confirmBoxWindow = new Stage();
    public Button yesButton = new Button(YES_BTN_TXT);
    public Button noButton = new Button(NO_BTN_TXT);

    // les variables finals + les controls de la fenetre de fermeture.
    private final String DIALOG_STAGE_TITLE = "End Session";
    private final String DIALOG_STAGE_LABEL_TEXT = "There are no more matches ";
    private final String OK_BTN_TXT = "Ok";
    public Button okButton = new Button(OK_BTN_TXT);
    public Stage dialogStage = new Stage();

    public HELBPleaseRecruitMeView(Stage primaryStage)
    {
        // Les radios bouttons sont placés dans un group pour eviter que il ya plusieurs choix.
        randomRadioButton.setToggleGroup(strategiesGroup);
        localScoreRadioButton.setToggleGroup(strategiesGroup);
        skillScoreRadioButton.setToggleGroup(strategiesGroup);
        anyRadioButton.setToggleGroup(strategiesGroup);
        randomRadioButton.setSelected(true);

        // Initialisation de la fenêtre principale.
        initStage(primaryStage);
    }


   public void initStage(Stage primaryStage)
    {
        this.stage = primaryStage;
        this.stage.setTitle(FRAME_TITLE);

        ArrayList<VBox> profilesVboxList = new ArrayList<>();

        for (int counter = 0; counter < Match.MAX_CANDIDATES_NUMBERS; counter++)
        {
            Button profileDetailButton = new Button();
            Button profileSubmitButton = new Button();

            profileDetailButton.setPrefSize(PROFILE_BUTTON_WIDTH, PROFILE_BUTTON_HEIGHT);
            profileDetailButton.setStyle(BACKGROUND_PREFIX + HEX_COLORS[counter]);

            profileSubmitButton.setPrefSize(SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT);
            profileSubmitButton.setMinHeight(SUBMIT_BUTTON_HEIGHT);

            VBox profileVbox = new VBox(profileDetailButton, profileSubmitButton);
            profileVbox.setSpacing(VERTICAL_SPACING_VALUE);
            profileVbox.setAlignment(Pos.CENTER);

            profilesDetailsButtonList.add(profileDetailButton);
            profilesSubmitButtonList.add(profileSubmitButton);
            profilesVboxList.add(profileVbox);
        }

        workingRateButton.setPrefSize(SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT);
        workingRateButton.setMinHeight(SUBMIT_BUTTON_HEIGHT);

        randomRadioButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
        localScoreRadioButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
        skillScoreRadioButton.setPrefWidth(PROFILE_BUTTON_WIDTH);
        anyRadioButton.setPrefWidth(PROFILE_BUTTON_WIDTH);

        randomRadioButton.setAlignment(Pos.CENTER_LEFT);
        localScoreRadioButton.setAlignment(Pos.CENTER_LEFT);
        skillScoreRadioButton.setAlignment(Pos.CENTER_LEFT);
        anyRadioButton.setAlignment(Pos.CENTER_LEFT);

        VBox radiosBox = new VBox(
            randomRadioButton,
            localScoreRadioButton,
            skillScoreRadioButton,
            anyRadioButton
        );

        radiosBox.setSpacing(VERTICAL_SPACING_VALUE);
        radiosBox.setAlignment(Pos.CENTER);
        radiosBox.setFillWidth(true);

        radiosBox.setPrefHeight(PROFILE_BUTTON_HEIGHT);
        radiosBox.setMinHeight(PROFILE_BUTTON_HEIGHT);
        radiosBox.setMaxHeight(PROFILE_BUTTON_HEIGHT);

        VBox radioButtonVbox = new VBox(radiosBox, workingRateButton);
        radioButtonVbox.setSpacing(VERTICAL_SPACING_VALUE);
        radioButtonVbox.setAlignment(Pos.CENTER);
        radioButtonVbox.setPrefWidth(PROFILE_BUTTON_WIDTH);

        HBox topHbox = new HBox();

        for (VBox vbox : profilesVboxList)
        {
            topHbox.getChildren().add(vbox);
        }

        topHbox.getChildren().add(radioButtonVbox);

        topHbox.setSpacing(HORIZONTAL_SPACING_VALUE);
        topHbox.setAlignment(Pos.CENTER);

        scrollPane.setPrefSize(MATCHES_SCROLLPANE_WIDTH, MATCHES_SCROLLPANE_HEIGHT);

        HBox middleHbox = new HBox(scrollPane);
        middleHbox.setAlignment(Pos.CENTER);

        SubmitSessionButton.setPrefSize(SUBMIT_BUTTON_WIDTH, SUBMIT_BUTTON_HEIGHT);

        HBox bottomHbox = new HBox(currentScoreLabel, bonusScoreLabel, SubmitSessionButton);
        bottomHbox.setSpacing(HORIZONTAL_SPACING_VALUE);
        bottomHbox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(topHbox, middleHbox, bottomHbox);
        layout.setSpacing(VERTICAL_SPACING_VALUE);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void updateProfiles(Match match)
    {
        ArrayList<ApplicationProfile> candidateList = match.getCandidatesProfilesList();

        profilesDetailsButtonMap.clear();
        profilesSubmitButtonMap.clear();

        for(int index = 0; index < candidateList.size(); index++)
        {
            ApplicationProfile candidate = candidateList.get(index);
            Image profileImage = new Image(getClass().getResource(candidate.getPicture()).toExternalForm());
            ImageView profileImageView = new ImageView(profileImage);
            profileImageView.setFitHeight(PROFILE_BUTTON_HEIGHT);
            profileImageView.setPreserveRatio(true);
            Button profileDetailsButton = profilesDetailsButtonList.get(index);
            Button profileSubmitButton = profilesSubmitButtonList.get(index);
            profileDetailsButton.setGraphic(profileImageView);
            profileSubmitButton.setText(SUBMIT_BUTTON_TEXT + candidate.getName());
            profilesDetailsButtonMap.put(profileDetailsButton,candidate);
            profilesSubmitButtonMap.put(profileSubmitButton,candidate);
        }
    }

    @Override
    public void updateMatchesList(ArrayList<Match> matchesList)
    {
        VBox centralSection = new VBox();

        for (Match match : matchesList) {
            HBox matchRow = new HBox();
            matchRow.setAlignment(Pos.CENTER_LEFT);
            matchRow.setStyle(BORDER_STYLE);

            Rectangle SelectProfileColorSquare = new Rectangle(MATCHES_RECTANGLE_WINNER_WIDTH, MATCHES_RECTANGLE_WINNER_HEIGHT, Color.web(match.getMatchColor()));

            Label matchLabel = new Label(match.toString());
            matchLabel.setAlignment(Pos.CENTER);
            matchLabel.setPrefWidth(MATCHES_SCROLLPANE_WIDTH);

            matchRow.getChildren().addAll(SelectProfileColorSquare, matchLabel);
            centralSection.getChildren().add(matchRow);
        }

        scrollPane.setContent(centralSection);
    }

    @Override
    public void updateRecruiterStatistics(int score, int bonusScore, double workingRate)
    {
        currentScoreLabel.setText(CURRENT_SCORE_LABEL_TEXT + score);
        bonusScoreLabel.setText(BONUS_SCORE_LABEL_TEXT + bonusScore);
        workingRateButton.setText(WORKING_RATE_LABEL_TEXT + workingRate + WORKING_RATE_PREFIX);
    }

    // Cette méthode affiche la fenêtre du profile du candidat.
    public void openCandidateProfile(ApplicationProfile candidate)
    {
        candidateNameLabel.setText(candidate.getDisplayedName());
        candidateNameLabel.setFont(new Font(FONT_NAME, FONT_SIZE));
        candidateMotivationLabel.setText(MOTIVATION_LABEL_TEXT + candidate.getDisplayedMotivationLetter());
        candidateDistanceLabel.setText(DISTANCE_LABEL_TEXT + candidate.getDistance() + DISTANCE_PREFIX);
        candidateMotivationLabel.setWrapText(true);
        candidateMotivationLabel.setMaxWidth(PROFILE_TEXT_MAX_WIDTH);

        explanationLabel.setAlignment(Pos.TOP_LEFT);

        confirmButton.setDisable(true);
        VBox inputBox = new VBox(bonusTextField, confirmButton);
        inputBox.setSpacing(VERTICAL_SPACING_VALUE);
        inputBox.setAlignment(Pos.CENTER_RIGHT);

        HBox hbox = new HBox(explanationLabel, inputBox);
        hbox.setSpacing(HORIZONTAL_SPACING_VALUE);
        hbox.setAlignment(Pos.TOP_LEFT); 
        hbox.setMaxWidth(PROFILE_CONTENT_WIDTH);

        VBox secondaryLayout = new VBox(candidateNameLabel,candidateMotivationLabel,candidateDistanceLabel,bonusLabel,hbox);

        secondaryLayout.setSpacing(VERTICAL_SPACING_VALUE);
        secondaryLayout.setAlignment(Pos.CENTER_LEFT);
        secondaryLayout.setMaxWidth(PROFILE_CONTENT_WIDTH);

        StackPane root = new StackPane(secondaryLayout);
        root.setAlignment(Pos.CENTER);

        Scene secondScene = new Scene(root, CANDIDATE_FRAME_WIDTH, CANDIDATE_FRAME_HEIGHT);

        Stage newWindow = new Stage();

        newWindow.setTitle(candidate.getName() + FRAME_TITLE_TEXT);
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.initOwner(stage);
        newWindow.setX(stage.getX());
        newWindow.setY(stage.getY());

        newWindow.show();
    }

    @Override
    public void onNoCandidatesAvailable(){openDialogtWindow();}

    // cette méthode creer une confirmbox pour valider la fin de la session.
    public void openSessionSubmitWindow()
    {
        confirmBoxWindow.initModality(Modality.APPLICATION_MODAL); 
        confirmBoxWindow.setTitle(CONFIRM_BOX_TITLE);
        confirmBoxWindow.setMinWidth(minWidth);
        
        Label label = new Label();
        label.setText(CONFIRM_BOX_LABEL_TEXT);
        
        VBox layout = new VBox();
        layout.getChildren().addAll(label, yesButton, noButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        confirmBoxWindow.setScene(scene);
        confirmBoxWindow.showAndWait(); 
    }

    // Cette méthode afiche un message pour dire qu'il n'y a plus de match
    // dans une strtégie avant la fermeture de l'application.
    public void openDialogtWindow()
    {
        dialogStage.initModality(Modality.APPLICATION_MODAL); 
        dialogStage.setTitle(DIALOG_STAGE_TITLE);

        Label label = new Label();
        label.setText(DIALOG_STAGE_LABEL_TEXT);

        VBox layout = new VBox();
        layout.getChildren().addAll(label, okButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        dialogStage.setScene(scene);
        dialogStage.showAndWait(); 

    }

    public void setBonusScoreBtnEnabled(Match currentMatch, String content)
    {
        boolean valid = !currentMatch.isBonusScoreUsed() && content.matches(ONLY_NUMBERS_REGEX) && Integer.valueOf(content) <= ApplicationProfile.MAX_PROFILE_SCORE;

        confirmButton.setDisable(!valid);
    }

    // Cette méthode permet de récuperer le nom de la stratégie choci en fonction du boutton radio cocher.
    public String getProfileStrategy(){
        if (randomRadioButton.isSelected()) {
            return ProfileSelectionStrategyFactory.RANDOM;
        } else if (localScoreRadioButton.isSelected()) {
            return ProfileSelectionStrategyFactory.LOCAL_SCORE;
        } else if (skillScoreRadioButton.isSelected()) {
            return ProfileSelectionStrategyFactory.SKILL_SCORE;
        } else {
            return ProfileSelectionStrategyFactory.ANY;
        }
    }
}