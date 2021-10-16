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
    private LinkedList<Person> contestants;
    private Picks selection;
    private int obtainedBeams;
    private int unkownBeams;

    public Season( String[][] names, boolean[][] gender) {
        secretMatchMaker = new SecretMatchMaker();
        contestants = secretMatchMaker.makeCast(names, gender);
        System.out.println(secretMatchMaker.toString());
        correctMatches = new LinkedList<>();
        nonMatches = new LinkedList<>();
        unconfirmed = new LinkedList<>();
        this.isQueer = false;
        numPairs =10;
        knowAllBeams = false;
        obtainedBeams =0;
        unkownBeams =0;
    }
    public Season( String[][] names, boolean[][] gender, LinkedList<Integer> order) {
        secretMatchMaker = new SecretMatchMaker();
        contestants = secretMatchMaker.makeCastNoShuffle(names, gender, order);
        System.out.println(secretMatchMaker.toString());
        correctMatches = new LinkedList<>();
        nonMatches = new LinkedList<>();
        unconfirmed = new LinkedList<>();
        this.isQueer = false;
        numPairs =10;
        knowAllBeams = false;
        obtainedBeams =0;
        unkownBeams =0;
    }

    public Season( String[][] names) {
        secretMatchMaker = new SecretMatchMaker();
        contestants = secretMatchMaker.makeCast(names);
        correctMatches = new LinkedList<>();
        nonMatches = new LinkedList<>();
        unconfirmed = new LinkedList<>();
        isQueer = true;
        numPairs =8;
        knowAllBeams = false;
        obtainedBeams =0;
        unkownBeams =0;
    }

    public LinkedList<Person> getContestants() {
        return contestants;
    }
    public LinkedList<Person>[] getContestantsSplit() {
        LinkedList<Person> l1 = new LinkedList<>();
        LinkedList<Person> l2 = new LinkedList<>();
        for(int i=0; i<contestants.size(); i++){
            if(contestants.get(i).isMale()){
                l1.add(contestants.get(i));
            }
            else{
                l2.add(contestants.get(i));
            }
        }
        return new LinkedList[]{l1,l2};
    }

    public int ceremony(Picks p){
        return secretMatchMaker.ceremony(p);
    }

    public boolean truthBoth(Match m){
        return secretMatchMaker.isMatch(m.getP1(), m.getP2());
    }

    public int playSeason(){

        //loop of truth booth and ceremony

        return 0;

    }


    public void confirmMatch(Match m){
        correctMatches.add(m);
        m.setmatch(true);
    }

    public void confirmNonMatch(Match m){
        nonMatches.add(m);
        m.setmatch(false);
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
