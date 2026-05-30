package br.com.tenorio.swing.ui.fields;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * Área de texto moderna (TextArea) envelopada em ScrollPane nativo FlatLaf.
 * Oferece validação por perfis, limite de caracteres e contorno de erro integrado.
 */
public class ModernTextArea extends JScrollPane {

    public enum ValidationProfile {
        NONE,
        NUMERIC_ONLY,
        ALPHABETIC_ONLY,
        ALPHANUMERIC
    }

    private final JTextArea textArea;
    private ValidationProfile validationProfile = ValidationProfile.NONE;
    private boolean required = false;
    private String placeholder = "";
    private int maxLength = -1;
    private int minLength = -1;

    // ==================================================
    // CONSTRUTORES
    // ==================================================
    public ModernTextArea() {
        this("");
    }

    public ModernTextArea(String placeholder) {
        super();
        this.placeholder = placeholder != null ? placeholder : "";
        this.textArea = new JTextArea();

        configureTextArea();
        configureScrollWrapper();
        configureFiltersAndListeners();
    }

    // ==================================================
    // CONFIGURAÇÃO COMPONENTES E DESIGN
    // ==================================================
    private void configureTextArea() {
        textArea.setOpaque(false);
        textArea.setLineWrap(true);       // Quebra linha ao chegar no limite da borda
        textArea.setWrapStyleWord(true);   // Preserva integridade das palavras na quebra
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setMargin(new Insets(8, 10, 8, 10));

        updatePlaceholder();
    }

    private void configureScrollWrapper() {
        setOpaque(false);
        // Herda a borda padrão de inputs do sistema para consistência visual
        setBorder(UIManager.getBorder("TextField.border"));

        // Define o estilo de arredondamento diretamente na casca do ScrollPane
        putClientProperty(
                FlatClientProperties.STYLE,
                "arc: 16; focusWidth: 2;"
        );

        setPreferredSize(new Dimension(300, 100));
        setMinimumSize(new Dimension(100, 60));
        setViewportView(textArea);
    }

    private void configureFiltersAndListeners() {
        AbstractDocument document = (AbstractDocument) textArea.getDocument();
        document.setDocumentFilter(new DocumentFilter() {

            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string == null) return;

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String futureText = currentText.substring(0, offset) + string + currentText.substring(offset);

                if (acceptsInput(futureText, string)) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text == null || text.isEmpty()) {
                    super.replace(fb, offset, length, "", attrs);
                    return;
                }

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String futureText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                if (acceptsInput(futureText, text)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        // Captura o foco do componente interno e repassa os estados visuais para o container correto
        textArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                clearError();
            }

            @Override
            public void focusLost(FocusEvent e) {
                validateField();
            }
        });
    }

    private boolean acceptsInput(String fullText, String input) {
        if (maxLength != -1 && fullText.length() > maxLength) {
            return false;
        }

        // Permite sempre quebras de linha (\n) para manter a integridade do JTextArea
        if ("\n".equals(input)) {
            return true;
        }

        switch (validationProfile) {
            case NUMERIC_ONLY:
                return input.matches("[0-9\\n]*");
            case ALPHABETIC_ONLY:
                return input.matches("[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ \\n]*");
            case ALPHANUMERIC:
                return input.matches("[a-zA-Z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÊÍÓÔÕÖÚÇÑ \\n]*");
            default:
                return true;
        }
    }

    // ==================================================
    // SISTEMA DE VALIDAÇÃO INTEGRADO
    // ==================================================
    public boolean validateField() {
        String text = textArea.getText().trim();

        // Validação de Obrigatoriedade
        if (required && text.isEmpty()) {
            showError();
            return false;
        }

        // Validação de Tamanho Mínimo
        if (!text.isEmpty() && minLength != -1 && text.length() < minLength) {
            showError();
            return false;
        }

        clearError();
        return true;
    }

    public void showError() {
        // CORREÇÃO: Aplicado no "this" (JScrollPane) para que o contorno fique visível na borda
        this.putClientProperty(FlatClientProperties.OUTLINE, "error");
        this.repaint();
    }

    public void clearError() {
        // CORREÇÃO: Limpa a propriedade diretamente na casca externa
        this.putClientProperty(FlatClientProperties.OUTLINE, null);
        this.repaint();
    }

    private void updatePlaceholder() {
        String finalPlaceholder = placeholder;
        if (required && !placeholder.isEmpty() && !placeholder.endsWith(" *")) {
            finalPlaceholder = placeholder + " *";
        }
        textArea.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, finalPlaceholder);
    }

    // ==================================================
    // API PÚBLICA / REPASSE
    // ==================================================
    public String getText() {
        return textArea.getText();
    }

    public void setText(String text) {
        textArea.setText(text);
        validateField();
    }

    public JTextArea getInternalTextArea() {
        return textArea;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
        updatePlaceholder();
        validateField();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder != null ? placeholder : "";
        updatePlaceholder();
    }

    public ValidationProfile getValidationProfile() {
        return validationProfile;
    }

    public void setValidationProfile(ValidationProfile validationProfile) {
        this.validationProfile = validationProfile != null ? validationProfile : ValidationProfile.NONE;
        validateField();
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }
}