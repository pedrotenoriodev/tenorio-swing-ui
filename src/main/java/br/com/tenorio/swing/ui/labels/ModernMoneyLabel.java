/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class ModernMoneyLabel extends ModernLabel {

    private BigDecimal value = BigDecimal.ZERO;

    public ModernMoneyLabel() {

        setBold(true);

        setFontSize(18);

        updateText();
    }

    private void updateText() {

        NumberFormat format =
                NumberFormat.getCurrencyInstance(
                        new Locale("pt", "BR")
                );

        setText(
                format.format(value)
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