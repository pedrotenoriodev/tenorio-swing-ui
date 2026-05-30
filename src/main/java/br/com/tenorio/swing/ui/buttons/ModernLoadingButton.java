/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.buttons;

public class ModernLoadingButton extends ModernButton {

    private boolean loading;

    private String originalText;

    public ModernLoadingButton() {

        super();

        loading = false;
    }

    public ModernLoadingButton(String text) {

        super(text);

        loading = false;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {

        this.loading = loading;

        if (loading) {

            originalText = getText();

            setEnabled(false);

            setText("Carregando...");

        } else {

            setEnabled(true);

            if (originalText != null) {

                setText(originalText);
            }
        }
    }

}