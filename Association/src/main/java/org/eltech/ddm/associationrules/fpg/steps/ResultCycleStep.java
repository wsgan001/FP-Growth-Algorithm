package org.eltech.ddm.associationrules.fpg.steps;

import org.eltech.ddm.associationrules.fpg.FPGModel;
import org.eltech.ddm.inputdata.MiningInputStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.CycleStep;
import org.eltech.ddm.miningcore.algorithms.Step;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;

public class ResultCycleStep extends CycleStep {
    public ResultCycleStep(EMiningFunctionSettings settings) throws MiningException {
        super(settings);
    }

    public ResultCycleStep(EMiningFunctionSettings settings, Step... steps) throws MiningException {
        super(settings, steps);
    }

    @Override
    protected EMiningModel initLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        fpgModel.setCurrentResult(1);
        return fpgModel;
    }

    @Override
    protected boolean conditionLoop(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        return fpgModel.getCurrentResult() < fpgModel.getResult().size();
    }

    @Override
    protected EMiningModel beforeIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        return model;
    }

    @Override
    protected EMiningModel afterIteration(MiningInputStream inputData, EMiningModel model) throws MiningException {
        FPGModel fpgModel = ((FPGModel)model);
        fpgModel.setCurrentResult(fpgModel.getCurrentResult() + 1);
        return model;
    }
}
