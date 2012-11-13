import java.io.File;
import java.util.List;


public interface PathSearchEngine {
	
	//Initialize the map
	public void initMap(String source);
	
	//Implements an algorithm
	public void findPath(City origin, City destiny);
	
	//Print the path
	public void printPath();
	
}
