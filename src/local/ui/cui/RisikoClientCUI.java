package local.ui.cui;

import local.domain.Spielfeld;

public class RisikoClientCUI {

	/**
	 * Main-Methode der CUI
	 * @param args
	 */
	public static void main(String[] args) {
		
		String name = "";
		int anzahl = 0;
		IO read = new IO();
		Spielfeld sp = new Spielfeld();
		
		//endlose Schleife bis richtige Eingabe um Spieler zu erstellen
		while(true)
		{
			//Anzahl der Spieler einlesen
			System.out.println("Wie viele Spieler spielen mit? (2-6)");
			anzahl = read.readInt();
			//Bei Eingaben zwischen 2 und 6, werden die neuen Spieler mit eingegebenen Namen erstellt
			if(anzahl > 1 && anzahl < 7 )
			{
				for(int i = 1;i <= anzahl;i++)
				{
					System.out.println("Hallo Spieler " + i + ", wie heißt du?");
					name = read.readString();
					sp.erstelleSpieler(name);
				}
				//springt aus der Endlos-Schleife
				break;
			} else {
				//Ausgabe bei falscher Eingabe
				System.out.println("Falsche Eingabe.");
			}
			//comment
		}	
		
		//Ausgabe, wer nun alles mitspielt (Überprüfung, ob Eingabe erfolgreich war)
		System.out.println("Los gehts.\n Es spielen mit:");
		for(int i = 1;i <= anzahl;i++)
		{
			System.out.println(sp.zeigeName(i));
		}
		
	}
	


}
