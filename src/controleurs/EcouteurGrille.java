package controleurs;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurGrille implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action sur la grille");
    }
}