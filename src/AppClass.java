
public class AppClass {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String roadMapTextFile="road_map.txt";
				
		PathCalculator pathCalculator=new PathCalculator();
		
		pathCalculator.initMap(roadMapTextFile);
		
		
	}

}
