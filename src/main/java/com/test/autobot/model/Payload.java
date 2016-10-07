package com.test.autobot.model;

import org.jsondoc.core.annotation.ApiObjectField;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Payload {
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Double getCoordinatesLat() {
		return coordinatesLat;
	}
	public void setCoordinatesLat(Double coordinatesLat) {
		this.coordinatesLat = coordinatesLat;
	}
	public Double getCoordinatesLong() {
		return coordinatesLong;
	}
	public void setCoordinatesLong(Double coordinatesLong) {
		this.coordinatesLong = coordinatesLong;
	}
		
	private String url;
	@JsonProperty("coordinates.lat")
	@ApiObjectField(name = "coordinates.lat")
	private Double coordinatesLat;
	@JsonProperty("coordinates.long")
	@ApiObjectField(name = "coordinates.long")
	private Double coordinatesLong;
}
