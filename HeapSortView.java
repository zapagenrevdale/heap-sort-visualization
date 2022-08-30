import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HeapSortView extends JFrame {
    /**
     * Main property
     */
    private HeapSortProcesses processes;
    private int[] theArray;

    /**
     * These are the components inside the JFrame
     */
    private JPanel animationPanel, arrayPanel;
    private Node[] nodes;
    private Line[] lines;
    private JTextField[] elementFields;
    private JLabel pass;
    private JButton  pause, exit;

    /**
     * Global variables for the animation
     */
    private Node curr_node1, curr_node2;
    private int node1_x, node1_y, node2_x, node2_y;
    private int direction, dx, dy;
    private int curr_index1, curr_index2;
    private boolean isSorted, displayFlag;
    private int pass_count;

    /**
     * Timer for the animations
     */
    private Timer tmr, swaptmr;


    public HeapSortView(int[] theArray, HeapSortProcesses processes){
        this.processes = processes;
        this.theArray = theArray;
        setTitle("Heap Sort Visualization");
        setSize(1250, 750);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initialize();
        startAnimation();
    }

    private void startAnimation(){
        tmr = new Timer(500, new TimerAction());
        tmr.start();
    }

    /**
     * This function initializes all the components in the Animation Panel
     */

    private void initialize(){
        initializeButtons();
        nodes = new Node[theArray.length];
        lines = new Line[theArray.length];
        initializeNodes();

        animationPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Line line : lines) {
                    g.drawLine(line.x1, line.y1, line.x2, line.y2);
                }
            }
        };
        animationPanel.setBackground(Color.decode("#ffbe76"));
        for(int i = 0; i < nodes.length; i++){
            animationPanel.add(nodes[i]);
        }
        setArrayPanel();
        setPassDisplay();
        add(animationPanel);
        setVisible(true);
    }


    /**
     * This functions initializes the the Pass label
     */
    private void setPassDisplay(){
        Font newFont = new Font("Courier New", Font.BOLD, 35);
        pass = new JLabel("PASS: 0");
        pass.setFont(newFont);
        pass.setBounds(20,0, 180,35);
        add(pass);
    }

    /**
     * This function updates to the current Pass
     */
    private void incrementPass(){
        pass.setText("PASS: "+ ++pass_count);
    }

    /**
     * This function initializes the panel and its subcomponents for the array visualization
     */
    private void setArrayPanel(){
        arrayPanel = new JPanel(new GridLayout(1,theArray.length));
        elementFields = new JTextField[theArray.length];
        for(int i = 0; i < elementFields.length; i++){
            elementFields[i] = new JTextField(String.valueOf(theArray[i]));
            elementFields[i].setEnabled(false);
            elementFields[i].setSize(30, 80);
            elementFields[i].setHorizontalAlignment(JTextField.CENTER);
            elementFields[i].setFont(new Font("Comic Sans MS", Font.BOLD, 12));
            elementFields[i].setDisabledTextColor(Color.decode("#1e272e"));
            arrayPanel.add(elementFields[i]);
        }
        arrayPanel.setBounds(20,620,1200,50);
        add(arrayPanel);
    }


    /**
     * This function initializes/creates the pause and exit button
     */
    private void initializeButtons(){
        pause = new JButton( "pause");
        pause.setBounds(1100,570,100,42);
        pause.addActionListener(new ButtonListeners());
        exit = new JButton( "exit");
        exit.setBounds(980,570,100,42);
        designButtons();
        add(pause);
        add(exit);
    }


    /**
     * Function for designing the buttons
     */
    private void designButtons(){
        Border border = new LineBorder(Color.decode("#f3a683"), 4);
        Font newFont = new Font("Courier", Font.BOLD, 15);
        Color summer_time = Color.decode("#f19066");
        Color white = Color.WHITE;

        pause.setPreferredSize(new Dimension(180,42));
        pause.setText("pause");
        pause.setForeground(white);
        pause.setFont(newFont);
        pause.setBorder(border);
        pause.setFocusPainted(false);
        pause.setBackground(summer_time);

        exit.setPreferredSize(new Dimension(180,42));
        exit.setText("exit");
        exit.setForeground(white);
        exit.setFont(newFont);
        exit.setBorder(border);
        exit.setFocusPainted(false);
        exit.setBackground(summer_time);
    }

    /**
     * This function initializes all the nodes for the heap sort visualizationn
     * Nodes = elements in the array
     */

    private void initializeNodes(){
        int location_x = 0;
        int location_y = 0;
        for(int i = 0; i < nodes.length; i++){
            int parent = (i-1)/2;
            switch (i){
                case 0:  location_x = 560;  location_y = 25;  break;
                case 1:  location_x = 260;  location_y = 125; break;
                case 2:  location_x = 860;  location_y = 125; break;
                case 3:  location_x = 110;  location_y = 275; break;
                case 4:  location_x = 410;  location_y = 275; break;
                case 5:  location_x = 710;  location_y = 275; break;
                case 6:  location_x = 1010; location_y = 275; break;
                case 7:  location_x = 35;   location_y = 475; break;
                case 8:  location_x = 185;  location_y = 475; break;
                case 9:  location_x = 335;  location_y = 475; break;
                case 10: location_x = 485;  location_y = 475; break;
                case 11: location_x = 635;  location_y = 475; break;
                case 12: location_x = 785;  location_y = 475; break;
                case 13: location_x = 935;  location_y = 475; break;
                case 14: location_x = 1085; location_y = 475; break;
            }

            nodes[i] =  new Node(theArray[i], location_x, location_y);
            nodes[i].setBounds(location_x, location_y, 80, 80);
            lines[i] = new Line(location_x+40, location_y+40, nodes[parent].x_bound + 40, nodes[parent].y_bound + 40);

        }
    }

    /**
     * This function does the animation for swapping the nodes
     * @param index1 the index of the first node
     * @param index2 the index of the second node
     */

    private void swap(int index1, int index2){

        tmr.stop();
        swaptmr = new Timer(20, new TimerSwapAction());

        curr_index1 = index1;
        curr_index2 = index2;
        curr_node1 = nodes[index1];
        curr_node2 = nodes[index2];
        node1_x = curr_node1.x_bound;
        node1_y = curr_node1.y_bound;
        node2_x = curr_node2.x_bound;
        node2_y = curr_node2.y_bound;

        if(node1_x < node2_x){
            direction = -1;
        }
        else{
            direction = 1;
        }
        dx = Math.abs(curr_node1.x_bound - curr_node2.x_bound);
        dy = Math.abs(curr_node1.y_bound - curr_node2.y_bound);



        try {
            int gcd = gcd(dx,dy);

            if(gcd == 225){
                gcd = 45;
            }
            else{
                gcd = 25;
            }
            dx /= gcd;
            dy /= gcd;
        }catch (ArithmeticException e){}

        swaptmr.start();
    }

    /**
     * This function gets the the common divisor of two numbers
     * @param a
     * @param b
     * @return the greatest common divisor
     */

    int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }


    /**
     * This class does the detailed animation/changes in swapping two nodes
     */
    private class TimerSwapAction implements ActionListener {

        public void actionPerformed(ActionEvent ae){
            displayFlag = displayFlag? false: true;
            node1_x = node1_x - direction*dx;
            node2_x = node2_x + direction*dx;
            node1_y = node1_y - dy;
            node2_y = node2_y + dy;

            curr_node1.setBounds(node1_x, node1_y, 80,80);
            curr_node2.setBounds(node2_x, node2_y, 80,80);

            toggleSwap();

            if(doneSwapping() || processes.isEmpty()){
                ((Timer)ae.getSource()).stop();
                swapNodes();
                if(!isSorted){
                    curr_node1.setDefaultColor();
                    curr_node2.setDefaultColor();
                    setDefault(curr_index1);
                    setDefault(curr_index2);
                }
                else{
                    setDefault(curr_index2);
                    setSorted(curr_index1);
                    if(pass_count == theArray.length -1){
                        pass_count--;
                    }
                    incrementPass();
                    isSorted = false;
                }
                swapVisualElement();
                tmr.start();
                pause.setEnabled(true);
                swaptmr.stop();
                swaptmr = null;

            }
        }
    }

    /**
     * This function swaps the nodes
     */
    private void swapNodes(){
        nodes[curr_index1].x_bound = node1_x;
        nodes[curr_index1].y_bound = node1_y;
        nodes[curr_index2].x_bound = node2_x;
        nodes[curr_index2].y_bound = node2_y;
        Node temp = nodes[curr_index1];
        nodes[curr_index1] = nodes[curr_index2];
        nodes[curr_index2] = temp;
    }

    /**
     * This function checks whether two nodes are done swapping
     * @return
     */
    private boolean doneSwapping(){
        return node1_x == curr_node2.x_bound;
    }

    public void setExitListener(ActionListener ae){
        exit.addActionListener(ae);
    }
    
    /**
     * This class does the decision and the animation
     */

    private class TimerAction implements ActionListener {
        public void actionPerformed(ActionEvent ae){
            if(processes.isEmpty() || processes == null){
                tmr.stop();
                return;
            }
            Operation operation =  processes.dequeueProcess();
            if(operation.operation == OperationType.CHECK){
                nodes[operation.index1].setCheckedColor();
                setChecked(operation.index1);
                if(operation.index2 != -1){
                    nodes[operation.index2].setCheckedColor();
                    setChecked(operation.index2);
                }

            }
            else if(operation.operation == OperationType.PICK){
                nodes[operation.index1].setSelectedColor();
                setPick(operation.index1);
                if(operation.index2 != -1) {
                    nodes[operation.index2].setDefaultColor();
                    setDefault(operation.index2);
                }
            }
            else if(operation.operation == OperationType.SWAP){
                pause.setEnabled(false);
                swap(operation.index1, operation.index2);
            }
            else if(operation.operation == OperationType.IDLE){
                nodes[operation.index1].setDefaultColor();
                nodes[operation.index2].setDefaultColor();
                setDefault(operation.index1);
                setDefault(operation.index2);
            }
            else{
                pause.setEnabled(false);
                swap(operation.index1, operation.index2);
                isSorted = true;
                curr_node2.setSortedColor();
            }

        }
    }

    /**
     * This class is an action listener for the buttons pause and exit
     */
    private class ButtonListeners implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String action = ((JButton)actionEvent.getSource()).getText();

            if(action.equals("pause")) {
                if(tmr.isRunning()){
                    tmr.stop();
                }

                ((JButton) actionEvent.getSource()).setText("resume");
            }
            else if(action.equals("resume")){

                if(!tmr.isRunning()){
                    tmr.start();
                }
                ((JButton)actionEvent.getSource()).setText("pause");
            }

        }
    }

    /**
     *  All functions and properties below are mainly for the animation 
     *  appearance for the Array visualization
     */

    private final Color light_green     = Color.decode("#badc58");
    private final Color light_blue      = Color.decode("#686de0");
    private final Color golden_yellow   = Color.decode("#f9ca24");
    private Color red_days =  Color.decode("#4bcffa");
    private int counter;

    private void setChecked(int index){
        elementFields[index].setBackground(light_blue);
    }

    private void setPick(int index){
        elementFields[index].setBackground(light_green);
    }

    private void setSorted(int index){
        elementFields[index].setBackground(golden_yellow);
    }

    private void setDefault(int index){
        elementFields[index].setBackground(Color.WHITE);
    }

    /**
     * This function swaps the value of the displayed  array
     */

    private void swapVisualElement(){
        String value_1 = elementFields[curr_index1].getText();
        String value_2 = elementFields[curr_index2].getText();
        elementFields[curr_index1].setText(value_2);
        elementFields[curr_index2].setText(value_1);
    }

    /**
     * This function animates the array elements being swapped
     */
    private void toggleSwap(){
        counter++;
        if(counter == 5){
            if(displayFlag){
                elementFields[curr_index1].setBackground(red_days);
                elementFields[curr_index2].setBackground(Color.WHITE);
            }
            else{
                elementFields[curr_index1].setBackground(Color.WHITE);
                elementFields[curr_index2].setBackground(red_days);
            }
            counter = 0;
        }
    }

}
