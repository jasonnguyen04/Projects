/**
 * COP 3530: Project 3 – Linked Lists
 * 
 * Class made to set and get and print country values.
 *
 * @author Jason Nguyen
 * @version 10/26/23
 */
public class Country {
	private String name;
	private String capital;
	private double population;
	private double gdp;
	private double area;
	private double happinessIndex;

	// Constructor
	public Country(String name, String capital, double population, double gdp, double area, double happinessIndex) {
		this.name = name;
		this.capital = capital;
		this.population = population;
		this.gdp = gdp;
		this.area = area;
		this.happinessIndex = happinessIndex;
	}

	// Getter and setter methods for each field
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public double getPopulation() {
		return population;
	}
	
	public void setPopulation(double population) {
		this.population = population;
	}

	public double getGdp() {
		return gdp;
	}

	public void setGdp(double gdp) {
		this.gdp = gdp;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public double getHappinessIndex() {
		return happinessIndex;
	}

	public void setHappinessIndex(double happinessIndex) {
		this.happinessIndex = happinessIndex;
	}
	public double getGDPPC() {
		double GDPPC = this.gdp/this.population;
		return GDPPC;
	}

	/**
	* Method called to print all country reports.
	*
	* @param 
	* @return Returns a list of all country reports.
	*/
	public void printCountry() {
		double GDPPC = this.gdp/this.population;
		double APC = this.area/this.population;
		double happyIndex = this.happinessIndex;
		System.out.printf("%-35s %-15s %-15.3f %-15.6f %-15.3f\n", this.name, this.capital, GDPPC, APC, happyIndex);
	}
	
	/**
	* Method called to print a specific country report.
	* 
	* @param 
	* @return Returns a formatted single country report.
	*/
	public void findPrintCountry() {
		double GDPPC = this.gdp/this.population;
		double APC = this.area/this.population;
		double happyIndex = this.happinessIndex;
		System.out.printf("Name:		" + this.name + "\nCapital		" + this.capital + "\nGDPPC:		" 
		+ GDPPC + "\nAPC:		" + APC + "\nHappiness	" + happyIndex + "\n\n");
	}
}