package com.ubiquisoft.evaluation.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;

	private static final int NUM_CAR_TIRES = 4;

	private List<Part> parts;

	public Map<PartType, Integer> getMissingPartsMap() {
		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car only missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */

		//Build map of available parts
		Map<PartType, Integer> availableParts = new EnumMap<PartType, Integer>(PartType.class);
		int tireCount = 0;
		if(!parts.isEmpty()) {
			for (Part part : parts) {
				if (part.getType().equals(PartType.TIRE)) {
					tireCount++;
					availableParts.put(part.getType(), tireCount);
				} else { //Assume only 1 of every other type per comments above
					availableParts.put(part.getType(), 1);
				}
			}

			//Derive missing parts from available parts list
			Map<PartType, Integer> missingParts = new EnumMap<PartType, Integer>(PartType.class);
			int missingTireCount = 4;
			for (PartType partType : PartType.values()) {
				if (availableParts.get(partType) == null) {
					if (partType.equals(PartType.TIRE)) {
						missingParts.put(partType, 4);
					} else {
						missingParts.put(partType, 1);
					}
				} else {
					if (partType.equals(PartType.TIRE) &&
							availableParts.get(partType).longValue() < 4) {
						missingParts.put(partType, NUM_CAR_TIRES - availableParts.get(partType));
					}
				}
			}
			return missingParts;
		}
		else{
			return null;
		}

	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return parts;
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
