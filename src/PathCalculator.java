import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

//Implement the interface PathSearchEngine with the A* algorithm and reading from a text file
public class PathCalculator implements PathSearchEngine {

	private HashMap<String, City> roadMap = new HashMap<String, City>();
	private String path;
	Connection conn;
	int visitedTimesPath = 1;

	public PathCalculator() {
		connect();
	}

	// Create the DB connection
	private void connect() {
		try {
			conn = DriverManager.getConnection(
					"jdbc:mysql://luisrecio.es/qpbcdjoi_biicode_maps",
					"qpbcdjoi_biicode", "root1");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Add path to DB or update the existing path with more visits
	private void addPathToDB(String origin, String destiny, String path)
			throws SQLException {
		Statement stmt = conn.createStatement();
		int cont = 0;
		try {
			ResultSet rs = stmt
					.executeQuery("SELECT count(*) as numberOfRecords FROM routes where origin ='"
							+ origin + "' and destiny ='" + destiny + "'");
			rs.next();
			if (rs.getString("numberOfRecords").equalsIgnoreCase("1")) {
				rs = stmt.executeQuery("SELECT  * FROM routes where origin ='"
						+ origin + "' and destiny ='" + destiny + "'");
				rs.next();
				cont = Integer.parseInt(rs.getString("searchedTimes"));
				stmt.executeUpdate("update routes set searchedTimes='"
						+ (++cont) + "' where origin ='" + origin
						+ "' and destiny ='" + destiny + "'");

				visitedTimesPath = cont;
			} else {
				stmt.executeUpdate("insert into routes values ('" + origin
						+ "','" + destiny + "','" + path + "','1')");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (Throwable ignore) {
			}
		}
	}

	// Add the visited city or update it with more visits
	private void updateVisitedCities(String city) throws SQLException {
		Statement stmt = conn.createStatement();
		int cont = 0;
		try {
			ResultSet rs = stmt
					.executeQuery("SELECT count(*) as numberOfRecords FROM visitedCities where visitedCity='"
							+ city + "'");
			rs.next();
			if (rs.getString("numberOfRecords").equalsIgnoreCase("1")) {
				rs = stmt
						.executeQuery("SELECT  * FROM visitedCities where visitedCity='"
								+ city + "'");
				rs.next();
				cont = Integer.parseInt(rs.getString("visitedTimes"));
				stmt.executeUpdate("update visitedCities set visitedTimes='"
						+ (++cont) + "' where visitedCity ='" + city + "'");
			} else
				stmt.executeUpdate("insert into visitedCities values ('" + city
						+ "','1')");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (Throwable ignore) {
			}
		}
	}

	// obtain the top 5 list of visited cities
	private String getTopFive() throws SQLException {
		Statement stmt = conn.createStatement();
		String output = "Top 5: ";
		try {
			ResultSet rs = stmt.executeQuery("SELECT * FROM visitedCities order by visitedTimes desc limit 5");
			while (rs.next()) {
				output += rs.getString("visitedCity") + "("
						+ rs.getString("visitedTimes") + "), ";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (Throwable ignore) {
			}
		}
		return output;
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
		List<City> closedSet = new ArrayList<City>();
		HashMap<City, Double> openSet = new HashMap<City, Double>();
		HashMap<City, City> cameFrom = new HashMap<City, City>();
		HashMap<City, Double> gScore = new HashMap<City, Double>();
		HashMap<City, Double> fScore = new HashMap<City, Double>();

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

			Iterator<Entry<City, Double>> it = openSet.entrySet().iterator();

			while (it.hasNext()) {
				Entry<City, Double> e = it.next();
				if (((double) e.getValue()) == min) {
					current = (City) e.getKey();
					break;
				}
			}

			// Checking if it is the goal city
			if (current.equals(goal)) {
				reconstructPath(cameFrom, goal);
				try {
					addPathToDB(origin, destiny, path);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			// Removing from openSet
			openSet.remove(current);
			closedSet.add(current);

			// Evaluating connected cities
			for (City city : current.getConnectedCities()) {
				if (closedSet.contains(city))
					continue;

				double tentativeGScore = gScore.get(current)
						+ calculateDistance(current, city);

				if (!openSet.containsKey(city)
						|| tentativeGScore <= gScore.get(city)) {
					cameFrom.put(city, current);
					try {
						updateVisitedCities(city.getName());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					gScore.put(city, tentativeGScore);
					fScore.put(city,
							gScore.get(city)
									+ heuristicCostEstimate(city, goal));

					if (!openSet.containsKey(city))
						openSet.put(city, fScore.get(city));
				}
			}
		}
	}

	// rebuilding path
	private String reconstructPath(HashMap<City, City> cameFrom, City goal) {
		if (cameFrom.containsKey(goal)) {
			path = reconstructPath(cameFrom, cameFrom.get(goal));
			path += ">" + goal.getName();
			return (path);
		} else {
			return goal.getName();
		}

	}

	// calculate the distance
	private double calculateDistance(City start, City end) {
		return Math.sqrt(Math.pow((start.getX() - end.getX()), 2)
				+ Math.pow((start.getY() - end.getY()), 2));
	}

	//HeuristicCostEstimation
	private double heuristicCostEstimate(City current, City goal) {
		return calculateDistance(current, goal);
	}

	@Override
	public void printPath() {
		try {
			System.out.println("\n\n"+path + " " + visitedTimesPath
					+ " times visited - \n" + getTopFive());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Add toad to cities
	private void addRoadToCities(String line) {
		// Processing the text line
		String[] cities = line.split(",");

		// Setting the road
		roadMap.get(cities[0]).add(roadMap.get(cities[1]));
		roadMap.get(cities[1]).add(roadMap.get(cities[0]));
	}

	// Add a city from a text line got from the file
	private void addCityFromText(String line) {
		// Processing the text line
		String[] cities = line.split(",");

		City newCity = new City(cities[0], Integer.parseInt(cities[1]),
				Integer.parseInt(cities[2]));

		roadMap.put(cities[0], newCity);
	}
}
