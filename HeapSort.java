/*
This HeapSort class is used to save all actions done into a list of processes.
 */


public class HeapSort {

    // This is where all processes are saved during the execution of heap sort
    private HeapSortProcesses processes;

    public HeapSort(HeapSortProcesses processes){
        this.processes = processes;
    }


    /**
     * This function sorts the array using Heap Sort.
     * @param arr array to be partitioned
     *
     **/

    public void sort(int[] arr) {
        int n = arr.length;

        for(int i = n/2-1; i>=0; i--){
            max_heap(arr, n, i);
        }

        for (int i=n-1; i>=0; i--)
        {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            finishProcessAdd(i,0);

            max_heap(arr, i, 0);
        }

    }


    /**
     * This function transforms the array into max-heap tree (logically)
     * This function also saves actions done in executing the max-heap
     *
     * @param arr array to be partitioned
     * @param limit index-limit of array to be max-heaped
     * @param parent index of parent node
     *
     *
     **/
    private void max_heap(int[] arr, int limit, int parent) {

        int child = 2*parent + 1;
        if(child >= limit){
            return;
        }

        if(child < limit-1){
            checkProcessAdd(child, child+1);
            if( arr[child] < arr[child+1]){
                pickProcessAdd(child+1, child);
                child++;
            }
            else{
                pickProcessAdd(child,child+1);
            }
        }
        else{
            checkProcessAdd(child,-1);
            pickProcessAdd(child,-1);
        }

        checkProcessAdd(child,parent);

        if(arr[child]>arr[parent]){
            pickProcessAdd(child, parent);
            swapProcessAdd(parent, child);

            //swap
            int temp = arr[child];
            arr[child] = arr[parent];
            arr[parent] = temp;

            max_heap(arr, limit, child);
        }
        else{
            pickProcessAdd(parent,child);
            idleProcessAdd(parent,child);
        }
    }




    /**
     * This function appends an action "check" to processes (the total process made in heap sort)
     * CHECK means the one/two elements are being weighted
     * @param index1 index of the element checked
     * @param index2 index of the element checked
     */

    private void checkProcessAdd(int index1, int index2){
        processes.addProcess(new Operation(OperationType.CHECK, index1, index2 ));
    }


    /**
     * This function appends an action "pick" to processes (the total process made in heap sort)
     * PICK means the element selected after weighing
     * @param picked index of the element picked
     * @param not_picked index of the element not picked
     */

    private void pickProcessAdd(int picked, int not_picked){
        processes.addProcess(new Operation(OperationType.PICK, picked, not_picked ));
    }



    /**
     * This function appends an action "swap" to processes (the total process made in heap sort)
     * SWAP means that two elements have been swapped
     * @param parent index of the (parent) element to be swapped
     * @param child index of the (child) element to be swapped
     */
    private void swapProcessAdd(int parent, int child){
        processes.addProcess(new Operation(OperationType.SWAP, child, parent ));
    }


    /**
     * This function appends an action "idle" to processes (the total process made in heap sort)
     * IDLE means that after being checked and picked no swapping happened
     * @param index1 index of the element in idle state
     * @param index2 index of the element in idle state
     */
    private void idleProcessAdd(int index1, int index2){
        processes.addProcess(new Operation(OperationType.IDLE, index1, index2));
    }

    /**
     * This function appends an action "finished" to processes (the total process made in heap sort)
     * Sorted means that the pass has been finished and that the last element is sorted
     * @param curr_end index of the current-last-element
     * @param root index of the root element
     */

    private void finishProcessAdd(int curr_end, int root){
        processes.addProcess(new Operation(OperationType.SORTED, curr_end, root));
    }


}

