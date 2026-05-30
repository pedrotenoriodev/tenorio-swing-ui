/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class ModernOutlineButton extends ModernButton {

    private Color borderColor;

    public ModernOutlineButton() {

        super();

        initializeOutline();

        setText("Outline Button");
    }

    public ModernOutlineButton(String text) {

        super(text);

        initializeOutline();
    }

    private void initializeOutline() {

        borderColor = ModernColors.PRIMARY;

        setBackgroundColor(new Color(0, 0, 0, 0));

        setForegroundColor(borderColor);

        setHoverColor(new Color(
                borderColor.getRed(),
                borderColor.getGreen(),
                borderColor.getBlue(),
                20
        ));

        setPressedColor(new Color(
                borderColor.getRed(),
                borderColor.getGreen(),
                borderColor.getBlue(),
                40
        ));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color background;

        if (!isEnabled()) {

            background = new Color(
                    getDisabledColor().getRed(),
                    getDisabledColor().getGreen(),
                    getDisabledColor().getBlue(),
                    30
            );

        } else if (getModel().isPressed()) {

            background = getPressedColor();

        } else if (getModel().isRollover()) {

            background = getHoverColor();

        } else {

            background = new Color(0, 0, 0, 0);
        }

        g2.setColor(background);

        g2.fillRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                getRadius(),
                getRadius()
        );

        g2.setColor(borderColor);

        g2.setStroke(new BasicStroke(1.5f));

        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                getRadius(),
                getRadius()
        );

        g2.dispose();

        super.paintComponent(g);
    }

    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {

        this.borderColor = borderColor;

        setForegroundColor(borderColor);

        repaint();
    }

}