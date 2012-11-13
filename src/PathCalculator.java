import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

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
		// TODO Auto-generated method stub

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
