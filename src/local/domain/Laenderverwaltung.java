package local.domain;

public class Laenderverwaltung {

	Weltverwaltung weltVw = new Weltverwaltung();
	Spielerverwaltung spielerVw = new Spielerverwaltung();
	
	public void laenderAufteilen(int anzahlSpieler)
	{
		for(int i = 0;i < weltVw.getLaenderListe().length;i = i+anzahlSpieler)
		{
			for(int j = 0;j < anzahlSpieler;j++)
			{
				if(i<weltVw.getLaenderListe().length)
				{
					weltVw.getLaenderListe()[i].setBesitzer(spielerVw.getSpielerArray()[j]);
				}
			}
		}
	}
}
