import java.util.LinkedList;

public class Person {
    private String name;
    private LinkedList<Person> potentialMatches;
    private boolean isMale;
    private boolean foundMarch;


    public Person(String name, Boolean isMale) {
        this.name = name;
        this.isMale = isMale;
        foundMarch = false;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }

    public Person(String name) {
        this.name = name;
        foundMarch = false;
    }

    public String getName() {
        return name;
    }

    public LinkedList<Person> getPotentialMatches() {
        return potentialMatches;
    }

    public boolean isPotentialMarch(Person p){
        return this.potentialMatches.contains(p);
    }

    public void setNonMatch(Person non){
        potentialMatches.remove(non);
        return;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }

    public void setFoundMarch(Person p){
        foundMarch =true;
        potentialMatches.clear();
        potentialMatches.add(p);
    }
}
