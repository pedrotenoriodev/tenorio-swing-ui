/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class ModernLinkLabel extends ModernLabel {

    private String url = "";

    private boolean underline = true;

    public ModernLinkLabel() {

        initialize();
    }

    public ModernLinkLabel(String text) {

        super(text);

        initialize();
    }

    private void initialize() {

        setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        updateText();

        addMouseListener(
                new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                openUrl();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

                setFont(
                        getFont().deriveFont(
                                Font.BOLD
                        )
                );
            }

            @Override
            public void mouseExited(MouseEvent e) {

                setFont(
                        getFont().deriveFont(
                                Font.PLAIN
                        )
                );
            }
        });
    }

    private void updateText() {

        String text = super.getText();

        if (text == null) {
            text = "";
        }

        if (underline) {

            super.setText(
                    "<html><u>"
                    + text
                    + "</u></html>"
            );

        } else {

            super.setText(text);
        }
    }

    private void openUrl() {

        if (url == null || url.isBlank()) {
            return;
        }

        try {

            Desktop.getDesktop().browse(
                    new URI(url)
            );

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setText(String text) {

        super.setText(text);

        updateText();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }

    public boolean isUnderline() {
        return underline;
    }

    public void setUnderline(boolean underline) {

        this.underline = underline;

        updateText();
    }
}