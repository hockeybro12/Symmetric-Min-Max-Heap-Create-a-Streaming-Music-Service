import java.util.List;
import java.util.*;

public class SongCollection {
    
    /*
     * int date;
     * int listenedToNTimes;
     * int likedLTimes;
    */
    int transactionCount = 0;
    boolean stillGoing = false;
    ArrayList<Song> list = new ArrayList<Song>();
    
    Song[] heap = new Song[500];
    int arrayIndex = 0;
    
    HashMap<String, Integer> hashMap= new HashMap<String, Integer>();
        
    
    public void initialize() {
        //fill everything with null and store a new song called -1 which represents the first object in the SMMH
        Arrays.fill(heap, null);
        Song s = new Song(-1, -1, -1, -1, "Starting");
        list.add(s);
        heap[arrayIndex] = s;
        hashMap.put(s.name, arrayIndex);
        arrayIndex++;
    }
    
    
    public boolean add(Song s, int modifiedIndex) {
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
                //swap
                swap (modifiedIndex, modifiedIndex-1);
            }
        }
        //get current index cos we need to keep bubbling up
        int currIndex = modifiedIndex;

        boolean stop = false;
        while (!stop) {
            if(!hasParent(currIndex)) {
                return true;
            }
            //get parent index of current
            int parent = parentIndex(currIndex);
            //do a mod 2 to see if the parent is a lnode or a rnode
            int lrNodeIndex = parent % 2;
            Song s2 = heap[parent];
            int pop2 = s2.popularity;
            int pop1 = s.popularity;
            if (lrNodeIndex == 1) {
                //parent is a lnode
                if (pop1 < pop2) {
                    swap(modifiedIndex, parent);
                    currIndex = parent;
                } else {
                    if (parent != 0) {
                        Song s3 = heap[parent + 1];
                        int pop3 = s3.popularity;
                        //pop3 is now next node popularity
                        if (pop1 > pop3) {
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
                //do the same process as above except other side
                if (pop1 > pop2) {
                    Song s6 = heap[modifiedIndex];
                    swap(modifiedIndex, parent);
                    currIndex = parent;
                } else {
                    if (parent != 0) {
                        Song s3 = heap[parent - 1];
                        int pop3 = s3.popularity;
                        //pop3 is now LNode popularity
                        if (pop1 < pop3) {
                            swap(modifiedIndex, parent - 1);
                            currIndex = parent - 1;
                        } else {
                            stop = true;
                        }
                    } else {
                        
                    }
                }
            }
            
        }  
        return true;  
    }
    
    public boolean deleteMin(int currIndex) {
        
        //int currIndex = 1;
        boolean stop = false;
        boolean firstTime = false;
        boolean secondTime = false;
        boolean doneASwap = false;
        while (!stop) {
            doneASwap = false;
            //get current song
            Song s1 = heap[currIndex];
            int pop1 = s1.popularity;
            
            Song s2;
            int pop2;
            
            boolean switched = false;
            
            boolean left = true;
            //check to see if we are at a left node or a right node
            int check = currIndex % 2;
            if (check == 1) {
                left = true;
            } else {
                left = false;
            }
            
            Song s3;
            int pop3;
            
            if (((currIndex + 1) <= 500) && heap[currIndex + 1] != null && (left == true)) {
                //current is a left so check to see if has a right
                //check to see if the current index has a right
                s2 = heap[currIndex + 1];
                pop2 = s2.popularity;
            } else {
                pop2 = -1;
            }
            
            if (((currIndex - 1) > 0) && heap[currIndex - 1] != null && (left == false)) {
                //current is a left so check to see if has a right
                //check to see if the current index has a right
                s3 = heap[currIndex - 1];
                pop3 = s3.popularity;
            } else {
                pop3 = -1;
            }
            
            //if next one after current node is not null AND is greater 
            if ((pop2 != -1) && (pop1 > pop2) && (left == true)) {
                //printHeap();
               // System.out.println("Swap 1");
                swap(currIndex, (currIndex+1));
                //currIndex = (currIndex+1);
                //doneASwap = true means that we swapped something so we need to run the while loop again
                doneASwap = true;
                //switched is useless statement
                switched = true;
                //if we have switched then we don't care about it's next node's left index
            }
            
            if ((pop3 != -1) && (pop1 < pop3)) {
               // System.out.println("Swap 2");
                swap(currIndex, (currIndex-1));
                //currIndex = (currIndex-2);
                doneASwap = true;
                //switched = true;
                //if we have switched then we don't care about it's next node's left index
            }
            
            int leftIndex = leftChild(currIndex);
            
            
            Song leftSong;
            int popLeft = 0;
            //make sure left is not null
            if ((leftIndex < 500) && heap[leftIndex] != null) {
                leftSong = heap[leftIndex];
                popLeft = leftSong.popularity;
            } else {
                popLeft = -1;
            }
            //make sure right is not null
            int indexToCheck = currIndex + 1;
            int rightIndex = leftChild(indexToCheck);
            Song rightSong;
            int popRight;
            if ((rightIndex < 500) && heap[rightIndex] != null) {
                rightSong = heap[rightIndex];
                popRight = rightSong.popularity;
            } else {
                popRight = -1;
            }
            
            
            
           // if (switched == false) {
                if ((popLeft != -1) && (popRight != -1) && (popLeft < popRight)) {
                    //the first left is smaller so switch there
                    swap(leftIndex, currIndex);
                    doneASwap = true;
                    currIndex = leftIndex;
                } else if ((popLeft != -1) && (popRight != -1) && (popRight < popLeft)) {
                    //the second left is smaller so switch there
                    swap(rightIndex, currIndex);
                    doneASwap = true;
                    currIndex = rightIndex;
                    
                } else if ((popLeft != -1) && (popLeft < pop1)) {
                    swap(leftIndex, currIndex);
                    doneASwap = true;
                    currIndex = leftIndex;
                }
          /*  } else {
                if ((popLeft != -1) && (popLeft < pop1)) {
                  //  System.out.println("Swap 6");
                    swap(leftIndex, currIndex);
                    doneASwap = true;
                    currIndex = leftIndex;
                } 
                */
            //}
            
            
            int rightIndexReal = rightChild(currIndex);
            Song s8 = heap[currIndex];
            pop1 = s8.popularity;
            
            Song rightSongReal;
            int popRightReal = 0;
            //make sure right child of current is not null
            if ((rightIndexReal < 500) && (heap[rightIndexReal] != null)) {
                rightSongReal = heap[rightIndexReal];
                popRightReal = rightSongReal.popularity;
            } else {
                popRightReal = -1;
            }
            //make sure right child of current + 1is not null
            indexToCheck = currIndex + 1;
            int rightIndexReal2 = rightChild(indexToCheck);
            Song rightSongReal2;
            int popRightReal2;
            if ((rightIndexReal2 < 500) && (heap[rightIndexReal2] != null)) {
                rightSongReal2 = heap[rightIndexReal2];
                popRightReal2 = rightSongReal2.popularity;
            } else {
                popRightReal2 = -1;
            }
            
         //   if (switched == false) {
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
           /* } else {
                if ((popRightReal != -1) && (popRightReal < pop1)) {
                  //  System.out.println("Swap 10");
                    swap(rightIndexReal, currIndex);
                    doneASwap = true;
                    currIndex = rightIndexReal;
                } 
            }
            */
            if (doneASwap == false) {
                stop = true;
            }
        }
        
        return false;
    }
    
    
    static int leftChild(int nodeIndex) {
        //calculate left child
        return (2 * nodeIndex + 1);
    }
    
    static int rightChild(int nodeIndex) {
        //calculate right child
        return (2 * nodeIndex + 2);
    }
    
    public void swap (int index1, int index2) {
        //swap using temp and basic
        Song temp = heap[index1];
        //make sure we update the hashmap
        hashMap.put(temp.name, index2);
        hashMap.put(heap[index2].name, index1);
        
        heap[index1] = heap[index2];
        heap[index2] = temp;
    }
    
    boolean hasParent(int nodeIndex) {
        //see if the node has a parent
        return nodeIndex > 2;
    }
    
    static int parentIndex(int nodeIndex) {
        //figure out the parent
        if (nodeIndex % 2 == 1) {
            return ((nodeIndex - 1) / 2);
        } 
        return ((nodeIndex - 2) / 2);
    }

    static int leftDaughterIndex(int nodeIndex) {
        //find the left daughter
        return 2 * nodeIndex;
    }

    static int rightDaughterIndex(int nodeIndex) {
        //find the right daughter
        return 2 * nodeIndex + 1;
    }
    // TODO Task 2
    // Organize all the songs here
    
    // add a song at date, with the name S{date}
    // and default L and N
    public boolean addSong(int date, int lineCount){
        // TODO Task 3
        String dateString = Integer.toString(date);
        String name = "S" + dateString;
        Song s = new Song(date, 20, 20, 60, name);
        //add(s);
        //used to keep track of semantics
        transactionCount++;
        
        if (transactionCount < 5) {
            stillGoing = false;
        } else {
            stillGoing = true;
        }
        arrayIndex--;
         if (arrayIndex < 500) {
             arrayIndex++;
             heap[arrayIndex] = s;
             System.out.println(s.name + ": N=20, L=20, pop=60"); 
             hashMap.put(s.name, arrayIndex);
             
             if (!hasParent(arrayIndex)) {
                 int leftNode = arrayIndex % 2;
                 if (leftNode == 1) {
                     //left node, no need to check if less than sibling
                 } else {
                     //we are right node
                     Song s2 = heap[arrayIndex - 1];
                     int pop2 = s2.popularity;
                     int pop1 = s.popularity;
                     
                     //if right node's popularity less than sibling's
                     if (pop1 < pop2) {
                         //swap
                         swap (arrayIndex, arrayIndex-1);
                     }
                 }
                 
                 arrayIndex++;
                 //printHeap();
                 return true;
            }
            
            add(s, arrayIndex);
            //arrayIndex increments so next time we can just add without incrementing
            arrayIndex++;
           // printHeap();
            return true;
         } else {
             arrayIndex++;
             System.out.println("Error max size exceeded date " + lineCount);
         }
         return false;  
    }
    
    // delete the n songs of lowest priorities
    public void deleteSong(int n) {
        // TODO Task 3
        //subtract one from array index since we are at +1 right now
        
        transactionCount--;
        if (transactionCount < 3 && (stillGoing == true)) {
            System.out.println("Error min size violated");
            System.exit(0);
        }
        arrayIndex--;
        
        arrayIndex++;
        
        for (int i = 0; i < n; i++) {
            arrayIndex--;
            Song temp = heap[arrayIndex];
            Song temp2 = heap[1];
            System.out.println(temp2.name + " deleted, pop=" + temp2.popularity);
            heap[arrayIndex] = heap[1];
            heap[1] = temp;
            //remove min song from hashMap
            hashMap.remove(temp2.name);
            //set the index of temp to 1
            hashMap.put(temp.name, 1);
            //hashMap.remove(arrayIndex);
            heap[arrayIndex] = null;
            
            deleteMin(1);
        }
        
        
       // printHeap();
    }
    
    // update song named by songName
    public void updateSong(String songName, int deltaN, int deltaL, int lineNumber) {
        // TODO Task 3
        arrayIndex--;
        int index = 0;
        if (hashMap.containsKey(songName)) {
            index = hashMap.get(songName);
        } else {
            System.out.println("Error semantics date " + lineNumber);
            System.exit(0);
        }
        
        Song s = heap[index];
        s.listenedToNTimes += deltaN;
        s.likedLTimes += deltaL;
        
        int N = s.listenedToNTimes;
        int L = s.likedLTimes;
        s.popularity = (s.listenedToNTimes + (2 * s.likedLTimes));
        
        System.out.println(s.name + ": N=" + s.listenedToNTimes + ", L=" + s.likedLTimes + ", pop=" + s.popularity);
        //if song has a parent, need to check above
        add(s, index);
        arrayIndex++;
        deleteMin(index);
        
        
    }
    
    // return the 3 most popular songs in the return value
    // with the most popular at index 0
    //      the second popular at index 1
    //      the third popular at index 2
    public void popular() {
        
        // TODO Task 3
        int length = arrayIndex - 1;
        //System.out.println("Length is " + length);
        int indexToSend1, indexToSend2, indexToSend3;
        //for various lengths, perform different comparisons to figure out where exactly the max node is at 
        if (length == 3) {
            printPopular(1,2,3,1);
        } else if (length == 4) {
            printPopular(2,4,3,1);
        } else if (length == 5) {
            int x = greaterThan(3,5);
            //if x happens to be 5 then we must compare with 4
            int y = greaterThan(x, 4);
            if (y == 4) {
                //if 4 happened to be bigger, then print it before x
                printPopular(2,4,x,1);
            } else {
                printPopular(2,x,4,1);
            }
        } else if (length == 6) {
            int x = greaterThan(3,5);
            int y = greaterThan(4,6);
            if (y == 4) {
                int l = greaterThan(6,x);
                printPopular(2,y,l,1);
            } else if (y == 6) {
                int l = greaterThan(4,x);
                printPopular(2,y,l,1);
            }
        } else if (length == 7) {
            int y = greaterThan(4,6);
            if (y == 4) {
                int x = greaterThan(7, 4);
                if (x == 4) {
                    int z = greaterThan(6,7);
                    printPopular(2,x,z,1); 
                } else if (x == 7) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            } else if (y == 6) {
                int x = greaterThan(7,6);
                if (x == 6) {
                    int z = greaterThan(4,7);
                    printPopular(2,x,z,1);
                } else if (x == 7) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            }
        } else if (length == 8) {
            int y = greaterThan(4,6);
            if (y == 4) {
                int x = greaterThan(8, 4);
                if (x == 4) {
                    int z = greaterThan(6,8);
                    printPopular(2,x,z,1); 
                } else if (x == 8) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            } else if (y == 6) {
                int x = greaterThan(8,6);
                if (x == 6) {
                    int z = greaterThan(4,8);
                    printPopular(2,x,z,1);
                } else if (x == 8) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            }
        } else if (length == 9) {
            int y = greaterThan(4,6);
            int m = greaterThan(8,9);
            if (y == 4) {
                int x = greaterThan(m, 4);
                if (x == 4) {
                    int z = greaterThan(6,m);
                    printPopular(2,x,z,1); 
                } else if (x == m) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            } else if (y == 6) {
                int x = greaterThan(m,6);
                if (x == 6) {
                    int z = greaterThan(4,m);
                    printPopular(2,x,z,1);
                } else if (x == m) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            }
        } else if (length == 10) {
            int y = greaterThan(4,6);
            int m = greaterThan(8,10);
            if (y == 4) {
                int x = greaterThan(m, 4);
                if (x == 4) {
                    int z = greaterThan(6,m);
                    printPopular(2,x,z,1); 
                } else if (x == m) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            } else if (y == 6) {
                int x = greaterThan(m,6);
                if (x == 6) {
                    int z = greaterThan(4,m);
                    printPopular(2,x,z,1);
                } else if (x == m) {
                    int z = greaterThan(4,6);
                    printPopular(2,x,z,1);
                }
            }
        } else if (length == 11) {
            int m = greaterThan(8,10);
            int y = greaterThan(4,6);
            int l = greaterThan(m, 11);
            if (y == 4) {
                int q = greaterThan(4, l);
                int z = greaterThan(l, 6);
                if (q == 4) {
                    printPopular(2,4,z,1);
                } else if (q == l) {
                    if (l == 8) {
                        int t = greaterThan(10, 11);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 10) {
                        int t = greaterThan(8, 11);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 11) {
                        int t = greaterThan(8, 10);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    }
                }
            } else if (y == 6) {
                int q = greaterThan(6, l);
                if (q == 6) {
                    int z = greaterThan(l, 4);
                    printPopular(2,6,z,1);
                } else if (q == l) {
                    if (l == 8) {
                        int t = greaterThan(10, 11);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 10) {
                        int t = greaterThan(8, 11);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 11) {
                        int t = greaterThan(8, 10);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    }
                }
            }
        } else if (length == 12) {
            int m = greaterThan(8,10);
            int y = greaterThan(4,6);
            int l = greaterThan(m, 12);
            if (y == 4) {
                int q = greaterThan(4, l);
                int z = greaterThan(l, 6);
                if (q == 4) {
                    printPopular(2,4,z,1);
                } else if (q == l) {
                    if (l == 8) {
                        int t = greaterThan(10, 12);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 10) {
                        int t = greaterThan(8, 12);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 12) {
                        int t = greaterThan(8, 10);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    }
                }
            } else if (y == 6) {
                int q = greaterThan(6, l);
                if (q == 6) {
                    int z = greaterThan(l, 4);
                    printPopular(2,6,z,1);
                } else if (q == l) {
                    if (l == 8) {
                        int t = greaterThan(10, 12);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 10) {
                        int t = greaterThan(8, 12);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    } else if (l == 12) {
                        int t = greaterThan(8, 10);
                        int u = greaterThan(t, y);
                        printPopular(2, l, u, 1);
                    }
                }
            }
        } else if (length == 13) {
            int y = greaterThan(4,6);
            int l = greaterThan(8,10);
            int r = greaterThan(12,13);
            int w = greaterThan(l,r);
            
            if (y == 4) {
                int q = greaterThan(4, w);
                if (q == 4) {
                    int z = greaterThan(l, 6);
                    printPopular(2,q,z,1);
                } else if (q == w) {
                    if (w == 8) {
                        int u = greaterThan(10,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 10) {
                        int u = greaterThan(8,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 12) {
                        int u = greaterThan(l,13);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 13) {
                        int u = greaterThan(l,12);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    }
                }
            } else if (y == 6) {
                int q = greaterThan(6, w);
                if (q == 6) {
                    int z = greaterThan(l, 4);
                    printPopular(2,q,z,1);
                } else if (q == w) {
                    if (w == 8) {
                        int u = greaterThan(10,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 10) {
                        int u = greaterThan(8,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 12) {
                        int u = greaterThan(l,13);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 13) {
                        int u = greaterThan(l,12);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    }
                }
            }
        } else {
            System.out.println("Printing FUCKING NOW");
            printHeap();
            int y = greaterThan(4,6);
            int l = greaterThan(8,10);
            int r = greaterThan(12,14);
            int w = greaterThan(l,r);
            
            if (y == 4) {
                int q = greaterThan(4, w);
                if (q == 4) {
                    int z = greaterThan(l, 6);
                    printPopular(2,q,z,1);
                } else if (q == w) {
                    if (w == 8) {
                        int u = greaterThan(10,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 10) {
                        int u = greaterThan(8,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 12) {
                        int u = greaterThan(l,14);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 14) {
                        int u = greaterThan(l,12);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    }
                }
            } else if (y == 6) {
                int q = greaterThan(6, w);
                if (q == 6) {
                    int z = greaterThan(l, 4);
                    printPopular(2,q,z,1);
                } else if (q == w) {
                    if (w == 8) {
                        int u = greaterThan(10,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 10) {
                        int u = greaterThan(8,r);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 12) {
                        int u = greaterThan(l,14);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    } else if (w == 14) {
                        int u = greaterThan(l,12);
                        int v = greaterThan(u, y);
                        printPopular(2,w,v,1);
                    }
                }
            }
        }
        
        
        
        return;
    }
    
    public void printHeap() {
        for (int i = 0; i < arrayIndex-1; i++) {
            Song s1 = heap[i];
            System.out.println(s1.name);
        }
    }
    
    public void printPopular(int index1, int index2, int index3, int counter) {
        Song s1 = heap[index1];
        Song s2 = heap[index2];
        Song s3 = heap[index3];
        
        System.out.println(counter + ": " + s1.name + ", pop=" + s1.popularity);
        counter++;
        System.out.println(counter + ": " + s2.name + ", pop=" + s2.popularity);
        counter++;
        System.out.println(counter + ": " + s3.name + ", pop=" + s3.popularity);
    }
    
    public int greaterThan(int index1, int index2) {
        Song s1;
        Song s2;
        int pop1;
        int pop2;
        if (heap[index1] != null) {
            s1 = heap[index1];
            pop1 = s1.popularity;
        } else {
            pop1 = -1;
        }
        if (heap[index2] != null) {
            s2 = heap[index2];
            pop2 = s2.popularity;
        } else {
            pop2 = -1;
        }
        
        if (pop1 > pop2) {
            return index1;
        } else if (pop2 > pop1) {
            return index2;
        } else if (pop2 == pop1) {
            return index1;
        }
        System.out.println("Returning 0");
        return 0;
    }
    
    public boolean lessThan(int index1, int index2) {
        Song s1;
        Song s2;
        int pop1;
        int pop2;
        if (heap[index1] != null) {
            s1 = heap[index1];
            pop1 = s1.popularity;
        } else {
            pop1 = -1;
        }
        if (heap[index2] != null) {
            s2 = heap[index2];
            pop2 = s2.popularity;
        } else {
            pop2 = -1;
        }
        
        if (pop1 < pop2) {
            return true;
        }
        return false;
    }
    
    // return the popularity
    // of the most and least popular songs
    // the 0th integer in the returned object
    // should be the popularity of the most popular song
    // and the 1st integer should be the popularity of
    // the least popular song 
    public void minMax() {
        // TODO Task 3
        
        arrayIndex--;
        if (arrayIndex > 1 && arrayIndex <=2) {
            //left is valid
            Song s1 = heap[1];
            System.out.println("min " + s1.popularity);
        } else if (arrayIndex > 2) {
            //left and right is valid
            Song s1 = heap[1];
            Song s2 = heap[2];
            System.out.println("min " + s1.popularity + ", max " + s2.popularity);
        }
        
        return;
    }
}