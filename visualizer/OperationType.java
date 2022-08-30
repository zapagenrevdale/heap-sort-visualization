/**
 * This class contains the type of operation that is done in heap sort
 */
public enum OperationType {
    
    /**
     * PICK = Element has been selected after being weighted which one is greater
     * SORTED = The current max value is at the root
     * CHECK =  Element is being weighted
     * IDLE = No swapping happened
     */
    PICK, SWAP, SORTED, CHECK, IDLE
}
