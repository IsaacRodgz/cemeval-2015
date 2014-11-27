import java.util.*;

public class Imagen {
	
	private String frase1;
	private String frase2;
	private String scoreRef;
	private String scoreSistema;
	private double diferenciaDouble;
	private String diferenciaString;

	public Imagen(String frase1, String frase2, String scoreRef,String scoreSistema,Double diferencia){
		this.frase1 = frase1;
		this.frase2 = frase2;
		this.scoreRef = scoreRef;
		this.scoreSistema = scoreSistema;
		this.diferenciaDouble = diferencia;
	}

	public void calculaDiferencia() {
		this.diferenciaDouble = Math.abs(Double.parseDouble(this.scoreRef) - Double.parseDouble(this.scoreSistema));
		this.diferenciaString = String.format("%.6f", this.diferenciaDouble);
	}

	public void setFrase1(String frase1){
		this.frase1 = frase1;
	}
	public String getFrase1(){
		return this.frase1;
	}

	public void setFrase2(String frase2){
		this.frase2 = frase2;
	}
	public String getFrase2(){
		return this.frase2;
	}

	public void setScoreRef(String scoreRef){
		this.scoreRef = scoreRef;
	}
	public String getScoreRef(){
		return this.scoreRef;
	}
        public Double getScoreRefDouble(){
		return Double.parseDouble(this.scoreRef);
	}

	public void setScoreSistema(String scoreSistema){
		this.scoreSistema = scoreSistema;
	}
	public String getScoreSistema(){
		return this.scoreSistema;
	}
        public Double getScoreSistemaDouble(){
		return Double.parseDouble(this.scoreSistema);
	}

	public void setDiferenciaString(String diferencia){
		this.diferenciaString = diferencia;
	}
	public String getDiferenciaString(){
		return this.diferenciaString;
	}

	public void setDiferenciaDouble(Double diferencia){
		this.diferenciaDouble = diferencia;
	}
	public Double getDiferenciaDouble(){
		return this.diferenciaDouble;
	}
}