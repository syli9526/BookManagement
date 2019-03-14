/*
 * 공공 API를 통해 얻어온 데이터들을 DB에 저장하기 위해 만들어 둔 클래스이다.
 * 함께 첨부한 외부 jar 파일들을 라이브러리에 애드하고 난 후 이 클래스의 전체 주석을 해제한 후 이 클래스 안의 메인을 실행해주면 DB연결
 * 클래스가 이미 있었으므로 이를 사용하여 DB에 접근하고 정보들을 저장해준다.
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
			//XML파일을 읽어서 DB에 저장하기 위해 사용 --> 경로는 다운 받으신 곳으로 따로 설정하시면 됩니다.
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			//sheet수 취득
			int sheetCn = workbook.getNumberOfSheets();
			dao.getLinkDB().connectDB();
			for(int cn = 0; cn < sheetCn; cn++){
				
				XSSFSheet sheet = workbook.getSheetAt(cn);
				//취득된 sheet에서 rows수 취득
				String value = null;
				int rows = sheet.getPhysicalNumberOfRows();
				//취득된 row에서 취득대상 cell수 취득
				int cells = sheet.getRow(cn).getPhysicalNumberOfCells(); //
				int r;
				for (r = 1; r < 200; r++) {
				--> r이 가져올 행의 개수
					row = sheet.getRow(r); // row 가져오기
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
									value = "[null 아닌 공백]";
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