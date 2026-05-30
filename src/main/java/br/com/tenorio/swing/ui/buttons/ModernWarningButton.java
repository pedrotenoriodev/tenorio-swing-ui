/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;


import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.Color;

public class ModernWarningButton extends ModernButton {

    public ModernWarningButton() {

        super();

        initializeWarning();
    }

    public ModernWarningButton(String text) {

        super(text);

        initializeWarning();
    }

    private void initializeWarning() {

        setBackgroundColor(ModernColors.WARNING);

        setHoverColor(new Color(217, 119, 6));

        setPressedColor(new Color(180, 83, 9));
    }

}