package com.example.huffman.algorithm;

import com.example.huffman.App;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class HuffmanTreePane extends Pane {

    private HuffmanTree huffmanTree;
    private final Canvas canvas;

    public HuffmanTreePane() {
        this.huffmanTree = null;
        this.canvas = new Canvas(App.WIDTH, App.HEIGHT - 100);
        super.setStyle("""
                -fx-border-color: black;
                -fx-background-color: white;
                -fx-border-style: solid;
                -fx-border-width: 1;
                -fx-border-radius: 0;
                """);
        this.getChildren().add(canvas);
    }

    public void setHuffmanTree(HuffmanTree huffmanTree) {
        this.huffmanTree = huffmanTree;
    }

    public void draw() {
        if (huffmanTree == null) {
            return;
        }
        this.canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);

        drawTree(gc, huffmanTree.getRoot(), canvas.getWidth() / 2, 30, canvas.getWidth() / 5);

        gc.setStroke(Color.BLUE);
        gc.strokeText(huffmanTree.getEncodedData(), 20, canvas.getHeight() - 20);
    }

    private void drawTree(final GraphicsContext gc, HuffmanNode root, double x, double y, double hGap) {
        if (root == null) {
            return;
        }

        if (root.left != null) {
            double childX = x - hGap;
            double childY = y + 50;
            gc.strokeLine(x, y, childX, childY);
            gc.strokeText("0", (x + childX) / 2 - 5, (y + childY) / 2 - 5);
            drawTree(gc, root.left, childX, childY, hGap / 2);
        }
        if (root.right != null) {
            double childX = x + hGap;
            double childY = y + 50;
            gc.strokeLine(x, y, childX, childY);
            gc.strokeText("1", (x + childX) / 2 + 5, (y + childY) / 2 - 5);
            drawTree(gc, root.right, childX, childY, hGap / 2);
        }

        gc.setFill(Color.WHITE);
        gc.fillOval(x - 15, y - 15, 30, 30);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - 15, y - 15, 30, 30);

        if (root.ch != null) {
            gc.setFill(Color.BLACK);
            gc.fillText(root.ch.toString() + "," + root.freq, x - 7, y + 4);
        } else {
            gc.setFill(Color.RED);
            gc.fillText(String.valueOf(root.freq), x - 10, y + 4);
        }
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
