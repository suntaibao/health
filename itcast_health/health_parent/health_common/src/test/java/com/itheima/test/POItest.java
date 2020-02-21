package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.jar.JarEntry;

public class POItest {
    //poi测试
    @Test
    public void test1() throws IOException {
        XSSFWorkbook excel=new XSSFWorkbook(new FileInputStream(new File("D:\\zys.xlsx")));
        XSSFSheet sheet = excel.getSheetAt(0);
        for (Row row : sheet) {
            for (Cell cell : row) {
                System.out.println(cell.getStringCellValue());
            }
        }
        excel.close();
    }


    @Test
    public void test2() throws IOException {
        XSSFWorkbook excel=new XSSFWorkbook(new FileInputStream(new File("D:\\zys.xlsx")));
        XSSFSheet sheet = excel.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <=lastRowNum ; i++) {
            XSSFRow row = sheet.getRow(i);//根据行号获取每一行
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j <lastCellNum ; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());

            }


        }
        excel.close();
    }

    @Test
    public void test3() throws IOException {
        //在内存中创建一个文件
        XSSFWorkbook excel=new XSSFWorkbook();
        XSSFSheet sheet = excel.createSheet("传智播客");
        //创建行
        XSSFRow row = sheet.createRow(0);
        //
        row.createCell(0).setCellValue("你好");
        row.createCell(1).setCellValue("世界");
//创建输出流
        FileOutputStream out=new FileOutputStream(new File("D:\\zyy.xlsx"));
        excel.write(out);
        out.flush();
        out.close();
        excel.close();
    }
}
