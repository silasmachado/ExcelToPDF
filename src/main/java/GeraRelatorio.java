import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class GeraRelatorio {
    private static Font subFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    
    public static void geraRelatorio(File file) throws Exception {
    	try {
        	File directory = new File(getReportDirectory());
            if (! directory.exists())
                directory.mkdir();
            createDocument(readXLS(file));
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    private static String getReportDirectory() {
    	return "relatorios_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
    
    private static void createDocument(List<Aluno> listaAlunos) throws DocumentException, MalformedURLException, IOException {
    	for (Aluno aluno : listaAlunos) {
    		Document document = new Document();
    		PdfWriter.getInstance(document, new FileOutputStream(getReportDirectory() + "/Relatório Acadêmico " + aluno.getNome() + ".pdf"));
    		document.open();
    		addHeader(document);
    		addContent(document, aluno);
    		document.close();
    	}
    	
    }
    
    private static void addHeader(Document document) throws MalformedURLException, IOException, DocumentException {
    	Image img = Image.getInstance(GeraRelatorio.class.getResource("logo_focar.png"));
    	img.scaleAbsolute(160, 120);
    	img.setAlignment(Element.ALIGN_CENTER);
    	document.add(img);
    	
    	Paragraph parag = new Paragraph();
        addEmptyLine(parag, 2);
        document.add(parag);
    }
    
    private static void addContent(Document document, Aluno aluno) throws DocumentException, MalformedURLException, IOException {
		PdfPTable table = new PdfPTable(4);
		
		PdfPCell title = new PdfPCell(new Phrase("RELATÓRIO ACADÊMICO", subFont));
		title.setColspan(4);
		title.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(title);
		
		PdfPCell nomeAluno = new PdfPCell(new Phrase(aluno.getNome().toUpperCase(), subFont));
		nomeAluno.setColspan(4);
		nomeAluno.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(nomeAluno);
		
		PdfPCell c1 = new PdfPCell(new Phrase("Disciplina"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		
		PdfPCell c2 = new PdfPCell(new Phrase("Data em que realizou a aula"));
		c2.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c2);
		
		PdfPCell c3 = new PdfPCell(new Phrase("Média Final"));
		c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c3);
		
		PdfPCell c4 = new PdfPCell(new Phrase("Freq."));
		c3.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c4);
		
		table.setHeaderRows(1);
		
		for (Disciplina disciplina : aluno.getListaDisciplina()) {
			table.addCell(disciplina.getNome());
			table.addCell(disciplina.getData());
			table.addCell(disciplina.getMediaFinal());
			table.addCell(disciplina.getFrequencia());
		}
		
		document.add(table);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
    private static List<Aluno> readXLS(File file) throws IOException, InvalidFormatException {
    	//String tmpdir = System.getProperty("java.io.tmpdir");
    	//FileInputStream file = new FileInputStream(tmpdir + "/temp.xlsx");
    	XSSFWorkbook workbook = new XSSFWorkbook(file);
    	List<Aluno> listaAlunos = new ArrayList<Aluno>();
    	
    	XSSFSheet sheet = workbook.getSheetAt(0);
    	
    	for(int rowAluno = 4; rowAluno <= sheet.getLastRowNum(); rowAluno++) {
    		if (sheet.getRow(rowAluno).getCell(1).getStringCellValue() == "")
    			break;
    		Aluno aluno = new Aluno();
    		aluno.setNome(getCellValue(sheet.getRow(rowAluno).getCell(1)));
    		
    		listaAlunos.add(aluno);
    		
    		//PERCORRE DE 3 EM 3 CELULAS POIS AS COLUNAS DE DISCIPLINAS ESTAO EM 3 CELULAS MESCLADAS
        	for(int cellDisciplina = 10; cellDisciplina <= sheet.getRow(1).getLastCellNum(); cellDisciplina+=3) {
    			if (sheet.getRow(1).getCell(cellDisciplina) == null ||
    					//DESCONSIDERA AS CELULAS QUE NAO POSSUEM DISCIPLINA
    					getCellValue(sheet.getRow(1).getCell(cellDisciplina)).replace("Módulo: ", "") == "")
    				break;
    			
    			Disciplina disciplina = new Disciplina();
    			disciplina.setNome(getCellValue(sheet.getRow(1).getCell(cellDisciplina)).replace("Módulo: ", ""));
    			disciplina.setData(getCellValue(sheet.getRow(2).getCell(cellDisciplina)).replace("Data: ", ""));
    			disciplina.setMediaFinal(getCellValue(sheet.getRow(rowAluno).getCell(cellDisciplina)));
    			aluno.addDisciplina(disciplina);
    		}
    	}
    	
    	workbook.close();
    	return listaAlunos;
    }
    
    private static String getCellValue(XSSFCell cell) {
    	if (cell.getCellType() == CellType.NUMERIC) {
    		return String.valueOf(cell.getNumericCellValue());
    	}
    	return cell.getStringCellValue();
    }
}