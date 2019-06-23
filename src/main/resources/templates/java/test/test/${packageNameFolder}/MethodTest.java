package ${packageName};

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;

import org.json.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Stephan Krusche (krusche@in.tum.de)
 * @version 2.0 (24.02.2019)
 *
 * This test evaluates if the specified methods in the structure oracle
 * are correctly implemented with the expected name, return type, parameter types
 * and visibility modifiers (in case these are specified).
 */
@RunWith(Parameterized.class)
public class MethodTest extends StructuralTest {

    public MethodTest(String expectedClassName, String expectedPackageName, JSONObject expectedClassJSON) {
        super(expectedClassName, expectedPackageName, expectedClassJSON);
    }

    /**
     * This method collects the classes in the structure oracle file for which methods are specified.
     * These classes are packed into a list, which represents the test data.
     * @return A list of arrays containing each class' name, package and the respective JSON object defined in the structure oracle.
     */
    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> findClasses() throws IOException {
        List<Object[]> testData = new ArrayList<Object[]>();

        if (structureOracleJSON == null) {
            return testData;
        }

        for (int i = 0; i < structureOracleJSON.length(); i++) {
            JSONObject expectedClassJSON = structureOracleJSON.getJSONObject(i);

            // Only test the classes that have methods defined in the structure oracle.
            if (expectedClassJSON.has("class") && expectedClassJSON.has("methods")) {
                JSONObject expectedClassPropertiesJSON = expectedClassJSON.getJSONObject("class");
                String expectedClassName = expectedClassPropertiesJSON.getString("name");
                String expectedPackageName = expectedClassPropertiesJSON.getString("package");
                testData.add(new Object[] { expectedClassName, expectedPackageName, expectedClassJSON });
            }
        }
        return testData;
    }

    /**
     * This test loops over the list of the test data generated by the method findClasses(), checks if each class is found
     * at all in the assignment and then proceeds to check its methods.
     */
    @Test(timeout = 1000)
    public void testMethods() {
        Class<?> observedClass = findClassForTestType("method");

        if (expectedClassJSON.has("methods")) {
            JSONArray methodsJSON = expectedClassJSON.getJSONArray("methods");

            checkMethods(observedClass, methodsJSON);
        }
    }

    /**
     * This method checks if a observed class' methods match the expected ones defined in the structure oracle.
     * @param observedClass: The class that needs to be checked as a Class object.
     * @param expectedMethods: The information on the expected methods contained in a JSON array. This information consists
     * of the name, parameter types, return type and the visibility modifiers of each method.
     */
    private void checkMethods(Class<?> observedClass, JSONArray expectedMethods) {
        for(int i = 0; i < expectedMethods.length(); i++) {
            JSONObject expectedMethod = expectedMethods.getJSONObject(i);
            String expectedName = expectedMethod.getString("name");
            JSONArray expectedParameters = expectedMethod.has("parameters") ? expectedMethod.getJSONArray("parameters") : new JSONArray();
            JSONArray expectedModifiers = expectedMethod.has("modifiers") ? expectedMethod.getJSONArray("modifiers") : new JSONArray();
            String expectedReturnType = expectedMethod.getString("returnType");

            boolean nameIsRight = false;
            boolean parametersAreRight = false;
            boolean modifiersAreRight = false;
            boolean returnTypeIsRight = false;

            for(Method observedMethod : observedClass.getDeclaredMethods()) {
                String observedName = observedMethod.getName();
                Class<?>[] observedParameters = observedMethod.getParameterTypes();
                String[] observedModifiers = Modifier.toString(observedMethod.getModifiers()).split(" ");
                String observedReturntype = observedMethod.getReturnType().getSimpleName();

                // If the names don't match, then proceed to the next observed method
                if(!expectedName.equals(observedName)) {
                    //TODO: we should also take wrong case and typos into account
                    //TODO: check if overloading is supported properly
                    continue;
                } else {
                    nameIsRight = true;
                }

                // Then check the parameters
                parametersAreRight = checkParameters(observedParameters, expectedParameters);

                // Then the modifiers
                modifiersAreRight = checkModifiers(observedModifiers, expectedModifiers);

                // And then the return type
                returnTypeIsRight = expectedReturnType.equals(observedReturntype);

                // If all are correct, then we found our method and we can break the loop
                if(nameIsRight && parametersAreRight && modifiersAreRight && returnTypeIsRight) {
                    break;
                }
            }

            String expectedMethodInformation = "the expected method '" + expectedName + "' of the class '" + expectedClassName + "' with "
                + ((expectedParameters.length() == 0) ? "no parameters" : "the parameters: " + expectedParameters.toString());

            assertTrue("Problem: " + expectedMethodInformation + " was not found or is named wrongly.", nameIsRight);
            assertTrue("Problem: the parameters of " + expectedMethodInformation + " are not implemented as expected.", parametersAreRight);
            assertTrue("Problem: the modifiers (access type, abstract, etc.) of " + expectedMethodInformation + " are not implemented as expected.", modifiersAreRight);
            assertTrue("Problem: the return type of " + expectedMethodInformation + " is not implemented as expected.", returnTypeIsRight);
            assertTrue("Problem: the method '" + expectedName + "' of the class " + expectedClassName + " is not implemented as expected.", nameIsRight && parametersAreRight && modifiersAreRight && returnTypeIsRight);
        }
    }

}
