package net.ddns.kimai.explorer.metier.carte.collectionitems;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.function.BiConsumer;

// no dependencies on ConcreteItems

import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.Terrain;
import net.ddns.kimai.explorer.metier.position.Position;
import net.ddns.kimai.explorer.metier.position.Position2D;
import net.ddns.kimai.explorer.metier.rendering.Frame;
import net.ddns.kimai.explorer.metier.rendering.Renderable;
import net.ddns.kimai.explorer.metier.utils.Dimension;

public class Terrain2DArray<T extends FixedItem> implements Terrain, Renderable  {
	// implementation as array a bit tricky with generics
	// other possibility : say than paysage[][] contains FixedItem, a derived type is enought
	// pros and cons ??
	private final T[][] paysage;
	private final FixedItem typeDefault;
	
	@SuppressWarnings("unchecked")
	public Terrain2DArray(Class<T> clazz, Dimension dim, FixedItem typeDefault ) {
		this.typeDefault = typeDefault;
		this.paysage = (T[][]) Array.newInstance(clazz, dim.getElement(0), dim.getElement(1));
		Arrays.stream(paysage)
			  .forEach(a -> Arrays.fill(a, typeDefault));
	}
		
	public T getFixedItem(Position pos) {
		return paysage[ pos.getCartesianCoordinates()[0] ][ pos.getCartesianCoordinates()[1] ];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <I extends FixedItem> void setFixedItem(Position pos, I item) {
		paysage[ pos.getCartesianCoordinates()[0]][ pos.getCartesianCoordinates()[1]] = (T) item;
	}

// Rendering service
	@Override
	public Frame fillFrame(Frame frame, Frame.REPLACEMENT strategyReplace) {
		for( int i = 0; i < paysage.length; i++ )
			for( int j = 0; j < paysage[i].length; j++ ) {
				if( paysage[i][j] != this.typeDefault )
					frame.setFrame( paysage[i][j].toString(),
									Position2D.create(i, j),
									strategyReplace
								   );
		}
		return frame;
		// external librairies with Zip style function also exist
		//Arrays.stream(paysage).range(0, paysage.length )		
	}

	@Override
	public void fillFrameWithLambda(BiConsumer<String, Position> fillProcess) {
		// TODO Auto-generated method stub
		for( int i = 0; i < paysage.length; i++ )
			for( int j = 0; j < paysage[i].length; j++ ) {
				if( paysage[i][j] != this.typeDefault )
					fillProcess.accept( paysage[i][j].toString(),
										Position2D.create(i, j));
			}		
	}
}
