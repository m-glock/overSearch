package com.mareike.solrsearch.UI;

import javax.swing.*;
import java.awt.*;

public class ErrorMessage {

    public ErrorMessage(String message){
        JLabel errorMessage = new JLabel();
        errorMessage.setFont(new Font("Tahoma", Font.PLAIN, 20));
        errorMessage.setText(message);
        JOptionPane.showMessageDialog(null, errorMessage, "Connection lost", JOptionPane.ERROR_MESSAGE);
    }
}
