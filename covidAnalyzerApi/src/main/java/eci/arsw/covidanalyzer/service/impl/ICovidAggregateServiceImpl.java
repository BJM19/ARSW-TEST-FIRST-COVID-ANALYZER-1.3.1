package eci.arsw.covidanalyzer.service.impl;


import eci.arsw.covidanalyzer.model.Result;
import eci.arsw.covidanalyzer.model.ResultType;
import eci.arsw.covidanalyzer.percistence.InMemory;
import eci.arsw.covidanalyzer.service.ICovidAggregateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;


@Component()
@Qualifier("aggredate")
public class ICovidAggregateServiceImpl implements ICovidAggregateService {
    private InMemory truePositive = new InMemory();
    private InMemory falsePositive = new InMemory();
    private InMemory trueNegative = new InMemory();
    private InMemory falseNegative = new InMemory();
    @Override
    public boolean aggregateResult(Result result, ResultType type) {
        boolean correct=false;
        if(type == ResultType.TRUE_POSITIVE){
            truePositive.add(result);
            correct=true;
        }
        else if(type == ResultType.TRUE_NEGATIVE){
            falsePositive.add(result);
            correct=true;
        }
        else if(type == ResultType.FALSE_POSITIVE){
            trueNegative.add(result);
            correct=true;
        }
        else {
            falseNegative.add(result);
            correct=true;
        }
        return correct;
    }

    @Override
    public Collection<Result> getResult(ResultType type) {
        Collection<Result> colect = null;
        if(type == ResultType.TRUE_POSITIVE){
            colect = truePositive.getAll().values();

        }
        else if(type == ResultType.TRUE_NEGATIVE){
             colect = falsePositive.getAll().values();

        }
        else if(type == ResultType.FALSE_POSITIVE){
             colect = trueNegative.getAll().values();

        }
        else {
             colect = falseNegative.getAll().values();

        }
        return colect;
    }

    @Override
    public void upsertPersonWithMultipleTests(UUID id) {
        truePositive.update(id);
        falsePositive.update(id);
        trueNegative.update(id);
        falseNegative.update(id);
    }
}
