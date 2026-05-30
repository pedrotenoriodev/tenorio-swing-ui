/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import javax.swing.JComboBox;

public class ModernComboBox<E> extends JComboBox<E> {

    private boolean required;

    public ModernComboBox() {

        initialize();
    }

    private void initialize() {

        putClientProperty(
                FlatClientProperties.STYLE,
                "arc:16;"
        );

        putClientProperty(
                "JComponent.minimumHeight",
                40
        );

        setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );
    }

    public boolean validateField() {

        if (required && getSelectedItem() == null) {

            putClientProperty(
                    FlatClientProperties.OUTLINE,
                    "error"
            );

            return false;
        }

        putClientProperty(
                FlatClientProperties.OUTLINE,
                null
        );

        return true;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

}