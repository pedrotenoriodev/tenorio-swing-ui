/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

public class ModernRoundedButton extends ModernButton {

    public ModernRoundedButton() {

        super();

        initializeRounded();
    }

    public ModernRoundedButton(String text) {

        super(text);

        initializeRounded();
    }

    private void initializeRounded() {

        setRadius(30);
    }

}