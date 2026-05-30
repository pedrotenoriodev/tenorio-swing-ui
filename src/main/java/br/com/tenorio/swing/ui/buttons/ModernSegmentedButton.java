/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

import java.awt.FlowLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ModernSegmentedButton extends JPanel {

    private final ButtonGroup group;

    public ModernSegmentedButton() {

        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        group = new ButtonGroup();
    }

    public void addSegment(String text) {

        JToggleButton button = new JToggleButton(text);

        group.add(button);

        add(button);
    }

}