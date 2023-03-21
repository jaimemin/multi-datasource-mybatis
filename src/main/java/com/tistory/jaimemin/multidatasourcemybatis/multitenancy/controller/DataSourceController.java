package com.tistory.jaimemin.multidatasourcemybatis.multitenancy.controller;

import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.dto.DataSourceSaveDto;
import com.tistory.jaimemin.multidatasourcemybatis.multitenancy.service.DataSourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/dataSource")
public class DataSourceController {

    private final DataSourceService dataSourceService;

    @GetMapping
    public ResponseEntity<?> getAllDataSources() {
        try {
            return ResponseEntity.ok(dataSourceService.getAllDataSources());
        } catch (Exception e) {
            log.error("ERROR ", e);

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<String> save(@RequestBody DataSourceSaveDto dataSource) {
        try {
            dataSourceService.save(dataSource);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            log.error("[DataSourceController.save] ERROR ", e);

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @DeleteMapping("/{tenantId}")
    public ResponseEntity<String> delete(@PathVariable String tenantId) {
        try {
            dataSourceService.delete(tenantId);

            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            log.error("[DataSourceController.delete] ERROR ", e);

            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
