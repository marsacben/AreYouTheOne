import java.util.LinkedList;

public class Picks {
    private Match[] pairs;
    private int length;

    public int size() {
        return length;
    }

    public Picks(Match[] pairs) {
        this.pairs = pairs;
        this.length = this.pairs.length;
    }

    public Match[] getPairs() {
        return pairs;
    }

    public Match getPair(int i) {
        return pairs[i];
    }

    public void swapPair(int i , int j, int[] toswitch){
        Person[] a = {pairs[i].getP1(), pairs[i].getP2()};
        Person[] b = {pairs[j].getP1(), pairs[j].getP2()};
        Person as = a[toswitch[0]];
        a[toswitch[0]] = b[toswitch[1]];
        b[toswitch[1]] = as;
        pairs[i].setP(a);
        pairs[j].setP(b);

    }

    public void swapAllnonMatches(LinkedList<Match> newPairs){
        int j =0;
        for(int i=0; i<pairs.length; i++){
            if(!pairs[i].ismatch()){
                newPairs.get(j);
                j++;
            }
        }
    }
}
