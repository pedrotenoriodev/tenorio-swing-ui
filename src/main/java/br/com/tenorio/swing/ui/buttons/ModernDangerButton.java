/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.Color;

public class ModernDangerButton extends ModernButton {

    public ModernDangerButton() {

        super();

        initializeDanger();
    }

    public ModernDangerButton(String text) {

        super(text);

        initializeDanger();
    }

    private void initializeDanger() {

        setBackgroundColor(ModernColors.DANGER);

        setHoverColor(new Color(220, 38, 38));

        setPressedColor(new Color(185, 28, 28));
    }

}