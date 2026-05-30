package br.com.tenorio.swing.ui.fields;

import br.com.tenorio.swing.ui.fields.base.ModernBaseTextField;
import br.com.tenorio.swing.ui.fields.base.ModernValidationType;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Modern text field focused on monetary values (BRL - R$).
 * Automatically formats as the user types and returns a clean BigDecimal.
 * Integrated with Tenorio UI base validation.
 */
public class ModernCurrencyField extends ModernBaseTextField {

    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
    private boolean isFormatting = false;
    private int maxDigits = 11; // Safe default for up to 999 million (999.999.999,00)

    public ModernCurrencyField() {
        super();
        configureCurrencyStyle();
        configureCurrencyFormatting();
    }

    private void configureCurrencyStyle() {
        // Alinha o texto para a direita (padrão financeiro)
        this.setHorizontalAlignment(SwingConstants.RIGHT);
        
        // Ignora os filtros padrões da base para gerenciar via DocumentListener local
        this.setValidationType(ModernValidationType.CUSTOM);

        // Injeta o "R$" fixo na esquerda usando FlatLaf leading component
        JLabel lblSymbol = new JLabel("R$ ");
        lblSymbol.setFont(new Font("Segoe UI", Font.BOLD, this.getFontSize()));
        lblSymbol.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));
        lblSymbol.setForeground(UIManager.getColor("TextField.foreground")); 
        
        this.putClientProperty(FlatClientProperties.TEXT_FIELD_LEADING_COMPONENT, lblSymbol);
        
        // Ajusta a margem para balancear o texto alinhado à direita
        this.setMargin(new Insets(0, 5, 0, 12));

        // Inicia zerado
        setValue(BigDecimal.ZERO);
    }

    private void configureCurrencyFormatting() {
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { format(); }

            @Override
            public void removeUpdate(DocumentEvent e) { format(); }

            @Override
            public void changedUpdate(DocumentEvent e) { format(); }

            private void format() {
                if (isFormatting) return;

                SwingUtilities.invokeLater(() -> {
                    isFormatting = true;

                    String cleanText = getText().replaceAll("[^0-9]", "");

                    if (cleanText.length() > maxDigits) {
                        cleanText = cleanText.substring(0, maxDigits);
                    }

                    if (cleanText.isEmpty()) {
                        cleanText = "0";
                    }

                    try {
                        double doubleValue = Double.parseDouble(cleanText) / 100.0;
                        BigDecimal value = BigDecimal.valueOf(doubleValue);
                        setFormattedText(value);
                    } catch (NumberFormatException ex) {
                        setFormattedText(BigDecimal.ZERO);
                    }

                    isFormatting = false;
                });
            }
        });
    }

    private void setFormattedText(BigDecimal value) {
        String formatted = currencyFormatter.format(value);
        formatted = formatted.replace("R$", "").trim();
        this.setText(formatted);
    }

    // ==================================================
    // OVERRIDES - INTEGRAÇÃO COM A VALIDAÇÃO DA BASE
    // ==================================================

    @Override
    public boolean validateField() {
        // Se for obrigatório, garante que o valor seja maior que zero (ou mude a lógica se aceitar zero)
        if (this.isRequired() && getBigDecimalValue().compareTo(BigDecimal.ZERO) == 0) {
            showError();
            return false;
        }
        
        // Executa as validações padrão de tamanho da base se houverem
        return super.validateField();
    }

    // ==================================================
    // PUBLIC API
    // ==================================================

    /**
     * Returns the typed value as a clean BigDecimal, perfect for Databases.
     */
    public BigDecimal getBigDecimalValue() {
        String cleanText = getText().replaceAll("[^0-9]", "");
        if (cleanText.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            if (cleanText.length() > maxDigits) {
                cleanText = cleanText.substring(0, maxDigits);
            }
            double doubleValue = Double.parseDouble(cleanText) / 100.0;
            return BigDecimal.valueOf(doubleValue);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Sets a BigDecimal value into the field from backend/database.
     */
    public void setValue(BigDecimal value) {
        if (value == null) {
            value = BigDecimal.ZERO;
        }
        setFormattedText(value);
    }

    public int getMaxDigits() {
        return maxDigits;
    }

    public void setMaxDigits(int maxDigits) {
        if (maxDigits > 0) {
            this.maxDigits = maxDigits;
            setValue(getBigDecimalValue());
        }
    }
}