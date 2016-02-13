
package com.inteliment.kiran.scenario2.network;

public class Place {
	
	public static final String TAG_ID = "id";
	public static final String TAG_NAME = "name";
	public static final String TAG_LOCATION = "location";
	public static final String TAG_FROM_CENTRAL = "fromcentral";
	public static final String TAG_CAR = "car";
	public static final String TAG_TRAIN = "train";
	public static final String TAG_LATITUDE = "latitude";
	public static final String TAG_LONGITUDE = "longitude";

    private Integer id;
    private String name;
    private String fromcentral;
    private Location location;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The fromcentral
     */
    public String getFromcentral() {
        return fromcentral;
    }

    /**
     * 
     * @param fromcentral
     *     The fromcentral
     */
    public void setFromcentral(String fromcentral) {
        this.fromcentral = fromcentral;
    }

    /**
     * 
     * @return
     *     The location
     */
    public Location getLocation() {
        return location;
    }

    /**
     * 
     * @param location
     *     The location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
	 * We override toString as ArrayAdapter uses it for showing data on Spinner
	 */
    @Override
    public String toString() {
    	return getName();
    }
}
