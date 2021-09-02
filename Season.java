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

    public Season(boolean isQueer, String[][] names, boolean[][] gender) {
        secretMatchMaker = new SecretMatchMaker();
        contestants = secretMatchMaker.makeCastWithGender(names, gender);
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

    public int playSeason(){
        //create season, create matches
        String[][] names = {{"F1","F2","F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10"},
                {"M1","M2","M3","M4","M5","M6","M7","M8","M9","M10"}};
        boolean[][] gender= {{false,false,false,false,false,false,false,false,false,false},
                {true,true,true,true,true,true,true,true,true,true}};
        //loop of truth booth and ceremony
        return 0;

    }


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
