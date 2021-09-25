import java.util.LinkedList;

public class MiniMax {
    Node head;
    Node on;
    LinkedList<Person> contestants;
    LinkedList<Person> toMatch;

    public MiniMax(LinkedList<Person> contestantsMale, LinkedList<Person> contestantsFemale) {
        this.contestants = contestantsMale;
        head = new Node(new LinkedList<>(),null,contestants, null);
        on = head;
        toMatch = contestantsFemale;
    }

    public Picks getCeremony(){
        if(on == null){
            System.out.println("on is null! 18");
        }
        else{
            System.out.println("on is not null! 18");
        }
        //if already a leaf
        while(on.children.isEmpty()){ // && !(on.parent == null)){
            if(on.parent == null){
                System.out.println(on.val);
            }else{
                on = on.parent;
            }

        }
        while(!on.children.isEmpty()){
            //traverse
            on = on.addChildNode(contestants);
        }
        return createSelection();
    }

    // returns a list of matches from the node you are on
    public Picks createSelection(){
        LinkedList<Person> selection = new LinkedList<>();
        selection.addAll(on.above);
        selection.add(on.val);
        if(toMatch.size() != selection.size()){
            System.out.println("Contestants and contestants to match are nor the same size!" + toMatch.size() +" " + selection.size());
        }
        LinkedList<Match> matches = new LinkedList<>();
        for (int i=0; i<toMatch.size(); i++){
            Match m = new Match(toMatch.get(i), selection.get(i));
            matches.add(m);
        }
        return new Picks(matches);
    }


}
