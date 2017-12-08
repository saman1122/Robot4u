package data;

import java.awt.Color;

import tools.Position;

public class Vetement {
	private TypeVetement type;
	private Color couleur;
	private Position position;
	
	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
	public TypeVetement getType() {
		return type;
	}
	public void setType(TypeVetement type) {
		this.type = type;
	}
	public Color getCouleur() {
		return couleur;
	}
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
}
