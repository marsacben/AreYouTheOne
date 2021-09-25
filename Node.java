import sun.awt.image.ImageWatched;

import java.util.LinkedList;

public class Node {
    LinkedList<Person> above;
    Node parent;
    Person val;
    LinkedList<Person> children;
    LinkedList<Node> ChildrenNodes;
    int score;
    Boolean tested;

    public Node(LinkedList<Person> above, Node parent, LinkedList<Person> children, Person val) {
        this.above = above;
        this.parent = parent;
        this.children = new LinkedList<>();
        this.children.addAll(children);
        this.ChildrenNodes = new LinkedList<>();
        this.tested = false;
        this.val = val;
    }

    public LinkedList<Person> getChildren(LinkedList<Person> contestants){
        LinkedList<Person> children = new LinkedList<>();
        children.addAll(contestants);
        children.removeAll(above);
        children.remove(val);
        return children;
    }
    public Node addChildNode(LinkedList<Person> contestants){
        LinkedList<Person> newabove = new LinkedList<>();
        newabove.addAll(above);
        if(val != null){
            newabove.add(val);
        }
        Person newVal = children.pop();
        //LinkedList<Person> grandChildren = new LinkedList<>();
        //for(int i =0; i<ChildrenNodes.size(); i++){
        //    grandChildren.add(ChildrenNodes.get(i).val);
        //}
        Node c = new Node(newabove,this, getChildren(contestants), newVal);
        this.ChildrenNodes.add(c);
        return c;
    }
}
