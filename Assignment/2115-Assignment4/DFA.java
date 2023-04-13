import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.FileWriter;

public class DFA {
    public static void main(String[] args) throws IOException {
        Scanner kb = new Scanner(System.in);

//        creating an ArrayList of type Characters to store the language for the DFA (ab,01,0123456789,etc) as char
        ArrayList<Character> language = new ArrayList<>();

//        creating an ArrayList of type State from the State.java class to store the states for the DFA
        ArrayList<State> states = new ArrayList<>();

//        initializing start state of the DFA
        int start = 0;

//        creating an ArrayList of type State from the State.java class to store the transitions for the DFA
        ArrayList<Integer> transitions = new ArrayList<>();

        /**
         * DESCRIPTION FILE
         */
        System.out.print("Enter name of the file to read the Description of DFA from:\t");

//        reading from the file

//        String fileName = "a4_test_inputs/dfa_odd_bs_description.txt";
//        String fileName = "a4_test_inputs/dfa_zero_one_description.txt";
//        String fileName = "a4_test_inputs/dfa_mul_3_or_5_description.txt";
        String fileName = kb.nextLine();
        System.out.println();
        File file = new File(fileName);

        Scanner descriptionFile = new Scanner(file);

        if (descriptionFile.hasNextLine()) {
//            reading first line of description file, that is language of the DFA
            String line = descriptionFile.nextLine();
//            System.out.println("Language: " + line);

            for (int i = 0; i < line.length(); i++){
                language.add(line.charAt(i));
            }

//            reading second line of description file, that is states of the DFA
            line = descriptionFile.nextLine();
            int noOfStates = Integer.parseInt(line.trim());

//            System.out.println("Num of states: " + noOfStates);

            states = new ArrayList<>();

            for(int i = 0; i < noOfStates; i++){
                states.add(new State());
            }

//            reading third line of description file, that is start state of the DFA; contains only a single digit
            line = descriptionFile.nextLine();

            start = Integer.parseInt(line.trim());
//            System.out.println("Start state: " + start);

//            reading fourth line of description file, that is the accepting states (Final states) of the DFA
            String acceptingStates = descriptionFile.nextLine();

//            System.out.println("Accepting states: ");
            for(int i = 0; i < acceptingStates.length(); i += 2){
//                i += 2 since accepting_states = "0 1" or "1 2 3 4" so 1 is at index 0; 2 is at index 2;
//                3 is at index 4; 4 is at index 6; etc.

                int x = Integer.parseInt(Character.toString(acceptingStates.charAt(i)));

//                get the state for the accepting state and set it as accepting state
                states.get(x).setAcceptingState(true);
//                System.out.println(x);
            }

//            System.out.println("***********");

//            reading fifth line of description file, that is the transition states of the DFA
            int k = 0;
//            using a while loop since there are multiple transition states
            while(descriptionFile.hasNextLine()){
                String transitionString = descriptionFile.nextLine();
                transitions.clear();
                for (int i = 0; i < transitionString.length(); i += 2){
                    int y = Integer.parseInt(Character.toString(transitionString.charAt(i)));
                    transitions.add(y);
                }

//                System.out.println(transitions);
                states.get(k).setTransition(new ArrayList<>(transitions));
                k++;
            }
//            System.out.println("States: " + states);
        }

        /**
         * INPUTS FILE
         */
        Scanner in = new Scanner(System.in);

        System.out.print("Enter name of the file to read the Inputs for the DFA from:\t");
        String inputsFile = in.nextLine();
        System.out.println();

//        starting state of the DFA
        int s = start;

//      Read all lines from a file. This method ensures that the file is closed when all bytes have been read
//      or an I/O error, or other runtime exception, is thrown.
        List<String> lines = Files.readAllLines(Path.of(inputsFile));

        boolean check = true;

        FileWriter myWriter = new FileWriter("output.txt");

        for(String eachLine : lines) {
            check = true;
            s = start;
            for(int i = 0; i < eachLine.length(); i++){
                char c = eachLine.charAt(i);

                if(!language.contains(c)) {
                    try {
                        myWriter.append("INVALID\n");
                    }
                    catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
//                    System.out.println("INVALID");
                    check = false;
                    break;
                }
                int m = language.indexOf(c);

                s = states.get(s).getTransitions().get(m);
            } //for loop

            if (states.get(s).isAccept() && check){
                try {
                    myWriter.append("ACCEPT\n");
                }
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
//                System.out.println("ACCEPT");
            }

            else if (!states.get(s).isAccept() && check){
                try {
                    myWriter.append("REJECT\n");
                }
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
//                System.out.println("REJECT");
            }
        } //for each loop
        myWriter.close();
    } //main
} //public class
