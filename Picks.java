import java.util.Arrays;
import java.util.LinkedList;

public class Picks {
    private Match[] pairs;
    private Match[] oldPairs;
    private int numMatch =0;
    private int numNonMatch =0;
    private int numUnkown;

    public int size() {
        return pairs.length;
    }

    public Picks(Match[] pairs) {
        this.pairs = pairs;
        this.oldPairs = pairs.clone();
        numUnkown= pairs.length -numMatch - numNonMatch;
    }

    public Picks(LinkedList<Match> p) {
        pairs = new Match[p.size()];
        for(int i=0; i<p.size(); i++){
            pairs[i] = p.get(i);
        }
        numUnkown= pairs.length -numMatch - numNonMatch;
    }

    public Match[] getPairs() {
        return pairs;
    }

    public void restore(){
        this.pairs = this.oldPairs.clone();
    }

    public Match getPair(int i) {
        return pairs[i];
    }

    public int getNumUnkown() {
        return numUnkown;
    }

    public int getNumMatch() {
        return numMatch;
    }

    public void setNumNonMatch(int numNonMatch) {
        this.numNonMatch = numNonMatch;
    }

    public void setNumUnkown(int numUnkown) {
        this.numUnkown = numUnkown;
    }

    public void addNumMatch(int toAdd) {
        this.numMatch = numMatch + toAdd;
        numUnkown= pairs.length -numMatch - numNonMatch;
    }

    public int getNumNonMatch() {
        return numNonMatch;
    }

    public void addNumNonMatch(int num) {
        this.numNonMatch = numNonMatch + num;
        numUnkown= pairs.length -numMatch - numNonMatch;
    }

    @Override
    public String toString() {
        return "Picks{" +
                "pairs=" + Arrays.toString(pairs) +
                '}';
    }

    public Match[] getUntestedPair(int num){
        Match[] matches = new Match[num];
        int i=0;
        int j=0;
        while(j<num && i<pairs.length){
            if(pairs[i].isunconfirmed()){
                matches[j] = pairs[i];
                j++;
            }
            i++;
        }
        return matches;
    }

    public LinkedList<Person> getConfirmed(){
        LinkedList<Person> confirmed = new LinkedList<>();
        for(int i=0; i<pairs.length; i++){
            if(pairs[i].ismatch()){
                confirmed.add(pairs[i].getP1());
                confirmed.add(pairs[i].getP2());
            }
        }
        return confirmed;
    }

    public LinkedList<Match> getUnConfirmed(){
        LinkedList<Match> nonConfirmed = new LinkedList<>();
        for(int i=0; i<pairs.length; i++){
            if(pairs[i].isunconfirmed()){
                nonConfirmed.add(pairs[i]);
            }
        }
        return nonConfirmed;
    }

    public void swapPair(PairSwap ps){
        oldPairs = pairs.clone();
        ps.toString();
        int j=0;
        for(int i =0; i< pairs.length; i++){
            if(pairs[i].equals(ps.getOldP()[0]) || pairs[i].equals(ps.getOldP()[1])){
                pairs[i]= ps.getNewP()[j];
                j++;
            }
        }
        return;
    }

    public void swapAllnonMatches(LinkedList<Match> newPairs){
        System.out.println("swapAllnonMatches");
        System.out.println("newPairs.size()=" + newPairs.size());
        System.out.println("numNonM=" + numNonMatch);
        System.out.println(this.toString());
        int j =0;
        setNumNonMatch(0);
        for(int i=0; i<pairs.length; i++){
            if(!pairs[i].ismatch()){
                pairs[i] = newPairs.get(j);
                j++;
            }
        }
        this.numNonMatch = 0;
        this.numUnkown = this.pairs.length - this.numMatch - this.numNonMatch;
    }

    public int cntNumNonMatch(){
        int num =0;
        for(int i=0; i<pairs.length; i++){
            if(!pairs[i].ismatch()){
                num++;
            }
        }
        return num;
    }
}
