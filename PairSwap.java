import java.util.Arrays;

public class PairSwap {
    private Match[] oldP;
    private Match[] newP;

    @Override
    public String toString() {
        return "PairSwap{" +
                "oldP=" + oldP[0].toString() + ", " + oldP[1].toString()+
                " newP=" + newP[0].toString() + ", " + newP[1].toString()+
                '}' + '}';
    }

    public PairSwap(Match[] old) {
        this.oldP = old;
        Match s1 = new Match(old[0].getP1(), old[1].getP2());
        Match s2 = new Match(old[1].getP1(), old[0].getP2());
        this.newP = new Match[]{s1,s2};
    }

    public Match[] getOldP() {
        return oldP;
    }

    public Match[] getNewP() {
        return newP;
    }
}
