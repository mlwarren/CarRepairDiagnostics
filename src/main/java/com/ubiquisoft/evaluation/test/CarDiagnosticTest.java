package com.ubiquisoft.evaluation.test;

import com.ubiquisoft.evaluation.CarDiagnosticEngine;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarDiagnosticTest {

    private final ByteArrayOutputStream outputContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorContent = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;
    private final PrintStream originalError = System.err;

    @BeforeEach
    public void redirectStreams(){
        System.setOut(new PrintStream(outputContent));
        System.setErr(new PrintStream(errorContent));
    }

    @AfterEach
    public void restoreStreams(){
        System.setOut(originalOutput);
        System.setErr(originalError);
    }

    @Test
    public void TestSampleCar() throws JAXBException{
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

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Damaged Part Detected: ENGINE - Condition: USED" + System.lineSeparator() +
                "Damaged Part Detected: ELECTRICAL - Condition: NO_POWER" + System.lineSeparator() +
                "Damaged Part Detected: TIRE - Condition: FLAT" + System.lineSeparator() +
                "Damaged Part Detected: OIL_FILTER - Condition: CLOGGED" + System.lineSeparator() +
                "Vehicle has damaged parts. Ending diagnostic.".trim(), outputContent.toString().trim());

    }

    @Test
    public void TestSampleCar2() throws JAXBException{
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar2.xml");

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load SampleCar2.xml");
            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object

        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Car car = (Car) unmarshaller.unmarshal(xml);


        // Build new Diagnostics Engine and execute on deserialized car object.

        CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
        diagnosticEngine.executeDiagnostics(car);

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Missing Part(s) Detected: FUEL_FILTER - Count: 1" + System.lineSeparator() +
                "Vehicle is missing parts. Ending diagnostic.".trim(), outputContent.toString().trim());
    }

    @Test
    public void TestSampleCar3() throws JAXBException{
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar3.xml");

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load SampleCar3.xml");
            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object

        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Car car = (Car) unmarshaller.unmarshal(xml);


        // Build new Diagnostics Engine and execute on deserialized car object.

        CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
        diagnosticEngine.executeDiagnostics(car);

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Missing Part(s) Detected: TIRE - Count: 2" + System.lineSeparator() +
                "Vehicle is missing parts. Ending diagnostic.".trim(), outputContent.toString().trim());

    }

    @Test
    public void TestSampleCar4() throws JAXBException{
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("SampleCar4.xml");

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load SampleCar4.xml");
            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object

        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Car car = (Car) unmarshaller.unmarshal(xml);


        // Build new Diagnostics Engine and execute on deserialized car object.

        CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
        diagnosticEngine.executeDiagnostics(car);

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Missing Part(s) Detected: TIRE - Count: 4" + System.lineSeparator() +
                "Vehicle is missing parts. Ending diagnostic.".trim(), outputContent.toString().trim());

    }

    @Test
    public void TestNoInfoCar() throws JAXBException{
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("NoInfoCar.xml");

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

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Missing Car Info: year" + System.lineSeparator() +
                "Missing Car Info: make" + System.lineSeparator() +
                "Missing Car Info: model" + System.lineSeparator() +
                "Please enter required vehicle information. Ending diagnostic.".trim(),
                outputContent.toString().trim());
    }

    @Test
    public void TestNoPartsCar() throws JAXBException{
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("NoPartsCar.xml");

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load NoPartsCar.xml");
            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object

        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Car car = (Car) unmarshaller.unmarshal(xml);


        // Build new Diagnostics Engine and execute on deserialized car object.

        CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
        diagnosticEngine.executeDiagnostics(car);

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Vehicle missing all parts. Ending diagnostic." + System.lineSeparator() +
                "Vehicle is missing parts. Ending diagnostic.".trim(), outputContent.toString().trim());

    }

    @Test
    public void TestPerfectCar() throws JAXBException{
        // Load classpath resource
        InputStream xml = ClassLoader.getSystemResourceAsStream("PerfectCar.xml");

        // Verify resource was loaded properly
        if (xml == null) {
            System.err.println("An error occurred attempting to load PerfectCar.xml");
            System.exit(1);
        }

        // Build JAXBContext for converting XML into an Object

        JAXBContext context = JAXBContext.newInstance(Car.class, Part.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        Car car = (Car) unmarshaller.unmarshal(xml);


        // Build new Diagnostics Engine and execute on deserialized car object.

        CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
        diagnosticEngine.executeDiagnostics(car);

        originalOutput.println("Output from test data:\n" + outputContent.toString().trim());
        assertEquals("Your 2006 Ford Explorer vehicle is in working condition!", outputContent.toString().trim());

    }

}

