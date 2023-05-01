package priorityqueue;

import java.util.Comparator;

public class Heap<T> implements PriorityQueueADT<T> {

  private int numElements;
  private T[] heap;
  private boolean isMaxHeap;
  private Comparator<T> comparator;
  private final static int INIT_SIZE = 5;

  private int getRightChildOf(int parentIndex){
    return (parentIndex * 2) + 1;
  }

  private int getLeftChildOf(int parentIndex){
    return (parentIndex * 2) + 2;
  }

  private int getParentOf(int childIndex){
    return (childIndex - 1) / 2;
  }
  
  /**
   * Constructor for the heap.
   * @param comparator comparator object to define a sorting order for the heap elements.
   * @param isMaxHeap Flag to set if the heap should be a max heap or a min heap.
   */
  public Heap(Comparator<T> comparator, boolean isMaxHeap) {
      //TODO: Implement this method.

      this.comparator = comparator;
      this.isMaxHeap = isMaxHeap;
      heap = (T[]) new Object[INIT_SIZE];
  }

  /**
   * This results in the entry at the specified index "bubbling up" to a location
   * such that the property of the heap are maintained. This method should run in
   * O(log(size)) time.
   * Note: When enqueue is called, an entry is placed at the next available index in 
   * the array and then this method is called on that index. 
   *
   * @param index the index to bubble up
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleUp(int index) {
      //TODO: Implement this method.

      if (index > numElements || index < 0) throw new IndexOutOfBoundsException();

      if (index == 0) return;
      int parentIndex = getParentOf(index);
      
      if (compareElements(heap[index], heap[parentIndex]) > 0){
        
          swapIndices(index, parentIndex);
          bubbleUp(parentIndex);
      }
  }

  private void swapIndices(int index1, int index2){

      T tempData = heap[index1];
      heap[index1] = heap[index2];
      heap[index2] = tempData;
  }

  /**
   * This method results in the entry at the specified index "bubbling down" to a
   * location such that the property of the heap are maintained. This method
   * should run in O(log(size)) time.
   * Note: When remove is called, if there are elements remaining in this
   *  the bottom most element of the heap is placed at
   * the 0th index and bubbleDown(0) is called.
   * 
   * @param index
   * @throws IndexOutOfBoundsException if invalid index
   */
  public void bubbleDown(int index) {
      //TODO: Implement this method.

      if (index > numElements || index < 0) throw new IndexOutOfBoundsException();
      
      int leftIndex = getLeftChildOf(index);
      int rightIndex = getRightChildOf(index);
      int maxChildIndex = -1;

      if (leftIndex > numElements) return;

      else if (rightIndex >= numElements || compareElements(heap[leftIndex], heap[rightIndex]) > 0){
        maxChildIndex = leftIndex;
      }

      else{
        maxChildIndex = rightIndex;
      }
      
      
      if (compareElements(heap[maxChildIndex], heap[index]) > 0){
        swapIndices(index, maxChildIndex);
        bubbleDown(maxChildIndex);
      }
  }



  /**
   * Test for if the queue is empty.
   * @return true if queue is empty, false otherwise.
   */
  public boolean isEmpty() {
    boolean isEmpty = false;
      //TODO: Implement this method.

      if (numElements == 0) isEmpty = true;
    return isEmpty;
  }

  /**
   * Number of data elements in the queue.
   * @return the size
   */
  public int getSize(){
    int size = -100;
      //TODO: Implement this method.
    return numElements;
  }

  /**
   * Compare method to implement max/min heap behavior. It changes the value of a variable, compareSign, 
   * based on the state of the boolean variable isMaxHeap. It then calls the compare method from the 
   * comparator object and multiplies its output by compareSign.
   * @param element1 first element to be compared
   * @param element2 second element to be compared
   * @return positive int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * negative int otherwise (if isMaxHeap),
   * return negative int if {@code element1 > element2}, 0 if {@code element1 == element2}, 
   * positive int otherwise (if ! isMinHeap).
   */
  public int compareElements(T element1 , T element2) {
    int result = 0;
    int compareSign =  -1;
    if (isMaxHeap) {
      compareSign = 1;
    }
    result = compareSign * comparator.compare(element1, element2);
    return result;
  }

  /**
   * Return the element with highest (or lowest if min heap) priority in the heap 
   * without removing the element.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T peek() throws QueueUnderflowException {
     
      //TODO: Implement this method.

    if (isEmpty()) throw new QueueUnderflowException();
    T data = heap[0];

    return data;
  }  

  /**
   * Removes and returns the element with highest (or lowest if min heap) priority in the heap.
   * @return T, the top element
   * @throws QueueUnderflowException if empty
   */
  public T dequeueElement() throws QueueUnderflowException{
    T data = null;
      //TODO: Implement this method.

      if (isEmpty()) throw new QueueUnderflowException();

      data = heap[0];
      heap[0] = heap[numElements - 1];
      numElements--;

      bubbleDown(0);

    return data;
  }

  /**
   * Enqueue the element.
   * @param the new element
   */
  public void enqueueElement(T newElement) {
      //TODO: Implement this method.

        if (isEmpty()){
          heap[0] = newElement;
          numElements = 1;
          return;
        } 

        if (numElements >= heap.length) {
            expandCapacity();
        }

        heap[numElements] = newElement;
        bubbleUp(numElements);
        numElements++;
  }

  private void expandCapacity(){

    T[] expandedHeap = (T[]) new Object[heap.length*2]; //doubles the current length of the heap

    for (int i = 0; i < numElements; i++){ //copying elements from old heap to new expanded heap

      expandedHeap[i] = heap[i];
    }

    heap = expandedHeap;
  }

}