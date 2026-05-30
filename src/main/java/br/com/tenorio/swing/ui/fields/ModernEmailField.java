/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields;

import br.com.tenorio.swing.ui.fields.base.ModernBaseTextField;
import br.com.tenorio.swing.ui.fields.base.ModernValidationType;

public class ModernEmailField extends ModernBaseTextField {

    public ModernEmailField() {

        super();

        setValidationType(
                ModernValidationType.EMAIL
        );

        setPlaceholder("email@empresa.com");
    }

}