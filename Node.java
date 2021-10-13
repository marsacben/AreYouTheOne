import sun.awt.image.ImageWatched;

import java.util.Hashtable;
import java.util.LinkedList;

public class Node {
    LinkedList<Person> above;
    Node parent;
    Person val;
    LinkedList<Person> children;
    LinkedList<Node> ChildrenNodes;
    int score;
    int depth;
    Boolean tested;

    public Node(LinkedList<Person> above, Node parent, LinkedList<Person> children, Person val, int depth) {
        this.above = above;
        this.parent = parent;
        this.children = new LinkedList<>();
        this.children.addAll(children);
        this.ChildrenNodes = new LinkedList<>();
        this.tested = false;
        this.val = val;
        this.depth = depth;
    }

    public LinkedList<Person> getChildren(LinkedList<Person> contestants){
        LinkedList<Person> children = new LinkedList<>();
        children.addAll(contestants);
        children.removeAll(above);
        children.remove(val);
        return children;
    }
    public Node addChildNode(LinkedList<Person> grandChildren, LinkedList<Person> toskip, LinkedList<Person> newabove){
        //System.out.println("in new node depth:" +depth+", to pick" + grandChildren.toString());
        Person newVal = grandChildren.pop();
        grandChildren.addAll(toskip);
        for(int i =0; i<ChildrenNodes.size(); i++){
            grandChildren.add(ChildrenNodes.get(i).val);
        }
        Node c = new Node(newabove,this, grandChildren, newVal, depth+1);
        this.ChildrenNodes.add(c);
        this.children.remove(c.val);
        //System.out.println("val n: " + c.val);
        //System.out.println("above n: " + c.above);
        //System.out.println("children n: " + c.children);
        return c;
    }

    public LinkedList<Person> getNewAbove(){
        LinkedList<Person> newabove = new LinkedList<>();
        newabove.addAll(above);
        if(val != null){
            newabove.add(val);
        }
        //System.out.println("newabove size " + newabove.size() +" depth" + depth);
        return newabove;
    }

    public LinkedList<Person> getAvailChildren(LinkedList<Person> contestants, LinkedList<Person> toskip, LinkedList<Person> newabove ){

        //System.out.println("newA n: " + newabove);
        //System.out.println("con n: " + contestants);
        LinkedList<Person> grandChildren = new LinkedList<>();
        //System.out.println("val n: " + val);
        //System.out.println("above n: " + above);
        //System.out.println("children n: " + children);
        grandChildren.addAll(contestants);
        grandChildren.removeAll(newabove);
        //System.out.println(grandChildren);
        //System.out.println("...");
        //System.out.println(ChildrenNodes);
        for(int i =0; i<ChildrenNodes.size(); i++){
            grandChildren.remove(ChildrenNodes.get(i).val);
        }
        //System.out.println(grandChildren);
        //grandChildren.removeAll(toskip);
        for(int i=0; i<toskip.size(); i++){
            if(grandChildren.contains(toskip.get(i))){
                grandChildren.remove(toskip.get(i));
            }
        }
        return grandChildren;
    }
}
