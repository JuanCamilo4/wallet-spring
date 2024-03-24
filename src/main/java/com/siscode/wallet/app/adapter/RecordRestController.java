package com.siscode.wallet.app.adapter;

import com.siscode.wallet.app.application.RecordApplication;
import com.siscode.wallet.app.domain.RecordDomain;
import com.siscode.wallet.app.domain.RecordDomainPagination;
import com.siscode.wallet.app.infraestructure.RecordEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/record")
public class RecordRestController {

    private static final Logger log = LoggerFactory.getLogger(RecordRestController.class);

    @Autowired
    private RecordApplication recordApplication;

    @PostMapping("/add-record")
    public ResponseEntity<RecordDomain> addRecord(@RequestBody RecordDomain record) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recordApplication.addRecord(record));
    }

    @GetMapping("/list-records")
    public ResponseEntity<RecordDomainPagination> listRecords(
            @RequestParam Integer limit, @RequestParam Integer skip, @RequestParam String ...filter) {
        return ResponseEntity.status(HttpStatus.OK).body(recordApplication.listRecord(limit, skip, filter));
    }

}
