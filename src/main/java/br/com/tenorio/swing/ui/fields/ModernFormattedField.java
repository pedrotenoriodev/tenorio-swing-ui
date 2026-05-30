/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields;

import javax.swing.JFormattedTextField;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

public class ModernFormattedField extends JFormattedTextField {

    public ModernFormattedField() {

        super();
    }

    public ModernFormattedField(String mask) {

        super();

        try {

            setFormatterFactory(
                    new DefaultFormatterFactory(
                            new MaskFormatter(mask)
                    )
            );

        } catch (Exception e) {
        }
    }

}