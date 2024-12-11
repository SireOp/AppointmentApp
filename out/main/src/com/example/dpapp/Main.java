package com.example.dpapp;

import com.example.dpapp.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 640);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        ZonedDateTime zoneTime = ZonedDateTime.now();
        //ZoneId zoneId1 = zoneTime.withZoneSameInstant(ZoneId );
        ZoneId zoneId = ZoneId.of("America/New_York");
        LocalTime id = LocalTime.now(zoneId);
        ZonedDateTime canZonedDateTime = dateTime.atZone(ZoneId.of("Africa/Tunis"));
        //ZonedDateTime affDateTime = canZonedDateTime.withZoneSameInstant("America/New_York","A")
        Timestamp  now = Timestamp.from(Instant.now());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
        LocalTime A = LocalTime.of(20,14);
        DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("MMMM dd, YYYY HH:mm");

        System.out.println(A);

        System.out.println(dateTime);
        System.out.println(zoneTime);
        System.out.println(id);
        System.out.println(canZonedDateTime);
        System.out.println(dtf.format(dateTime));
        System.out.println(dtf1.format(dateTime));





        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();




    }
}