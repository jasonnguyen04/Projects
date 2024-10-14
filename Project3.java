/**
 * COP 3530: Project 3 – Linked Lists
 * 
 * Class designed to contain the main method and supporting methods. Runs through the menu.
 *
 * @author Jason Nguyen
 * @version 10/26/23
 */
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project3 {
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
			System.out.print("COP3530 Project 3\n"
					+ "Jason Nguyen\n\n"
					+ "Linked Lists\n"
					+ "Enter the file name (Countries3.csv): ");
			String fileName = scanner.nextLine();
			FileInputStream fileStream = null;
			Scanner fileScanner = null;

			try {
				fileStream = new FileInputStream(fileName);
				fileScanner = new Scanner(fileStream);
				readCSV(fileName, fileScanner);
				int choice;

				do {
					System.out.println("1) Enter a happiness interval for deletions on priority queue\n"
							+ "2) Print the priority queue\n"
							+ "3) Exit program");
					System.out.print("Enter your choice: ");

					choice = scanner.nextInt();
					scanner.nextLine();


					switch (choice) {
					case 1:
						double lowInt = 0;
						double highInt = 0;
						String input;
						boolean flag1 = false;
						while(flag1 == false)
						{
							try{

								System.out.println("Enter happiness interval like [x,y]: ");

								input = scanner.nextLine();

								String[] arr = input.replaceAll("[\\[\\]]", "").split(",");

								lowInt = Double.parseDouble(arr[0]);
								highInt = Double.parseDouble(arr[1]);
								if(lowInt < highInt) {
									priorityQ.intervalDelete(lowInt, highInt);
									System.out.println("Countries of priority queue with happiness values in [" + lowInt +","+ highInt + "] are deleted");
									flag1 = true;
								}
								else
								{
									System.out.println("Invalid interval, first number must be no bigger than the second: ");
								}

							}
							catch (NumberFormatException e) {
								System.out.println("Invalid interval, enter numbers: ");
							}
							catch (ArrayIndexOutOfBoundsException e) {
								System.out.println("Invalid interval, enter numbers: ");
							}
						}
						break;
					case 2:
						priorityQ.printPriorityQ();
						break;
					case 3:
						System.out.println("Have a good day!");
						System.exit(0);
					default:
						System.out.println("Invalid choice. Enter your choice.");
					}
				} while (choice != 3);
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
	 * @return Parses and inputs the CSV file into the stack and priority queue.
	 */
	public static void readCSV(String fileName, Scanner fileScanner) {

		int numRecords = -1;
		int counter = 0;
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
				if(Double.parseDouble(arr[5]) > 4){
					stack.push(new Country(arr[0], arr[1], Double.parseDouble(arr[2]),
							Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5])));
					counter++;
				}
			}
			System.out.println("\nStack contents:");
			stack.printStack();
			for (int i = 0; i < counter; i++) {
				priorityQ.insert(stack.pop());
			}
			System.out.println("\nPriority queue contents:");
			priorityQ.printPriorityQ();
			System.out.println();

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		}
	}
}