    import javax.swing.*;
    import javax.swing.border.Border;
    import javax.swing.border.LineBorder;
    import java.awt.*;
    import java.awt.event.ActionListener;
    import java.util.Random;

    public class MenuView extends JFrame {
        private JPanel mainPanel, displayPanel, menuPanel, buttonPanel, arrayPanel;
        private JButton randomButton, inputButton, sortButton;
        private JTextField[] elementFields;
        private JComboBox<Integer> arraySize;
        private final Integer[] sizes =  {10, 11, 12, 13, 14, 15};
        private final Random random = new Random();
        private int[] theArray;
        private final Color squeaky = Color.decode("#63cdda");;


        public MenuView() {
            setTitle("Heap Sort Visualization Menu");
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setMinimumSize(new Dimension(1200,720));
            initialize();
            setVisible(true);
        }

        private void initialize(){
            mainPanel = new JPanel(new GridLayout(2,1));
            displayPanel = new JPanel(new BorderLayout()); // panel for image/logo
            menuPanel = new JPanel(new BorderLayout());
            buttonPanel = new JPanel(new GridLayout(3,1));

            arraySize = new JComboBox<>(sizes);
            randomButton = new JButton("Random Elements");
            inputButton = new JButton("Input Elements");
            sortButton = new JButton("Heap Sort. Let's go!");

            designPanel();
            designButtons();

            displayLogo();

            //Dummy Panels
            JPanel dummyPanel[] = new JPanel[3];
            for(int i = 0; i < dummyPanel.length; i++){
                dummyPanel[i] = new JPanel(new FlowLayout(FlowLayout.CENTER));
                dummyPanel[i].setBackground(Color.decode("#f5cd79"));
            }
            dummyPanel[0].add(arraySize);
            dummyPanel[1].add(randomButton);
            dummyPanel[2].add(inputButton);

            JPanel dummy_sort_panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            dummy_sort_panel.add(sortButton);
            dummy_sort_panel.setBackground(Color.decode("#f5cd79"));

            // adding to menuPanel
            for(JPanel panel: dummyPanel){
                buttonPanel.add(panel);
            }

            menuPanel.add( buttonPanel, BorderLayout.CENTER);
            menuPanel.add(dummy_sort_panel, BorderLayout.SOUTH);


            // adding to mainframe
            mainPanel.add(displayPanel);
            mainPanel.add(menuPanel);
            add(mainPanel);
        }

        private void displayLogo(){
            Font newFont = new Font("Courier New", Font.BOLD, 230);
            JPanel logo = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JLabel text_logo = new JLabel("HEAP SORT");
            text_logo.setFont(newFont);
            text_logo.setForeground(Color.decode("#485460"));
            logo.setBackground(squeaky);
            logo.add(text_logo);
            displayPanel.add(logo, BorderLayout.CENTER);

        }


        /**
         * This functions resets all that is displayed in the display panel
         */

        public void resetDisplayPanel(){
            displayPanel.removeAll();
            displayPanel.revalidate();
            displayPanel.repaint();
            displayLogo();
        }


        /**
         * This function designs the buttons
         */

        private void designButtons(){
            Color summer_time = Color.decode("#f19066");
            Color white = Color.WHITE;
            Color creamy_peach = Color.decode("#596275");

            randomButton.setBackground(summer_time);
            inputButton.setBackground(summer_time);
            sortButton.setBackground(summer_time);

            randomButton.setPreferredSize(new Dimension(180,42));
            randomButton.setText("Random");
            inputButton.setPreferredSize(new Dimension(220,42));
            inputButton.setText("Input Mode");
            arraySize.setPreferredSize(new Dimension(100,40));
            arraySize.setBackground(creamy_peach);
            arraySize.setForeground(Color.decode("#303952"));
            ((JLabel)arraySize.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

            Font newFont = new Font("Courier", Font.BOLD, 15);
            randomButton.setForeground(white);
            inputButton.setForeground(white);
            sortButton.setForeground(white);
            arraySize.setFont(newFont);
            randomButton.setFont(newFont);
            inputButton.setFont(newFont);
            sortButton.setFont(new Font("Courier", Font.BOLD, 20));

            sortButton.setPreferredSize(new Dimension(300,60));

            Border border = new LineBorder(Color.decode("#f3a683"), 4);
            randomButton.setBorder(border);
            inputButton.setBorder(border);
            sortButton.setBorder( new LineBorder(Color.decode("#f3a683"), 6));

            randomButton.setFocusPainted(false);
            inputButton.setFocusPainted(false);
            sortButton.setFocusPainted(false);
        }

        private void designPanel(){
            mainPanel.setBackground(squeaky);
            displayPanel.setBackground(squeaky);
        }


        public void setRandomButtonListener(ActionListener ae){
            randomButton.addActionListener(ae);
        }

        public void setInputButtonListener(ActionListener ae){
            inputButton.addActionListener(ae);
        }

        public void setSortButtonListener(ActionListener ae){
            sortButton.addActionListener(ae);
        }

        public void createInputElement(){
            int size = Integer.parseInt(String.valueOf(arraySize.getSelectedItem()));
            arrayPanel = new JPanel(new GridLayout(1,size));
            arrayPanel.setPreferredSize(new Dimension(1200, 50));
            elementFields = new JTextField[size];
            for(int i = 0; i < size; i++){
                elementFields[i] = new JTextField("");
                elementFields[i].setSize(30, 80);
                elementFields[i].setHorizontalAlignment(JTextField.CENTER);
                elementFields[i].setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                arrayPanel.add(elementFields[i]);
            }

            displayPanel.add(arrayPanel, BorderLayout.SOUTH);
        }

        public void createRandomElement(){
            int size = Integer.parseInt(String.valueOf(arraySize.getSelectedItem()));
            arrayPanel = new JPanel(new GridLayout(1,size));
            arrayPanel.setPreferredSize(new Dimension(1200, 50));
            elementFields = new JTextField[size];

            int bound = Integer.MAX_VALUE;

            for(int i = 0; i < size; i++){
                elementFields[i] = new JTextField(String.valueOf(random.nextInt(bound)));
                elementFields[i].setSize(30, 80);
                elementFields[i].setHorizontalAlignment(JTextField.CENTER);
                elementFields[i].setFont(new Font("Comic Sans MS", Font.BOLD, 12));
                arrayPanel.add(elementFields[i]);
            }

            displayPanel.add(arrayPanel, BorderLayout.SOUTH);
        }


        /**
         * This function fills the main array holder with the values from the text fields
         *
         * @return true if the inputs from text fields have no error
         */

        public boolean fillArray(){
            if(elementFields == null){
                return false;
            }

            theArray = new int[elementFields.length];
            boolean hasError = false;
            for(int i = 0; i < elementFields.length; i++){
                try {
                    theArray[i] = Integer.parseInt(elementFields[i].getText().trim());
                    elementFields[i].setBackground(Color.WHITE);
                }catch(NumberFormatException e){
                    elementFields[i].setBackground(Color.decode("#e66767"));
                    hasError = true;
                }
            }
            return !hasError;
        }

        /**
         * This functions returns the array of elements  to be sorted
         * @return the main array holder
         */

        public int[] fetchArray(){
            return theArray;
        }

        /**
         * This functions provides a copy of the array of integers to be sorted
         * @return main array copy
         */

        public int[] fetchArrayCopy(){
            int[] copy = new int[theArray.length];
            for(int i = 0; i < copy.length; i++){
                copy[i] = theArray[i];
            }
            return copy;
        }

    }
