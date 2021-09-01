import java.util.Hashtable;
import java.util.LinkedList;

public class Season {
    private SecretMatchMaker secretMatchMaker;
    private boolean isQueer;
    private int numPairs;
    private boolean knowAllBeams;
    private LinkedList<Match> correctMatches;
    private LinkedList<Match> nonMatches;
    private LinkedList<Match> unconfirmed;
    private Picks selection;
    private int obtainedBeams;
    private int unkownBeams;

    public Season(Hashtable<Person, Person> contestants, boolean isQueer) {
        secretMatchMaker = new SecretMatchMaker(contestants);
        correctMatches = new LinkedList<>();
        nonMatches = new LinkedList<>();
        unconfirmed = new LinkedList<>();
        this.isQueer = isQueer;
        if (isQueer){
            numPairs =8;
        }else{
            numPairs =10;
        }
        knowAllBeams = false;
        obtainedBeams =0;
        unkownBeams =0;
    }

    public

    public void confirmMatch(Match m){
        correctMatches.add(m);
        m.setMatch();
    }

    public void confirmNonMatch(Match m){
        nonMatches.add(m);
        m.setNonMatch();
    }

    public void recordCeramonyFindingsfromSwap(int newbeams, int oldbeams, LinkedList<Match> newPairs) {
        unkownBeams = newbeams - oldbeams;
        if(unkownBeams == 0){
            nonMatches.addAll(newPairs);
        }
        else{
            if(unconfirmed.size() == 0 && unkownBeams == newPairs.size()){
                for(int i=0; i< newPairs.size(); i++) {
                    confirmMatch(newPairs.get(i));
                }
            }
            else{
                unconfirmed.addAll(newPairs);
            }
        }
    }

    public void recordTruthBoth(Match m){
        if(secretMatchMaker.isMatch(m.getP2(),m.getP2())){
            confirmMatch(m);
        }
        else{

        }
    }
}
