package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurQuitter implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur le bouton Quitter");
        System.exit(0); // Exit the application
    }
}