package org.wcontacts.commons;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Excel {

	public static Row getRow(final HSSFSheet sheet, Row row, int rowNum) {
		try {
			if (sheet.getRow(rowNum) == null) {
				row = sheet.createRow(rowNum);
			} else {
				row = sheet.getRow(rowNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return row;
	}

	public static Cell getCell(final HSSFSheet sheet, Row row, Cell cell, int cellNum) {
		try {
			if (row.getCell(cellNum) == null
					|| row.getCell(cellNum).getCellType() == Cell.CELL_TYPE_BLANK) {
				cell = row.createCell(cellNum, Cell.CELL_TYPE_STRING);
			} else {
				cell = row.getCell(cellNum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return cell;
	}
}
