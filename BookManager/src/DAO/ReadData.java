/*
 * ���� API�� ���� ���� �����͵��� DB�� �����ϱ� ���� ����� �� Ŭ�����̴�.
 * �Բ� ÷���� �ܺ� jar ���ϵ��� ���̺귯���� �ֵ��ϰ� �� �� �� Ŭ������ ��ü �ּ��� ������ �� �� Ŭ���� ���� ������ �������ָ� DB����
 * Ŭ������ �̹� �־����Ƿ� �̸� ����Ͽ� DB�� �����ϰ� �������� �������ش�.
 */

/*
package DAO;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import DataStructure.BookData;

public class ReadData {   
	    
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		String sql;
		BookInfoDAO dao = new BookInfoDAO();
		XSSFRow row;
		XSSFCell cell;
		
		try {
			BookData book = new BookData();
			FileInputStream inputStream = new FileInputStream("C:\\file\\Library.xlsx");
			//XML������ �о DB�� �����ϱ� ���� ��� --> ��δ� �ٿ� ������ ������ ���� �����Ͻø� �˴ϴ�.
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			//sheet�� ���
			int sheetCn = workbook.getNumberOfSheets();
			dao.getLinkDB().connectDB();
			for(int cn = 0; cn < sheetCn; cn++){
				
				XSSFSheet sheet = workbook.getSheetAt(cn);
				//���� sheet���� rows�� ���
				String value = null;
				int rows = sheet.getPhysicalNumberOfRows();
				//���� row���� ����� cell�� ���
				int cells = sheet.getRow(cn).getPhysicalNumberOfCells(); //
				int r;
				for (r = 1; r < 200; r++) {
				--> r�� ������ ���� ����
					row = sheet.getRow(r); // row ��������
					if (row != null) {
						for (int c = 0; c < cells; c++) {
							cell = row.getCell(c);
							if (cell != null) {
								value = null;
								switch (cell.getCellType()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									value = "" + cell.getNumericCellValue();
									break;
								case STRING:
									value = "" + cell.getStringCellValue();
									break;
								case BLANK:
									value = "[null �ƴ� ����]";
									break;
								case ERROR:
									value = "" + cell.getErrorCellValue();
									break;
								default:
								}
							}
							
							if(c==3) {
								System.out.print(value + "------------");
								book.setBookName(value);
							}else if(c==5) {
								System.out.print(value  + "------------");
								book.setAuthor(value);
							}else if(c==6) {
								System.out.println(value);
								book.setPublish(value);
							}
						} 
						book.setBookID(String.valueOf(r));
						dao.insertBook(book);
						
					}
				} 
			}
			dao.getLinkDB().closeDB();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
*/