/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernDimensions;
import java.awt.Dimension;
import javax.swing.Icon;

public class ModernIconButton extends ModernButton {

    private int iconSize;

    public ModernIconButton() {

        super();

        initializeIconButton();

        setText(null);
    }

    public ModernIconButton(Icon icon) {

        super();

        initializeIconButton();

        setIcon(icon);

        setText(null);
    }

    private void initializeIconButton() {

        iconSize = 18;

        setText(null);

        setFocusable(false);
    }

    @Override
    public Dimension getPreferredSize() {

        return new Dimension(
                ModernDimensions.BUTTON_HEIGHT,
                ModernDimensions.BUTTON_HEIGHT
        );
    }

    public int getIconSize() {
        return iconSize;
    }

    public void setIconSize(int iconSize) {

        this.iconSize = iconSize;

        repaint();
    }

}