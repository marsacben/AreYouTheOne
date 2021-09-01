import java.util.Hashtable;

public class SecretMatchMaker {
    private Hashtable<Person, Person> contestants;

    public SecretMatchMaker(Hashtable<Person, Person> contestants) {
        this.contestants = contestants;
    }

    public int ceremony(Picks p){
        int beams =0;
        for(int i=0; i<p.size(); i++ ) {
            Match pair = p.getPair(i);
            if(isMatch(pair.getP1(), pair.getP2())){
                beams++;
            }
        }
        return beams;
    }

    public boolean isMatch(Person p1, Person p2){
        boolean ismatch = false;
        if (contestants.get(p1) == p2) {
            ismatch = true;
        }
        return ismatch;
    }
}
