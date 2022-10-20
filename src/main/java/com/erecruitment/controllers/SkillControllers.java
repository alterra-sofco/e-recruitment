package com.erecruitment.controllers;

import com.erecruitment.dtos.requests.SkillRequest;
import com.erecruitment.dtos.response.CommonResponse;
import com.erecruitment.dtos.response.PageableResponse;
import com.erecruitment.dtos.response.ResponseGenerator;
import com.erecruitment.dtos.response.SkillResponse;
import com.erecruitment.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/master_skill")
public class SkillControllers {

    @Autowired
    private SkillService skillService;

    @GetMapping
    public ResponseEntity<PageableResponse<SkillResponse>> getAllData(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "skillId") String sort_column,
            @RequestParam(defaultValue = "DESC") String sort_order
    ) {
        PageableResponse response = skillService.getData(page, size, keyword, sort_column, sort_order);
        response.setStatus(String.valueOf(HttpStatus.OK.value()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<SkillResponse>> addData(@RequestBody SkillRequest request) {
        SkillResponse response = skillService.saveData(request, 0L);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.CREATED.value()), "ok", response), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<SkillResponse>> updateData(@RequestBody SkillRequest request, @PathVariable("id") Long id) {
        SkillResponse response = skillService.saveData(request, id);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.ACCEPTED.value()), "ok", response), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> removeOne(@PathVariable Long id) {
        skillService.removeOne(id);
        ResponseGenerator responseGenerator = new ResponseGenerator();
        return new ResponseEntity<>(responseGenerator.responseData(String.valueOf(HttpStatus.OK.value()), "ok", id), HttpStatus.CREATED);
    }
}
