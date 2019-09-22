package com.mareike.solrsearch.UI;

import javax.swing.*;

public class FPComponent {

    private JComboBox dropdown;
    private JCheckBox checkbox;

    public FPComponent(JCheckBox checkbox, JComboBox dropdown){
        this.dropdown = dropdown;
        this.checkbox = checkbox;
    }

    public JComboBox getDropdown(){
        return dropdown;
    }

    public JCheckBox getCheckBox(){
        return checkbox;
    }
}
