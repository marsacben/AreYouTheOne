import java.util.Objects;

public class Match {
    private Person p1;
    private Person p2;
    private boolean unconfirmed;
    private boolean ismatch;

    public Match(Person p1, Person p2) {
        this.p1 = p1;
        this.p2 = p2;
        unconfirmed = true;
    }

    public Person getP1() {
        return p1;
    }

    public void setP1(Person p1) {
        this.p1 = p1;
    }

    public Person getP2() {
        return p2;
    }

    public void setP2(Person p2) {
        this.p2 = p2;
    }

    @Override
    public String toString() {
        String name = "null";
        if(p1.getName() != null){
            name = p1.getName();
        }
        String name2 = "null";
        if(p2.getName() != null){
            name2 = p2.getName();
        }
        return "Match{" +
                "p1=" + name +
                ", p2=" + name2 +
                '}';
    }

    public void setP(Person[] p) {
        this.p1 = p[0];
        this.p2 = p[1];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        Match match = (Match) o;
        return p1.getName().equals(match.p1.getName()) &&
                p2.getName().equals(match.p2.getName());
    }
//    public void setMatch(){
//        p1.setFoundMarch(p2);
//        p2.setFoundMarch(p1);
//    }
//    public void setNonMatch(){
//        p1.setNonMatch(p2);
//        p2.setNonMatch(p1);
//    }

    public Match[] swap(Match m){
        Person p = this.p2;
        this.p2 = m.p2;
        m.p2 = p;
        Match [] swaped = {this, m};
        return swaped;
    }
    public boolean isunconfirmed() {
        return unconfirmed;
    }

    public void setunconfirmed(boolean isconfirmed) {
        this.unconfirmed = isconfirmed;
    }

    public boolean ismatch() {
        return ismatch;
    }

    public void setmatch(boolean ismatch) {
        this.ismatch = ismatch;
    }
}
