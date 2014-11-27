import com.csvreader.CsvWriter;
import java.io.*;
import java.util.*;

public class GeneradorCsv {
    public static void main(String[] args) throws IOException, ArrayIndexOutOfBoundsException {

    // ArrayList para guardar cada par de frases con sus respectivos valores
    List<Imagen> imagenes = new ArrayList<Imagen>();
	String[] temp = new String[2];
	String delimitador1 = "\\\t";
	String delimitador2 = " ";
		
	if(args.length != 4){
	    System.out.println ("Uso correcto es: java programa archivoInput archivoOutput archivoGs archivoCsv");
	    System.exit(0);
        }

	Lector lector = new Lector(args[0]);
	lector.leeLinea();
		
	// Guarda cada par de frases en una sola cadena, que corresponde al atributo frase1
	while(lector.getLinea() != null){
	    imagenes.add(new Imagen(lector.getLinea(), "frase2", "scoreRef", "scoreSistema", 0.0));
	    lector.leeLinea();
	}
	lector.cierraFlujoR();

	// Separa los pares de frases en atributos frase1 y frase2 y quita espacios en blanco
	for (int i = 0; i < imagenes.size(); i++) {
	    temp = imagenes.get(i).getFrase1().split(delimitador1);
	    imagenes.get(i).setFrase1(temp[0]);
	    imagenes.get(i).setFrase2(temp[1]);
	    imagenes.get(i).setFrase2(imagenes.get(i).getFrase2().trim());	
	}

	// Guarda score del Sistema
	lector = new Lector(args[1]);
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
	lector = new Lector(args[2]);
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
        
        // Variables para manejar archivo csv
        String outputFile = args[3];
        boolean alreadyExists = new File(outputFile).exists();
                
        // Escritura del archivo csv
        try {
         
            CsvWriter csvOutput = new CsvWriter(new FileWriter(outputFile, true), ',');

            csvOutput.write("Frase1");
            csvOutput.write("Frase2");
            csvOutput.write("ScoreReferencia");
            csvOutput.write("ScoreSistema");
            csvOutput.write("Diferencia");
            csvOutput.endRecord();

            for(Imagen img : imagenes){

                csvOutput.write(img.getFrase1());
                csvOutput.write(img.getFrase2());
                csvOutput.write(img.getScoreRef());
                csvOutput.write(img.getScoreSistema());
                csvOutput.write(img.getDiferenciaString());
                csvOutput.endRecord();                   
            }
            csvOutput.close();
        } catch (IOException e) {
                e.printStackTrace();
        }
        
        System.out.println("\nArchivo Analisis_export.csv generado...\n");
    }
}