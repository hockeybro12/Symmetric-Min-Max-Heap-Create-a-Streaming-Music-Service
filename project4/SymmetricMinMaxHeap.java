import java.util.*;

public class SymmetricMinMaxHeap {
    
    Integer[] heap = new Integer[500];
    static int arrayIndex = 0;
    
    public void initialize() {
        Arrays.fill(heap, null);
        heap[arrayIndex] = -1;
        arrayIndex++;
        
    }
    
    
    
    public static void main(String[] args) {
        SymmetricMinMaxHeap symmHeap = new SymmetricMinMaxHeap();
        symmHeap.initialize();
        symmHeap.printHeap();
        
        Scanner sc = new Scanner(System.in);
        while(sc.hasNextInt()) {
            int x = sc.nextInt();
            //symmHeap.add(x);
        }
        
        symmHeap.printHeap();
        
    }
    
    public boolean deleteMin() {
        int currIndex = 1;
        int firstIndex = 1;
        int currIndex1 = currIndex+1;
        boolean stop = false;
        boolean firstTime = false;
        boolean secondTime = false;
        boolean doneASwap = false;
        while (!stop) {
            doneASwap = false;
            //get current song
            //Song s1 = heap[currIndex];
            int pop1 = heap[currIndex];
            
            Song s2;
            int pop2;
            
            boolean switched = false;
            
            if (heap[currIndex + 1] != null) {
                //check to see if the current index has a right
                //s2 = heap[currIndex + 1];
                pop2 = heap[currIndex + 1];
            } else {
                pop2 = -1;
            }
            
            //if next one after current node is not null AND is greater 
            if ((pop2 != -1) && (pop1 > pop2)) {
                swap(currIndex, (currIndex+1));
                currIndex = (currIndex+1);
                doneASwap = true;
                switched = true;
                //if we have switched then we don't care about it's next node's left index
            }
            int leftIndex = leftChild(currIndex);
            
            Song leftSong;
            int popLeft = 0;
            //make sure left is not null
            if (heap[leftIndex] != null) {
                //leftSong = heap[leftIndex];
                popLeft = heap[leftIndex];
            } else {
                popLeft = -1;
            }
            //make sure right is not null
            //actually the left child of the node + 1 index
            int indexToCheck = currIndex + 1;
            int rightIndex = rightChild(indexToCheck);
            Song rightSong;
            int popRight;
            if (heap[rightIndex] != null) {
                //rightSong = heap[rightIndex];
                popRight = heap[rightIndex];
            } else {
                popRight = -1;
            }
            
            if (switched == false) {
                if ((popLeft != -1) && (popRight != -1) && (popLeft < popRight) && (popLeft < pop1)) {
                    //the first left is smaller so switch there
                    swap(leftIndex, currIndex);
                    doneASwap = true;
                    currIndex = leftIndex;
                } else if ((popLeft != -1) && (popRight != -1) && (popRight < popLeft) && (popRight < pop1)) {
                    //the second left is smaller so switch there
                    swap(rightIndex, currIndex);
                    doneASwap = true;
                    currIndex = rightIndex;
                    
                } else if ((popLeft != -1) && (popLeft < pop1)) {
                    swap(leftIndex, currIndex);
                    doneASwap = true;
                    currIndex = leftIndex;
                }
            } else {
                if ((popLeft != -1) && (popLeft < pop1)) {
                    swap(leftIndex, currIndex);
                    doneASwap = true;
                    currIndex = leftIndex;
                } 
            }
            
            
            int rightIndexReal = rightChild(currIndex);
            pop1 = heap[currIndex];
            
            Song rightSongReal;
            int popRightReal = 0;
            //make sure right child of current is not null
            if (heap[rightIndexReal] != null) {
                //leftSong = heap[leftIndex];
                popRightReal = heap[rightIndexReal];
            } else {
                popRightReal = -1;
            }
            //make sure right child of current + 1is not null
            indexToCheck = currIndex + 1;
            int rightIndexReal2 = rightChild(indexToCheck);
            Song rightSongReal2;
            int popRightReal2;
            if (heap[rightIndexReal2] != null) {
                //rightSong = heap[rightIndex];
                popRightReal2 = heap[rightIndexReal2];
            } else {
                popRightReal2 = -1;
            }
            
            if (switched == false) {
                if ((popRightReal != -1) && (popRightReal2 != -1) && (popRightReal > popRightReal2) && (popRightReal > pop1)) {
                    //the first left is smaller so switch there
                    swap(rightIndexReal, currIndex);
                    doneASwap = true;
                    currIndex = rightIndexReal;
                } else if ((popRightReal != -1) && (popRightReal2 != -1) && (popRightReal2 > popRightReal) && (popRightReal2 > pop1)) {
                    //the second left is smaller so switch there
                    swap(rightIndexReal2, currIndex);
                    doneASwap = true;
                    currIndex = rightIndexReal2;
                } else if ((popRightReal != -1) && (popRightReal < pop1)) {
                    swap(rightIndexReal, currIndex);
                    doneASwap = true;
                    currIndex = rightIndexReal;
                }
            } else {
                if ((popRightReal != -1) && (popRightReal < pop1)) {
                    swap(popRightReal, currIndex);
                    doneASwap = true;
                    currIndex = rightIndexReal;
                } 
            }
            
            
            if (doneASwap == false) {
                stop = true;
            }
        }
        
        return false;
    }
    
    public boolean delete() {
        arrayIndex--;
        int temp = heap[arrayIndex];
        System.out.println("Temp is " + temp);
        heap[arrayIndex] = heap[1];
        heap[1] = temp;
        heap[arrayIndex] = null;
        
        deleteMin();
        
        return true;
    }
    
    /*
    public boolean add(int s) {
        int leftNode = modifiedIndex % 2;
        if (leftNode == 1) {
            //left node, no need to check if less than sibling
        } else {
            //we are right node
            Song s2 = heap[modifiedIndex - 1];
            int pop2 = s2.popularity;
            int pop1 = s.popularity;
            
            //if right node's popularity less than sibling's
            if (pop1 < pop2) {
                //System.out.println("Swap 1");
                //swap
                swap (modifiedIndex, modifiedIndex-1);
            }
        }
        //get current index cos we need to keep bubbling up
        int currIndex = modifiedIndex;
       // System.out.println("Current is " + currIndex);
        boolean stop = false;
        while (!stop) {
            if(!hasParent(currIndex)) {
            //    arrayIndex++;
                return true;
            }
            //get parent index of current
            int parent = parentIndex(currIndex);
           // System.out.println("Parent is " + parent);
            //do a mod 2
            int lrNodeIndex = parent % 2;
            //System.out.printf("parent = %d\n", parent);
            Song s2 = heap[parent];
            int pop2 = s2.popularity;
            int pop1 = s.popularity;
            if (lrNodeIndex == 1) {
                //parent is a lnode
                if (pop1 < pop2) {
                   // System.out.println("Swap 2");
                    swap(modifiedIndex, parent);
                    currIndex = parent;
                } else {
                    if (parent != 0) {
                        Song s3 = heap[parent + 1];
                        int pop3 = s3.popularity;
                        //pop3 is now RNode popularity
                        if (pop1 > pop3) {
                          //  System.out.println("Swap 4");
                            swap(modifiedIndex, parent+1);
                            currIndex = parent+1;
                        } else {
                            stop = true;
                        } 
                    } else {
                        
                    }
                } 
            } else {
                //parent is a rnode
                if (pop1 > pop2) {
                  //  System.out.println("Swap 6");
                    Song s6 = heap[modifiedIndex];
                  //  System.out.println("index 1 is + " + modifiedIndex);
                  //  System.out.println(s6.name);
                    swap(modifiedIndex, parent);
                    currIndex = parent;
                } else {
                    if (parent != 0) {
                        Song s3 = heap[parent - 1];
                        int pop3 = s3.popularity;
                        //pop3 is now LNode popularity
                        if (pop1 < pop3) {
                    //        System.out.println("Swap 5");
                            swap(modifiedIndex, parent - 1);
                            currIndex = parent - 1;
                        } else {
                            stop = true;
                        }
                    } else {
                        
                    }
                }
            }
            //currIndex = parent;
        }  
        return true;  
    }
    
    */
    public void printHeap() {
        for (int i = 1; i < arrayIndex; i++) {
            int s = heap[i];
            System.out.println(s);
        }   
    }
    
    static int leftChild(int nodeIndex) {
        return (2 * nodeIndex + 1);
    }
    
    static int rightChild(int nodeIndex) {
        return (2 * nodeIndex + 2);
    }
    
    public void swap (int index1, int index2) {
        System.out.println("Swapping");
        int temp = heap[index1];
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    
    boolean hasParent(int nodeIndex) {
        return nodeIndex > 2;
    }
    
    static int parentIndex(int nodeIndex) {
        if (nodeIndex % 2 == 1) {
            return ((nodeIndex - 1) / 2);
        } 
        return ((nodeIndex - 2) / 2);
    }

    static int leftDaughterIndex(int nodeIndex) {
        return 2 * nodeIndex;
    }

    static int rightDaughterIndex(int nodeIndex) {
        return 2 * nodeIndex + 1;
    }
    
    
    
}