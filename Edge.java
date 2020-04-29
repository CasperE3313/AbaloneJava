package abalone;

public class Edge {

    public String[] edgeDL = { "e;1", "d;1", "c;1", "b;1", "a;1", "a;2", "a;3", "a;4", "a;5" };
    public String[] edgeDR = { "e;9", "d;8", "c;7", "b;6", "a;1", "a;2", "a;3", "a;4", "a;5" };
    public String[] edgeL = { "e;1", "d;1", "c;1", "b;1", "a;1", "i;5", "h;4", "g;3", "f;2" };
    public String[] edgeR = { "e;9", "d;8", "c;7", "b;6", "a;5", "i;9", "h;9", "g;9", "f;9" };
    public String[] edgeUL = { "i;5", "h;4", "g;3", "f;2", "e;1", "i;6", "i;7", "i;8", "i;9" };
    public String[] edgeUR = { "e;9", "h;9", "g;9", "f;9", "i;6", "i;7", "i;8", "i;9", "i;5" };
    public String[] edges = { "a;1", "a;2", "a;3", "a;4", "a;5", "i;5", "i;6", "i;7", "i;8", "i;9", "e;1", "d;1", "c;1",
        "b;1", "e;9", "h;9", "g;9", "f;9", "f;2", "g;3", "h;4", "d,8", "c;7", "b;7" };

    /**
     * This method checks if a given coordinate is an edge field, given a certain
     * direction.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @requires crd != null, direction != null
     * @param crd String
     * @param direction String
     * @return true is the given coordinate in a certain direction is an edge
     */
    public boolean isEdgeField(String crd, String direction) {
        switch (direction) {
            case "UL":
                if (this.containsValue(crd, edgeUL)) {
                    return true;
                } else {
                    return false;
                }
            case "UR":
                if (this.containsValue(crd, edgeUR)) {
                    return true;
                } else {
                    return false;
                }
            case "R":
                if (this.containsValue(crd, edgeR)) {
                    return true;
                } else {
                    return false;
                }
            case "L":
                if (this.containsValue(crd, edgeL)) {
                    return true;
                } else {
                    return false;
                }
            case "DR":
                if (this.containsValue(crd, edgeDR)) {
                    return true;
                } else {
                    return false;
                }
            case "DL":
                if (this.containsValue(crd, edgeDL)) {
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * This method checks whether a certain coordinate is an edge field.
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @requires crd != null
     * @param crd String
     * @return true when the given coordinate is an edge field
     */
    public boolean isEdgeField(String crd) {
        if (this.isEdgeField(crd, "UL") || this.isEdgeField(crd, "UR") || this.isEdgeField(crd, "L")
                || this.isEdgeField(crd, "R") || this.isEdgeField(crd, "DL") || this.isEdgeField(crd, "DR")) {
            return true;
        }
        return false;
    }

    /**
     * This method gives a String with directions. If the given coordinate is an
     * edge field, it will return a string containing two or three directions If the
     * given coordinate is not an edge field, it will return a string saying
     * "default"
     * 
     * @requires a string character from a - i followed by ";" followed by an
     *           integer from 1-9.
     * @requires crd != null
     * @param crd String
     * @return a string with directions or a string "default"
     */
    public String getEdgeDirections(String crd) {
        String edges = "";
        if (this.isEdgeField(crd, "UL")) {
            edges = edges + "UL ";
        }
        if (this.isEdgeField(crd, "UR")) {
            edges = edges + "UR ";
        }
        if (this.isEdgeField(crd, "L")) {
            edges = edges + "L ";
        }
        if (this.isEdgeField(crd, "R")) {
            edges = edges + "R ";
        }
        if (this.isEdgeField(crd, "DL")) {
            edges = edges + "DL ";
        }
        if (this.isEdgeField(crd, "DR")) {
            edges = edges + "DR ";
        }
        if (edges.equals(null)) {
            edges = "default";
        }
        return edges;
    }

    /**
     * This method checks whether an array of strings contains a specific string.
     * 
     * @requires value != null, arr != null
     * @param value String
     * @param arr String[]
     * @return true if the array contains the string
     */
    public boolean containsValue(String value, String[] arr) {
        for (int i = 0; i < arr.length; i++) {
            String j = arr[i];
            if (j.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
