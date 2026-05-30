package br.com.tenorio.swing.ui.buttons;

import br.com.tenorio.swing.ui.core.ModernColors;
import br.com.tenorio.swing.ui.core.ModernDimensions;
import br.com.tenorio.swing.ui.core.ModernFonts;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;

public class ModernButton extends JButton {

    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color disabledColor;
    private Color foregroundColor;

    private int radius;

    private boolean hovered;
    private boolean pressed;

    public ModernButton() {

        initialize();

        setText("Modern Button");
    }

    public ModernButton(String text) {

        initialize();

        setText(text);
    }

    private void initialize() {

        backgroundColor = ModernColors.PRIMARY;

        hoverColor = ModernColors.PRIMARY_HOVER;

        pressedColor = ModernColors.PRIMARY_PRESSED;

        disabledColor = ModernColors.DISABLED;

        foregroundColor = ModernColors.WHITE;

        radius = ModernDimensions.RADIUS_MEDIUM;

        setFont(ModernFonts.DEFAULT);

        setForeground(foregroundColor);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFocusPainted(false);

        setBorderPainted(false);

        setContentAreaFilled(false);

        setOpaque(false);

        installListeners();
    }

    private void installListeners() {

        MouseAdapter adapter = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {

                if (isEnabled()) {

                    hovered = true;

                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {

                hovered = false;

                pressed = false;

                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {

                if (isEnabled()) {

                    pressed = true;

                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                pressed = false;

                repaint();
            }

        };

        addMouseListener(adapter);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        Color paintColor;

        if (!isEnabled()) {

            paintColor = disabledColor;

        } else if (pressed) {

            paintColor = pressedColor;

        } else if (hovered) {

            paintColor = hoverColor;

        } else {

            paintColor = backgroundColor;
        }

        g2.setColor(paintColor);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                radius,
                radius
        );

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    public Dimension getPreferredSize() {

        Dimension size = super.getPreferredSize();

        size.height = ModernDimensions.BUTTON_HEIGHT;

        if (size.width < ModernDimensions.BUTTON_WIDTH) {

            size.width = ModernDimensions.BUTTON_WIDTH;
        }

        return size;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {

        this.backgroundColor = backgroundColor;

        repaint();
    }

    public Color getHoverColor() {
        return hoverColor;
    }

    public void setHoverColor(Color hoverColor) {

        this.hoverColor = hoverColor;

        repaint();
    }

    public Color getPressedColor() {
        return pressedColor;
    }

    public void setPressedColor(Color pressedColor) {

        this.pressedColor = pressedColor;

        repaint();
    }

    public Color getDisabledColor() {
        return disabledColor;
    }

    public void setDisabledColor(Color disabledColor) {

        this.disabledColor = disabledColor;

        repaint();
    }

    public Color getForegroundColor() {
        return foregroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {

        this.foregroundColor = foregroundColor;

        super.setForeground(foregroundColor);

        repaint();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {

        this.radius = radius;

        repaint();
    }

}