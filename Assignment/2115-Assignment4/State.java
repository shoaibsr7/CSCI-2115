import java.util.ArrayList;

public class State {
    //List of transitions corresponding to characters at the same index as the alphabets.
    private ArrayList<Integer> transitions = new ArrayList<>();
    private boolean accept;

    public State(){
        accept = false;
    }

    /**
     * @param transitions sets transition depending on the input read
     */
    public void setTransition(ArrayList<Integer> transitions) {
        this.transitions = transitions;
    }

    /**
     * @param accept sets accepting state depending on the input read
     */
    public void setAcceptingState(boolean accept) {
        this.accept = accept;
    }

    /**
     * Returns the list of transitions
     * @return transitions
     */
    public ArrayList<Integer> getTransitions() {
        return transitions;
    }

    /**
     * Returns true if the current state is an accepting state; false otherwise
     * @return accept
     */
    public boolean isAccept() {
        return accept;
    }

    @Override
    public String toString() {
        return "State{" +
                "transitions=" + transitions +
                ", accept=" + accept +
                '}';
    }
}
