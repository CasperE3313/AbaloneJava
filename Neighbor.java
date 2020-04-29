package abalone;

public class Neighbor {

    Board board;
    Edge edge;

    public static final String[] drc = { "UL", "UR", "L", "R", "DR", "DL" };

    // ----CONSTRUCTOR---------------

    /**
     * The constructor of Neighbor.
     */
    public Neighbor() {
        edge = new Edge();
        board = new Board();
    }

    // ----METHODS-------------------

    /**
     * This method gets the array with all the directions.
     * 
     * @return array with directions
     */
    public String[] getDrc() {
        return drc;
    }

    /**
     * This method gets the opposite direction of the direction give.
     * 
     * @requires drc !=null
     * @param drc String
     * @return String of opposite direction
     */
    public String getOppositeDirection(String drc) {
        switch (drc) {
            case "UL":
                return "DR";
            case "UR":
                return "DL";
            case "L":
                return "R";
            case "R":
                return "L";
            case "DL":
                return "UR";
            case "DR":
                return "UL";
            default:
                return null;
        }
    }

    /**
     * This method checks whether a coordinate has empty neighbors.
     * 
     * @requires crd != null
     * @param crd String
     * @return true if the given coordinate has empty neighbors
     */
    public boolean hasEmptyNeighbors(String crd) {
        String edges = edge.getEdgeDirections(crd);
        String[] arr = edges.split(" ");
        for (String e : arr) {
            if (!e.equals("DL")) {
                if (board.getField(this.getDL(crd)) == Marble.o) {
                    return true;
                }
            }
            if (!e.equals("DR")) {
                if (board.getField(this.getDR(crd)) == Marble.o) {
                    return true;
                }
            }
            if (!e.equals("L")) {
                if (board.getField(this.getL(crd)) == Marble.o) {
                    return true;
                }
            }
            if (!e.equals("R")) {
                if (board.getField(this.getR(crd)) == Marble.o) {
                    return true;
                }
            }
            if (!e.equals("UL")) {
                if (board.getField(this.getUL(crd)) == Marble.o) {
                    return true;
                }
            }
            if (!e.equals("UR")) {
                if (board.getField(this.getUR(crd)) == Marble.o) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * This method gets a string containing all the coordinates of empty
     * neighbors.
     * 
     * @requires crd != null
     * @param crd String
     * @return string of coordinates of empty neighbors
     */
    public String getEmptyNeighbors(String crd) {
        String returns = "";
        String edges = edge.getEdgeDirections(crd);
        String[] arr = edges.split(" ");
        for (String e : arr) {
            if (!e.equals("DL")) {
                if (board.getField(this.getDL(crd)) == Marble.o) {
                    returns = returns + crd + " ";
                }
            }
            if (!e.equals("DR")) {
                if (board.getField(this.getDR(crd)) == Marble.o) {
                    returns = returns + crd + " ";
                }
            }
            if (!e.equals("L")) {
                if (board.getField(this.getL(crd)) == Marble.o) {
                    returns = returns + crd + " ";
                }
            }
            if (!e.equals("R")) {
                if (board.getField(this.getR(crd)) == Marble.o) {
                    returns = returns + crd + " ";
                }
            }
            if (!e.equals("UL")) {
                if (board.getField(this.getUL(crd)) == Marble.o) {
                    returns = returns + crd + " ";
                }
            }
            if (!e.equals("UR")) {
                if (board.getField(this.getUR(crd)) == Marble.o) {
                    returns = returns + crd + " ";
                }
            }
        }
        if (returns.equals(null)) {
            returns = "default";
        }
        return returns;
    }

    /**
     * This method checks whether two coordinates are neighbors of each other.
     * 
     * @requires crd1 != null && crd2 != null
     * @param crd1 String
     * @param crd2 String
     * @return true if the two given coordinates are neighbors
     */
    public boolean checkIfNeighbor(String crd1, String crd2) {
        boolean neighbor = false;
        while (!neighbor) {
            if (!this.edge.isEdgeField(crd1, "UL")) {
                if (this.getUL(crd1).equals(crd2)) {
                    neighbor = true;
                    break;
                }
            }
            if (!this.edge.isEdgeField(crd1, "DR")) {
                if (this.getDR(crd1).equals(crd2)) {
                    neighbor = true;
                    break;
                }
            }
            if (!this.edge.isEdgeField(crd1, "UR")) {
                if (this.getUR(crd1).equals(crd2)) {
                    neighbor = true;
                    break;
                }
            }
            if (!this.edge.isEdgeField(crd1, "DL")) {
                if (this.getDL(crd1).equals(crd2)) {
                    neighbor = true;
                    break;
                }
            }
            if (!this.edge.isEdgeField(crd1, "R")) {
                if (this.getR(crd1).equals(crd2)) {
                    neighbor = true;
                    break;
                }
            }
            if (!this.edge.isEdgeField(crd1, "L")) {
                if (this.getL(crd1).equals(crd2)) {
                    neighbor = true;
                    break;
                }
            }
            break;
        }
        return neighbor;
    }

    /**
     * Checks if the two given coordinates are shared neighbors, i.e. there is one
     * coordinate in between the two given coordinates in a line
     * 
     * @requires crd1 != null && crd2 != null
     * @param crd1 String
     * @param crd2 String
     * @return true when the two coordinates have a shared neighbor in a line
     */
    public boolean checkIfSharedNeighbor(String crd1, String crd2) {
        boolean oneshared = false;
        if (!this.edge.isEdgeField(crd1, "UL") && !this.edge.isEdgeField(crd2, "DR")) {
            if (this.getUL(crd1).equals(this.getDR(crd2))) {
                oneshared = true;
            }
        }
        if (!this.edge.isEdgeField(crd2, "UL") && !this.edge.isEdgeField(crd1, "DR")) {
            if (this.getUL(crd2).equals(this.getDR(crd1))) {
                oneshared = true;
            }
        }
        if (!this.edge.isEdgeField(crd1, "UR") && !this.edge.isEdgeField(crd2, "DL")) {
            if (this.getUR(crd1).equals(this.getDL(crd2))) {
                oneshared = true;
            }
        }
        if (!this.edge.isEdgeField(crd2, "UR") && !this.edge.isEdgeField(crd1, "DL")) {
            if (this.getUR(crd2).equals(this.getDL(crd1))) {
                oneshared = true;
            }
        }
        if (!this.edge.isEdgeField(crd1, "R") && !this.edge.isEdgeField(crd2, "L")) {
            if (this.getR(crd1).equals(this.getL(crd2))) {
                oneshared = true;
            }
        }
        if (!this.edge.isEdgeField(crd2, "R") && !this.edge.isEdgeField(crd1, "L")) {
            if (this.getR(crd2).equals(this.getL(crd1))) {
                oneshared = true;
            }
        }
        return oneshared;
    }

    /**
     * This method gets a string of the direction the shared neighbor is in, from
     * the perspective of coordinate crd1.
     * 
     * @requires crd1 != null && crd2 != null
     * @param crd1 String
     * @param crd2 String
     * @return string with the direction from crd1 where the shared neighbor is
     */
    public String getSharedNeighborDirection(String crd1, String crd2) {
        this.checkIfSharedNeighbor(crd1, crd2);
        String direction = null;
        if (!this.edge.isEdgeField(crd1, "UL") && !this.edge.isEdgeField(crd2, "DR")) {
            if (this.getUL(crd1).equals(this.getDR(crd2))) {
                direction = "UL";
            }
        }
        if (!this.edge.isEdgeField(crd2, "UL") && !this.edge.isEdgeField(crd1, "DR")) {
            if (this.getUL(crd2).equals(this.getDR(crd1))) {
                direction = "DR";
            }
        }
        if (!this.edge.isEdgeField(crd1, "UR") && !this.edge.isEdgeField(crd2, "DL")) {
            if (this.getUR(crd1).equals(this.getDL(crd2))) {
                direction = "UR";
            }
        }
        if (!this.edge.isEdgeField(crd2, "UR") && !this.edge.isEdgeField(crd1, "DL")) {
            if (this.getUR(crd2).equals(this.getDL(crd1))) {
                direction = "DL";
            }
        }
        if (!this.edge.isEdgeField(crd1, "R") && !this.edge.isEdgeField(crd2, "L")) {
            if (this.getR(crd1).equals(this.getL(crd2))) {
                direction = "R";
            }
        }
        if (!this.edge.isEdgeField(crd2, "R") && !this.edge.isEdgeField(crd1, "L")) {
            if (this.getR(crd2).equals(this.getL(crd1))) {
                direction = "L";
            }
        }
        return direction;
    }

    /**
     * Returns the shared neighbor coordinate, i.e. the coordinate between two given
     * coordinate in one line.
     * 
     * @requires crd1 != null && crd2 != null
     * @param crd1 String
     * @param crd2 String
     * @return the shared neighbor coordinate as string
     */
    public String getSharedNeighbor(String crd1, String crd2) {
        String drc = this.getSharedNeighborDirection(crd1, crd2);
        switch (drc) {
            case "UL":
                return this.getUL(crd1);
            case "UR":
                return this.getUR(crd1);
            case "L":
                return this.getL(crd1);
            case "R":
                return this.getR(crd1);
            case "DL":
                return this.getDL(crd1);
            case "DR":
                return this.getDR(crd1);
            default:
                return null;
        }
    }

    /**
     * This method gets a coordinate as string of the neighbor of the given
     * coordinate, in the given direction.
     * 
     * @requires crd != null && direction != null
     * @param crd String
     * @param direction String
     * @return coordinate of neighbor as string
     */
    public String getNeighbor(String crd, String direction) {
        if (direction.equals("UL")) {
            return this.getUL(crd);
        } else if (direction.equals("UR")) {
            return this.getUR(crd);
        } else if (direction.equals("R")) {
            return this.getR(crd);
        } else if (direction.equals("L")) {
            return this.getL(crd);
        } else if (direction.equals("DL")) {
            return this.getDL(crd);
        } else if (direction.equals("DR")) {
            return this.getDR(crd);
        }
        return null;
    }

    /**
     * This method gets the direction from crd1 to crd2 as string.
     * 
     * @requires crd1 != null && crd2 != null
     * @param crd1 String
     * @param crd2 String
     * @return direction as string
     */
    public String getDirection(String crd1, String crd2) {
        if (this.checkIfNeighbor(crd1, crd2)) {
            int first1 = board.getFirst(crd1);
            int second1 = board.getSecond(crd1);
            int first2 = board.getFirst(crd2);
            int second2 = board.getSecond(crd2);
            if (first1 == first2) {
                if (second1 > second2) {
                    return "L";
                } else if (second1 < second2) {
                    return "R";
                }
            }
            if (first1 > first2) {
                if (second1 > second2) {
                    return "UL";
                } else if (second1 < second2) {
                    return "UR";
                } else if (second1 == second2 && first1 < 5) {
                    return "UR";
                } else if (second1 == second2 && first1 > 4) {
                    return "UL";
                }
            }
            if (first1 < first2) {
                if (second1 > second2) {
                    return "DL";
                } else if (second1 < second2) {
                    return "DR";
                } else if (second1 == second2 && first1 < 4) {
                    return "DL";
                } else if (second1 == second2 && first1 > 3) {
                    return "DR";
                }
            }
        }
        return null;
    }

    /**
     * Return the upper-left coordinate located from coordinate in String format.
     * 
     * @requires coordinate != null
     * @param coordinate String
     * @return String of the coordinate Up-Left
     */
    public String getUL(String coordinate) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        int newfirst = first - 1;
        int newsecond;
        if (first > 4) {
            newsecond = second;
        } else {
            newsecond = second - 1;
        }
        String newcoordinate = board.integersToStringCoordinate(newfirst, newsecond);
        return newcoordinate;
    }

    /**
     * Return the upper-right coordinate located from coordinate in String format.
     * 
     * @requires coordinate != null
     * @param coordinate String
     * @return String of the coordinate Up-Right
     */
    public String getUR(String coordinate) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        int newfirst = first - 1;
        int newsecond;
        if (first < 5) {
            newsecond = second;
        } else {
            newsecond = second + 1;
        }
        String newcoordinate = board.integersToStringCoordinate(newfirst, newsecond);
        return newcoordinate;
    }

    /**
     * Return the right coordinate located from coordinate in String format.
     * 
     * @requires coordinate != null
     * @param coordinate String
     * @return String of the coordinate Right
     */
    public String getR(String coordinate) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        int newfirst = first;
        int newsecond = second + 1;
        String newcoordinate = board.integersToStringCoordinate(newfirst, newsecond);
        return newcoordinate;
    }

    /**
     * Return the left coordinate located from coordinate in String format.
     * 
     * @requires coordinate != null
     * @param coordinate String
     * @return String of the coordinate Left
     */
    public String getL(String coordinate) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        int newfirst = first;
        int newsecond = second - 1;
        String newcoordinate = board.integersToStringCoordinate(newfirst, newsecond);
        return newcoordinate;
    }

    /**
     * Return the down-left coordinate located from coordinate in String format.
     * 
     * @requires coordinate != null
     * @param coordinate String
     * @return String of the coordinate Down-Left
     */
    public String getDL(String coordinate) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        int newfirst = first + 1;
        int newsecond;
        if (first < 4) {
            newsecond = second;
        } else {
            newsecond = second - 1;
        }
        String newcoordinate = board.integersToStringCoordinate(newfirst, newsecond);
        return newcoordinate;
    }

    /**
     * Return the down right coordinate located from coordinate in String format.
     * 
     * @requires coordinate != null
     * @param coordinate String
     * @return String of the coordinate Down-Right
     */
    public String getDR(String coordinate) {
        int first = board.getFirst(coordinate);
        int second = board.getSecond(coordinate);
        int newfirst = first + 1;
        int newsecond;
        if (first > 3) {
            newsecond = second;
        } else {
            newsecond = second + 1;
        }
        String newcoordinate = board.integersToStringCoordinate(newfirst, newsecond);
        return newcoordinate;
    }

    /**
     * This method checks whether a given string is a direction.
     * 
     * @requires direction != null
     * @param direction String
     * @return true if 'direction' is a direction
     */
    public boolean isDirection(String direction) {
        switch (direction) {
            case "UL":
                return true;
            case "UR":
                return true;
            case "L":
                return true;
            case "R":
                return true;
            case "DL":
                return true;
            case "DR":
                return true;
            default:
                return false;
        }
    }
}
