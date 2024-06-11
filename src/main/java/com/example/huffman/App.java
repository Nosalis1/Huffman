package com.example.huffman;

import com.example.huffman.algorithm.HuffmanTree;
import com.example.huffman.algorithm.HuffmanTreePane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

import static com.example.huffman.algorithm.HuffmanTree.createHuffmanTree;

public class App extends Application {

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    HuffmanTree huffmanTree;
    HuffmanTreePane huffmanTreePane;

    @Override
    public void start(Stage stage) throws IOException {
        BorderPane root = new BorderPane();

        huffmanTree = createHuffmanTree("abracadabra");
        huffmanTreePane = new HuffmanTreePane();
        root.setCenter(huffmanTreePane);

        huffmanTreePane.setHuffmanTree(huffmanTree);
        huffmanTreePane.draw();

        // resize canvas on window resize
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            huffmanTreePane.getCanvas().setWidth(newValue.doubleValue());
            huffmanTreePane.draw();
        });
        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            huffmanTreePane.getCanvas().setHeight(newValue.doubleValue() - 100);
            huffmanTreePane.draw();
        });

        VBox infoPane = new VBox(10);
        infoPane.setPadding(new Insets(10));

        GridPane gridPane = new GridPane();

        infoPane.getChildren().addAll(gridPane);
        root.setRight(infoPane);

        HBox inputPane = new HBox(10);
        inputPane.setPadding(new Insets(10));

        TextField inputField = new TextField("abracadabra");
        inputPane.getChildren().addAll(
                new Label("Enter text:") {{
                    setAlignment(javafx.geometry.Pos.CENTER);
                }},
                inputField,
                new Button("Encode") {{
                    setOnAction(actionEvent -> {
                        huffmanTree = createHuffmanTree(inputField.getText());
                        huffmanTreePane.setHuffmanTree(huffmanTree);

                        gridPane.getChildren().clear();
                        Map<Character, String> huffmanCode = huffmanTree.getHuffmanCode();

                        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
                            Label label = new Label(entry.getKey() + ": " + entry.getValue());
                            gridPane.add(label, 0, gridPane.getRowCount());
                        }

                        huffmanTreePane.draw();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Huffman code");
                        alert.setHeaderText(null);
                        alert.setContentText(huffmanTree.getEncodedData());
                        alert.showAndWait();
                    });
                }},
                new Button("Decode") {{

                }}
        );
        root.setBottom(inputPane);

        Scene scene = new Scene(root, WIDTH, HEIGHT);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}