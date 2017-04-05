package local.persistence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import local.valueobjects.Land;

public class FilePersistenceManager {
	
	/**
	 * BufferedWriter mit FileWriter Funktion aus der Vorlesung rauskopiert
	 */
	public void schreibeLaender(List<Land> laender) {
		 try (FileWriter fw = new FileWriter("buffer.txt");
			 BufferedWriter bw = new BufferedWriter(fw)) {
			 
			 for (Land land : laender) { 
			 bw.write(land.getName());
			 bw.newLine();
			 bw.write(land.getBesitzer().getName());
			 bw.newLine();
			 bw.write(land.getEinheiten() + "");
			 bw.newLine();
			}
		 } catch (IOException e) { 	 
		}
	}

	//für Adjazenzmatrix brauch man einen Printwriter
}