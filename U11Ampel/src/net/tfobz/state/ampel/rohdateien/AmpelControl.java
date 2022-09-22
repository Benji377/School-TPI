package net.tfobz.state.ampel.rohdateien;

/**
 * Dieses Singleton wird verwendet um den synchronisierten Zugriff auf alle 
 * Ampeln durchzuführen. Nur wenn der Zustand über dieses Singleton verfügt
 * kann er arbeiten. Dadurch wird verhindert, dass andere Zustände parallel
 * arbeiten können. Denn auch sie brauchen dieses Singleton um arbeiten zu
 * können
 * @author Michael Wild
 */
public class AmpelControl 
{
	private static final AmpelControl INSTANCE = new AmpelControl();
	
	private AmpelControl() {}
	
	public static AmpelControl getInstance() {
		return INSTANCE;
	}
}
