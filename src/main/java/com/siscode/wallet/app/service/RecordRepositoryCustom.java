package com.siscode.wallet.app.service;

import com.siscode.wallet.app.infraestructure.RecordEntity;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface RecordRepositoryCustom {

    Page<RecordEntity> findAllWithFilters(Map filters, Integer skip, Integer limit);

}
