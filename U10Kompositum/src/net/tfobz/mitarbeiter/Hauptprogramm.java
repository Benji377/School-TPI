package net.tfobz.mitarbeiter;

public class Hauptprogramm {

	public static void main(String[] args) {
		
		/*
		 * Müller, +39 329 123 4500, Vorstand
		 * ..Fischer, +39 329 123 4502, Vertrieb
		 * ....Elsler, +39 329 123 4504
		 * ....Mader, +39 329 123 4503
		 * ..Hintner, +39 329 123 4508, Entwicklung
		 * ....Auer, +39 329 123 4505, Technologie
		 * ......Alber, +39 329 123 4507
		 * ......Rohrer, +39 329 123 4506
		 * ....Schmidt, +39 329 123 4509
		 * ..Meier, +39 329 123 4501
		 */
		
		Abteilungsleiter al1 = new Abteilungsleiter("Müller", "+39 329 123 4500", "Vorstand");
		Abteilungsleiter al2 = new Abteilungsleiter("Fischer", "+39 329 123 4502", "Vertrieb");
		Abteilungsleiter al3 = new Abteilungsleiter("Hintner", "+39 329 123 4508", "Entwicklung");
		Abteilungsleiter al4 = new Abteilungsleiter("Auer", "+39 329 123 4505", "Technologien");
		Arbeiter a1 = new Arbeiter("Elsler", "+39 329 123 4504");
		Arbeiter a2 = new Arbeiter("Mader", "+39 329 123 4503");
		Arbeiter a3 = new Arbeiter("Alber", "+39 329 123 4507");
		Arbeiter a4 = new Arbeiter("Rohrer", "+39 329 123 4506");
		Arbeiter a5 = new Arbeiter("Schmidt", "+39 329 123 4509");
		Arbeiter a6 = new Arbeiter("Meier", "+39 329 123 4501");
		// Müller:
		al1.add(al2);
		al1.add(al3);
		al1.add(a6);
		// Fischer:
		al2.add(a1);
		al2.add(a2);
		// Hintner:
		al3.add(al4);
		al3.add(a5);
		// Auer:
		al4.add(a3);
		al4.add(a4);
		
		print(al1, "");
	}
	/*
	 * Gibt die Arbeiter und Abteilungsleiter hierarchisch aus
	 * Übergeben wird je ein Mitarbeiter deren Hierarchie man
	 * ausgeben möchte. Methode ist rekursiv
	 * @text wird benutzt um den Abstand in Punkte anzugeben 
	 */
	public static void print(Mitarbeiter m, String text) {
		// Wenn es sich um ein einfacher Arbeiter handelt, dann nur ausgeben
		if (m instanceof Arbeiter) {
			System.out.print(text + m.toString());
		} else if (m instanceof Abteilungsleiter) {
			// Bei Abteilungsleiter geben wir die Person aus, aber 
			// kontrollieren auch deren unterstehenden
			System.out.print(text + m.toString());
			text += "..";
			// Für jede Person die dem Abteilungsleiter unterliegt wird die
			// Funktion nochmals aufgreufen
			for (int i = 0; i < ((Abteilungsleiter) m).getEmployeeCount(); i++) {
				print(((Abteilungsleiter) m).getEmployee(i), text);
			}
		} else {
			// Bei Fehlern
			System.out.println("ERROR: Unknown instance given");
		}
	}

}
