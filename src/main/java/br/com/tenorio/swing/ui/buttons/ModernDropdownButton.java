/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class ModernDropdownButton extends ModernButton {

    private final JPopupMenu popupMenu;

    public ModernDropdownButton() {

        super("Dropdown ▼");

        popupMenu = new JPopupMenu();

        initializeDropdown();
    }

    public ModernDropdownButton(String text) {

        super(text + " ▼");

        popupMenu = new JPopupMenu();

        initializeDropdown();
    }

    private void initializeDropdown() {

        addActionListener(e -> {

            popupMenu.show(
                    this,
                    0,
                    getHeight()
            );
        });
    }

    public void addItem(String text) {

        popupMenu.add(new JMenuItem(text));
    }

    public void removeAllItems() {

        popupMenu.removeAll();
    }

    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

}