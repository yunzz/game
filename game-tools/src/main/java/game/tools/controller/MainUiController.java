package game.tools.controller;

import game.base.uitools.event.EventEnum;
import game.base.uitools.event.EventManager;
import game.base.uitools.event.events.ConsoleEvent;
import game.tools.json.JsonFileGen;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.collections4.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Yunzhe.Jin
 * 2020/4/7 14:23
 */
public class MainUiController implements Initializable {
    private Stage primaryStage;
    private boolean connected = false;

    @FXML
    private TextField address;
    @FXML
    private Label status;
    @FXML
    private TextArea console;

    @FXML
    private TextField p1;
    @FXML
    private TextField p2;
    @FXML
    private TextField p3;
    @FXML
    private TextField p4;

    private int seq = 1;
    @FXML
    private Button open;
    @FXML
    private Button saveJson;
    @FXML
    public Button saveJsonAs;
    @FXML
    public Button moveBtn;
    @FXML
    private ListView<String> leftList;
    private List<File> selected;
    private File jsonSaveDir;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EventManager.addEventHandler(EventEnum.CONSOLE, (ConsoleEvent event) -> {
            console.appendText(LocalDateTime.now() + ":" + event.getText() + "\n");
        });
        EventManager.fireConsoleEvent(LocalDateTime.now().toString());

        open.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择Excel文件");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Excel", "*.xlsx")
                    , new FileChooser.ExtensionFilter("All Files", "*.*"));
            List<File> files = fileChooser.showOpenMultipleDialog(primaryStage);

            if (CollectionUtils.isNotEmpty(files)) {
                selected = files;
                List<String> items = new ArrayList<>();
                for (File file : files) {
                    String name = file.getName();
                    items.add(name);
                }
                ObservableList<String> strings = FXCollections.observableArrayList(items);
                leftList.setItems(strings);
            }
        });

        saveJson.setOnAction(event -> {
            if (jsonSaveDir == null) {
                DirectoryChooser fileChooser = new DirectoryChooser();
                fileChooser.setTitle("选择json保存位置");
                File file = fileChooser.showDialog(primaryStage);
                jsonSaveDir = file;
            }
            try {
                JsonFileGen.gen(selected, jsonSaveDir);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
