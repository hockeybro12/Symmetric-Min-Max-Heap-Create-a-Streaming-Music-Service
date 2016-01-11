import java.util.*;

/*
class Command {
    // TODO Task 1
    // define a class to represent all the
    // commands from the user input
    public int getCommandType() {
        return 0;
    }
}
*/

public class Project4 {
    
    // get the input from the user
    // parse the input
    // construct a Command object
    // and return it
    
    public static void main(String[] args) {
        SongCollection sc = new SongCollection();
        Scanner in = new Scanner(System.in);
        sc.initialize();
        int lineCounter = 0;
        ArrayList<Integer> dateList = new ArrayList<Integer>();
        
        HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
        while (in.hasNextLine()) {
            lineCounter++;
            int counter = 0;
            String input = in.nextLine();
            
            //split by spaces
            input = input.trim();
            String[] x = input.split(" ");
            boolean dateExists = false;
            int date = Integer.parseInt(x[counter]);
            if (hashMap.containsKey(date)) {
                dateExists = true;
            }
            hashMap.put(date, "Hi");
            int duplicateDate = 0;
            int length = dateList.size();
            /*for (int i = 0; i < length; i++) {
                int z = dateList.get(i);
                if (z == date) {
                    dateExists = true;
                    duplicateDate = z;
                }
            }
            */
            
            if (length >= 1) {
                int l = dateList.get(length-1);
                if (date > l) {
                    
                } else {
                    dateExists = true;
                }
            }
            if (dateExists == true) {
                System.out.println("Error semantics date " + lineCounter);
                System.exit(0);
            }
            
            dateList.add(date);
            counter++;
            String second = x[counter];
            counter++;
            if (second.equals("end")) {
                //end
                sc.minMax();
                break;
            } else if (second.equals("T3")) {
                //report top 3
                sc.popular();
            } else if (second.equals("B")) {
                //buy a new song with initial N = 20, L= 20 with title Sd
                sc.addSong(date, lineCounter);
            } else if (second.equals("X")) {
                //delete n songs with lowest priority
                int numberToDelete = Integer.parseInt(x[counter]);
                sc.deleteSong(numberToDelete);
            } else {
                try {
                    //update song with delta N and delta L
                    int deltaN = Integer.parseInt(x[counter]);
                    counter++;
                    int deltaL = Integer.parseInt(x[counter]);
                    sc.updateSong(second, deltaN, deltaL, lineCounter);
                } catch( Exception e) {
                   // System.out.println(input);
                    System.out.println("Error input syntax line " + lineCounter);
                    //System.out.println(e);
                    System.exit(0);
                }
                
            }
                
            
        }
       
    }
    
    /*
    public static void main(String[] args) {
        SongCollection songCollection = new SongCollection();
        
        Command command = getNextCommand();
        while (null != command) {
            switch (command.getCommandType()) {
                // TODO Task1
                
                // case 1:
                // ......
                // break;
                
                // case 2:
                // ......
                // break;
                
                default:
                    break;
            }
            command = getNextCommand();
            // if command.getCommandType = Command.END
            // System.exit(0);
        }
        
    }
    */
}
