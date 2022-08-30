/**
 * This class contains the operation type and the indices of the elements
 * that were affected by that operation
 */

public class Operation {
    protected OperationType operation;
    int index1, index2;

    public Operation(OperationType operation, int index1, int index2){
        this.operation = operation;
        this.index1 = index1;
        this.index2 = index2;
    }
}
