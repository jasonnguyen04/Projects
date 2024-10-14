/**
* COP 3530: Project 4 – Binary Search Trees
* <p>
* Class made to hold elements of the node.
*
* @author Jason Nguyen
* @version 11/16/23
*/
class Node
{
	//initializes variables
	Node next;
	Node prev;
	public String name;
	public double happiness;
	
	//constructor
	public Node(String name, double happiness) {
		this.name = name;
        this.happiness = happiness;
        this.next = null;
		this.prev = null;
	}

	//prints names and happiness values
	public void print() {
		System.out.printf("%-24s%.3f\n", name, happiness);
    }
}