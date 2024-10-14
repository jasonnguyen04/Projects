/**
 * COP 3530: Project 5 – Hash Tables
 * <p>
 * Class designed to contain the main method and supporting methods. Runs through the menu.
 *
 * @author Jason Nguyen
 * @version 12/8/23
 */
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project5 {
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
			System.out.print("COP3530 Project 5\n"
					+ "Jason Nguyen\n\n"
					+ "Enter the file name (Countries5.csv): ");
			String fileName = scanner.nextLine();
			FileInputStream fileStream = null;
			Scanner fileScanner = null;
			HashTable hashTable = new HashTable(257);
			loadDataFromFile(hashTable, "Countries5.csv");


			try {
				fileStream = new FileInputStream(fileName);
				fileScanner = new Scanner(fileStream);
				int numRecords = -1;
				int counter = 0;
				while (fileScanner.hasNextLine()) {
					numRecords++;
					fileScanner.nextLine();
				}
				try {
					fileScanner = new Scanner(new FileInputStream(fileName));
					fileScanner.nextLine();
					for (int i = 0; i < numRecords; i++) {
						String line = fileScanner.nextLine();
						String[] arr = line.split(",");

						//bst.insert(arr[0], Double.parseDouble(arr[5]));
						counter++;

					}

				} catch (FileNotFoundException e) {
					System.out.println("File not found: " + fileName);
				}
				int choice;

				do {
					System.out.println("1) Print hash table\n"
							+ "2) Delete a country of a given name\n"
							+ "3) Insert a country of its name, population, and area\n"
							+ "4) Search and print a country and its population density for a given name.\n"
							+ "5) Print numbers of empty cells and collided cells\n"
							+ "6) Exit");
					System.out.print("Enter your choice: ");

					choice = scanner.nextInt();
					scanner.nextLine();


					switch (choice) {
					case 1:
						hashTable.display();
						break;
					case 2:
						System.out.print("Enter country name: ");
						String deleteCountry = scanner.nextLine();
						hashTable.delete(deleteCountry);
						break;
					case 3:
						System.out.print("Enter country name: ");
						String insertName = scanner.nextLine();
						System.out.print("Enter country population: ");
						long insertPopulation = Long.parseLong(scanner.nextLine());
						System.out.print("Enter country area: ");
						long insertArea = Long.parseLong(scanner.nextLine());
						hashTable.insert(insertName, insertPopulation, insertArea);
						break;
					case 4:
						System.out.print("Enter country name: ");
						String searchName = scanner.nextLine();
						hashTable.findAndPrint(searchName);
						break;
					case 5:
						hashTable.printEmptyAndCollidedCells();
						break;
					case 6:
						System.out.println("Have a good day!");
						break;
					default:
						System.out.println("Invalid choice. Enter 1-6.");
					}
				} while (choice != 6);
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
	 * @param filName is the name of the file and hashTable is the hash table.
	 * @return Parses and inputs the CSV file into the hash table.
	 */
	private static void loadDataFromFile(HashTable hashTable, String fileName) {
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			String line;
			br.readLine();
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String name = parts[0].trim();
				long population = Long.parseLong(parts[2].trim());
				long area = Long.parseLong(parts[4].trim());
				hashTable.insert(name, population, area);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}