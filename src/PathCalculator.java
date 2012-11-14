import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//Implement the interface PathSearchEngine with the A* algorithm and reading from a text file
public class PathCalculator implements PathSearchEngine {

	private HashMap<String, City> roadMap = new HashMap<String, City>();
	private String path;

	public PathCalculator() {
	}

	@Override
	public void initMap(String source) {
		boolean processingCities = false;
		boolean processingRoads = false;
		File file;
		FileReader fr = null;
		BufferedReader br = null;
		try {
			// Opening the file
			file = new File(source);
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			// Reading the file
			String line;
			while ((line = br.readLine()) != null) {
				switch (line) {
				// Check if processing Cities or Roads
				case "Cities":
					processingCities = true;
					processingRoads = false;
					break;

				case "Roads":
					processingCities = false;
					processingRoads = true;
					break;
				// Processing line
				default:
					if (processingCities)
						addCityFromText(line);
					else if (processingRoads)
						addRoadToCities(line);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Closing the file
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	@Override
	public void findPath(String origin, String destiny) {

		// Initializing maps
		HashMap<City, Double> closedSet = new HashMap();
		HashMap<City, Double> openSet = new HashMap();
		HashMap<City, Double> cameFrom = new HashMap();
		HashMap<City, Double> gScore = new HashMap();
		HashMap<City, Double> fScore = new HashMap();

		// Feching cities
		City start = roadMap.get(origin);
		City goal = roadMap.get(destiny);

		// Creating firsts values for maps
		gScore.put(start, Double.parseDouble("0"));
		fScore.put(start, gScore.get(start)
				+ heuristicCostEstimate(start, goal));
		openSet.put(start, fScore.get(start));

		// A* Running!
		while (!openSet.isEmpty()) {

			// Finding the min fScore City
			City current = null;
			Double min = Collections.min(openSet.values());

			Iterator it = openSet.entrySet().iterator();
			
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				if (((int) e.getValue()) == min) {
					current = (City) e.getKey();
					break;
				}
			}
			
			//Checking if it is the goal city
			if (current.equals(goal))
				reconstructPath(cameFrom, goal);

			//Removing from openSet
			openSet.remove(current);
			
			//EVALUATION OF CONNECTED CITIES
		}
	}

	private void reconstructPath(HashMap<City, Double> cameFrom, City goal) {
		// TODO Auto-generated method stub
		
	}

	private double calculateDistance(City start, City end) {
		return Math.sqrt(Math.pow((start.getX() - end.getX()), 2)
				+ Math.pow((start.getY() - end.getY()), 2));
	}

	private double heuristicCostEstimate(City start, City end) {
		// Heuristic function to be created
		return 0;
	}

	@Override
	public void printPath() {
		// TODO Auto-generated method stub

	}

	private void addRoadToCities(String line) {
		// Processing the text line
		String[] cities = line.split(",");

		// Setting the road
		roadMap.get(cities[0]).add(roadMap.get(cities[1]));
		roadMap.get(cities[1]).add(roadMap.get(cities[0]));
	}

	private void addCityFromText(String line) {
		// Processing the text line
		String[] cities = line.split(",");

		City newCity = new City(cities[0], Integer.parseInt(cities[1]),
				Integer.parseInt(cities[2]));

		roadMap.put(cities[0], newCity);
	}
}
