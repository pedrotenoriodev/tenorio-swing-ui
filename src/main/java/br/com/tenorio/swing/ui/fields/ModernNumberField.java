/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields;

import br.com.tenorio.swing.ui.fields.base.ModernBaseTextField;
import br.com.tenorio.swing.ui.fields.base.ModernValidationType;

public class ModernNumberField extends ModernBaseTextField {

    public ModernNumberField() {

        super();

        setValidationType(
                ModernValidationType.NUMBERS
        );
    }

    public Integer getIntegerValue() {

        String text = getText();

        if (text == null || text.isBlank()) {
            return 0;
        }

        return Integer.valueOf(text);
    }

}