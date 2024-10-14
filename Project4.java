/**
* COP 3530: Project 4 – Binary Search Trees
* <p>
* Class designed to contain the main method and supporting methods. Runs through the menu.
*
* @author Jason Nguyen
* @version 11/16/23
*/
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project4 {
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
			System.out.print("COP3530 Project 4\n"
					+ "Jason Nguyen\n\n"
					+ "Enter the file name (Countries4.csv): ");
			String fileName = scanner.nextLine();
			FileInputStream fileStream = null;
			Scanner fileScanner = null;
			BinarySearchTree bst = new BinarySearchTree();

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

						bst.insert(arr[0], Double.parseDouble(arr[5]));
						counter++;

					}

				} catch (FileNotFoundException e) {
					System.out.println("File not found: " + fileName);
				}
				int choice;

				do {
					System.out.println("1) Print tree inorder\n"
							+ "2) Print tree preorder\n"
							+ "3) Print tree postorder\n"
							+ "4) Insert a country with name and happiness\n"
							+ "5) Delete a country for a given name\n"
							+ "6) Search and print a country and its path for a given name\n"
							+ "7) Print bottom countries regarding happiness\n"
							+ "8) Print top countries regarding happiness\n"
							+ "9) Exit\n");
					System.out.print("Enter your choice: ");

					choice = scanner.nextInt();
					scanner.nextLine();


					switch (choice) {
					case 1:
						bst.printInorder();
						break;
					case 2:
						bst.printPreorder();
						break;
					case 3:
						bst.printPostorder();
						break;
					case 4:
						System.out.print("Enter country name: ");
						String insertName = scanner.next();
						System.out.print("Enter country happiness: ");
						double insertHappiness = scanner.nextDouble();
						bst.insert(insertName, insertHappiness);
						System.out.println(insertName + " with happiness of " + insertHappiness + " is inserted.");
						break;
					case 5:
						System.out.print("Enter country name: ");
						String deleteName = scanner.next();
						bst.delete(deleteName);
						System.out.println(deleteName + " is deleted from the binary search tree.");
						break;
					case 6:
						System.out.print("Enter country name: ");
						String searchName = scanner.next();
						double happiness = bst.find(searchName);
						if (happiness != -1) {
							System.out.println(searchName + " is found with happiness of " + happiness);
						} else {
							System.out.println(searchName + " is not found.");
						}
						break;
					case 7:
						System.out.print("Enter the number of countries: ");
						int bottomCount = scanner.nextInt();
						bst.printBottomCountries(bottomCount);
						break;
					case 8:
						System.out.print("Enter the number of countries: ");
						int topCount = scanner.nextInt();
						bst.printTopCountries(topCount);
						break;
					case 9:
						System.out.println("Have a good day!");
						break;
					default:
						System.out.println("Invalid choice. Enter 1-9.");
					}
				} while (choice != 9);
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
}