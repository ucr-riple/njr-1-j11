package by.epam.lab.controller;

public interface IAction extends Runnable{
       public void start();
       public void abort();
       public void viewLog();
       public void updateView();
}
