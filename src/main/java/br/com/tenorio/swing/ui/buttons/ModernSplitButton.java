/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class ModernSplitButton extends JPanel {

    private final ModernButton actionButton;

    private final JButton menuButton;

    private final JPopupMenu popupMenu;

    public ModernSplitButton() {

        setLayout(new BorderLayout());

        actionButton = new ModernButton("Ação");

        menuButton = new JButton("▼");

        popupMenu = new JPopupMenu();

        add(actionButton, BorderLayout.CENTER);

        add(menuButton, BorderLayout.EAST);

        menuButton.addActionListener(e -> {

            popupMenu.show(
                    menuButton,
                    0,
                    menuButton.getHeight()
            );
        });
    }

    public ModernButton getActionButton() {
        return actionButton;
    }

    public void addItem(String text) {

        popupMenu.add(new JMenuItem(text));
    }

}