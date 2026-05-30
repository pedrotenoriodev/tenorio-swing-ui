/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ModernPercentLabel extends ModernLabel {

    private BigDecimal value = BigDecimal.ZERO;

    public ModernPercentLabel() {

        setBold(true);

        updateText();
    }

    private void updateText() {

        setText(
                value.setScale(
                        2,
                        RoundingMode.HALF_UP
                )
                + "%"
        );
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {

        this.value =
                value == null
                ? BigDecimal.ZERO
                : value;

        updateText();
    }
}