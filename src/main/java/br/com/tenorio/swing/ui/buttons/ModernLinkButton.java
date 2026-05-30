/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.BorderFactory;

public class ModernLinkButton extends ModernButton {

    private boolean underline;

    public ModernLinkButton() {

        super();

        initializeLink();

        setText("Link Button");
    }

    public ModernLinkButton(String text) {

        super(text);

        initializeLink();
    }

    private void initializeLink() {

        underline = true;

        setBorder(BorderFactory.createEmptyBorder());

        setBackgroundColor(new java.awt.Color(0, 0, 0, 0));

        setHoverColor(new java.awt.Color(0, 0, 0, 0));

        setPressedColor(new java.awt.Color(0, 0, 0, 0));

        setForegroundColor(ModernColors.PRIMARY);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFont(getFont().deriveFont(Font.PLAIN));
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        if (!underline) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        int width = g.getFontMetrics().stringWidth(getText());

        int x = (getWidth() - width) / 2;

        int y = getHeight() / 2 + 8;

        g2.drawLine(x, y, x + width, y);

        g2.dispose();
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {

        this.underline = underline;

        repaint();
    }

}
