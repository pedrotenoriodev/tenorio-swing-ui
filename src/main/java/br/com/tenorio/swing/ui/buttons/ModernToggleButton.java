/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.Color;

public class ModernToggleButton extends ModernButton {

    private boolean selected;

    public ModernToggleButton() {

        super();

        initializeToggle();
    }

    public ModernToggleButton(String text) {

        super(text);

        initializeToggle();
    }

    private void initializeToggle() {

        selected = false;

        addActionListener(e -> {

            selected = !selected;

            updateState();
        });

        updateState();
    }

    private void updateState() {

        if (selected) {

            setBackgroundColor(ModernColors.SUCCESS);

            setHoverColor(new Color(22, 163, 74));

            setPressedColor(new Color(21, 128, 61));

        } else {

            setBackgroundColor(ModernColors.PRIMARY);

            setHoverColor(ModernColors.PRIMARY_HOVER);

            setPressedColor(ModernColors.PRIMARY_PRESSED);
        }

        repaint();
    }

    public boolean isSelectedState() {
        return selected;
    }

    public void setSelectedState(boolean selected) {

        this.selected = selected;

        updateState();
    }

}