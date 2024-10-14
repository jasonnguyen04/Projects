/**
 * COP 3530: Project 3 – Linked Lists
 * 
 *Class made to hold elements of the link.
 *
 * @author Jason Nguyen
 * @version 10/26/23
 */
class Link
{
	public Country data;
	Link next;
	Link prev;

	//constructor
	public Link(Country data) {
		this.data = data;
		this.next = null;
		this.prev = null;
	} // set to null)
} // end class Link