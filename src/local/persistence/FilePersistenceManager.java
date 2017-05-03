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
import local.valueobjects.MissionAlt;
import local.valueobjects.Spieler;

public class FilePersistenceManager {
	
	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void lesekanalOeffnen(String datei) throws FileNotFoundException{
		reader = new BufferedReader(new FileReader(datei));
	}
	
	public void schreibkanalOeffnen(String datei) throws IOException{
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
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
	public MissionAlt ladeMission() throws IOException{
		String beschreibung = liesZeile();
			if(beschreibung == null){
				return null;
			}
			return new MissionAlt(beschreibung,null);
	}
	
	private String liesZeile() throws IOException{
		if(reader != null){
			return reader.readLine();
		}else{
			return "";
		}
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