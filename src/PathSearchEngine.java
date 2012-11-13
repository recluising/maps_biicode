//Provide an interface to implement a path searcher between two elements
public interface PathSearchEngine {
	
	//Initialize the map
	public void initMap(String source);
	
	//Implements an algorithm to find a path between two cities
	public void findPath(String origin, String destiny);
	
	//Print the path
	public void printPath();
	
}
