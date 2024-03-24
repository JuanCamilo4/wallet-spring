package com.siscode.wallet.app.service.impl;

import com.siscode.wallet.app.application.AccountApplication;
import com.siscode.wallet.app.infraestructure.RecordEntity;
import com.siscode.wallet.app.service.RecordRepositoryCustom;
import com.siscode.wallet.app.utils.ConstantsParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(RecordRepositoryCustomImpl.class);

    private final MongoTemplate mongoTemplate;

    @Autowired
    public RecordRepositoryCustomImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Page<RecordEntity> findAllWithFilters(Map filters, Integer skip, Integer limit) {
        log.debug(">>> START FIND WITH FILTERS");
        Query query = new Query();
        List<Criteria> criteria = new ArrayList<>();
        Pageable pageable = PageRequest.of(skip, limit);

        if (filters.get(ConstantsParams.FILTER_TYPE.getValue()) != null) {
            criteria.add(Criteria.where("type").is(filters.get(ConstantsParams.FILTER_TYPE.getValue())));
        }
        if (filters.get(ConstantsParams.FILTER_PAYMENT.getValue()) != null) {
            criteria.add(Criteria.where("paymentType").is(filters.get(ConstantsParams.FILTER_PAYMENT.getValue())));
        }
        if (filters.get(ConstantsParams.FILTER_CATEGORY.getValue()) != null) {
            criteria.add(Criteria.where("category").is(filters.get(ConstantsParams.FILTER_CATEGORY.getValue())));
        }
        if (filters.get(ConstantsParams.FILTER_ACCOUNT.getValue()) != null) {
            criteria.add(Criteria.where("account").is(filters.get(ConstantsParams.FILTER_ACCOUNT.getValue())));
        }

        if (!criteria.isEmpty()) {
            query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
        }

        long count = mongoTemplate.count(query, RecordEntity.class);
        query.with(pageable);

        List<RecordEntity> results = mongoTemplate.find(query, RecordEntity.class);
        return new PageImpl<>(results, PageRequest.of(skip, limit), count);
    }

}
