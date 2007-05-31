package com.bretth.osm.conduit.data;

import java.util.Date;


/**
 * A data class representing a single OSM node.
 * 
 * @author Brett Henderson
 */
public class Node extends OsmElement implements Comparable<Node> {
	private Date timestamp;
	private double latitude;
	private double longitude;
	
	
	/**
	 * Creates a new instance.
	 * 
	 * @param id
	 *            The unique identifier.
	 * @param timestamp
	 *            The last updated timestamp.
	 * @param latitude
	 *            The geographic latitude.
	 * @param longitude
	 *            The geographic longitude.
	 */
	public Node(long id, Date timestamp, double latitude, double longitude) {
		super(id);
		
		this.timestamp = timestamp;
		this.latitude = latitude;
		this.longitude = longitude;
	}


	/**
	 * Compares this node to the specified node. The node comparison is based on
	 * a comparison of id, latitude, longitude and tags in that order.
	 * 
	 * @param comparisonNode
	 *            The node to compare to.
	 * @return 0 if equal, <0 if considered "smaller", and >0 if considered
	 *         "bigger".
	 */
	public int compareTo(Node comparisonNode) {
		if (this.getId() < comparisonNode.getId()) {
			return -1;
		}
		
		if (this.getId() > comparisonNode.getId()) {
			return 1;
		}
		
		if (this.latitude < comparisonNode.latitude) {
			return -1;
		}
		
		if (this.latitude > comparisonNode.latitude) {
			return 1;
		}
		
		if (this.timestamp == null && comparisonNode.timestamp != null) {
			return -1;
		}
		if (this.timestamp != null && comparisonNode.timestamp == null) {
			return 1;
		}
		if (this.timestamp != null && comparisonNode.timestamp != null) {
			int result;
			
			result = this.timestamp.compareTo(comparisonNode.timestamp);
			
			if (result != 0) {
				return result;
			}
		}
		
		return compareTags(comparisonNode.getTagList());
	}
	
	
	/**
	 * @return The timestamp. 
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	
	
	/**
	 * @return The latitude. 
	 */
	public double getLatitude() {
		return latitude;
	}
	
	
	/**
	 * @return The longitude. 
	 */
	public double getLongitude() {
		return longitude;
	}
}