/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.awt.Font;

public class ModernSubtitleLabel extends ModernLabel {

    public ModernSubtitleLabel() {

        super();

        initialize();
    }

    public ModernSubtitleLabel(String text) {

        super(text);

        initialize();
    }

    private void initialize() {

        setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        16
                )
        );
    }
}