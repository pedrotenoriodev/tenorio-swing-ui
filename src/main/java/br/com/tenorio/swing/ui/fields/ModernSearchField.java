package br.com.tenorio.swing.ui.fields;

import br.com.tenorio.swing.ui.fields.base.ModernBaseTextField;
import br.com.tenorio.swing.ui.fields.base.ModernValidationType;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Modern search field extending ModernBaseTextField.
 * Features an embedded search button, clear button (X), debounce-based real-time 
 * search, and structured query validation.
 */
public class ModernSearchField extends ModernBaseTextField {

    private JButton searchButton;
    private ModernSearchListener searchListener;
    
    // Search settings
    private int minSearchLength = 0;
    private boolean alphaNumericOnly = false;
    private boolean realTimeSearchEnabled = false;
    
    // Debounce timer to prevent rapid database/query spamming
    private Timer debounceTimer;

    public ModernSearchField() {
        this("Search...");
    }

    public ModernSearchField(String placeholder) {
        super();
        this.setPlaceholder(placeholder);
        this.setValidationType(ModernValidationType.CUSTOM);
        initializeSearchComponent();
    }

    private void initializeSearchComponent() {
        // 1. Configure and embed the magnifying glass button on the right side
        searchButton = new JButton("🔍");
        searchButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        searchButton.setFocusPainted(false);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false); // Keeps background transparent inside the field
        searchButton.setPreferredSize(new Dimension(35, 30));
        searchButton.putClientProperty(FlatClientProperties.BUTTON_TYPE, FlatClientProperties.BUTTON_TYPE_BORDERLESS);
        
        // Action when clicking the glass icon
        searchButton.addActionListener((ActionEvent e) -> executeActionSearch());

        // Attach the button inside the JTextField trailing edge
        this.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, searchButton);
        
        // 2. Setup Enter Key action
        this.addActionListener(e -> executeActionSearch());

        // 3. Setup Debounce Timer (300ms delay)
        debounceTimer = new Timer(300, (ActionEvent e) -> executeInstantSearch());
        debounceTimer.setRepeats(false);

        // 4. Monitor text changes for real-time capabilities
        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { resetDebounceTimer(); }

            @Override
            public void removeUpdate(DocumentEvent e) { resetDebounceTimer(); }

            @Override
            public void changedUpdate(DocumentEvent e) { resetDebounceTimer(); }
            
            private void resetDebounceTimer() {
                if (realTimeSearchEnabled) {
                    debounceTimer.restart();
                } else if (getText().isEmpty()) {
                    // If the user clicks the native "X" clear button, reset the listing immediately
                    executeInstantSearch();
                }
            }
        });
    }

    /**
     * Validates the search query based on lengths and alphanumeric constraints.
     */
    private boolean validateSearchQuery(boolean showVisualError) {
        String query = getText();

        // Empty strings are always valid to clear active table filters
        if (query.isEmpty()) {
            clearError();
            return true;
        }

        // Minimum character enforcement
        if (query.length() < minSearchLength) {
            if (showVisualError) showError();
            return false;
        }

        // Alphanumeric sanitization if flag is enabled
        if (alphaNumericOnly && !query.matches("[a-zA-Z0-9 áàâãéèêíóôõúçÁÀÂÃÉÊÍÓÔÕÚÇ]*")) {
            if (showVisualError) showError();
            return false;
        }

        clearError();
        return true;
    }

    /**
     * Executes search triggered by an intentional direct action (Clicking glass icon or pressing Enter).
     */
    private void executeActionSearch() {
        if (validateSearchQuery(true)) {
            if (searchListener != null) {
                searchListener.onSearchTriggered(getText());
            }
        }
    }

    /**
     * Executes background search safely as the user types (Debounced).
     */
    private void executeInstantSearch() {
        if (validateSearchQuery(false)) {
            if (searchListener != null) {
                searchListener.onInstantSearch(getText());
            }
        }
    }

    // ==================================================
    // OVERRIDE - INTEGRATION WITH BASE VALIDATION
    // ==================================================

    @Override
    public boolean validateField() {
        // Integrates with required/mandatory checks of the core structure
        if (this.isRequired() && getText().isEmpty()) {
            showError();
            return false;
        }
        return validateSearchQuery(true);
    }

    @Override
    public String getText() {
        String text = super.getText();
        return text == null ? "" : text.trim();
    }

    // ==================================================
    // PUBLIC CONFIGURATION API
    // ==================================================

    /**
     * Enables or disables instant character-by-character searching.
     * @param enabled If true, triggers onInstantSearch using automatic debouncing.
     */
    public void setRealTimeSearchEnabled(boolean enabled) {
        this.realTimeSearchEnabled = enabled;
    }

    public boolean isRealTimeSearchEnabled() {
        return realTimeSearchEnabled;
    }

    public void setMinSearchLength(int minSearchLength) {
        this.minSearchLength = minSearchLength;
    }

    public int getMinSearchLength() {
        return minSearchLength;
    }

    public void setAlphaNumericOnly(boolean alphaNumericOnly) {
        this.alphaNumericOnly = alphaNumericOnly;
    }

    public boolean isAlphaNumericOnly() {
        return alphaNumericOnly;
    }

    public void setModernSearchListener(ModernSearchListener listener) {
        this.searchListener = listener;
    }

    // ==================================================
    // MODERN SEARCH LISTENER INTERFACE
    // ==================================================
    public interface ModernSearchListener {
        /**
         * Triggered when the user confirms the search explicitly (Clicks 🔍 or presses ENTER).
         */
        void onSearchTriggered(String query);

        /**
         * Triggered asynchronously while typing. 
         * Ideal for fast memory list filters or local JTable row sorters.
         */
        void onInstantSearch(String query);
    }
}