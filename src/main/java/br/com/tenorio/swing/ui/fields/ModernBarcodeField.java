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

/**
 * Specialized field for capturing Barcodes (EAN-8, EAN-13, DUN-14, and 44-digit invoices).
 * Optimized for fast optical scanner inputs and strict alphanumeric blocking.
 */
public class ModernBarcodeField extends ModernBaseTextField {

    public ModernBarcodeField() {
        super();
        configureBarcodeComponent();
    }

    private void configureBarcodeComponent() {
        // Ignora os filtros genéricos da base para gerenciar via DocumentFilter numérico estrito
        this.setValidationType(ModernValidationType.CUSTOM);
        this.setPlaceholder("7890000000000");

        // Configuração de filtro estrito (Apenas números e limite de 44 caracteres)
        AbstractDocument doc = (AbstractDocument) this.getDocument();
        doc.setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;
                
                String onlyNumbers = string.replaceAll("[^0-9]", "");
                if (acceptsLength(fb, onlyNumbers.length())) {
                    super.insertString(fb, offset, onlyNumbers, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null) text = "";
                
                String onlyNumbers = text.replaceAll("[^0-9]", "");
                if (acceptsLength(fb, onlyNumbers.length() - length)) {
                    super.replace(fb, offset, length, onlyNumbers, attrs);
                }
            }

            private boolean acceptsLength(FilterBypass fb, int delta) {
                // Limite máximo de 44 caracteres (padrão de chaves de acesso de NF-e e boletos bancários)
                return (fb.getDocument().getLength() + delta) <= 44;
            }
        });

        // Comportamento do foco otimizado para leitores ópticos industriais
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Garante a seleção total do texto para que o próximo bip limpe o input anterior
                SwingUtilities.invokeLater(() -> selectAll());
            }
        });
    }

    // ==================================================
    // OVERRIDES - INTEGRAÇÃO COM A VALIDAÇÃO DA BASE
    // ==================================================

    @Override
    public boolean validateField() {
        String barcode = getText();

        // Se estiver vazio e for obrigatório, a classe base já manipula o erro de forma correta
        if (barcode.isEmpty()) {
            if (this.isRequired()) {
                showError();
                return false;
            }
            clearError();
            return true;
        }

        // Validação estrutural de padrões comerciais com Dígito Verificador (GS1)
        if (barcode.length() == 13 || barcode.length() == 14) {
            if (isValidLuhnMod10(barcode)) {
                clearError();
                return true;
            }
        } else if (barcode.length() == 8 || barcode.length() == 44) {
            // EAN-8 ou Chaves de acesso longas passam direto se o tamanho for válido
            clearError();
            return true;
        }

        showError();
        return false;
    }

    /**
     * Official Modulus 10 algorithm for GS1 barcode verification.
     */
    private boolean isValidLuhnMod10(String barcode) {
        int sum = 0;
        int length = barcode.length();
        int providedVerifier = Character.getNumericValue(barcode.charAt(length - 1));

        // Varre de trás para frente ignorando o último dígito
        for (int i = length - 2, weight = 3; i >= 0; i--) {
            sum += Character.getNumericValue(barcode.charAt(i)) * weight;
            weight = (weight == 3) ? 1 : 3; // Alterna pesos entre 3 e 1
        }

        int calculatedVerifier = (10 - (sum % 10)) % 10;
        return calculatedVerifier == providedVerifier;
    }

    @Override
    public String getText() {
        String text = super.getText();
        return text == null ? "" : text.trim();
    }
}