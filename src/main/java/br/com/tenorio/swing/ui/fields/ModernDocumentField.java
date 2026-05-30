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
 * Modern text field for Brazilian document identification (CPF/CNPJ).
 * Switches mask dynamically based on input length and performs structural validation.
 */
public class ModernDocumentField extends ModernBaseTextField {

    private boolean isFormatting = false;

    public ModernDocumentField() {
        super();
        configureComponent();
    }

    private void configureComponent() {
        this.setValidationType(ModernValidationType.CUSTOM);
        this.setPlaceholder("CPF or CNPJ");
        
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
        if (onlyNumbers.length() > 14) {
            onlyNumbers = onlyNumbers.substring(0, 14);
        }

        String formattedText;
        if (onlyNumbers.length() <= 11) {
            formattedText = applyMask(onlyNumbers, "###.###.###-##");
        } else {
            formattedText = applyMask(onlyNumbers, "##.###.###/####-##");
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

        if (cleanText.length() == 11) {
            if (!isValidCPF(cleanText)) { showError(); return false; }
        } else if (cleanText.length() == 14) {
            if (!isValidCNPJ(cleanText)) { showError(); return false; }
        } else {
            showError();
            return false;
        }

        return super.validateField();
    }

    private boolean isValidCPF(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false;
        try {
            int d1 = 0, d2 = 0;
            for (int nCount = 1; nCount < cpf.length() - 1; nCount++) {
                int cpfDigit = Integer.parseInt(cpf.substring(nCount - 1, nCount));
                d1 = d1 + (11 - nCount) * cpfDigit;
                d2 = d2 + (12 - nCount) * cpfDigit;
            }
            int rest = (d1 % 11);
            int digit1 = (rest < 2) ? 0 : 11 - rest;
            d2 = d2 + 2 * digit1;
            rest = (d2 % 11);
            int digit2 = (rest < 2) ? 0 : 11 - rest;
            return cpf.substring(cpf.length() - 2).equals("" + digit1 + digit2);
        } catch (Exception e) { return false; }
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false;
        try {
            int sum = 0, weight = 2;
            for (int i = 11; i >= 0; i--) {
                int num = (int) (cnpj.charAt(i) - 48);
                sum = sum + (num * weight);
                weight = weight + 1;
                if (weight == 10) weight = 2;
            }
            int rest = sum % 11;
            char digit13 = (rest < 2) ? '0' : (char) ((11 - rest) + 48);

            sum = 0; weight = 2;
            for (int i = 12; i >= 0; i--) {
                int num = (int) (cnpj.charAt(i) - 48);
                sum = sum + (num * weight);
                weight = weight + 1;
                if (weight == 10) weight = 2;
            }
            rest = sum % 11;
            char digit14 = (rest < 2) ? '0' : (char) ((11 - rest) + 48);
            return (digit13 == cnpj.charAt(12)) && (digit14 == cnpj.charAt(13));
        } catch (Exception e) { return false; }
    }

    public String getUnmaskedText() {
        String text = super.getText();
        return text == null ? "" : text.replaceAll("[^0-9]", "");
    }
}