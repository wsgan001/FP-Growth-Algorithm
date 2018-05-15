package org.eltech.ddm.associationrules.newFpgTest.nir;

import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.associationrules.nir.newfpg.AssociationRulesFunctionSettings;
import org.eltech.ddm.associationrules.nir.newfpg.fpg.FPGModel;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;

public class NirModelTest {
    protected MiningCsvStream inputData;
    protected AssociationRulesFunctionSettings miningSettings;
    protected FPGModel model;

    protected void setInputData() throws MiningException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        inputData = new MiningCsvStream("PREP_after_transformation3.csv", settings, false);
    }


    protected void setMiningSettings(EMiningAlgorithmSettings algorithmSettings) throws MiningException {
        ELogicalData logicalData = inputData.getLogicalData();

        miningSettings = new AssociationRulesFunctionSettings(logicalData);

        miningSettings.setTransactionIDsArributeName("transID");
        miningSettings.setItemIDsArributeName("itemID");
        miningSettings.setStatus("lastStatus");
        miningSettings.setMinSupport(0.1);
        miningSettings.setAlgorithmSettings(algorithmSettings);
    }

}
