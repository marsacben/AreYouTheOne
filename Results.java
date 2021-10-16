import java.util.LinkedList;

public class Results {
    LinkedList<Picks> picks;
    LinkedList<Match> matches;

    public Results(LinkedList<Picks> picks, LinkedList<Match> matches) {
        this.picks = picks;
        this.matches = matches;
    }
}
