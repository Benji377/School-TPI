package net.tfobz.searchtool;

public class FindMin extends Thread {
	int[] minArray;
	int temp;
	SearchMain main;
	
	public FindMin(int[] mina, SearchMain m) {
		this.minArray = mina;
		this.main = m;
	}
	
	@Override
	public void run() {
		// Bringt die kleinste Zahl im Array an erster Stelle
		System.out.println("MinThread started");
		main.minpro.setMaximum(this.minArray.length);
	     for(int i = 0; i<this.minArray.length; i++ ){
	         for(int j = i+1; j<this.minArray.length; j++){
	            if(this.minArray[i]>this.minArray[j]){
	               this.temp = this.minArray[i];
	               this.minArray[i] = this.minArray[j];
	               this.minArray[j] = temp;
	            }
	         }
	         System.out.println("Min prog: "+main.minpro.getString());
	         main.minpro.setValue(main.minpro.getValue()+1);
	     }
	     main.mintext.setText(String.valueOf(minArray[0]));
	}
}
