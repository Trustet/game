package local.persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import local.valueobjects.Land;
import local.valueobjects.Mission;
import local.valueobjects.Spieler;

public class FilePersistenceManager {
	//TODO EINLESEN UND SPEICHERN implementieren (keine Ahnung was Yannik schon gemacht hat^^)
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void lesekanalOeffnen(String datei) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(datei));
	}
	
	public void schreibkanalOeffnen(String datei) throws IOException{
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}
	public void schreibkanalOeffnen2(String datei) throws IOException{
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei,true)));
	}
	
	public boolean close(){
		if(writer != null){
			writer.close();
		}
		if(reader != null){
			try{
				reader.close();
			}catch(IOException e){
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	public Land ladeLand() throws IOException{
		String name = liesZeile();
		if(name == null){
			return null;
		}
		String kuerzel = liesZeile();
		
		return new Land(name,null,1,kuerzel);
		
	}
//	public Mission ladeMission() throws IOException{
//		String beschreibung = liesZeile();
//			if(beschreibung == null){
//				return null;
//			}
//			return new Mission(beschreibung,null);
//	}
	public boolean speichereSpieler(Spieler spieler) throws IOException {
		// Titel, Nummer und Verfügbarkeit schreiben
		schreibeZeile(spieler.getName());
		return true;
	}
	public boolean speichereWelt(Land land) throws IOException {
		schreibeZeile(land.getName());
		schreibeZeile(land.getBesitzer().getName());
		schreibeZeile(land.getEinheiten()+"");
		schreibeZeile(land.getKuerzel());
		return true;
	}
	public boolean spielSpeichern(List<Land> welt, List<Spieler> spielerListe, String phase, int aktiverSpieler){
		schreibeZeile(phase);
		for(Spieler s : spielerListe){
			schreibeZeile(s.getName());
		}
		schreibeZeile("");
		for(Land l : welt){
			schreibeZeile(l.getName());
			schreibeZeile(l.getBesitzer().getName());
			schreibeZeile(l.getEinheiten()+"");
			schreibeZeile(l.getKuerzel());
		}
		schreibeZeile("");
		
		schreibeZeile(aktiverSpieler+"");
		
		return true;
	}
	public String spielstandLaden() throws IOException{
		return liesZeile();
	}
	public void schreiben(String daten){
		schreibeZeile(daten);
	}
	private String liesZeile() throws IOException{
		if(reader != null){
			return reader.readLine();
		}else{
			return "";
		}
	}
	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
	
//	/**
//	 * BufferedWriter mit FileWriter Funktion aus der Vorlesung rauskopiert
//	 */
//	public void schreibeLaender(List<Land> laender) {
//		 try (FileWriter fw = new FileWriter("buffer.txt");
//			 BufferedWriter bw = new BufferedWriter(fw)) {
//			 
//			 for (Land land : laender) { 
//			 bw.write(land.getName());
//			 bw.newLine();
//			 bw.write(land.getBesitzer().getName());
//			 bw.newLine();
//			 bw.write(land.getEinheiten() + "");
//			 bw.newLine();
//			}
//		 } catch (IOException e) { 	 
//		}
//	}

	//für Adjazenzmatrix brauch man einen Printwriter
}