/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.core;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

public final class ModernThemeManager {

    private ModernThemeManager() {
    }

    public static void setupLightTheme() {

        try {

            UIManager.setLookAndFeel(new FlatLightLaf());

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

    public static void setupDarkTheme() {

        try {

            UIManager.setLookAndFeel(new FlatDarkLaf());

        } catch (Exception ex) {

            ex.printStackTrace();

        }

    }

}