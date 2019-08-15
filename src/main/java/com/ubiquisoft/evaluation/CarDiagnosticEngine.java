package com.ubiquisoft.evaluation;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {

	public boolean validatePartsCondition(List<Part> parts){
		boolean allWorkingParts=true;
		for(Part part: parts){
			if(! (part.getCondition().equals(ConditionType.NEW) ||
					part.getCondition().equals(ConditionType.GOOD) ||
					part.getCondition().equals(ConditionType.WORN)) ){
				printDamagedPart(part.getType(), part.getCondition());
				allWorkingParts=false;
			}
		}

		return allWorkingParts;
	}

	public boolean validateNoPartsMissing(Map<PartType, Integer> missingParts){
		if(missingParts==null){
			System.out.println("Vehicle missing all parts. Ending diagnostic.");
			return false;
		}
		if(missingParts.isEmpty()){
			return true;
		}

		for(Map.Entry<PartType, Integer> missingPart: missingParts.entrySet()){
			printMissingPart(missingPart.getKey(), missingPart.getValue());
		}
		return false;
	}

	public boolean validateFields(String year, String make, String model){
		boolean validated=true;
		//If all fields are null alert user and exit diagnostic tool
		if(year==null || year.equals("")){
			printMissingInformation("year");
			validated=false;
		}

		if(make==null || make.equals("")){
			printMissingInformation("make");
			validated=false;
		}

		if(model==null || model.equals("")){
			printMissingInformation("model");
			validated=false;
		}

		return validated;
	}

	public void executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively one and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

		//First Step
		boolean requiredFieldsPresent = validateFields(car.getYear(), car.getMake(), car.getModel());
		if(!requiredFieldsPresent){
			System.out.println("Please enter required vehicle information. Ending diagnostic.");
			System.exit(1);
		}

		//Second Step
		boolean noMissingParts = validateNoPartsMissing(car.getMissingPartsMap());
		if(!noMissingParts){
			System.out.println("Vehicle is missing parts. Ending diagnostic.");
			System.exit(1);
		}

		//Third Step
		boolean allWorkingParts = validatePartsCondition(car.getParts());
		if(!allWorkingParts){
			System.out.println("Vehicle has damaged parts. Ending diagnostic.");
			System.exit(1);
		}

		//Fourth Step
		System.out.println(String.format("Your %s %s %s vehicle is in working condition!",
					car.getYear(), car.getMake(), car.getModel()));
	}

	private void printMissingInformation(String carInformation){
		System.out.println(String.format("Missing Car Info: %s", carInformation));
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {
		// Load classpath resource
		InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar.xml");

		// Verify resource was loaded properly
		if (xml == null) {
			System.err.println("An error occurred attempting to load SampleCar.xml");

			System.exit(1);
		}

		// Build JAXBContext for converting XML into an Object
		JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Car car = (Car) unmarshaller.unmarshal(xml);

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
