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

    public void setP(Person[] p) {
        this.p1 = p[0];
        this.p2 = p[1];
    }

    public void setMatch(){
        p1.setFoundMarch(p2);
        p2.setFoundMarch(p1);
    }
    public void setNonMatch(){
        p1.setNonMatch(p2);
        p2.setNonMatch(p1);
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
