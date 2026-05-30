/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields;

import br.com.tenorio.swing.ui.fields.base.ModernBaseTextField;
import br.com.tenorio.swing.ui.fields.base.ModernValidationType;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Modern text field for Brazilian phone numbers.
 * Automatically handles masks for both landlines (8 digits) and mobile numbers (9 digits) with DDD.
 */
public class ModernPhoneField extends ModernBaseTextField {

    private boolean isFormatting = false;

    public ModernPhoneField() {
        super();
        configureComponent();
    }

    private void configureComponent() {
        this.setValidationType(ModernValidationType.CUSTOM);
        this.setPlaceholder("(00) 00000-0000");

        ((AbstractDocument) this.getDocument()).setDocumentFilter(new DocumentFilter() {
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
                super.remove(fb, offset, length);
                if (!isFormatting) {
                    reformatText(fb.getDocument().getText(0, fb.getDocument().getLength()));
                }
            }

            private void processInput(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (isFormatting) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String futureText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                reformatText(futureText);
            }
        });
    }

    private void reformatText(String rawText) {
        isFormatting = true;

        String onlyNumbers = rawText.replaceAll("[^0-9]", "");
        if (onlyNumbers.length() > 11) {
            onlyNumbers = onlyNumbers.substring(0, 11);
        }

        String formattedText;
        if (onlyNumbers.length() <= 10) {
            // Mask for landlines: (XX) XXXX-XXXX
            formattedText = applyMask(onlyNumbers, "(##) ####-####");
        } else {
            // Mask for mobile phones: (XX) XXXXX-XXXX
            formattedText = applyMask(onlyNumbers, "(##) #####-####");
        }

        this.setText(formattedText);
        isFormatting = false;
    }

    private String applyMask(String numbers, String mask) {
        StringBuilder result = new StringBuilder();
        int idx = 0;
        for (char m : mask.toCharArray()) {
            if (idx >= numbers.length()) break;
            if (m == '#') {
                result.append(numbers.charAt(idx));
                idx++;
            } else {
                result.append(m);
            }
        }
        return result.toString();
    }

    @Override
    public boolean validateField() {
        String cleanText = getUnmaskedText();

        if (cleanText.isEmpty()) {
            if (this.isRequired()) {
                showError();
                return false;
            }
            clearError();
            return true;
        }

        // Telefones brasileiros válidos precisam ter 10 (fixo) ou 11 (celular) dígitos com DDD
        if (cleanText.length() < 10) {
            showError();
            return false;
        }

        return super.validateField();
    }

    public String getUnmaskedText() {
        String text = super.getText();
        return text == null ? "" : text.replaceAll("[^0-9]", "");
    }
}