/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.awt.Font;
import javax.swing.JLabel;

public class ModernLabel extends JLabel {

    private int fontSize = 14;

    private boolean bold = false;

    public ModernLabel() {

        initialize();
    }

    public ModernLabel(String text) {

        super(text);

        initialize();
    }

    private void initialize() {

        updateFont();
    }

    private void updateFont() {

        setFont(
                new Font(
                        "Segoe UI",
                        bold ? Font.BOLD : Font.PLAIN,
                        fontSize
                )
        );
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {

        this.fontSize = fontSize;

        updateFont();
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {

        this.bold = bold;

        updateFont();
    }
}