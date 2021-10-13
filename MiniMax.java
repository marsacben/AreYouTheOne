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
    LinkedList<Integer> confirmedAtdepth;
    int TBdepth = -1;
    Node TBNode;
    Node TBhead;

    public MiniMax(LinkedList<Person> contestantsMale, LinkedList<Person> contestantsFemale) {
        this.contestants = contestantsMale;
        head = new Node(new LinkedList<>(),null,contestants, null, -1);
        on = head;
        TBhead = new Node(new LinkedList<>(),null,contestants, null, -1);
        TBNode = TBhead;
        toMatch = contestantsFemale;
        selected = new LinkedList<>();
        ruledOut = new Hashtable<>();
        incompleateInfo = new LinkedList<>();
        numUnkown = new LinkedList<>();
        depthIndex = new LinkedList<>();
        confirmedAtdepth = new LinkedList<>();
    }

    public Picks getCeremony(){
        /*System.out.println("ruledout "+ ruledOut.toString());
        System.out.println("data" + incompleateInfo.toString());
        System.out.println("data index" + depthIndex.toString());
        System.out.println("data beams" + numUnkown.toString());
        //updateIncomplete();
        System.out.println("ruledout "+ ruledOut.toString());
        System.out.println("data" + incompleateInfo.toString());
        System.out.println("data index" + depthIndex.toString());
        System.out.println("data beams" + numUnkown.toString());*/
        /*System.out.println("val: " +on.val);
        System.out.println("above: " +on.above);
        System.out.println("children: " +on.children);
        System.out.println("------------");
        if(on == null){
            System.out.println("on is null! 18");
        }
        else{
            System.out.println("on is not null! 18");
        }*/
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
                //System.out.println("on val" + on.val);
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

            LinkedList<Person> toskip = new LinkedList<>();
            //LinkedList<Person> available = new LinkedList<>();
            //available.addAll(contestants);
            //System.out.println("children " + on.children.toString());
            if(ruledOut.containsKey(on.depth+1)) {
                //for (int i = 0; i < on.children.size(); i++) {
                //    if (ruledOut.get(on.depth + 1).contains(on.children.get(i))) {
                        //toskip.add(on.children.get(i));
                 //       on.children.remove(i);
                 //   }
                toskip.addAll(ruledOut.get(on.depth + 1));
            }

            LinkedList<Person> newabove = on.getNewAbove();
            //System.out.println("contestants1: " + contestants );
            on.children = on.getAvailChildren(contestants, toskip, newabove);
            //System.out.println("toskip: " + toskip.toString() );
            //.out.println("val t: " + on.val + " depth:" + on.depth);
            //System.out.println("above t: " + on.above + " newabove " +newabove);
            //System.out.println("children t: " + on.children);
            //System.out.println("------------");
            if(newabove.size()< toMatch.size()){
                if(on.children.isEmpty()) {
                    traverseUp();
                }
                else{
                    //System.out.println("ruledout "+ ruledOut.toString());
                    //System.out.println("data" + incompleateInfo.toString());
                    //System.out.println("depth -1:" + on.depth + "remove: " + toskip.toString());
                    on = on.addChildNode(on.children, toskip, newabove);
                }
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
        //System.out.println("hi");
        if(numCorrect == toMatch.size()){
            System.out.println("//////////////////");
            System.out.println("//////////////////");
            System.out.println("YOU WON");
            System.out.println("//////////////////");
            System.out.println("//////////////////");
        }
        else {
            //System.out.println("hi2");
            if (numCorrect == 0) {
                //then all selected are wrong
                for (int i = 0; i < selected.size(); i++) {
                    setRuledOut(i,selected.get(i));
                }
            }
            else{
                //System.out.println("hi4");
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
            }
            //System.out.println("hi5");
            updateIncomplete();
            //System.out.println("hi6");
        }
        //("ruledout "+ ruledOut.toString());
        //System.out.println("data" + incompleateInfo.toString());
        //System.out.println("data index" + depthIndex.toString());
        //System.out.println("data beams" + numUnkown.toString());
    }

    public void updateIncomplete(){
        for(int i=0; i<incompleateInfo.size(); i++){
            //System.out.println("hi i" + i);
            LinkedList<Person> data = incompleateInfo.get(i);
            LinkedList<Integer> rowIndexes = depthIndex.get(i);
            for(int j=0; j<data.size(); j++){ //for each row of data
                int atDepth = rowIndexes.get(j);
                if(ruledOut.containsKey(atDepth)){
                    if(ruledOut.get(atDepth).contains(data.get(j))){ //if can be ruled out
                        data.remove(j);
                        rowIndexes.remove(j);
                    }
                }
            }
            if(data.size() == numUnkown.get(i)){
                for(int k=0; k<data.size(); k++){
                    setFound(rowIndexes.get(k), data.get(k));
                }
                //i--;
            }
        }
    }

    public void setRuledOut(int depth, Person p){
        LinkedList<Person> l = new LinkedList();
        if (ruledOut.containsKey(depth)) {
            l.addAll(ruledOut.get(depth));
        }
        l.add(p);
        ruledOut.put(depth, l);
    }

    public void setFound(int depth, Person p){
        confirmedAtdepth.add(depth);
        for(int i=0; i<toMatch.size(); i++){
            if(i != depth){
                //get ruled out for depth into l
                LinkedList<Person> l = new LinkedList<>();
                if(ruledOut.containsKey(i)){
                    l.addAll(ruledOut.get(i));
                }
                //add found to confirmed to to all other depths
                if(!l.contains(p)){
                    l.add(p);
                    ruledOut.put(i,l);
                }
            }
        }
        //rule out all other than it found for found depth
        LinkedList<Person> toRuleOut = new LinkedList<>();
        toRuleOut.addAll(contestants);
        toRuleOut.remove(p);
        ruledOut.put(depth,toRuleOut);
    }

    public Match getTruthBooth(){
        Node tb = TBhead;
        Node n = breadthFirstSearch(tb);
        TBdepth = n.depth;
        TBNode = n;
        return new Match(toMatch.get(n.depth), n.val);
    }

    public Node breadthFirstSearch(Node tb){
        //get children
        LinkedList<Person> toskip = new LinkedList<>();
        if(ruledOut.containsKey(tb.depth+1)) {
            toskip.addAll(ruledOut.get(tb.depth + 1));
        }
        LinkedList<Person> newabove = tb.getNewAbove();
        tb.children = tb.getAvailChildren(contestants, toskip, newabove);

        //get validChild
        tb = tb.addChildNode(tb.children, toskip, newabove);

        if(confirmedAtdepth.contains(tb.depth)){
            tb = breadthFirstSearch(tb);
        }
        return tb;
    }

    public void recordTruthBooth(boolean isMatch){
        if(isMatch){
            setFound(TBdepth, TBNode.val);
            TBhead = TBNode;
        }else{
            setRuledOut(TBdepth, TBNode.val);
        }
    }

}
