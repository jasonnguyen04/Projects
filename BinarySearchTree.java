import java.util.ArrayList;

/**
 * COP 3530: Project 4 – Binary Search Trees
 * <p>
 * Class made to insert, remove, and print the binary search tree.
 *
 * @author Jason Nguyen
 * @version 11/16/23
 */
public class BinarySearchTree {

	private Node root;
	// Constructor
	public BinarySearchTree() {
		this.root = null;
	}

	/**
	 * Inserts into node
	 *
	 * @param name is country name
	 * @param happiness is country happiness
	 */
	public void insert(String name, double happiness) {
		root = insertRec(root, name, happiness);
	}

	private Node insertRec(Node root, String name, double happiness) {
		if (root == null) {
			return new Node(name, happiness);
		}
		if (name.compareTo(root.name) < 0) {
			root.prev = insertRec(root.prev, name, happiness);
		} 
		else if (name.compareTo(root.name) > 0) {
			root.next = insertRec(root.next, name, happiness);
		}

		return root;
	}

	/**
	 * Finds country based off of name
	 *
	 * @param name is country name
	 * @return Returns roots
	 */

	public double find(String name) {
		return findRec(root, name);
	}

	private double findRec(Node root, String name) {
		if (root == null) {
			return -1;
		}

		if (name.equals(root.name)) {
			System.out.println("Path to " + name + " is printed here."); // Print path
			return root.happiness;
		}

		System.out.print(root.name + " -> ");

		if (name.compareTo(root.name) < 0) {
			return findRec(root.prev, name);
		} else {
			return findRec(root.next, name);
		}
	}

	/**
	 * Deletes country based off of name
	 *
	 * @param name is country name
	 */
	public void delete(String name) {
		root = deleteRec(root, name);
	}

	private Node deleteRec(Node root, String name) {
		if (root == null) {
			return null;
		}

		if (name.compareTo(root.name) < 0) {
			root.prev = deleteRec(root.prev, name);
		} else if (name.compareTo(root.name) > 0) {
			root.next = deleteRec(root.next, name);
		} else {
			if (root.prev == null) {
				return root.next;
			} else if (root.next == null) {
				return root.prev;
			}
			root.name = minValue(root.next);
			root.next = deleteRec(root.next, root.name);
		}

		return root;
	}

	private String minValue(Node root) {
		String minValue = root.name;
		while (root.prev != null) {
			minValue = root.prev.name;
			root = root.prev;
		}
		return minValue;
	}


	/**
	 * Called to print tree inOrder
	 *
	 * @param
	 */

	public void printInorder() {
		System.out.println("Inorder traversal:");
		System.out.println("Name                    Happiness\n"
				+ "---------------------------------------");
		printInorderRec(root);

	}

	private void printInorderRec(Node root) {
		if (root != null) {
			printInorderRec(root.prev);
			root.print();
			printInorderRec(root.next);
		}
	}

	/**
	 * Called to print tree preOrder
	 *
	 * @param
	 */
	public void printPreorder() {
		System.out.println("Preorder traversal:");
		System.out.println("Name                    Happiness\n"
				+ "---------------------------------------");
		printPreorderRec(root);

	}

	private void printPreorderRec(Node root) {
		if (root != null) {
			root.print();
			printPreorderRec(root.prev);
			printPreorderRec(root.next);
		}
	}
	/**
	 * Called to print tree postOrder
	 *
	 * @param
	 */
	public void printPostorder() {
		System.out.println("Postorder traversal:");
		System.out.println("Name                    Happiness\n"
				+ "---------------------------------------");
		printPostorderRec(root);

	}

	private void printPostorderRec(Node root) {
		if (root != null) {
			printPostorderRec(root.prev);
			printPostorderRec(root.next);
			root.print();
		}
	}

	//--------------------------------------------------------------------------------------------------------------

	/*
	 * Called to print bottom countries by happiness
	 *
	 * @param int c is number of countries we want to print
	 */
	public void printBottomCountries(int c) {
		ArrayList<Node> bottomCountries = new ArrayList<>();
		findBottomCountries(root, bottomCountries, c);
		for (int i = 0; i < bottomCountries.size(); i++) {
			bottomCountries.get(i).print();
		}
	}

	private void findBottomCountries(Node node, ArrayList<Node> bottomCountries, int c) {
		if (node != null) {
			findBottomCountries(node.prev, bottomCountries, c);

			if (bottomCountries.size() < c) {
				bottomCountries.add(node);
			} 
			else {
				int maxIndex = findMaxIndex(bottomCountries);
				if (node.happiness < bottomCountries.get(maxIndex).happiness) {
					bottomCountries.set(maxIndex, node);
				}
			}

			findBottomCountries(node.next, bottomCountries, c);
		}
	}

	private int findMaxIndex(ArrayList<Node> countries) {
		int maxIndex = 0;
		for (int i = 1; i < countries.size(); i++) {
			if (countries.get(i).happiness > countries.get(maxIndex).happiness) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	/*
	 * Called to print top countries by happiness
	 *
	 * @param int c is number of countries we want to print
	 */
	public void printTopCountries(int c) {
		ArrayList<Node> topCountries = new ArrayList<>();
		findTopCountries(root, topCountries, c);
		for (int i = topCountries.size() - 1; i >= 0; i--) {
			topCountries.get(i).print();
		}
	}

	private void findTopCountries(Node node, ArrayList<Node> topCountries, int c) {
		if (node != null) {
			findTopCountries(node.next, topCountries, c);

			if (topCountries.size() < c) {
				topCountries.add(node);
			} 
			else {
				int minIndex = findMinIndex(topCountries);
				if (node.happiness > topCountries.get(minIndex).happiness) {
					topCountries.set(minIndex, node);
				}
			}

			findTopCountries(node.prev, topCountries, c);
		}
	}

	private int findMinIndex(ArrayList<Node> countries) {
		int minIndex = 0;
		for (int i = 1; i < countries.size(); i++) {
			if (countries.get(i).happiness < countries.get(minIndex).happiness) {
				minIndex = i;
			}
		}
		return minIndex;
	}
}