import java.util.HashSet;
import java.util.Set;

//Implements a City
public class City {

	private String name;
	private int x;
	private int y;
	private Set<City> connectedCities;

	public City(String name, int x, int y) {
		this.setName(name);
		this.setX(x);
		this.setY(y);
		connectedCities=new HashSet<City>();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void add(City city) {
		connectedCities.add(city);
	}

	public Set<City> getConnectedCities() {
		return connectedCities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		String output = "City: " + name + ", (" + x + "," + y + ")";
		return output;
	}
}
