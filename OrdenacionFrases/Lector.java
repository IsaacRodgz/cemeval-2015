import java.io.*;

public class Lector{

	private String linea;
	private BufferedReader entrada;

	public Lector(String archivo) throws IOException {
		entrada = new BufferedReader(new FileReader(archivo));
	}

	public String getLinea() {
		return this.linea;
	}

	public void leeLinea() throws IOException {
		linea = entrada.readLine();
	}	

	public void cierraFlujoR() throws IOException {
		entrada.close();
	}
}
