/**
 * COP 3530: Project 1 – Array Searches and Sorts
 * 
 * Class designed to contain the main method and supporting methods. Runs through the menu.
 *
 * @author Jason Nguyen
 * @version 9/14/23
 */
import java.io.*;
import java.util.Scanner;

public class Project1 {
	/**
	 * The main method containing the menu.
	 *
	 * @param 
	 * @return Runs through the menu and asks the user for input.
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		boolean flag = false;
		boolean binaryFlag = false;
		while(flag == false) {
			System.out.print("COP3530 Project 1\n"
					+ "Jason Nguyen\n\n"
					+ "Array Searches and Sorts\n"
					+ "Enter the file name (Countries1.csv): ");
			String fileName = scanner.nextLine();
			FileInputStream fileStream = null;
			Scanner fileScanner = null;

			try {
				fileStream = new FileInputStream(fileName);
				fileScanner = new Scanner(fileStream);
				Country[] countries = readCSV(fileName, fileScanner);
				System.out.println("\nThere were " + countries.length + " records read.\n");
				int choice;

				do {
					System.out.println("1. Print a Countries report");
					System.out.println("2. Sort by name");
					System.out.println("3. Sort by happiness index");
					System.out.println("4. Sort by GDP per capita");
					System.out.println("5. Find and print a country by name");
					System.out.println("6. Print Kendall’s tau correlation matrix");
					System.out.println("7. Quit");
					System.out.print("Enter your choice: ");
					choice = scanner.nextInt();
					scanner.nextLine(); // Consume the newline

					switch (choice) {
					case 1:
						printCountriesReport(countries);
						System.out.println();
						break;
					case 2:
						System.out.println("\nCountries sorted by Name.\n");
						binaryFlag = true;
						sortByName(countries);
						break;
					case 3:
						System.out.println("\nCountries sorted by Happiness Index.\n");
						binaryFlag = false;
						sortByHappinessIndex(countries);
						break;
					case 4:
						System.out.println("\nCountries sorted by GDP per capita.\n");
						binaryFlag = false;
						sortByGDPPC(countries);
						break;
					case 5:
						System.out.print("\nEnter country name: ");
						String countryName = scanner.nextLine();
						if(binaryFlag == false) {
							System.out.println("\nSequential search is used\n");
							findAndPrintCountryByNameSeq(countries, countryName);
							break;
						} else {
							System.out.println("\nBinary search is used\n");
							findAndPrintCountryByNameBin(countries, countryName);
							break;
						}
					case 6:
						printKendallsTauMatrix(countries);
						System.out.println();
						break;
					case 7:
						System.out.println("\nHave a good day!\n");
						break;
					default:
						System.out.println("\nInvalid choice! Enter 1-7.\n");
					}
				} while (choice != 7);
			} catch (FileNotFoundException e) {
				System.out.println("File not found: " + fileName);
			} finally {
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
	 * @return Parses and stores the CSV file into an array.
	 */
	public static Country[] readCSV(String fileName, Scanner fileScanner) {
		int numRecords = -1;
		while (fileScanner.hasNextLine()) {
			numRecords++;
			fileScanner.nextLine();
		}

		Country[] countries = new Country[numRecords];
		try {
			fileScanner = new Scanner(new FileInputStream(fileName));
			fileScanner.nextLine();
			for (int i = 0; i < numRecords; i++) {
				String line = fileScanner.nextLine();
				String[] arr = line.split(",");
				countries[i] = new Country(arr[0], arr[1], Double.parseDouble(arr[2]),
						Double.parseDouble(arr[3]), Double.parseDouble(arr[4]), Double.parseDouble(arr[5]));
			}
		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + fileName);
		}

		return countries;
	}

	/**
	 * Method to print the array values.
	 *
	 * @param Country[] countries is the array holding all of the data.
	 * @return Prints the array.
	 */
	public static void printCountriesReport(Country[] countries) {
		System.out.println("Name				    Capitol	    GDPPC	    APC		    HappinessIndex");
		System.out.println("--------------------------------------------------------------------------------------------------");

		for (Country country : countries) {
			country.printCountry();
		}
	}

	/**
	 * Sorts countries by name.
	 * 
	 * @param Country[] countries is the array holding all of the data.
	 * @return Returns countries sorted alphabetically.
	 */
	public static void sortByName(Country[] countries) {
		int inner;
		int outer;
		for(outer=1; outer < countries.length; outer++){//outer is picking new number up
			Country temp = countries[outer]; // safe it
			inner = outer-1; // start shifts at left of outer
			while(inner>=0 && countries[inner].getName().compareTo(temp.getName()) > 0) { // until one is smaller
				countries[inner+1] = countries[inner]; // shift item to right
				inner--; // go left one position
			} // end while
			countries[inner+1] = temp; // insert it back item
		} // end for
	} // end insertionSort()

	/**
	 * Sorts countries by happiness index.
	 * 
	 * @param Country[] countries is the array holding all of the data.
	 * @return Returns sorted by happiness index value
	 */
	public static void sortByHappinessIndex(Country[] countries) {
		for(int outer=0; outer<countries.length-1; outer++) {
			int lowest = outer;
			for(int inner=outer+1; inner<countries.length; inner++) {
				if(countries[inner].getHappinessIndex() < countries[lowest].getHappinessIndex()) {
					lowest = inner;
				}
			}
			if(lowest != outer) {
				Country temp = countries[lowest];
				countries[lowest] = countries[outer];
				countries[outer] = temp;
			}
		}
	}

	/**
	 * Calculates the GDP per capita.
	 *
	 * @param Country[] countries is the array holding all of the data.
	 * @return Returns sorted calculated GDP per capita.
	 */
	public static void sortByGDPPC(Country[] countries) {
		for (int outer=0; outer<countries.length-1; outer++) {
			for (int inner=countries.length-1; inner>outer; inner--) {
				if (countries[inner].getGdp() < countries[inner-1].getGdp()) {
					Country temp = countries[inner];
					countries[inner] = countries[inner-1];
					countries[inner-1] = temp;
				}
			}
		}
	}

	/**
	 * Finds and prints countries using sequential search.
	 *
	 * @param Country[] countries is the array holding all of the data. searchName holds the user's desired country.
	 * @return Returns desired country info via sequential search.
	 */
	public static void findAndPrintCountryByNameSeq(Country[] countries, String searchName) {
		int i=0;
		while(i<countries.length) {
			if(countries[i].getName().compareTo(searchName) == 0) {
				break;
			}
			i++;
		}
		if(i == countries.length) {
			System.out.println("Error: country " + searchName+ " not found");
		}
		else {
			countries[i].findPrintCountry();
		}
	} 
	/**
	* Finds and prints countries using binary search.
	*
	* @param Country[] countries is the array holding all of the data. searchName holds the user's desired country.
	* @return Returns desired country info via binary search.
	*/

	public static void findAndPrintCountryByNameBin(Country[] countries, String searchName) {
		int lowerBound = 0;
		int upperBound = countries.length-1;
		int mid;
		boolean checker = false;
		while(lowerBound <= upperBound) {
			mid = (lowerBound + upperBound ) / 2;
			if(countries[mid].getName().compareTo(searchName) == 0) {
				countries[mid].findPrintCountry();
				checker = true;
				break;
			} else if (countries[mid].getName().compareTo(searchName) > 0) {
				upperBound = mid - 1; // it's in lower half
			} else {
				lowerBound = mid + 1; // it's in upper half
			}
		} // end while
		if(checker == false) {
			System.out.println("Error: country " + searchName+ " not found");
		}
	}

	/**
	 * Method is used to print Kendalls Tau calculation.
	 *
	 * @param Country[] countries is the array holding all of the data.
	 * @return The calculated value from comparisons.
	 */
	public static void printKendallsTauMatrix(Country[] countries) {
	/*	double n = 0;
		double a = 0;
		double d = 0;
		double kt = 0;
		double happy[][] = null;
		double GDPPC[][] = null;
		for(int i = 0; i < countries.length; i++)
		{
			happy[i][i] = countries[i].getHappinessIndex();
			GDPPC[i][i] = countries[i].getGDPPC();
		}
		
		kt = (a) - (d) / (n * (n - 1)) / 2;*/
	}
}
