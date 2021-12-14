package net.ddns.kimai.explorer.metier.rendering;

import java.util.Arrays;
import net.ddns.kimai.explorer.metier.position.Position;

public class Frame2D implements Frame {
	// good place to assign constant default value 
	private static final int SIZE_ENTRY = 10;
	// private static final String DEFAULT = ".";
	// tab final ? with immutable String ?
	private final String[][] tab;
	// Dimensions type to insert at a point ?
	private final int largeur;
	private final int hauteur; 
	
	public Frame2D( int largeur, int hauteur) {
		this.largeur = largeur;
		this.hauteur = hauteur;
		tab = new String[largeur][hauteur];
		Arrays.stream(tab)
		  .forEach(a -> Arrays.fill(a, Frame.REPLACEMENT.DEFAULT_STRING));
	}
	
	@Override
	public String toString() {
		// StringBuilder not thread-safe (vs Stringbuffer), faster... in theory
		StringBuilder oneLineFrame = new StringBuilder( largeur * SIZE_ENTRY );	
		StringBuilder fullFrame = new StringBuilder( largeur * SIZE_ENTRY * hauteur);
		// 2D here : print line-by-line, inner loop over columns
		for(int j=0; j < hauteur; j++) {
			oneLineFrame.setLength(0);
			for(int i=0; i < largeur; i++) {
				oneLineFrame.append( String.format("%"+SIZE_ENTRY+"s", tab[i][j]));
			}
			fullFrame.append(oneLineFrame.append("\n")); // "%n" not working ?
		}
		return fullFrame.toString();
	}

	@Override
	public void setFrame(String newValue, Position position, Frame.REPLACEMENT optReplace) {
		int i = position.getCartesianCoordinates()[0];
		int j = position.getCartesianCoordinates()[1];

		String prevValue = tab[i][j];
		tab[i][j] = optReplace.replace( prevValue, newValue);
	}

//	@Override
//	public Frame setFrame2(String newValue, Position position, REPLACEMENT optReplace) {
//		int i = position.getCartesianCoordinates()[0];
//		int j = position.getCartesianCoordinates()[1];
//
//		String prevValue = tab[i][j];
//		tab[i][j] = optReplace.replace( prevValue, newValue);
//		return this;
//	}

}
