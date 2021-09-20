import java.util.*;

public class DivideAndConquer {
    //private LinkedList<Match> correctMatches;
    private LinkedList<Match> nonMatches;
    //private LinkedList<Match> untested;
    private Hashtable<Person, LinkedList<Person>> allPossiblePairs;
    private LinkedList<Person> contestants;
    //private LinkedList<Match> testingPairs;
    private PairSwap pairSwap;
    private TruthBothSelection truthBooth;
    private Picks picks;
    private int numBeams;
    private int unknownBeams;
    private int beamsToFind;
    private int prevBeams;
    private int numPairs;
    private boolean stateSwaping = false;
    private Picks prevPick;
    private int numPicksFound =0;

    @Override
    public String toString() {
        String newline = System.lineSeparator();
        return "DivideAndConquer{" +
                //"correctMatches=" + correctMatches +  newline +
                //", nonMatches=" + nonMatches + newline+
                //", untested=" + untested + newline+
                //", testingPairs=" + testingPairs + newline+
                ", pairSwap=" + pairSwap + newline+
                ", truthBoth=" + truthBooth + newline+
                ", unknownBeams=" + unknownBeams + newline+
                ", beamsToFind=" + beamsToFind + newline+
                ", prevBeams=" + prevBeams + newline+
                ", numPairs=" + numPairs + newline+
                '}';
    }

    /*public LinkedList<Match> getTestingPairs() {
        return testingPairs;
    }*/

    public DivideAndConquer(LinkedList<Person> contestants, boolean isQueer, int numPairs) {
        this.numPairs =numPairs;
        this.contestants = new LinkedList<>();
        //this.untested = new LinkedList<>();
        this.contestants.addAll(contestants);
        this.allPossiblePairs = new Hashtable<Person, LinkedList<Person>>();
        //this.correctMatches = new LinkedList<>();
        this.nonMatches = new LinkedList<>();


        if(isQueer){
            findAllPossiblePairsQueer(this.contestants);
        }else{
            findAllPossiblePairsStraight(this.contestants);
        }
        //this.testingPairs = getUntestedMatches(numPairs, new LinkedList<>());
        this.unknownBeams =0;
        this.prevBeams =0;
        this.picks = new Picks(getUntestedMatches(numPairs, new LinkedList<>()));
        this.truthBooth = null;
        this.pairSwap = null;
        this.beamsToFind =0;
        this.stateSwaping = false;
        this.numBeams=10;
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

    LinkedList<Match> unTestedPairs = new LinkedList<>();
    public void ListPairsStraight(LinkedList<Person> contestants){
        LinkedList<Person> m = new LinkedList<>();
        LinkedList<Person> f = new LinkedList<>();
        for(int i =0; i<contestants.size(); i++){
            Person p = contestants.get(i);
            if(p.isMale()){
                m.add(p);
            }else{
                f.add(p);
            }
        }

        for(int i =0; i<m.size(); i++){
            for(int k=0; k<f.size(); k++){
                Match pair = new Match(m.get(i), f.get(k));
                unTestedPairs.add(pair);
            }
        }

    }

    public void findAllPossiblePairsStraight(LinkedList<Person> contestants){
        ListPairsStraight(contestants);
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

    public Picks getPicks(){
        //if only one left
        if(stateSwaping){
            pairSwap = new PairSwap(picks.getUntestedPair(2));
            System.out.println("swapping " + pairSwap.getOldP()[0].toString() + ", " + pairSwap.getOldP()[1].toString()+ " to " + pairSwap.getNewP()[0] + ", " + pairSwap.getNewP()[1].toString());
            picks.swapPair(pairSwap);
        }
        else{
            stateSwaping = true;
            System.out.println("stateSwapping:" +stateSwaping);
        }
        return picks;
    }
/*
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
*/

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
        if(truthBooth == null){
            truthBooth = new TruthBothSelection(picks.getUntestedPair(1)[0], null);
        }
        return truthBooth.getSent();
    }

        public void recordCeremony(int newbeams){
            numBeams = newbeams;
            if(stateSwaping && pairSwap!=null){
                recordBeamSearchSwap();
            }
            else{
                recordNewMatchBatch(newbeams);
                stateSwaping = true;
                System.out.println("stateSwapping:" +stateSwaping);
            }
            prevBeams = numBeams ;//newbeams;
            //numBeams = newbeams; //fix
            System.out.println(picks);
            checkIfDone();
        }

    //checks if done swapping because all the matches in the batch are found
    public void checkIfDone(){
        if(stateSwaping) {
            //if only one pair left then you know by deduction they are the last match
            //System.out.println(picks.toString());
            if (picks.getNumUnkown() <= 1) {
                if (numBeams > picks.getNumMatch()) {
                    Match m = picks.getUntestedPair(1)[0];
                    recordConfirmedMatch(m);
                }
            }
            //if all matches in batch found all left are a non match by default
            if (picks.getNumMatch() == numBeams) {
                stateSwaping = false;
                System.out.println("stateSwapping:" +stateSwaping);
                System.out.print("All beams found, replacing non-matches: " + (numPairs - getPicks().getNumMatch()));
                recordNonMatches(picks.getUnConfirmed());
                picks.swapAllnonMatches(getUntestedMatches(numPairs - picks.getNumMatch(), picks.getConfirmed()));
                numMissing=0;
            }
        }
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

    public void recordBeamSearchSwap(){
        int diff = numBeams - prevBeams;
        if(diff == 0){  //non matches
            recordNonMatch(pairSwap.getNewP()[0]);
            recordNonMatch(pairSwap.getNewP()[1]);
            //recordNonMatch(pairSwap.getOldP()[0]);
            //recordNonMatch(pairSwap.getOldP()[1]);
        }
        else{
            if(diff == 2){ //both pairs are matches
                recordConfirmedMatch(pairSwap.getNewP()[0]);
                recordConfirmedMatch(pairSwap.getNewP()[1]);
                //recordNonMatch(pairSwap.getOldP()[0]);
                //recordNonMatch(pairSwap.getOldP()[1]);
            }
            else {
                if (diff == -2) {
                    //picks = prevPick;
                    picks.restore();
                    this.numBeams = prevBeams;
                    recordConfirmedMatch(pairSwap.getOldP()[0]);
                    recordConfirmedMatch(pairSwap.getOldP()[1]);
                    //picks.swapPair(new PairSwap(pairSwap.getNewP()));
                    //recordNonMatch(pairSwap.getNewP()[0]);
                    //recordNonMatch(pairSwap.getNewP()[1]);
                    beamsToFind -=2;
                } else {
                    if (diff == 1) {
                        //recordNonMatch(pairSwap.getOldP()[0]);
                        //recordNonMatch(pairSwap.getOldP()[1]);
                        truthBooth = new TruthBothSelection(pairSwap.getNewP()[0], pairSwap.getNewP()[1]);
                    } else {
                        // ==-1
                        //recordNonMatch(pairSwap.getNewP()[0]);
                        //recordNonMatch(pairSwap.getNewP()[1]);
                        //picks.swapPair(new PairSwap(pairSwap.getNewP()));
                        //picks = prevPick;
                        picks.restore();
                        this.numBeams = prevBeams;
                        beamsToFind -=1;
                        truthBooth = new TruthBothSelection(pairSwap.getOldP()[0], pairSwap.getOldP()[1]);
                    }
                }
            }
        }
        pairSwap =null;
    }

    public void recordTruthBoth(boolean ans){
        //testingPairs.remove(truthBoth.getSent());
        if(ans){
            recordConfirmedMatch(truthBooth.getSent());
            beamsToFind -=1;
            if(truthBooth.getDependant()!=null){
                recordNonMatch(truthBooth.getDependant());
                //testingPairs.remove(truthBoth.getDependant());
            }
        }
        else{
            recordNonMatch(truthBooth.getSent());
            if(truthBooth.getDependant()!=null){
                recordConfirmedMatch(truthBooth.getDependant());
                beamsToFind -=1;
                //testingPairs.remove(truthBoth.getDependant());
            }
        }
        checkIfDone();
        truthBooth = null;

    }

    public void recordNewMatchBatch(int newBeams){
        int diff  = newBeams - prevBeams;
        if(diff == 0){ //all non matches
            recordNonMatches(picks.getUnConfirmed());
            System.out.println("New Batch without matches, num unconfirmed swapped:" + picks.getNumNonMatch());
        }else{
           //find the matches by swapping pairs
            beamsToFind = diff;
            //picks.setNumUnkown(numPairs- picks.getNumMatch());
            //picks.setNumNonMatch(0);
            //picks.swapPair(pairSwap);
        }
    }

    int numMissing=0;
    public LinkedList<Match> getUntestedMatches2(int numMatches, LinkedList<Person> people){
        //System.out.println("IN GET BATCH");
        LinkedList<Person> peopleOrig = new LinkedList<>();
        peopleOrig.addAll(people);
        System.out.println("GETUNTESTEDMATCHES:num to replace " + numMatches);
        Enumeration<Person> e = allPossiblePairs.keys();
        LinkedList<Match> matches = new LinkedList<>();
        Person key;
        while(e.hasMoreElements() && matches.size()<numMatches){
            key = e.nextElement();

            //System.out.println("Fining partner for:" + key.getName());
            LinkedList<Person> l = allPossiblePairs.get(key);
            //System.out.println("People:" + l.toString());
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
                            //allPossiblePairs.get(key).remove(p);
                            //allPossiblePairs.get(p).remove(key);
                            System.out.println("Added Match: " + m.toString() + " matches size:" + matches.size());
                            i = l.size();
                        }
                    }
                }
            }
        }
        //while (matches.size() < numMatches){
        shufflePairOrders();
         //   getUntestedMatches(numMatches, peopleOrig);
        int nm =0;
        while (matches.size() < numMatches){
            matches.add(nonMatches.get(nm));
            nm++;
            numMissing++;
        }
        return matches;
    }

    public void shufflePairOrders(){
        Enumeration<Person> e = allPossiblePairs.keys();
        while(e.hasMoreElements()){
            Person key = e.nextElement();
            LinkedList<Person> l = allPossiblePairs.get(key);
            Collections.shuffle(l);
        }
    }

    public void recordConfirmedMatch(Match m){
        //correctMatches.add(m);
        m.setmatch(true);
        picks.addNumMatch(1);
        numPicksFound++;
        unTestedPairs.remove(m);
        //makePersonUnavailable(m.getP1(), m.getP2());
    }

    public void recordNonMatch(Match m){
        nonMatches.add(m);
        m.setmatch(false);
        picks.addNumNonMatch(1);
        allPossiblePairs.get(m.getP1()).remove(m.getP2());
        allPossiblePairs.get(m.getP2()).remove(m.getP1());
        unTestedPairs.remove(m);
        //testingPairs.remove(m);
    }

    public void recordNonMatches(LinkedList<Match> l){
        for(int i=0; i<l.size(); i++){
            recordNonMatch(l.get(i));
        }
    }

    public LinkedList<Match> getUntestedMatches(int numMatches, LinkedList<Person> people){
        System.out.println("In getUntestedMatches: num to get = " + numMatches);
        LinkedList<Match> matches = new LinkedList<>();
        for(int i=0; i<unTestedPairs.size(); i++){
            Match m = unTestedPairs.get(i);
            boolean canUse = people.contains(m.getP1()) || people.contains(m.getP2());
            if(!canUse){
                matches.add(m);
                unTestedPairs.remove(m);
                people.add(m.getP1());
                people.add(m.getP2());
            }
            if(matches.size() == numMatches){
                i= unTestedPairs.size();
            }
        }
        System.out.println("num got = " + matches.size());
        return matches;
    }

    //stable marriage problem
    public LinkedList<Match> getUntestedMatches3(int numMatches, LinkedList<Person> people){
        //System.out.println("IN GET BATCH");
        LinkedList<Match> matches = new LinkedList<>();
        Enumeration<Person> e = allPossiblePairs.keys();
        LinkedList<Person> stillSingle = new LinkedList<>();
        while(e.hasMoreElements()){
            Person key = e.nextElement();
            stillSingle.add(key);
        }
        stillSingle.removeAll(people);
        int i =0;
        while(matches.size() < numMatches){
            // find match that is possible
            if(i >= stillSingle.size()){
                i=0;
            }
            Person p1 = stillSingle.get(i);
            LinkedList<Person> l = allPossiblePairs.get(p1);
            boolean isAlone = true;
            for(int j =0; j<l.size(); j++){
                if(stillSingle.contains(l.get(j))){
                    Match m = new Match(p1, l.get(j));
                    matches.add(m);
                    stillSingle.remove(m.getP1());
                    stillSingle.remove(m.getP2());
                    isAlone = false;
                }
            }
            if(isAlone){
                for(int j =0; j<l.size(); j++){
                    Person p2 = l.get(j);
                    if(!people.contains(l.get(j))){
                        Match m = new Match(p1, p2);
                        matches.add(m);
                        stillSingle.remove(p1);

                        Match remove = breakupMatch(p2, matches);
                        stillSingle.add(remove.getP1());
                        stillSingle.add(remove.getP2());
                        stillSingle.remove(p2);
                        matches.remove(remove);
                    }
                }
            }
        }
        return matches;
    }

    public Match breakupMatch(Person p, LinkedList<Match> l){
        Match m = null;
        for(int i=0; i<l.size(); i++){
            if(l.get(i).getP1().equals(p) || l.get(i).getP2().equals(p)){
                m= l.get(i);
            }
        }
        return m;
    }
    /*public void makePersonUnavailable(Person p1, Person p2){
        allPossiblePairs.remove(p1);
        allPossiblePairs.remove(p2);
        Enumeration<Person> e = allPossiblePairs.keys();
        while(e.hasMoreElements()){
            Person key = e.nextElement();
            allPossiblePairs.get(key).remove(p1);
            allPossiblePairs.get(key).remove(p2);
        }
    }*/

    public int getNumBeams() {
        return numBeams;
    }

//    public LinkedList<Person> getMandatoryPeople(){
//        LinkedList<Person> l = new LinkedList<Person>();
//        for(int i=0; i<correctMatches.size(); i++){
//            l.add(correctMatches.get(i).getP1());
//            l.add(correctMatches.get(i).getP2());
//        }
//        return l;
//    }
}
