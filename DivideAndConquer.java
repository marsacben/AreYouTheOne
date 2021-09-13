import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;

public class DivideAndConquer {
    private LinkedList<Match> correctMatches;
    private LinkedList<Match> nonMatches;
    private LinkedList<Match> untested;
    private Hashtable<Person, LinkedList<Person>> allPossiblePairs;
    private LinkedList<Person> contestants;
    private LinkedList<Match> testingPairs;
    private PairSwap pairSwap;
    private TruthBothSelection truthBoth;
    private Picks picks;
    private int unknownBeams;
    private int beamsToFind;
    private int prevBeams;
    private int numPairs;
    private boolean stateSwaping = false;

    @Override
    public String toString() {
        String newline = System.lineSeparator();
        return "DivideAndConquer{" +
                "correctMatches=" + correctMatches +  newline +
                ", nonMatches=" + nonMatches + newline+
                ", untested=" + untested + newline+
                ", testingPairs=" + testingPairs + newline+
                ", pairSwap=" + pairSwap + newline+
                ", truthBoth=" + truthBoth + newline+
                ", unknownBeams=" + unknownBeams + newline+
                ", beamsToFind=" + beamsToFind + newline+
                ", prevBeams=" + prevBeams + newline+
                ", numPairs=" + numPairs + newline+
                '}';
    }

    public LinkedList<Match> getTestingPairs() {
        return testingPairs;
    }

    public DivideAndConquer(LinkedList<Person> contestants, boolean isQueer, int numPairs) {
        this.numPairs =numPairs;
        this.contestants = new LinkedList<>();
        this.untested = new LinkedList<>();
        this.contestants.addAll(contestants);
        this.allPossiblePairs = new Hashtable<Person, LinkedList<Person>>();
        this.correctMatches = new LinkedList<>();
        this.nonMatches = new LinkedList<>();


        if(isQueer){
            findAllPossiblePairsQueer(this.contestants);
        }else{
            findAllPossiblePairsStraight(this.contestants);
        }
        this.testingPairs = getUntestedMatches(numPairs, new LinkedList<>());
        this.unknownBeams =0;
        this.prevBeams =0;
        this.picks = new Picks(testingPairs);
        this.truthBoth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
        this.pairSwap = new PairSwap(picks.getUntestedPair(2));
        this.beamsToFind =0;
    }

    public void findAllPossiblePairsQueer(LinkedList<Person> contestants){
        allPossiblePairs = new Hashtable<>();
        for(int i =0; i<contestants.size(); i++){
            LinkedList<Person> possibleMatches = new LinkedList<>();
            for(int j=0; j<contestants.size(); j++){
                if(j != i) {
                    possibleMatches.add(contestants.get(j));
                }
            }
            allPossiblePairs.put(contestants.get(i), possibleMatches);
        }
    }

    public void findAllPossiblePairsStraight(LinkedList<Person> contestants){
        allPossiblePairs = new Hashtable<>();
        for(int i =0; i<contestants.size(); i++){
            LinkedList<Person> possibleMatches = new LinkedList<>();
            for(int j=0; j<contestants.size(); j++){
                Person p1 = contestants.get(i);
                Person p2 = contestants.get(j);
                if((p1.isMale() && !p2.isMale()) || (!p1.isMale() && p2.isMale())){
                    possibleMatches.add(contestants.get(j));
                }
            }
            allPossiblePairs.put(contestants.get(i), possibleMatches);
        }
        System.out.println("all possible pairs" + allPossiblePairs.toString());
    }
    public boolean hasStarted =false;
    public Picks getPicks(){
        System.out.println("unkown:" + picks.getNumUnkown() + " matched: " + picks.getNumMatch() + "nonmatches: " + picks.getNumNonMatch());
        if(!hasStarted){
            hasStarted = true;
            return picks;
        }
        if(picks.getNumUnkown()>0){
            System.out.println("swapping " + pairSwap.getOldP()[0].toString() + ", " + pairSwap.getOldP()[1].toString()+ " to " + pairSwap.getNewP()[0] + ", " + pairSwap.getNewP()[1].toString());
            picks.swapPair(pairSwap);
        }else{
            picks.swapAllnonMatches(getUntestedMatches(picks.getNumNonMatch(), picks.getConfirmed()));
        }
        return picks;
    }

    /*public Picks getPicks(int numPairs){
        Match[] matches = new Match[numPairs];
        int i=0;
        for(i=0; i<correctMatches.size(); i++){
            matches[i] = correctMatches.get(i);
        }
        if(pairSwap != null){
            matches[i] = pairSwap.getNewP()[0];
            i++;
            matches[i] = pairSwap.getNewP()[1];
            i++;
        }
        for(int j=0; j<testingPairs.size(); j++){
            if(i < numPairs){
                matches[i] = testingPairs.get(j);
            }
            i++;
        }
        int k=0;
        while(i<numPairs-1){
            matches[i] = nonMatches.get(k);
            i++;
            k++;
        }
        return new Picks(matches);
    }*/

    public Match getTruthBoth(){
        if(truthBoth == null){
            truthBoth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
        }
        return truthBoth.getSent();
    }

        public void recordCeremony(int newbeams){
        //Yoy know who all the previous matchs are
        if(picks.getNumUnkown() == 0){
            //mark all non matches
            recordNewMatchBatch(newbeams);
        }
        //looking for beams
        else{
            recordBeamSearchSwap(newbeams);
            // fix if(testingPairs.size()<2){
            pairSwap = new PairSwap(picks.getUntestedPair(2));
        }
        prevBeams = newbeams;
    }
    //marks any new confirmed match or non match and creates a new list of testing pairs
/*    public void recordCeremony(int newbeams){
        //Yoy know who all the previous matchs are
        if(newbeams==0){
            picks.setNumNonMatch(numPairs);
        }
        if(picks.getNumUnkown() == 0){
            //mark all non matches
            recordNewMatchBatch(newbeams);
        }
        //looking for beams
        else{
            recordBeamSearchSwap(newbeams);
            // fix if(testingPairs.size()<2){
            pairSwap = new PairSwap(picks.getUntestedPair(2));
        }
        prevBeams = newbeams;
    }*/

/*    public void recordCeremony(int newbeams){
        //Yoy know who all the previous matchs are
        if(stateSwaping){
            recordBeamSearchSwap(newbeams);
            pairSwap = new PairSwap(picks.getUntestedPair(2));
            //mark all non matches

        }
        //looking for beams
        else{
            recordNewMatchBatch(newbeams);
        }
        prevBeams = newbeams;
    }*/

    public void recordBeamSearchSwap(int newBeams){
        int diff = newBeams - prevBeams;
        if(diff == 0){  //non matches
            recordNonMatch(pairSwap.getNewP()[0]);
            recordNonMatch(pairSwap.getNewP()[1]);
            recordNonMatch(pairSwap.getOldP()[0]);
            recordNonMatch(pairSwap.getOldP()[1]);
            truthBoth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
        }
        else{
            if(diff == 2){ //both pairs are matches
                recordConfirmedMatch(pairSwap.getNewP()[0]);
                recordConfirmedMatch(pairSwap.getNewP()[1]);
                recordNonMatch(pairSwap.getOldP()[0]);
                recordNonMatch(pairSwap.getOldP()[1]);
                truthBoth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
            }
            else {
                if (diff == -2) {
                    recordConfirmedMatch(pairSwap.getOldP()[0]);
                    recordConfirmedMatch(pairSwap.getOldP()[1]);
                    recordNonMatch(pairSwap.getNewP()[0]);
                    recordNonMatch(pairSwap.getNewP()[1]);
                    beamsToFind -=2;
                    truthBoth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
                } else {
                    if (diff == 1) {
                        recordNonMatch(pairSwap.getOldP()[0]);
                        recordNonMatch(pairSwap.getOldP()[1]);
                        truthBoth = new TruthBothSelection(pairSwap.getNewP()[0], pairSwap.getNewP()[1]);
                    } else {
                        // ==-1
                        recordNonMatch(pairSwap.getNewP()[0]);
                        recordNonMatch(pairSwap.getNewP()[1]);
                        beamsToFind -=1;
                        truthBoth = new TruthBothSelection(pairSwap.getOldP()[0], pairSwap.getOldP()[1]);
                    }
                }
            }
        }
        pairSwap =null;
    }

    public void recordTruthBoth(boolean ans){
        testingPairs.remove(truthBoth.getSent());
        if(ans){
            recordConfirmedMatch(truthBoth.getSent());
            beamsToFind -=1;
            if(truthBoth.getDependant()!=null){
                recordNonMatch(truthBoth.getDependant());
                testingPairs.remove(truthBoth.getDependant());
            }
        }
        else{
            recordNonMatch(truthBoth.getSent());
            if(truthBoth.getDependant()!=null){
                recordConfirmedMatch(truthBoth.getDependant());
                beamsToFind -=1;
                testingPairs.remove(truthBoth.getDependant());
            }
        }
        truthBoth = null;

    }

    public void recordNewMatchBatch(int newBeams){
        int diff  = newBeams - prevBeams;
        if(diff == 0){ //all non matches
            recordNonMatches(testingPairs);
            //select new batch
            //testingPairs = getUntestedMatches(numPairs- correctMatches.size(), getMandatoryPeople());
            truthBoth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
        }else{
           //find the matches by swapping pairs
            beamsToFind = diff;
            //picks.setNumUnkown(numPairs- picks.getNumMatch());
            //picks.setNumNonMatch(0);
            pairSwap = new PairSwap(picks.getUntestedPair(2));
            //picks.swapPair(pairSwap);
        }
    }

    public LinkedList<Match> getUntestedMatches(int numMatches, LinkedList<Person> people){
        //System.out.println("IN GET BATCH");
        Enumeration<Person> e = allPossiblePairs.keys();
        LinkedList<Match> matches = new LinkedList<>();
        while(e.hasMoreElements() && matches.size()<numMatches){
            Person key = e.nextElement();
            //System.out.println("Fining partner for:" + key.getName());
            LinkedList<Person> l = allPossiblePairs.get(key);
            if(!people.contains(key)) {
                for (int i = 0; i < l.size(); i++) {
                    if (!l.isEmpty()) {
                        Person p = l.get(i);
                        //System.out.println(p.getName() + "...");
                        Match m = new Match(key, p);
                        if (!people.contains(p)) {
                            matches.add(m);
                            people.add(m.getP1());
                            people.add(m.getP2());
                            allPossiblePairs.get(key).remove(p);
                            allPossiblePairs.get(p).remove(key);
                            System.out.println("Added Match: " + m.toString() + " mateches size:" + matches.size());
                            i = l.size();
                        }
                    }
                }
            }
        }
        return matches;
    }

    public void recordConfirmedMatch(Match m){
        correctMatches.add(m);
        m.setmatch(true);
        m.setunconfirmed(false);
        picks.addNumMatch(1);
        makePersonUnavailable(m.getP1(), m.getP2());
    }

    public void recordNonMatch(Match m){
        nonMatches.add(m);
        m.setunconfirmed(false);
        m.setmatch(false);
        picks.addNumNonMatch(1);
        //allPossiblePairs.get(m.getP1()).remove(m.getP2());
        //allPossiblePairs.get(m.getP2()).remove(m.getP1());
        testingPairs.remove(m);
    }

    public void recordNonMatches(LinkedList<Match> l){
        for(int i=0; i<l.size(); i++){
            recordNonMatch(l.get(i));
        }
    }
    public void makePersonUnavailable(Person p1, Person p2){
        allPossiblePairs.remove(p1);
        allPossiblePairs.remove(p2);
        Enumeration<Person> e = allPossiblePairs.keys();
        while(e.hasMoreElements()){
            Person key = e.nextElement();
            allPossiblePairs.get(key).remove(p1);
            allPossiblePairs.get(key).remove(p2);
        }
    }



    public LinkedList<Person> getMandatoryPeople(){
        LinkedList<Person> l = new LinkedList<Person>();
        for(int i=0; i<correctMatches.size(); i++){
            l.add(correctMatches.get(i).getP1());
            l.add(correctMatches.get(i).getP2());
        }
        return l;
    }
}
