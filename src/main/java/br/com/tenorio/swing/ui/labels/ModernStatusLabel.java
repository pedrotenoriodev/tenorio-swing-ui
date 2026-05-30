/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package br.com.tenorio.swing.ui.labels;

import java.awt.Color;

public class ModernStatusLabel extends ModernLabel {

    public enum Status {

        SUCCESS,
        WARNING,
        ERROR,
        INFO
    }

    private Status status = Status.INFO;

    public ModernStatusLabel() {

        updateStatus();
    }

    public void setStatus(Status status) {

        this.status = status;

        updateStatus();
    }

    public Status getStatus() {

        return status;
    }

    private void updateStatus() {

        switch (status) {

            case SUCCESS ->
                setForeground(new Color(34, 197, 94));

            case WARNING ->
                setForeground(new Color(245, 158, 11));

            case ERROR ->
                setForeground(new Color(239, 68, 68));

            default ->
                setForeground(new Color(59, 130, 246));
        }
    }
}