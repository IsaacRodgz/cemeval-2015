import com.csvreader.CsvWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.util.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Graficas {
    public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException {

        // ArrayList para guardar cada par de frases con sus respectivos valores
       	List<Imagen> imagenes = new ArrayList<Imagen>();
	String[] temp = new String[2];
	String delimitador1 = "\\\t";
	String delimitador2 = " ";
        int[][] mat = new int[5][5];
		
	if(args.length != 3){
	    System.out.println ("Uso correcto es: java programa archivoOutput archivoGs archivoCsv");
	    System.exit(0);
        }

	Lector lector = new Lector(args[0]);
	lector.leeLinea();
		
	// Guarda cada par de frases en una sola cadena, que corresponde al atributo frase1
	while(lector.getLinea() != null){
	    imagenes.add(new Imagen("frase1", "frase2", "scoreRef", "scoreSistema", Double.parseDouble(lector.getLinea())));
	    lector.leeLinea();
	}
	lector.cierraFlujoR();
	
	// Guarda score del Sistema
	lector = new Lector(args[0]);
	lector.leeLinea();
	int j = 0;
	while(lector.getLinea() != null){
	    temp = lector.getLinea().split(delimitador2);
	    imagenes.get(j).setScoreSistema(temp[0].trim());
	    lector.leeLinea();
	    j++;
	}
	lector.cierraFlujoR();

	// Guarda score de Referencia
	lector = new Lector(args[1]);
	lector.leeLinea();
	j = 0;
	while(lector.getLinea() != null){
	    imagenes.get(j).setScoreRef(lector.getLinea().trim());
	    lector.leeLinea();
	    j++;
	}
	lector.cierraFlujoR();

	// Calcula la diferencia entre scoreSistema y scoreRef
	for (Imagen img : imagenes) {
	    img.calculaDiferencia();
	}

	// Ordena las frases de mayor a menor error
	Collections.sort(imagenes, new MyComparator());
        
        // Creacion de archivos pdf con estadisticas de errores
        // Estadistica 1
        
        int dif_5, dif_4, dif_3, dif_2, dif_1;
        dif_5 = dif_4 = dif_3 = dif_2 = dif_1 = 0;
        
        for (Imagen img : imagenes) {
            if(img.getDiferenciaDouble() >= 4)
                dif_5++;
            else if(img.getDiferenciaDouble() >= 3 && img.getDiferenciaDouble() < 4)
                dif_4++;
            else if(img.getDiferenciaDouble() >= 2 && img.getDiferenciaDouble() < 3)
                dif_3++;
            else if(img.getDiferenciaDouble() >= 1 && img.getDiferenciaDouble() < 2)
                dif_2++;
        }
        String datos1 = String.format("                                "+dif_5+ "                    "+dif_4
                +"                    "+dif_3+"                    "+dif_2);
        
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
        
        dataset.setValue(dif_5, "Frases", "5-4");       
        dataset.setValue(dif_4, "Frases", "4-3");       
        dataset.setValue(dif_3, "Frases", "3-2");
        dataset.setValue(dif_2, "Frases", "2-1");
        
        // Estadistica 2
        dif_5 = dif_4 = dif_3 = dif_2 = dif_1 = 0;
        
        for (Imagen img : imagenes) {
            if(img.getScoreRefDouble() >= 4 && img.getDiferenciaDouble() >= 1)
                dif_5++;
            else if(img.getScoreRefDouble() >= 3 && img.getScoreRefDouble() < 4 && img.getDiferenciaDouble() >= 1)
                dif_4++;
            else if(img.getScoreRefDouble() >= 2 && img.getScoreRefDouble() < 3 && img.getDiferenciaDouble() >= 1)
                dif_3++;
            else if(img.getScoreRefDouble() >= 1 && img.getScoreRefDouble() < 2 && img.getDiferenciaDouble() >= 1)
                dif_2++;
            else if(img.getScoreRefDouble() >= 0 && img.getScoreRefDouble() < 1 && img.getDiferenciaDouble() >= 1)
                dif_1++;
        }
        String datos2 = String.format("                           "+dif_5+ "               "+dif_4
                +"               "+dif_3+"               "+dif_2+"               "+dif_1);
        
        dataset2.setValue(dif_5, "Frases", "5-4");       
        dataset2.setValue(dif_4, "Frases", "4-3");       
        dataset2.setValue(dif_3, "Frases", "3-2");
        dataset2.setValue(dif_2, "Frases", "2-1");
        dataset2.setValue(dif_1, "Frases", "1-0");
        
        // Estadistica 3.
        dif_5 = dif_4 = dif_3 = dif_2 = dif_1 = 0;
        
        for (Imagen img : imagenes) {
            if(img.getScoreSistemaDouble() > 4 && img.getDiferenciaDouble() >= 1)
                dif_5++;
            else if(img.getScoreSistemaDouble() >= 3 && img.getScoreSistemaDouble() < 4 && img.getDiferenciaDouble() >= 1)
                dif_4++;
            else if(img.getScoreSistemaDouble() >= 2 && img.getScoreSistemaDouble() < 3 && img.getDiferenciaDouble() >= 1)
                dif_3++;
            else if(img.getScoreSistemaDouble() >= 1 && img.getScoreSistemaDouble() < 2 && img.getDiferenciaDouble() >= 1)
                dif_2++;
            else if(img.getScoreSistemaDouble() >= 0 && img.getScoreSistemaDouble() < 1 && img.getDiferenciaDouble() >= 1)
                dif_1++;
        }
        String datos3 = String.format("                           "+dif_5+ "               "+dif_4
                +"               "+dif_3+"               "+dif_2+"               "+dif_1);
        
        dataset3.setValue(dif_5, "Frases", "5-4");       
        dataset3.setValue(dif_4, "Frases", "4-3");       
        dataset3.setValue(dif_3, "Frases", "3-2");
        dataset3.setValue(dif_2, "Frases", "2-1");
        dataset3.setValue(dif_1, "Frases", "1-0");
        
        JFreeChart chart = ChartFactory.createBarChart(
                "Distribucion errores",
                "Diferencia", 
                "Num. de frases", 
                dataset, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false
        );
        JFreeChart chart2 = ChartFactory.createBarChart(
                "Errores en funcion de gold standard",
                "Diferencia", 
                "Num. de frases", 
                dataset2, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false
        );
        JFreeChart chart3 = ChartFactory.createBarChart(
                "Errores en funcion de score sistema",
                "Diferencia", 
                "Num. de frases", 
                dataset3, 
                PlotOrientation.VERTICAL,
                false, 
                false, 
                false
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("chart.jpg"), chart, 400, 230);
            ChartUtilities.saveChartAsJPEG(new File("chart2.jpg"), chart2, 400, 230);
            ChartUtilities.saveChartAsJPEG(new File("chart3.jpg"), chart3, 400, 230);
        } catch (Exception e) {
            System.out.println("Problem occurred creating chart.");
        }
        
        Document document = new Document();
        try {
            
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Analisis.pdf"));

            Paragraph paragraph1 = new Paragraph(datos1);
            paragraph1.setAlignment(Element.ALIGN_LEFT);
            Paragraph paragraph2 = new Paragraph(datos2);
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            Paragraph paragraph3 = new Paragraph(datos3);
            paragraph2.setAlignment(Element.ALIGN_LEFT);
            document.open();
            //
            // Creating image by file name
            //
            Image image = Image.getInstance("chart.jpg");   
            document.add(image);
            /*
            PdfContentByte cb = writer.getDirectContent();
            cb.beginText();
            cb.setTextMatrix(10, 10);
            cb.showText("Text at position 100,400.");
            cb.endText();
            */
            document.add(paragraph1);
            
            image = Image.getInstance("chart2.jpg");
            document.add(image);
            document.add(paragraph2);
            
            image = Image.getInstance("chart3.jpg");
            document.add(image);
            document.add(paragraph3);  
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
        
        for (Imagen img : imagenes) {
            if(img.getScoreRefDouble() >= 0 && img.getScoreRefDouble() < 1){
                if(img.getDiferenciaDouble() < 1)
                    mat[0][0]++;
                else if(img.getScoreSistemaDouble() >= 1 && img.getScoreSistemaDouble() < 2)
                    mat[0][1]++;
                else if(img.getScoreSistemaDouble() >= 2 && img.getScoreSistemaDouble() < 3)
                    mat[0][2]++;
                else if(img.getScoreSistemaDouble() >= 3 && img.getScoreSistemaDouble() < 4)
                    mat[0][3]++;
                else if(img.getScoreSistemaDouble() >= 4 && img.getScoreSistemaDouble() <= 5)
                    mat[0][4]++;
            }
            else if(img.getScoreRefDouble() >= 1 && img.getScoreRefDouble() < 2){
                if(img.getDiferenciaDouble() < 1)
                    mat[1][1]++;
                else if(img.getScoreSistemaDouble() >= 0 && img.getScoreSistemaDouble() < 1)
                    mat[1][0]++;
                else if(img.getScoreSistemaDouble() >= 2 && img.getScoreSistemaDouble() < 3)
                    mat[1][2]++;
                else if(img.getScoreSistemaDouble() >= 3 && img.getScoreSistemaDouble() < 4)
                    mat[1][3]++;
                else if(img.getScoreSistemaDouble() >= 4 && img.getScoreSistemaDouble() <= 5)
                    mat[1][4]++;
            }
            else if(img.getScoreRefDouble() >= 2 && img.getScoreRefDouble() < 3){
                if(img.getDiferenciaDouble() < 1)
                    mat[2][2]++;
                else if(img.getScoreSistemaDouble() >= 0 && img.getScoreSistemaDouble() < 1)
                    mat[2][0]++;
                else if(img.getScoreSistemaDouble() >= 1 && img.getScoreSistemaDouble() < 2)
                    mat[2][1]++;
                else if(img.getScoreSistemaDouble() >= 3 && img.getScoreSistemaDouble() < 4)
                    mat[2][3]++;
                else if(img.getScoreSistemaDouble() >= 4 && img.getScoreSistemaDouble() <= 5)
                    mat[2][4]++;
            }
            else if(img.getScoreRefDouble() >= 3 && img.getScoreRefDouble() < 4){
                if(img.getDiferenciaDouble() < 1)
                    mat[3][3]++;
                else if(img.getScoreSistemaDouble() >= 0 && img.getScoreSistemaDouble() < 1)
                    mat[3][0]++;
                else if(img.getScoreSistemaDouble() >= 1 && img.getScoreSistemaDouble() < 2)
                    mat[3][1]++;
                else if(img.getScoreSistemaDouble() >= 2 && img.getScoreSistemaDouble() < 3)
                    mat[3][2]++;
                else if(img.getScoreSistemaDouble() >= 4 && img.getScoreSistemaDouble() <= 5)
                    mat[3][4]++;
            }
            else if(img.getScoreRefDouble() >= 4 && img.getScoreRefDouble() <= 5){
                if(img.getDiferenciaDouble() < 1)
                    mat[4][4]++;
                else if(img.getScoreSistemaDouble() >= 0 && img.getScoreSistemaDouble() < 1)
                    mat[4][0]++;
                else if(img.getScoreSistemaDouble() >= 1 && img.getScoreSistemaDouble() < 2)
                    mat[4][1]++;
                else if(img.getScoreSistemaDouble() >= 2 && img.getScoreSistemaDouble() < 3)
                    mat[4][2]++;
                else if(img.getScoreSistemaDouble() >= 3 && img.getScoreSistemaDouble() < 4)
                    mat[4][3]++;
            }
        }          
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                System.out.print(mat[i][k]);
                System.out.print("   ");
            }
            System.out.print("\n");
        }
        // Variables para manejar archivo csv
        String outputFile = args[2];
        boolean alreadyExists = new File(outputFile).exists();
                
        // Escritura del archivo csv
        try {
         
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            csvOutput.write("0 <= dif <= 1");
            csvOutput.write("1 < dif <= 2");
            csvOutput.write("2 < dif <= 3");
            csvOutput.write("3 < dif <= 4");
            csvOutput.write("4 < dif <= 5");
            csvOutput.endRecord();

            for (int i = 0; i < 5; i++) {
                csvOutput.write(Integer.toString(mat[i][0]));
                csvOutput.write(Integer.toString(mat[i][1]));
                csvOutput.write(Integer.toString(mat[i][2]));
                csvOutput.write(Integer.toString(mat[i][3]));
                csvOutput.write(Integer.toString(mat[i][4]));
                csvOutput.endRecord(); 
                
            }    
            csvOutput.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        System.out.println("\nArchivo Analisis_export.csv generado...\n");
    }
}
