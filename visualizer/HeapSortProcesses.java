
/**
 * This class stores "operations" from Operation.java
 */
import java.util.LinkedList;
import java.util.Stack;

public class HeapSortProcesses {
    LinkedList<Operation> processes;
    Stack<Operation> history;

    public HeapSortProcesses(){
        processes = new LinkedList<>();
        history = new Stack<>();
    }

    public void addProcess(Operation operation){
        processes.add(operation);
    }

    public Operation dequeueProcess(){
        Operation temp = processes.pollFirst();
        history.push(temp);
        return temp;
    }
    
    public boolean isEmpty(){
        return processes.size() == 0;
    }

}
