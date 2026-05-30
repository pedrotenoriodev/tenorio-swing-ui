package br.com.tenorio.swing.ui.fields;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * Modern password field with native reveal button (eye icon),
 * rounded corners, and integrated form validation.
 */
public class ModernPasswordField extends JPasswordField {

    private boolean required;
    private String placeholder;

    public ModernPasswordField() {
        super();
        initialize();
    }

    private void initialize() {
        // CORREÇÃO: Unificando o estilo em uma única linha para o FlatLaf aplicar o 'arc' e o 'olho' juntos
        putClientProperty(FlatClientProperties.STYLE, ""
                + "arc: 16;"
                + "focusWidth: 2;"
                + "showRevealButton: true;"
        );

        putClientProperty("JComponent.minimumHeight", 40);

        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setMargin(new Insets(0, 12, 0, 12));

        // Listener para limpar o erro visual assim que o usuário focar no campo
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                clearError();
            }
        });
    }

    /**
     * Validates if the field meets the structural and business constraints.
     * @return true if valid, false if it fails validation.
     */
    public boolean validateField() {
        String text = new String(getPassword()).trim();

        if (required && text.isEmpty()) {
            showError();
            return false;
        }

        clearError();
        return true;
    }

    /**
     * Applies the visual error outline to the component.
     */
    public void showError() {
        putClientProperty(FlatClientProperties.OUTLINE, "error");
        repaint();
    }

    /**
     * Removes the visual error outline from the component.
     */
    public void clearError() {
        putClientProperty(FlatClientProperties.OUTLINE, null);
        repaint();
    }

    // ==================================================
    // GETTERS & SETTERS (PADRONIZADOS COM A BASE)
    // ==================================================

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
        // Se houver um placeholder, adiciona o asterisco indicador de obrigatório
        if (required && placeholder != null && !placeholder.endsWith(" *")) {
            setPlaceholder(placeholder);
        }
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        String finalPlaceholder = placeholder;
        
        if (required && placeholder != null && !placeholder.endsWith(" *")) {
            finalPlaceholder = placeholder + " *";
        }
        
        putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, finalPlaceholder);
    }
}