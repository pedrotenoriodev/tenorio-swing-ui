/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.awt.Font;

public class ModernCounterLabel extends ModernLabel {

    private int value;

    private String prefix = "";

    private String suffix = "";

    public ModernCounterLabel() {

        setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        24
                )
        );

        updateText();
    }

    private void updateText() {

        setText(
                prefix
                + value
                + suffix
        );
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {

        this.value = value;

        updateText();
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {

        this.prefix = prefix;

        updateText();
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {

        this.suffix = suffix;

        updateText();
    }
}