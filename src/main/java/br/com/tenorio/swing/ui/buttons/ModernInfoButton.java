/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.Color;

public class ModernInfoButton extends ModernButton {

    public ModernInfoButton() {

        super();

        initializeInfo();
    }

    public ModernInfoButton(String text) {

        super(text);

        initializeInfo();
    }

    private void initializeInfo() {

        setBackgroundColor(ModernColors.INFO);

        setHoverColor(new Color(8, 145, 178));

        setPressedColor(new Color(14, 116, 144));
    }

}