/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.Color;

public class ModernSuccessButton extends ModernButton {

    public ModernSuccessButton() {

        super();

        initializeSuccess();
    }

    public ModernSuccessButton(String text) {

        super(text);

        initializeSuccess();
    }

    private void initializeSuccess() {

        setBackgroundColor(ModernColors.SUCCESS);

        setHoverColor(new Color(22, 163, 74));

        setPressedColor(new Color(21, 128, 61));
    }

}