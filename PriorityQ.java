/**
 * COP 3530: Project 3 – Linked Lists
 * 
 *Class made to insert, remove, and print the priority queue.
 *
 * @author Jason Nguyen
 * @version 10/26/23
 */
public class PriorityQ {
	private Link head;

	public PriorityQ(int i) {
		this.head = null;
	}

	/**
	* Method called to insert object into priority queue.
	*
	* @param country is the object holding the country data
	* @return Returns priority queue with inserted object.
	*/
	public void insert(Country country) {
		Link newLink = new Link(country);
		if (head == null || country.getHappinessIndex() > head.data.getHappinessIndex()) {
			newLink.next = head;
			if (head != null) {
				head.prev = newLink;
			}
			head = newLink;
		} else {
			Link current = head;
			while (current.next != null && country.getHappinessIndex() <= current.next.data.getHappinessIndex()) {
				current = current.next;
			}
			newLink.next = current.next;
			newLink.prev = current;
			if (current.next != null) {
				current.next.prev = newLink;
			}
			current.next = newLink;
		}
	}

	/**
	* Method called to remove object from priority queue.
	*
	* @param 
	* @return Returns priority queue with removed object.
	*/
	public Country remove() {
		if (head == null) {
			return null;
		}
		Country removedData = head.data;
		head = head.next;
		if (head != null) {
			head.prev = null;
		}
		return removedData;
	}

	/**
	* Method called to remove objects from priority queue using two intervals.
	*
	* @param start and end as the intervals.
	* @return Returns priority queue with removed objects.
	*/
	public boolean intervalDelete(double start, double end) {
		boolean deleted = false;
		Link current = head;
		while (current != null) {
			if (current.data.getHappinessIndex() >= start && current.data.getHappinessIndex() <= end) {
				if (current.prev != null) {
					current.prev.next = current.next;
				} else {
					head = current.next;
				}
				if (current.next != null) {
					current.next.prev = current.prev;
				}
				deleted = true;
			}
			current = current.next;
		}
		return deleted;
	}

	/**
	* Method called to print the priority queue.
	*
	* @param 
	* @return Returns a list of the priority queue.
	*/
	public void printPriorityQ() {
		if (isEmpty()) {
			System.out.println("Priority Queue is empty.");
		} else {
			System.out.println("Name				    Capitol	    GDPPC	    APC		    HappinessIndex");
			System.out.println("--------------------------------------------------------------------------------------------------");
			recursivePriorityQ(head);
		}
	}

	/**
	* Method called to use recursion in the printing process.
	*
	* @param current.
	* @return Returns the print data.
	*/
	private void recursivePriorityQ(Link current) {
		if (current != null) {
			double GDPPC = current.data.getGdp()/current.data.getPopulation();
			double APC = current.data.getArea()/current.data.getPopulation();
			double happyIndex = current.data.getHappinessIndex();
			System.out.printf("%-35s %-15s %-15.3f %-15.6f %-15.3f\n", current.data.getName(), current.data.getCapital(), GDPPC, APC, happyIndex);
			recursivePriorityQ(current.next);
		}
	}

	/**
	* Method called to check if priority queue is empty.
	*
	* @param 
	* @return Returns rear set to empty.
	*/
	public boolean isEmpty() {
		return head == null;
	}
}