/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import javax.swing.Icon;

public class ModernIconLabel extends ModernLabel {

    private Icon icon;

    private String iconTextGap = "8";

    public ModernIconLabel() {

        initialize();
    }

    public ModernIconLabel(String text) {

        super(text);

        initialize();
    }

    private void initialize() {

        setHorizontalAlignment(LEFT);

        setHorizontalTextPosition(RIGHT);

        setIconTextGap(
                Integer.parseInt(iconTextGap)
        );
    }

    public Icon getCustomIcon() {
        return icon;
    }

    public void setCustomIcon(Icon icon) {

        this.icon = icon;

        setIcon(icon);
    }

    public String getIconTextGapValue() {
        return iconTextGap;
    }

    public void setIconTextGapValue(String iconTextGap) {

        this.iconTextGap = iconTextGap;

        try {

            setIconTextGap(
                    Integer.parseInt(iconTextGap)
            );

        } catch (Exception ignored) {
        }
    }
}