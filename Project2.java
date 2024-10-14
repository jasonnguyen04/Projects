/**
 * COP 3530: Project 2 – Stacks and Priority Queues
 * 
 * Class designed to contain the main method and supporting methods. Runs through the menu.
 *
 * @author Jason Nguyen
 * @version 10/5/23
 */
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project2 {
	static Stack stack;
	static PriorityQ priorityQ;
	/**
	 * The main method containing the menu.
	 *
	 * @param
	 * @return Runs through the menu and asks the user for input.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean flag = false;
		while(flag == false) {
			System.out.print("COP3530 Project 2\n"
					+ "Jason Nguyen\n\n"
					+ "Stacks and Priority Queues\n"
					+ "Enter the file name (Countries2.csv): ");
			String fileName = scanner.nextLine();
			FileInputStream fileStream = null;
			Scanner fileScanner = null;

			try {
				fileStream = new FileInputStream(fileName);
				fileScanner = new Scanner(fileStream);
				int counter = readCSV(fileName, fileScanner);
				System.out.println("\nStack created of " + counter + " countries");
				System.out.println("Priority queue created of " + counter + " countries\n");
				int choice;

				do {
					System.out.println("1) Print stack");
					System.out.println("2) Pop a country object from stack");
					System.out.println("3) Push a country object onto stack");
					System.out.println("4) Print priority queue");
					System.out.println("5) Remove a country object from priority queue");
					System.out.println("6) Insert a country object into priority queue");
					System.out.println("7) Exit");
					System.out.print("Enter your choice: ");
					
					choice = scanner.nextInt();
					scanner.nextLine();
					

					switch (choice) {
					case 1:
						stack.printStack();
						break;
					case 2:
						Country poppedCountry = stack.pop();
						if (poppedCountry != null) {
							System.out.println("One country is popped from stack.");
						}
						break;
					case 3:
						System.out.print("Enter name: ");
						String name = scanner.nextLine();
						System.out.print("Enter capital: ");
						String capital = scanner.nextLine();
						System.out.print("Enter population: ");
						double population = scanner.nextDouble();
						System.out.print("Enter GDP (USD): ");
						double gdp = scanner.nextDouble();
						System.out.print("Enter Area (km2): ");
						double area = scanner.nextDouble();
						System.out.print("Enter Happiness Index: ");
						double happinessIndex = scanner.nextDouble();
						Country newCountry = new Country(name, capital, population, gdp, area, happinessIndex);
						stack.push(newCountry);
						break;
					case 4:
						priorityQ.printPriorityQ();
						break;
					case 5:
						Country removedCountry = priorityQ.remove();
						if (removedCountry != null) {
							System.out.println("One country is removed from priority queue.");
						}
						break;
					case 6:
						System.out.print("Enter name: ");
						name = scanner.nextLine();
						System.out.print("Enter capital: ");
						capital = scanner.nextLine();
						System.out.print("Enter population: ");
						population = scanner.nextDouble();
						System.out.print("Enter GDP (USD): ");
						gdp = scanner.nextDouble();
						System.out.print("Enter Area (km2): ");
						area = scanner.nextDouble();
						System.out.print("Enter Happiness Index: ");
						happinessIndex = scanner.nextDouble();
						newCountry = new Country(name, capital, population, gdp, area, happinessIndex);
						priorityQ.insert(newCountry);
						break;
					case 7:
						System.out.println("Have a good day!");
						System.exit(0);
					default:
						System.out.println("Invalid choice. Enter your choice.");
					}
				} while (choice != 7);
			} catch (FileNotFoundException e) {
				System.out.println("File not found: " + fileName);
			} catch (InputMismatchException e) {
				System.out.println("Invalid input");
			}
			
			finally {
				if (fileScanner != null) {
					fileScanner.close();
				}
				scanner.close();
				flag = true;
			}
		}
	}
	/**
	 * Reads and parses CSV file.
	 *
	 * @param filName is the name of the file and fileScanner is the scanner.
	 * @return Parses and inputs the CSV file into the stack and priority queue. Returns country counter.
	 */
	public static int readCSV(String fileName, Scanner fileScanner) {

		int numRecords = -1;
		while (fileScanner.hasNextLine()) {
			numRecords++;
			fileScanner.nextLine();
		}
		stack = new Stack(150);
		priorityQ = new PriorityQ(150);
		try {
			fileScanner = new Scanner(new FileInputStream(fileName));
			fileScanner.nextLine();
			for (int i = 0; i < numRecords; i++) {
				String line = fileScanner.nextLine();
				String[] arr = line.split(",");
				stack.push(new Country(arr[0], arr[1], Double.parseDouble(arr[2]),
						Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5])));
				priorityQ.insert(new Country(arr[0], arr[1], Double.parseDouble(arr[2]),
						Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5])));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		}
		return numRecords;
	}
}