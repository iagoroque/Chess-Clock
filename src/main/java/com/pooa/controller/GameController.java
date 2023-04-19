package com.pooa.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.pooa.App;
import com.pooa.model.Clock;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameController implements Initializable {

    private Clock firstClock = new Clock(5, 0);
    private Clock secondClock = new Clock(5, 0);
    private int countOne;
    private int countTwo;

    @FXML
    private Button firstClockButton;

    @FXML
    private Button secondClockButton;

    @FXML
    private Button stopButton;

    @FXML
    private Button reloadButton;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            firstClockButton.setText(firstClock.getClock());
            secondClockButton.setText(secondClock.getClock());
            if (firstClock.isInterrupted()) {
                playSecond();
            }
            if (secondClock.isInterrupted()) {
                playFirst();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    // Second button
    @FXML
    void runFirstClock(ActionEvent event) {
        countTwo++;
        secondClockButton.setDisable(true);
        firstClockButton.setDisable(false);

        if (secondClock.isAlive()) {
            secondClock.suspend();
            firstClock.resume();
        }

        if (!firstClock.isAlive()) {
            playFirst();
        }
    }

    // First button
    @FXML
    void runSecondClock(ActionEvent event) {
        countOne++;
        firstClockButton.setDisable(true);
        secondClockButton.setDisable(false);

        if (firstClock.isAlive()) {
            firstClock.suspend();
            secondClock.resume();
        }

        if (!secondClock.isAlive()) {
            playSecond();
        }
    }

    @FXML
    void stopButton(ActionEvent event) {
        stop();
    }

    @FXML
    void reload(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                Stage stage = (Stage) reloadButton.getScene().getWindow();
                App newApp = new App();
                stage.close();
                newApp.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void stop() {
        firstClock.interrupt();
        secondClock.interrupt();
        System.out.print("Player one: " + countOne + " turns - time: " + String.format("%02d", firstClock.getLastMin())
                + ":"
                + String.format("%02d", firstClock.getLastSec()) + "\nPlayer two: " + countTwo + " turns - time: "
                + String.format("%02d", secondClock.getLastMin()) + ":"
                + String.format("%02d", secondClock.getLastSec())
                + "\nTotal: " + String.format("%02d", (firstClock.getLastMin() + secondClock.getLastMin())) + ":"
                + String.format("%02d", (firstClock.getLastSec() + secondClock.getLastSec())));
    }

    private void playFirst() {
        firstClock.start();
        if (secondClock.isInterrupted()) {
            secondClockButton.setDisable(true);
            firstClock.resume();
        }
        firstClockButton.setText(firstClock.getClock());
    }

    private void playSecond() {
        secondClock.start();
        if (firstClock.isInterrupted()) {
            firstClockButton.setDisable(true);
            secondClock.resume();
        }
        secondClockButton.setText(secondClock.getClock());
    }
}
