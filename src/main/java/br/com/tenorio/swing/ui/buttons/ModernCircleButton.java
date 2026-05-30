/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernDimensions;
import java.awt.Dimension;

public class ModernCircleButton extends ModernButton {

    private int diameter;

    public ModernCircleButton() {

        super();

        initializeCircle();

        setText("+");
    }

    public ModernCircleButton(String text) {

        super(text);

        initializeCircle();
    }

    private void initializeCircle() {

        diameter = 42;

        setRadius(999);
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(diameter, diameter);
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {

        this.diameter = diameter;

        revalidate();

        repaint();
    }

}