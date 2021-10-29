package net.tfobz.searchtool;

public class FindMax extends Thread {
	int[] maxArray;
	int temp;
	SearchMain main;
	
	public FindMax(int[] mina, SearchMain m) {
		this.maxArray = mina;
		this.main = m;
	}
	
	@Override
	public void run() {
		// Bringt die kleinste Zahl im Array an erster Stelle
		System.out.println("MaxThread started");
		main.maxpro.setMaximum(this.maxArray.length);
	     for(int i = 0; i<this.maxArray.length; i++ ){
	         for(int j = i+1; j<this.maxArray.length; j++){
	            if(this.maxArray[i]>this.maxArray[j]){
	               this.temp = this.maxArray[i];
	               this.maxArray[i] = this.maxArray[j];
	               this.maxArray[j] = temp;
	            }
	         }
	         System.out.println("Max prog: "+main.minpro.getString());
	         main.maxpro.setValue(main.maxpro.getValue()+1);
	     }
	    main.maxtext.setText(String.valueOf(this.maxArray[this.maxArray.length-1]));
	}
}
