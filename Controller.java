import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller {
    private MenuView menuView;
    private HeapSortView current_view;

    public Controller(){
        menuView = new MenuView();
        setListeners();
    }

    private void setListeners(){
        menuView.setRandomButtonListener(new RandomButtonListener());
        menuView.setInputButtonListener(new InputButtonListener());
        menuView.setSortButtonListener(new SortButtonListener());

    }

    private class RandomButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            menuView.resetDisplayPanel();
            menuView.createRandomElement();
        }
    }

    private class InputButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            menuView.resetDisplayPanel();
            menuView.createInputElement();
        }
    }

    private class SortButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if(menuView.fillArray()){
               menuView.setVisible(false);
               HeapSortProcesses hp = new HeapSortProcesses();
               new HeapSort(hp).sort(menuView.fetchArrayCopy());
               current_view =  new HeapSortView(menuView.fetchArray(), hp);
               current_view.setExitListener(new ExitButtonListener());

            }
        }
    }

    private class ExitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            current_view.dispose();
            menuView.setVisible(true);
        }
    }


}




