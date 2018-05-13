package org.eltech.ddm.classification.naivebayes;

import static org.junit.Assert.*;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.junit.Before;
import org.junit.Test;

public class NaiveBayesAlgorithmTest extends NaiveBayesModelTest {

	protected MiningAlgorithm algorithm;

	protected EMiningAlgorithmSettings algorithmSettings;
	
	@Before
	public void setUp() throws Exception {

		// Create and tuning algorithm settings
		algorithmSettings = new EMiningAlgorithmSettings();

		algorithmSettings.setName("Naive Bayes");
		algorithmSettings.setClassname("org.eltech.ddm.classification.naivebayes.NaiveBayesAlgorithm");

	}


	@Test
	public void test4WeatherNominal() {
		try
		{
			setInputData4WeatherNominal();
			setMiningSettings4WeatherNominal(algorithmSettings);
			
	        // Create and tuning algorithm
	        algorithm = new NaiveBayesAlgorithm(miningSettings);
			
	        //Building model
            model = (ClassificationMiningModel )algorithm.buildModel(inputData);

	        verifyModel4WeatherNominal();
		}
		catch( Exception ex )
		{
 		  ex.printStackTrace();
 		  fail();
		}
	}
	
}