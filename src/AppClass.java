//Application class
public class AppClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String roadMapTextFile = "road_map.txt";

		PathCalculator pathCalculator = new PathCalculator();

		// Initialize the roadMap
		pathCalculator.initMap(roadMapTextFile);

		// Find path from Roma to Constantinopla
		pathCalculator.findPath("Roma", "Constantinopla");

		pathCalculator.printPath();

		// Find path from Numancia to Cartago
		pathCalculator.findPath("Numancia", "Cartago");

		pathCalculator.printPath();
		
		// Find path from Cartago to Kursk
		pathCalculator.findPath("Cartago", "Kursk");

		pathCalculator.printPath();
	}

}
