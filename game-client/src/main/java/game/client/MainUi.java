package game.client;

import game.client.controller.MainUiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.net.URL;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 14:21
 */
public class MainUi extends Application {

    private static final Logger _LOG = LoggerFactory.getLogger(MainUi.class);
    public static MainUiController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = Thread.currentThread().getContextClassLoader().getResource("fxml/Main.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        root.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }

    public static void main(String[] args) {
        final String URL = System.getProperty("url", "ws://172.17.18.131:8888/g?uid=201350678034000100&appNo=3&token=a");

        String version = System.getProperty("java.version");
        if (Integer.parseInt(version.substring(2, 3)) >= 8 && Integer.parseInt(version.substring(6)) >= 60) {
            launch(args);
        } else {
            JFrame jFrame = new JFrame("版本错误");
            jFrame.setSize(500, 100);
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JPanel jPanel = new JPanel();
            JLabel jLabel = new JLabel("JDK的版本不能低于1.8.0.60，请升级至最近的JDK 1.8再运行此软件");
            jPanel.add(jLabel);
            jFrame.add(jPanel);
            jFrame.setLocationRelativeTo(null);
            jFrame.setVisible(true);

        }
    }
}

