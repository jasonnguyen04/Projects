/**
 * COP 3530: Project 5 – Hash Tables
 * <p>
 * Class designed to contain the hash table method and supporting methods.
 *
 * @author Jason Nguyen
 * @version 12/8/23
 */
public class HashTable {

	private class Node {
		//initializes variables
		String name;
		long population;
		long area;
		Node nextNode;

		//constructor
		public Node(String name, long population, long area) {
			this.name = name;
			this.population = population;
			this.area = area;
		}

		//prints names and happiness values
		public void printNode() {
			System.out.printf("%-24s%.4f\n", name, (double) population / area);
		}
	}
	private Node[] hashArray;

	public HashTable(int size) {
		hashArray = new Node[size];
	}

	/**
	 * The hash function
	 *
	 * @param key
	 * @return Returns hashVal modular hash array length
	 */
	private int hashFunction(String key) {
		int hashVal = 0;
		for (int i = 0; i < key.length(); i++) {
			hashVal += key.charAt(i);
		}
		return hashVal % hashArray.length;
	}
	/**
	 * Inserts into hash table
	 *
	 * @param name is country name
	 * @param population is country population
	 * @param area is country area
	 * 
	 */
	public void insert(String name, long population, long area) {
		int hashVal = hashFunction(name);
		Node newNode = new Node(name, population, area);

		if (hashArray[hashVal] == null) {
			hashArray[hashVal] = newNode;
		} else {
			Node current = hashArray[hashVal];
			while (current.nextNode != null) {
				current = current.nextNode;
			}
			current.nextNode = newNode;
		}
	}
	/**
	 * Finds country based off of name
	 *
	 * @param name is country name
	 * @return Returns -1 or hashVal
	 */
	public int find(String name) {
		int hashVal = hashFunction(name);
		Node current = hashArray[hashVal];

		while (current != null) {
			if (current.name.equals(name)) {
				return hashVal;
			}
			current = current.nextNode;
		}

		return -1;
	}
	/**
	 * Deletes country based off of name
	 *
	 * @param name is country name
	 */
	public void delete(String name) {
		int hashVal = hashFunction(name);
		Node current = hashArray[hashVal];
		Node previous = null;

		while (current != null && !current.name.equals(name)) {
			previous = current;
			current = current.nextNode;
		}

		if (current != null) {
			if (previous == null) {
				hashArray[hashVal] = current.nextNode;
			} else {
				previous.nextNode = current.nextNode;
			}
			System.out.printf("%s is deleted from hash table\n", name);
		} else {
			System.out.printf("%s is not a country\n", name);
		}
	}
	/**
	 * Called to print the hash array
	 *
	 * @param
	 */
	public void display() {
		for (int i = 0; i < hashArray.length; i++) {
			System.out.printf("%d. ", i);
			if (hashArray[i] == null) {
				System.out.println("Empty");
			} else {
				Node current = hashArray[i];
				while (current != null) {
					current.printNode();
					current = current.nextNode;
				}
			}
		}
	}
	/**
	 * Called to empty and collided cells
	 *
	 * @param
	 */
	public void printEmptyAndCollidedCells() {
		int emptyCells = 0;
		int collidedCells = 0;

		for (int i = 0; i < hashArray.length; i++) {
			if (hashArray[i] == null) {
				emptyCells++;
			} else {
				Node current = hashArray[i];
				if (current.nextNode != null) {
					collidedCells++;
				}
			}
		}

		System.out.printf("There are %d empty cells and %d collisions in the hash table\n", emptyCells, collidedCells);
	}
	/**
	 * Called to find and print a country
	 *
	 * @param country
	 */
	public void findAndPrint(String country) {
		int index = hashFunction(country);

		Node current = hashArray[index];

		while (current != null) {
			if (current.name.equals(country)) {
				// Country found, print its details
				System.out.println("Country: " + current.name);
				Double density = calculatePopulationDensity(current.population, current.area);
				System.out.printf("Population Density: %.4f\n", density);
				return;
			}
			current = current.nextNode;
		}
		System.out.println(country + " not found in the hash table.");
	}

	// Helper method to calculate population density
	private double calculatePopulationDensity(long population, long area) {
		return (double) population / area;
	}
}