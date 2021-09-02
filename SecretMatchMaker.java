import java.util.Collection;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;

public class SecretMatchMaker {
    private Hashtable<Person, Person> perfectMatches;

    public SecretMatchMaker() { }

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
        if (perfectMatches.get(p1) == p2) {
            ismatch = true;
        }
        return ismatch;
    }

    public LinkedList<Person> makeCastWithGender(String[][] names, boolean[][] gender){
        LinkedList<Person> contestants = new LinkedList<>();
        for(int i=0; i< names.length; i++){
            Person a = new Person(names[0][i], gender[0][i]);
            Person b = new Person(names[1][i], gender[1][i]);
            perfectMatches.put(new Person(names[0][i], gender[0][i]), new Person(names[1][i], gender[1][i]));
            contestants.add(a);
            contestants.add(b);
        }
        Collections.shuffle(contestants);
        return contestants;
    }
}
