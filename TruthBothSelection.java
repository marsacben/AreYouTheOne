public class TruthBothSelection {
    private Match sent;
    private Match dependant;

    @Override
    public String toString() {
        String sentstr =  "null";
        if(sent!= null){
            sentstr = sent.toString();
        }
        String depstr =  "null";
        if(dependant!= null){
            depstr = dependant.toString();
        }
        return "TruthBothSelection{" +
                "sent=" + sentstr +
                ", dependant=" + depstr +
                '}';
    }

    public Match getSent() {
        return sent;
    }

    public Match getDependant() {
        return dependant;
    }

    public TruthBothSelection(Match sent, Match dependant) {
        this.sent = sent;
        this.dependant = dependant;
    }
}
