package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.AddPengajuanSDMRequest;
import com.erecruitment.dtos.requests.UpdateStatusPengajuanSDMRequest;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.dtos.response.PengajuanSDMResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.services.PengajuanSDMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/request_sdm")
public class PengajuanSDMController {

    @Autowired
    private PengajuanSDMService pengajuanSDMService;

    @GetMapping
    public ResponseEntity<PageableResponse<PengajuanSDMResponse>> getAllData(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idPengajuan") String sort_column,
            @RequestParam(defaultValue = "DESC") String sort_order,
            @RequestParam(defaultValue = "0") int status
    ) {
        PageableResponse response = pengajuanSDMService.getData(page, size, keyword, sort_column, sort_order, (short) status);
        response.setStatus(String.valueOf(HttpStatus.OK.value()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<PengajuanSDMResponse>> addData(@RequestBody AddPengajuanSDMRequest request) {
        PengajuanSDMResponse response = pengajuanSDMService.saveData(request, 0L);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()), "ok", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<PengajuanSDMResponse>> updateData(@RequestBody AddPengajuanSDMRequest request, @PathVariable("id") Long id) {
        PengajuanSDMResponse response = pengajuanSDMService.saveData(request, id);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.ACCEPTED.value()), "ok", response), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> removeOne(@PathVariable Long id) {
        pengajuanSDMService.removeOne(id);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()), "ok", id), HttpStatus.OK);
    }

    @PutMapping("/{id}/update_status")
    public ResponseEntity<CommonResponse<PengajuanSDMResponse>> updateStatus(@RequestBody UpdateStatusPengajuanSDMRequest request, @PathVariable("id") Long id) {
        PengajuanSDMResponse response = pengajuanSDMService.updateStatus(request, id);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()), "ok", response), HttpStatus.OK);
    }

    @PutMapping("/{id}/close_job")
    public ResponseEntity<CommonResponse<PengajuanSDMResponse>> closeJob(@PathVariable("id") Long id) {
        PengajuanSDMResponse response = pengajuanSDMService.closeJobPosted(id);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()), "ok", response), HttpStatus.OK);

    }

}
