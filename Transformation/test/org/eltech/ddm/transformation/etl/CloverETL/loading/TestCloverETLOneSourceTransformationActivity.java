package org.eltech.ddm.transformation.etl.CloverETL.loading;

import java.util.Date;

import org.eltech.ddm.associationrules.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithm;
import org.eltech.ddm.associationrules.apriori.AprioriAlgorithmSettings;
import org.eltech.ddm.associationrules.apriori.AprioriMiningModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.transformation.MiningETLArrayStreamDataProvider;
import org.eltech.ddm.transformation.MiningETLArrayStreamInformer;
import org.eltech.ddm.transformation.MiningTransformationMetadata;
import org.junit.Test;

/**
 * Test for CloverETLOneSourceTransformationActivity.
 * 
 * @author SemenchenkoA
 *
 */
public class TestCloverETLOneSourceTransformationActivity implements MiningETLArrayStreamInformer{
	private MiningInputStream miningInputStream = null;
	private CloverETLOneSourceTransformationActivity cloverOneSourceActivity = null;
	
	@Test
	public void test() throws Exception{
		cloverOneSourceActivity = new CloverETLOneSourceTransformationActivity();
		cloverOneSourceActivity.setMetaData("relation", ";", "\n");
		cloverOneSourceActivity.addMetaDataField("transactId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		cloverOneSourceActivity.addMetaDataField("itemId", MiningTransformationMetadata.FIELD_TYPE_STRING);
		cloverOneSourceActivity.setBatchSize(15);
		//cloverOneSourceActivity.runActivity("..//data//excel//apriori.csv", this);
		cloverOneSourceActivity.runActivity("https://dl-doc.dropbox.com/s/rwlgatwl50wlw6r/apriori.xlsx", this);
		//cloverOneSourceActivity.runActivity("https://dl-doc.dropbox.com/s/ptfk58at3gwopg6/apriori.csv", this);
	}	
	
	@Override
	public void MiningETLArrayStreamPrepared(ELogicalData aLogicalData,
			MiningETLArrayStreamDataProvider aDataProvider) {
		try{
			miningInputStream = new MiningETLArrayStream(aLogicalData, aDataProvider);
			((MiningETLArrayStream)miningInputStream).setBatchSize(cloverOneSourceActivity.getBatchSize());
			((MiningETLArrayStream)miningInputStream).setMiningArrayLength(15);
			
			System.out.println("AprioriAlgorithm");
	
			Date start = new Date();
	
			ELogicalData logicalData = miningInputStream.getLogicalData();
	
		    AprioriAlgorithmSettings algorithmSettings = new AprioriAlgorithmSettings();
		    algorithmSettings.setNumberOfTransactions(4);
	
		    AssociationRulesFunctionSettings miningSettings = new AssociationRulesFunctionSettings(logicalData);
		    miningSettings.setTransactionIDsArributeName("transactId");
		    miningSettings.setItemIDsArributeName("itemId");
		    
		    miningSettings.setMinConfidence(0.6);
		    miningSettings.setMinSupport(0.6);
		    miningSettings.setAlgorithmSettings(algorithmSettings);
	
		    AprioriAlgorithm aprioriAlgorithm = new AprioriAlgorithm(miningSettings);
		    AprioriMiningModel miningModel = (AprioriMiningModel) aprioriAlgorithm.buildModel(miningInputStream);
			miningModel.getTransactionList().print();		
		   
			Date end = new Date();
			long time = (end.getTime() - start.getTime());
			System.out.println("Total time = " +  time + "�� \nAssociationRuleSet:  \n" + miningModel.getAssociationRuleSet());	
		} catch (MiningException e) {
			e.printStackTrace();
		}
		
	}

}
