import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class TestUtil {

    public Picks randomSelection(LinkedList<Person> cOrig, int pairs, boolean isQueer, LinkedList<Match> confirmedMatches){
        LinkedList<Person> contestants = new LinkedList<>();
        contestants.addAll(cOrig);
        Collections.shuffle(contestants);
        Match[] m = new Match[pairs];
        int i=0;
        for(i=0; i< confirmedMatches.size(); i++){
            m[i] = confirmedMatches.get(i);
            contestants.remove(m[i].getP1());
            contestants.remove(m[i].getP2());
        }
        for( i=i; i<pairs; i++){
            if(isQueer){
                m[i] = randomPairQueer(contestants);
                contestants.remove(m[i].getP1());
                contestants.remove(m[i].getP2());
            }else{
                m[i] = randomPairStraight(contestants);
                contestants.remove(m[i].getP1());
                contestants.remove(m[i].getP2());
            }
        }
        return new Picks(m);
    }

    public Match randomPairStraight(LinkedList<Person> contestants){
        Collections.shuffle(contestants);
        Person m = null;
        Person f = null;
        int i =0;
        while ((m==null || f==null)){
            if(m== null && contestants.get(i).isMale()){
                m= contestants.get(i);
            }else{
                if(f==null && !contestants.get(i).isMale()){
                    f = contestants.get(i);
                }
            }
            i++;
        }
        return new Match(m,f);
    }

    public Match randomPairQueer(LinkedList<Person> contestants){
        Collections.shuffle(contestants);
        return new Match(contestants.get(0), contestants.get(1));
    }
}
