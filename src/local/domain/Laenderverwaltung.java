package local.domain;

public class Laenderverwaltung {

	//Weltverwaltung weltVw = new Weltverwaltung();
	//Spielerverwaltung spielerVw = new Spielerverwaltung();
	
	public void laenderAufteilen(int anzahlSpieler, Spielerverwaltung spielerVw, Weltverwaltung weltVw)
	{
		int counter = 0;
		for(int i = 0;i < weltVw.getLaenderListe().length;i = i+anzahlSpieler)
		{
			for(int j = 0;j < anzahlSpieler;j++)
			{
				if(counter < weltVw.getLaenderListe().length)
				{
					weltVw.getLaenderListe()[counter].setBesitzer(spielerVw.getSpielerArray()[j]);
					counter++;
				}
			}
		}
		
	}
}
