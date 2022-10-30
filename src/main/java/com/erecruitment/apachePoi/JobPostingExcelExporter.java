package com.erecruitment.apachePoi;

import com.erecruitment.entities.Submission;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JobPostingExcelExporter {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Submission> listApplicant;

    public JobPostingExcelExporter(List<Submission> listApplicant) {
       this.listApplicant = listApplicant;
        workbook = new XSSFWorkbook();
    }

    private void writeHeaderLine() {
        sheet = workbook.createSheet("Applicant List");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Applicant Id", style);
        createCell(row, 1, "Nama", style);
        createCell(row, 2, "Email", style);
        createCell(row, 3, "Phone Number", style);
        createCell(row, 4, "Cover Letter", style);
        createCell(row, 5, "Status", style);
        createCell(row, 6, "Description", style);
        createCell(row, 7, "Applied at", style);
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH.mm.ss");
        DecimalFormat dFormat = new DecimalFormat("####,###,###.00");

        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Date) {
            String valueDate = formatter.format(value);
            cell.setCellValue(valueDate);
        }  else if (value instanceof Boolean){
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Submission job : listApplicant) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            System.out.println(job.getAppliedBy().getName());
            createCell(row, columnCount++, job.getAppliedBy().getUserId(), style);
            createCell(row, columnCount++, job.getAppliedBy().getName(), style);
            createCell(row, columnCount++, job.getAppliedBy().getEmail(), style);
            createCell(row, columnCount++, job.getAppliedBy().getPhoneNumber(), style);
            createCell(row, columnCount++, job.getCoverLetter(), style);
            createCell(row, columnCount++, job.getStatus(), style);
            createCell(row, columnCount++, job.getDescription(), style);
            createCell(row, columnCount++, job.getAppliedAt(), style);
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
