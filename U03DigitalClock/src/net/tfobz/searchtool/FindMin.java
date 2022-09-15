package net.tfobz.searchtool;

public class FindMin extends Thread {
	int[] minArray;
	int temp;
	// Referenz zum Hauptprogramm
	SearchMain main;
	
	public FindMin(int[] mina, SearchMain m) {
		this.minArray = mina;
		this.main = m;
	}
	
	@Override
	public void run() {
		// Bringt die kleinste Zahl im Array an erster Stelle
		System.out.println("MinThread started");
		// Gibt dem Ladebalken das Maximum an
		main.minpro.setMaximum(this.minArray.length);
	     for(int i = 0; i<this.minArray.length; i++ ){
	         for(int j = i+1; j<this.minArray.length; j++){
	            if(this.minArray[i]>this.minArray[j]){
	               this.temp = this.minArray[i];
	               this.minArray[i] = this.minArray[j];
	               this.minArray[j] = temp;
	            }
	         }
	         // Für debug: Gibt die Prozente in der Konsole aus
	         System.out.println("Min prog: "+main.minpro.getString());
	         // Setzt den Ladebalken einen Schritt weiter
	         main.minpro.setValue(main.minpro.getValue()+1);
	     }
	     // Zuletzt wird das Ergebnis augegeben
	     main.mintext.setText(String.valueOf(minArray[0]));
	}
}
