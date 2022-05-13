// Java Program to create a popup and display
// it on a parent frame
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//Alexander Stokes - 1578409, Ralph Huey Olegario - 1522057
class pop extends JFrame{
    /**
     * Shows a popup that displays a successful or failure message
     * @param message The message that is written on the popup
     */
    pop(String message)
    {
        //Pops up a message that shows if a path is found or not
        JOptionPane.showMessageDialog(null, message, "InfoBox: " + "Status", JOptionPane.INFORMATION_MESSAGE);
    }
}