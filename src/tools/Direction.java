package tools;

public enum Direction {
	NORD("Nord","-","y"),
	SUD("Sud","+","y"),
	EST("Est","+","x"),
	OUEST("Ouest","-","x");
	
	public String getSigne() {
		return signe;
	}

	public String getAxe() {
		return axe;
	}

	public String getNom() {
		return nom;
	}

	private String signe;
	private String axe;
	private String nom;
	
	Direction(String nom, String signe, String axe) {
		this.nom = nom;
		this.signe = signe;
		this.axe=axe;
	}
	
}
