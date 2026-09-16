package com.example;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;

// Cette classe permet de gérer les entrées et sorties de l’utilisateur
// ainsi que les interactions entre la vue et la logique métier.
//
// Elle initialise la session d’entraînement, configure les événements
// des boutons et des composants de l’interface, et met à jour le temps
// de la session via une Timeline exécutée à intervalle régulier.

public class HELBPleaseRecruitMeController {
    private HELBPleaseRecruitMeView view;
    private String trainingSessionInitialStrategy = ProfileSelectionStrategyFactory.RANDOM;
    private TrainingSession trainingSession;
    private Timeline timeline;
    private final double TIME_DELAYS = 1000;

    public HELBPleaseRecruitMeController(Stage primaryStage)
    {
        this.view = new HELBPleaseRecruitMeView(primaryStage);
        this.trainingSession = new TrainingSession(this.view, this.trainingSessionInitialStrategy);
        this.timeline = new Timeline(new KeyFrame(Duration.millis(TIME_DELAYS), e -> counterCurrentMatchTime()));
        this.timeline.setCycleCount(Animation.INDEFINITE);
        this.timeline.play();
        setActions();
    }

    // cette méthode permet de gérer les différentes actions de l’utilisateur
    private void setActions() {
        for (int index = 0; index < view.profilesDetailsButtonList.size(); index++)
        {
            final int i = index;
            view.profilesDetailsButtonList.get(i).setOnAction(e -> {
                ApplicationProfile viewedProfile = view.profilesDetailsButtonMap.get(view.profilesDetailsButtonList.get(i));
                view.openCandidateProfile(viewedProfile);
                trainingSession.setViewedProfile(viewedProfile);
            });
        }

        for (int index = 0; index < view.profilesSubmitButtonList.size(); index++)
        {
            final int i = index;
            view.profilesSubmitButtonList.get(i).setOnAction(e -> {
                trainingSession.confirmMatchEnd(view.profilesSubmitButtonMap.get(view.profilesSubmitButtonList.get(i)), view.HEX_COLORS[i]);
            });
        }

        view.strategiesGroup.selectedToggleProperty().addListener((observable, oldValue, newValue)->
        {
            // Le null vu avec le prof
            if (newValue != null) {
                trainingSession.setCurrentStrategy(view.getProfileStrategy());
            }
            trainingSession.changeProfiles();
        });
        
        view.confirmButton.setOnAction(e -> {
            trainingSession.calculateScoreBonus(Integer.parseInt(view.bonusTextField.getText()));
            trainingSession.getCurrentMatch().setBonusScoreUsed();
            view.setBonusScoreBtnEnabled(trainingSession.getCurrentMatch(), view.bonusTextField.getText());
            view.bonusTextField.clear();
        });

        view.SubmitSessionButton.setOnAction(e -> {
            view.openSessionSubmitWindow();
        });

        view.yesButton.setOnAction(e ->{
            HELBPleaseRecruitMeParser.writeSessionFile(trainingSession.getMatchesList(), trainingSession.getScore(), trainingSession.getBonusScore());
            view.confirmBoxWindow.close();
            view.stage.close();
        });
        
        view.noButton.setOnAction(e ->{
            view.confirmBoxWindow.close();
        });

        view.bonusTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            view.setBonusScoreBtnEnabled(trainingSession.getCurrentMatch(),newValue);
        });

        view.okButton.setOnAction(e ->{
            HELBPleaseRecruitMeParser.writeSessionFile(trainingSession.getMatchesList(), trainingSession.getScore(), trainingSession.getBonusScore());
            view.dialogStage.close();
            view.stage.close();
        });
    }

    // cette méthode est appelée par la timeline pour mettre à jour le temps.
    private void counterCurrentMatchTime()
    {
        trainingSession.updateSessionElapsedTime(TIME_DELAYS);
    }
}