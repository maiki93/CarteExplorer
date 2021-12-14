package net.ddns.kimai.explorer.metier.builders;

import net.ddns.kimai.explorer.metier.utils.Dimension;

public class ComponentCarteFactoryProvider {
	// createCarteFactory
//	public static FactoryComponent createFactory(int[] dimension) {
//		if( dimension.length == 2 )
//			return new FactoryComponents2D( ); // could pass the dimension there !
//		return null;
//	}
	
	public static FactoryComponent createFactory( Dimension dim) {
		if( dim.rank() == 2 )
			return new FactoryComponent2D( dim ); // could pass the dimension there !
		return null;
	}
}
