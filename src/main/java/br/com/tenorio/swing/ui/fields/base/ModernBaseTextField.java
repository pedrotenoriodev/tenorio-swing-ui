/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.fields.base;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.regex.Pattern;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.InputVerifier;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class ModernBaseTextField extends JTextField implements ModernValidatable {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private String placeholder;

    private boolean required;

    private String requiredMessage;

    private boolean validateOnFocusLost;

    private boolean showClearButton;

    private int maximumLength;

    private int minimumLength;

    private int cornerRadius;

    private int minimumHeight;

    private int fontSize;

    private ModernValidationType validationType;

    public ModernBaseTextField() {

        initialize();
    }

    private void initialize() {

        placeholder = "";

        required = false;

        requiredMessage = "Campo obrigatório";

        validateOnFocusLost = true;

        showClearButton = true;

        maximumLength = -1;

        minimumLength = -1;

        cornerRadius = 16;

        minimumHeight = 40;

        fontSize = 14;

        validationType = ModernValidationType.NONE;

        configureStyle();

        configureValidation();

        configureEvents();
    }

    private void configureStyle() {

        putClientProperty(
                FlatClientProperties.STYLE,
                "arc:" + cornerRadius + ";focusWidth:2;"
        );

        putClientProperty(
                "JComponent.minimumHeight",
                minimumHeight
        );

        putClientProperty(
                FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON,
                showClearButton
        );

        setFont(new Font("Segoe UI", Font.PLAIN, fontSize));

        setMargin(new Insets(0, 12, 0, 12));
    }

    private void configureValidation() {

        AbstractDocument document =
                (AbstractDocument) getDocument();

        document.setDocumentFilter(new DocumentFilter() {

            @Override
            public void insertString(
                    FilterBypass fb,
                    int offset,
                    String string,
                    AttributeSet attr
            ) throws BadLocationException {

                if (string == null) {
                    return;
                }

                String current =
                        fb.getDocument().getText(
                                0,
                                fb.getDocument().getLength()
                        );

                String future =
                        current.substring(0, offset)
                        + string
                        + current.substring(offset);

                if (acceptText(future, string)) {

                    super.insertString(
                            fb,
                            offset,
                            string,
                            attr
                    );
                }
            }

            @Override
            public void replace(
                    FilterBypass fb,
                    int offset,
                    int length,
                    String text,
                    AttributeSet attrs
            ) throws BadLocationException {

                if (text == null || text.isEmpty()) {

                    super.replace(
                            fb,
                            offset,
                            length,
                            "",
                            attrs
                    );

                    return;
                }

                String current =
                        fb.getDocument().getText(
                                0,
                                fb.getDocument().getLength()
                        );

                String future =
                        current.substring(0, offset)
                        + text
                        + current.substring(offset + length);

                if (acceptText(future, text)) {

                    super.replace(
                            fb,
                            offset,
                            length,
                            text,
                            attrs
                    );
                }
            }
        });
    }

    private boolean acceptText(
            String fullText,
            String insertedText
    ) {

        if (maximumLength > -1
                && fullText.length() > maximumLength) {

            return false;
        }

        switch (validationType) {

            case NUMBERS:
                return insertedText.matches("[0-9]*");

            case LETTERS:
                return insertedText.matches(
                        "[a-zA-ZáàâãéèêíïóôõöúçñÁÀÂÃÉÊÍÏÓÔÕÖÚÇÑ ]*"
                );

            case ALPHANUMERIC:
                return insertedText.matches(
                        "[a-zA-Z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÊÍÏÓÔÕÖÚÇÑ ]*"
                );

            default:
                return true;
        }
    }

    private void configureEvents() {

        addFocusListener(new FocusAdapter() {

            @Override
            public void focusGained(FocusEvent e) {

                clearError();

                SwingUtilities.invokeLater(() -> selectAll());
            }

            @Override
            public void focusLost(FocusEvent e) {

                if (validateOnFocusLost) {

                    validateField();
                }
            }
        });

        setInputVerifier(new InputVerifier() {

            @Override
            public boolean verify(JComponent input) {

                return validateField();
            }
        });
    }

    @Override
    public boolean validateField() {

        String text = getText();

        if (text != null) {
            text = text.trim();
        }

        if (required
                && (text == null || text.isEmpty())) {

            showError();

            return false;
        }

        if (minimumLength > -1
                && text != null
                && text.length() < minimumLength) {

            showError();

            return false;
        }

        if (validationType == ModernValidationType.EMAIL) {

            if (text != null
                    && !text.isEmpty()
                    && !EMAIL_PATTERN.matcher(text).matches()) {

                showError();

                return false;
            }
        }

        clearError();

        return true;
    }

    protected void showError() {

        putClientProperty(
                FlatClientProperties.OUTLINE,
                "error"
        );

        repaint();
    }

    public void clearError() {

        putClientProperty(
                FlatClientProperties.OUTLINE,
                null
        );

        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {

        this.placeholder = placeholder;

        putClientProperty(
                FlatClientProperties.PLACEHOLDER_TEXT,
                placeholder
        );
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {

        this.required = required;
    }

    public String getRequiredMessage() {
        return requiredMessage;
    }

    public void setRequiredMessage(String requiredMessage) {

        this.requiredMessage = requiredMessage;
    }

    public boolean isValidateOnFocusLost() {
        return validateOnFocusLost;
    }

    public void setValidateOnFocusLost(
            boolean validateOnFocusLost
    ) {

        this.validateOnFocusLost = validateOnFocusLost;
    }

    public boolean isShowClearButton() {
        return showClearButton;
    }

    public void setShowClearButton(
            boolean showClearButton
    ) {

        this.showClearButton = showClearButton;

        putClientProperty(
                FlatClientProperties.TEXT_FIELD_SHOW_CLEAR_BUTTON,
                showClearButton
        );
    }

    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {

        this.maximumLength = maximumLength;
    }

    public int getMinimumLength() {
        return minimumLength;
    }

    public void setMinimumLength(int minimumLength) {

        this.minimumLength = minimumLength;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int cornerRadius) {

        this.cornerRadius = cornerRadius;

        configureStyle();

        repaint();
    }

    public int getMinimumHeight() {
        return minimumHeight;
    }

    public void setMinimumHeight(int minimumHeight) {

        this.minimumHeight = minimumHeight;

        putClientProperty(
                "JComponent.minimumHeight",
                minimumHeight
        );

        revalidate();

        repaint();
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {

        this.fontSize = fontSize;

        setFont(
                getFont().deriveFont(
                        (float) fontSize
                )
        );
    }

    public ModernValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(
            ModernValidationType validationType
    ) {

        this.validationType = validationType;
    }

}