/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields;

import br.com.tenorio.swing.ui.fields.base.ModernBaseTextField;
import br.com.tenorio.swing.ui.fields.base.ModernValidationType;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Modern percentage input field extending ModernBaseTextField.
 * Features automated right-to-left numeric masking, custom decimal places,
 * range validation (min/max), and integrated required field status.
 */
public class ModernPercentField extends ModernBaseTextField {

    private boolean isFormatting = false;
    private int decimalPlaces = 2;

    private BigDecimal minValue = BigDecimal.ZERO;
    private BigDecimal maxValue = new BigDecimal("999999.99");

    public ModernPercentField() {
        super();
        configurePercentComponent();
    }

    private void configurePercentComponent() {
        this.setValidationType(ModernValidationType.CUSTOM);
        this.setPlaceholder("Percentual");
        this.setHorizontalAlignment(RIGHT);

        AbstractDocument doc = (AbstractDocument) this.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                processInput(fb, offset, 0, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) text = "";
                processInput(fb, offset, length, text, attrs);
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                if (isFormatting) {
                    super.remove(fb, offset, length);
                    return;
                }

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                int end = Math.min(offset + length, currentText.length());
                String futureText = currentText.substring(0, offset) + currentText.substring(end);
                String onlyNumbers = futureText.replaceAll("\\D", "");

                updateDocument(fb, onlyNumbers, null);
            }
        });

        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                SwingUtilities.invokeLater(() -> selectAll());
            }
        });
    }

    private void processInput(DocumentFilter.FilterBypass fb, int offset, int length, String insertedText, AttributeSet attrs) throws BadLocationException {
        if (isFormatting) {
            fb.replace(offset, length, insertedText, attrs);
            return;
        }

        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        int end = Math.min(offset + length, currentText.length());
        String futureText = currentText.substring(0, offset) + insertedText + currentText.substring(end);
        String onlyNumbers = futureText.replaceAll("\\D", "");

        updateDocument(fb, onlyNumbers, attrs);
    }

    private void updateDocument(DocumentFilter.FilterBypass fb, String digits, AttributeSet attrs) throws BadLocationException {
        // Limita a 12 dígitos para evitar estouro de tamanho visual no campo
        if (digits.length() > 12) {
            digits = digits.substring(0, 12);
        }

        isFormatting = true;
        try {
            fb.remove(0, fb.getDocument().getLength());
            fb.insertString(0, formatPercentage(digits), attrs);
        } finally {
            isFormatting = false;
        }

        adjustCaretPosition();
    }

    private String formatPercentage(String digits) {
        if (digits == null || digits.isEmpty()) {
            return "";
        }

        BigDecimal value = new BigDecimal(digits).movePointLeft(decimalPlaces);
        String text = value.setScale(decimalPlaces, RoundingMode.HALF_UP).toPlainString();
        
        return text.replace(".", ",") + " %";
    }

    private void adjustCaretPosition() {
        SwingUtilities.invokeLater(() -> {
            int length = getText().length();
            if (length > 2) {
                // Posiciona o cursor logo antes do caractere "%" para manter a digitação natural
                setCaretPosition(length - 2);
            }
        });
    }

    // ==================================================
    // OVERRIDES - INTEGRAÇÃO COM A VALIDAÇÃO DA BASE
    // ==================================================

    @Override
    public boolean validateField() {
        if (getText().isEmpty()) {
            if (this.isRequired()) {
                showError();
                return false;
            }
            clearError();
            return true;
        }

        BigDecimal value = getPercentValue();

        // Validação de limites numéricos (Mínimo e Máximo)
        if (value.compareTo(minValue) < 0 || value.compareTo(maxValue) > 0) {
            showError();
            return false;
        }

        clearError();
        return super.validateField();
    }

    @Override
    public String getText() {
        String text = super.getText();
        return text == null ? "" : text.trim();
    }

    // ==================================================
    // EXTENDED VALUE GETTERS & SETTERS
    // ==================================================

    /**
     * Returns the plain value typed as a full percent value (e.g., Returns 25.50 for "25,50 %").
     */
    public BigDecimal getPercentValue() {
        String cleanText = getText()
                .replace("%", "")
                .replace(" ", "")
                .replace(",", ".")
                .trim();

        if (cleanText.isEmpty()) {
            return BigDecimal.ZERO;
        }

        try {
            return new BigDecimal(cleanText);
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Returns the fractional multiplier representation of the value (e.g., Returns 0.255 for "25,50 %").
     * Ideal for multiplying directly with base money values.
     */
    public BigDecimal getFractionValue() {
        return getPercentValue().movePointLeft(2);
    }

    public double getDoubleValue() {
        return getPercentValue().doubleValue();
    }

    public void setPercentValue(BigDecimal value) {
        if (value == null) {
            clearField();
            return;
        }

        isFormatting = true;
        try {
            BigDecimal scaled = value.setScale(decimalPlaces, RoundingMode.HALF_UP);
            super.setText(scaled.toPlainString().replace(".", ",") + " %");
        } finally {
            isFormatting = false;
        }
    }

    public void clearField() {
        isFormatting = true;
        try {
            super.setText("");
        } finally {
            isFormatting = false;
        }
    }

    // ==================================================
    // CONFIGURATION GETTERS & SETTERS
    // ==================================================

    public void setDecimalPlaces(int decimalPlaces) {
        if (decimalPlaces >= 0) {
            this.decimalPlaces = decimalPlaces;
        }
    }

    public int getDecimalPlaces() {
        return decimalPlaces;
    }

    public void setMinValue(BigDecimal minValue) {
        if (minValue != null) this.minValue = minValue;
    }

    public void setMaxValue(BigDecimal maxValue) {
        if (maxValue != null) this.maxValue = maxValue;
    }
}