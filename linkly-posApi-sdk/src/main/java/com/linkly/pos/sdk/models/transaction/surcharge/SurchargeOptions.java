package com.linkly.pos.sdk.models.transaction.surcharge;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.linkly.pos.sdk.common.IPurchaseAnalysisDataConverter;

public class SurchargeOptions implements IPurchaseAnalysisDataConverter {

    private final List<SurchargeRule> surcharges = new ArrayList<>();

    public void add(SurchargeRule surchargeRule) {
        this.surcharges.add(surchargeRule);
    }
  
    public boolean isNotEmpty() {
    	return surcharges.size() > 0;
    }

    @Override
    public String toPadString() {
        return "[" + surcharges.stream()
            .map(s -> s.toPadSurchargeString())
            .collect(Collectors.joining(",")) + "]";
    }
}