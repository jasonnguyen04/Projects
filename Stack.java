/**
 * COP 3530: Project 3 – Linked Lists
 * 
 * Class made to push, pop, and print stack.
 *
 * @author Jason Nguyen
 * @version 10/26/23
 */
public class Stack {
	private Link first;
	private Link last;

	public Stack(int i) {
		this.first = null;
		this.last = null;
	}

	/**
	* Method called to push object into stack.
	*
	* @param country is the object holding the country data
	* @return Returns stack with pushed object.
	*/
	public void push(Country country) {
		Link newLink = new Link(country);
		if (first == null) {  // If the stack is empty
			first = newLink;
			last = newLink;
		} else {
			newLink.next = first;
			first = newLink;
		}
	}

	/**
	* Method called to pop object from stack.
	*
	* @param 
	* @return Returns stack with popped object.
	*/
	public Country pop() {
		if (first == null) {
			return null;
		} else {
			Country poppedData = first.data;
			if (first == last) {
				first = null;
				last = null;
			} else {
				first = first.next;
			}
			return poppedData;
		}
	}

	/**
	* Method called to print the stack.
	*
	* @param 
	* @return Returns a list of the stack.
	*/
	public void printStack() {
		if (isEmpty()) {
			System.out.println("Stack is empty.");
		} else {
			System.out.println("Name				    Capitol	    GDPPC	    APC		    HappinessIndex");
			System.out.println("--------------------------------------------------------------------------------------------------");
			recursiveStack(first);
		}
	}

	/**
	* Method called to use recursion in the printing process.
	*
	* @param current.
	* @return Returns the print data.
	*/
	private void recursiveStack(Link current) {
		if (current != null) {
			double GDPPC = current.data.getGdp()/current.data.getPopulation();
			double APC = current.data.getArea()/current.data.getPopulation();
			double happyIndex = current.data.getHappinessIndex();
			System.out.printf("%-35s %-15s %-15.3f %-15.6f %-15.3f\n", current.data.getName(), current.data.getCapital(), GDPPC, APC, happyIndex);
			recursiveStack(current.next);
		}
	}

	/**
	* Method called to check if stack is empty.
	*
	* @param 
	* @return Returns top set to empty.
	*/
	public boolean isEmpty() {
		return first == null;
	}
}