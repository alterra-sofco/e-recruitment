package com.erecruitment.controllers;

import com.erecruitment.apachePoi.JobPostingExcelExporter;
import com.erecruitment.entities.Department;
import com.erecruitment.entities.PengajuanSDMEntity;
import com.erecruitment.entities.PengajuanSDMSkillEntity;
import com.erecruitment.repositories.PengajuanSDMRepository;
import com.erecruitment.repositories.PengajuanSDMSkillRepository;
import com.erecruitment.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/api")
public class ExportXlxsController {

    @Autowired
    private PengajuanSDMRepository pengajuanSDMRepository;

    @Autowired
    private PengajuanSDMSkillRepository pengajuanSDMSkillRepository;

    @Autowired
    private DepartmentService departmentService;

    @GetMapping("/job-posting/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<PengajuanSDMEntity> listJobPosting = pengajuanSDMRepository.findAll();
        List<PengajuanSDMSkillEntity> skillTree = pengajuanSDMSkillRepository.findAll();
        List<Department> tempDept = departmentService.findAllDepartment();

        JobPostingExcelExporter excelExporter = new JobPostingExcelExporter(listJobPosting, skillTree, tempDept);

        excelExporter.export(response);
    }
}
