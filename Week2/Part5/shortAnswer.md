## Set 10

The source code for the AbstractGrid class is in Appendix D.

1. Where is the isValid method specified? Which classes provide an implementation of this method?
2. Which AbstractGrid methods call the isValid method? Why don't the other methods need to call it?
3. Which methods of the Grid interface are called in the getNeighbors method? Which classes provide implementations of these methods?
4. Why must the get method, which returns an object of type E, be used in the getEmptyAdjacentLocations method when this method returns locations, not objects of type E?
5. What would be the effect of replacing the constant Location.HALF_RIGHT with Location.RIGHT in the two places where it occurs in the getValidAdjacentLocations method?

## Set 11

The source code for the BoundedGrid class is in Appendix D.

6. What ensures that a grid has at least one valid location?
7. How is the number of columns in the grid determined by the getNumCols method? What assumption about the grid makes this possible?
8. What are the requirements for a Location to be valid in a BoundedGrid? In the next four questions, let r = number of rows, c = number of columns, and n = number of occupied locations.
9. What type is returned by the getOccupiedLocations method? What is the time complexity (Big-Oh) for this method?
10. What type is returned by the get method? What parameter is needed? What is the time complexity (Big-Oh) for this method?
11. What conditions may cause an exception to be thrown by the put method? What is the time complexity (Big-Oh) for this method?
12. What type is returned by the remove method? What happens when an attempt is made to remove an item from an empty location? What is the time complexity (Big-Oh) for this method?
13. Based on the answers to questions 4, 5, 6, and 7, would you consider this an efficient implementation? Justify your answer.

## Set 12 

The source code for the UnboundedGrid class is in Appendix D.

14. Which method must the Location class implement so that an instance of HashMap can be used for the map? What would be required of the Location class if a TreeMap were used instead? Does Location satisfy these requirements?
15. Why are the checks for null included in the get, put, and remove methods? Why are no such checks included in the corresponding methods for the BoundedGrid?
16. What is the average time complexity (Big-Oh) for the three methods: get, put, and remove? What would it be if a TreeMap were used instead of a HashMap?
17. How would the behavior of this class differ, aside from time complexity, if a TreeMap were used instead of a HashMap?
18. Could a map implementation be used for a bounded grid? What advantage, if any, would the two-dimensional array implementation that is used by the BoundedGrid class have over a map implementation?