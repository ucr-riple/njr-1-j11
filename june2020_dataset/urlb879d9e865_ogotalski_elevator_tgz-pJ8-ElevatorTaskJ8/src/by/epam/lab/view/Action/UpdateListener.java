package by.epam.lab.view.Action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import by.epam.lab.controller.IAction;


public class UpdateListener implements ActionListener {
        IAction controller;
        public UpdateListener(IAction controller){
                this.controller = controller;
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
                controller.updateView();
                
        }
}