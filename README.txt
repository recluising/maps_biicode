MAPS - BIICODE

It is an application that provides an interface to calculate the best path between two cities.

-->Implementation<--

There is an interface called PathSearchEngine that tries to provide a common way for this kind of programs (path searchers).

The class PathCalculator implements PathSearchEngine setting concret algorithms for the methods from the interface.

The class City implements the data of a city allocated in the map.

The cities are loaded in the application by processing a text file with the x,y coordenates and the direct roads between them.

The used algorithm is A*.



--Luis Recio Morán
