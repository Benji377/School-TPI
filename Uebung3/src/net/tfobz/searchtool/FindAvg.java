package net.tfobz.searchtool;

public class FindAvg extends Thread {
	int[] avgArray;
	int temp;
	SearchMain main;
	
	public FindAvg(int[] mina, SearchMain m) {
		this.avgArray = mina;
		this.main = m;
	}
	
	@Override
	public void run() {
		// Bringt die kleinste Zahl im Array an erster Stelle
		System.out.println("AvgThread started");
		main.avgpro.setMaximum(this.avgArray.length);
	     for(int i = 0; i<this.avgArray.length; i++ ){
	         for(int j = i+1; j<this.avgArray.length; j++){
	            if(this.avgArray[i]>this.avgArray[j]){
	               this.temp = this.avgArray[i];
	               this.avgArray[i] = this.avgArray[j];
	               this.avgArray[j] = temp;
	            }
	         }
	         System.out.println("Avg prog: "+main.avgpro.getString());
	         main.avgpro.setValue(main.avgpro.getValue()+1);
	     }
	     if (this.avgArray.length%2 == 0) {
	    	 Double result = ((double)(this.avgArray[this.avgArray.length/2]) + (double)(this.avgArray[this.avgArray.length/2+1]))/2;
	    	 // Bei gerade Zahlen gibt es zwei Mitten und man muss daraus den Mittelwert bilden
	    	 main.avgtext.setText(String.valueOf(result));
	     } else {
	    	 main.avgtext.setText(String.valueOf(this.avgArray[this.avgArray.length/2+1]));
	     }
	}
}
