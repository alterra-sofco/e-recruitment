package com.erecruitment.apachePoi;

import com.erecruitment.entities.*;
import com.erecruitment.repositories.StaffRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


public class JobPostingExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<PengajuanSDMEntity> listJobPosting;
    private List<PengajuanSDMSkillEntity> skillTree;
    private List<Department> tempDept;
    @Autowired
    private StaffRepository staffRepository;

    public JobPostingExcelExporter(
            List<PengajuanSDMEntity> listJobPosting
            ,List<PengajuanSDMSkillEntity> skillTree
            ,List<Department> tempDept
    ) {
        this.listJobPosting = listJobPosting;
        this.skillTree =skillTree;
        this.tempDept = tempDept;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("JobPosting");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Job ID", style);
        createCell(row, 1, "Posisition", style);
        createCell(row, 2, "Description", style);
        createCell(row, 3, "remark_staff", style);
        createCell(row, 4, "status", style);
        createCell(row, 5, "numberRequired", style);
        createCell(row, 6, "numberApplicant", style);
        createCell(row, 7, "deadline", style);
        createCell(row, 8, "createdAt", style);
        createCell(row, 9, "skill", style);
        createCell(row, 10, "department", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");

        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if ( value instanceof Date){
            String valueDate = formatter.format(value);
            cell.setCellValue(valueDate);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;
        ArrayList<String> skill = new ArrayList<>();
        Optional<Staff> tempStaff;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (PengajuanSDMEntity job : listJobPosting) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, job.getIdPengajuan(), style);
            createCell(row, columnCount++, job.getPosisi(), style);
            createCell(row, columnCount++, job.getDescription(), style);
            createCell(row, columnCount++, job.getIdPengajuan(), style);
            createCell(row, columnCount++, job.getStatus(), style);
            createCell(row, columnCount++, job.getNumberRequired(), style);
            createCell(row, columnCount++, job.getNumberApplicant(), style);
            createCell(row, columnCount++, job.getDeadline(), style);
            createCell(row, columnCount++, job.getCreatedAt(), style);

            for(PengajuanSDMSkillEntity item: skillTree) {
                if (item.getPengajuanSDMEntity().getIdPengajuan().equals(job.getIdPengajuan())) {
                    skill.add(item.getSkillName());
                }
            }
            createCell(row, columnCount++, skill.toString(), style);
            skill = null;

            tempStaff = staffRepository.findById(job.getUser().getUserId());
            if(tempStaff.isPresent() ){
                for (Department item: tempDept){
                    if(item.getDepartmentId().equals(tempStaff.get().getDepartmentId())){
                        createCell(row, columnCount++, item.getDepartmentName(), style);
                    }
                }
            }

        }
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
