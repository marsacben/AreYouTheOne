import java.util.Hashtable;
import java.util.LinkedList;

public class MiniMax {
    Node head;
    Node on;
    LinkedList<Person> contestants;
    LinkedList<Person> toMatch;
    LinkedList<Person> selected;
    Hashtable<Integer, LinkedList<Person>> ruledOut;
    LinkedList<LinkedList<Person>> incompleateInfo;
    LinkedList<LinkedList<Integer>> depthIndex;
    LinkedList<Integer> numUnkown;

    public MiniMax(LinkedList<Person> contestantsMale, LinkedList<Person> contestantsFemale) {
        this.contestants = contestantsMale;
        head = new Node(new LinkedList<>(),null,contestants, null, -1);
        on = head;
        toMatch = contestantsFemale;
        selected = new LinkedList<>();
        ruledOut = new Hashtable<>();
        incompleateInfo = new LinkedList<>();
        numUnkown = new LinkedList<>();
        depthIndex = new LinkedList<>();
    }

    public Picks getCeremony(){
        System.out.println("val: " +on.val);
        System.out.println("above: " +on.above);
        System.out.println("children: " +on.children);
        System.out.println("------------");
        if(on == null){
            System.out.println("on is null! 18");
        }
        else{
            System.out.println("on is not null! 18");
        }
        int numAbove = (contestants.size()/2) -2;
        //while(true) {
            //if already a leaf// going up
            traverseUp();
            //traversing down - works
            traverseDown();
       //s }
            //on = on.addChildNode(contestants);
        return createSelection();
    }

    public void traverseUp(){
        boolean onChopped = false;
        if(ruledOut.containsKey(on.depth)){
            onChopped = ruledOut.get(on.depth).contains(on.val);
        }
        while ((on.children.isEmpty() || onChopped) && on.parent !=null) { // && !(on.parent == null)){
            //System.out.println("val e: " + on.val);
           /// System.out.println("above e: " + on.above);
            //System.out.println("children e: " + on.children);
            //System.out.println("------------");
            if (on.parent == null) {
                System.out.println("on val" + on.val);
            } else {
                Person nameprev = on.val;
                on = on.parent;
                on.children.remove(nameprev);
            }

        }
        traverseDown();
    }

    public void traverseDown(){
        while (!on.children.isEmpty()) {
            //traverse
            //System.out.println("val t: " + on.val);
            //System.out.println("above t: " + on.above);
            //System.out.println("children t: " + on.children);
            //System.out.println("------------");
            LinkedList<Person> toskip = new LinkedList<>();
            //LinkedList<Person> available = new LinkedList<>();
            //available.addAll(contestants);
            if(ruledOut.containsKey(on.depth+1)) {
                for (int i = 0; i < on.children.size(); i++) {
                    if (ruledOut.get(on.depth + 1).contains(on.children.get(i))) {
                        toskip.add(on.children.get(i));
                        on.children.remove(i);
                    }
                }
            }
            if(on.children.isEmpty()) {
                traverseUp();
            }
            else{
                System.out.println("depth -1:" + on.depth + "remove: " + toskip.toString());
                on = on.addChildNode(contestants, toskip);
            }
        }
    }

    // returns a list of matches from the node you are on
    public Picks createSelection(){
        LinkedList<Person> selection = new LinkedList<>();
        selection.addAll(on.above);
        selection.add(on.val);
        selection.addAll(on.children);
        if(toMatch.size() != selection.size()){
            System.out.println("Contestants and contestants to match are nor the same size!" + toMatch.size() +" " + selection.size());
            System.out.println(selection);
        }
        LinkedList<Match> matches = new LinkedList<>();
        selected = selection;
        for (int i=0; i<toMatch.size(); i++){
            Match m = new Match(toMatch.get(i), selection.get(i));
            matches.add(m);
        }
        return new Picks(matches);
    }

    public void recordCeremony(int numCorrect){
        if(numCorrect == toMatch.size()){
            System.out.println("//////////////////");
            System.out.println("//////////////////");
            System.out.println("YOU WON");
            System.out.println("//////////////////");
            System.out.println("//////////////////");
        }
        else {
            if (numCorrect == 0) {
                for (int i = 0; i < selected.size(); i++) {
                    LinkedList<Person> l = new LinkedList();
                    if (ruledOut.containsKey(i)) {
                        l.addAll(ruledOut.get(i));
                    }
                    l.add(selected.get(i));
                    ruledOut.put(i, l);
                }
            }
            else{
                //add row of incompleate data
                LinkedList<Person> r = new LinkedList<>();
                r.addAll(selected);
                incompleateInfo.add(r);
                LinkedList<Integer> rowDepth = new LinkedList<>();
                for(int x=0; x<selected.size(); x++){
                    rowDepth.add(x);
                }
                depthIndex.add(rowDepth);
                numUnkown.add(numCorrect);
                updateIncomplete();
            }
        }
    }

    public void updateIncomplete(){
        for(int i=0; i<incompleateInfo.size(); i++){
            LinkedList<Person> data = incompleateInfo.get(i);
            LinkedList<Integer> rowIndexes = depthIndex.get(i);
            for(int j=0; j<data.size(); j++){
                int atDepth = rowIndexes.get(j);
                if(ruledOut.containsKey(atDepth)){
                    if(ruledOut.get(atDepth).contains(data.get(j))){
                        data.remove(j);
                        rowIndexes.remove(j);
                    }
                }
            }
            if(data.size() == numUnkown.get(i)){
                for(int k=0; i<data.size(); i++){
                    setFound(rowIndexes.get(i), data.get(i));
                }
                i--;
            }
        }
    }

    public void setFound(int depth, Person p){
        for(int i=0; i<toMatch.size(); i++){
            if(i != depth){
                LinkedList<Person> l = new LinkedList<>();
                if(ruledOut.containsKey(i)){
                    l.addAll(ruledOut.get(depth));
                }
                l.add(p);
                ruledOut.put(i,l);
            }
        }
        LinkedList<Person> toRuleOut = new LinkedList<>();
        toRuleOut.addAll(contestants);
        toRuleOut.remove(p);
        ruledOut.put(depth,toRuleOut);
    }
}
