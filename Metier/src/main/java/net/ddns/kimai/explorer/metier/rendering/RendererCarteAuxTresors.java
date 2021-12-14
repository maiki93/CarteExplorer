package net.ddns.kimai.explorer.metier.rendering;

import java.util.Optional;

// Nice, only interface
import net.ddns.kimai.explorer.metier.simulation.Carte;

import net.ddns.kimai.explorer.metier.carte.ActorsOnCarte;
import net.ddns.kimai.explorer.metier.carte.CollectableItemsOnCarte;
import net.ddns.kimai.explorer.metier.carte.Terrain;

// Will make all sense with communicating update of Position (Console, GUI) 

// Goal achieved :
// Did not break encapsulation in Carte and components (no getters added)
// Did not pollute Interface Carte, Terrain....to be Renderable, only implementation
// fillFrameWithLambda, give more control ( would need access to internal data for more, defaultType maybe not so bad)
//   => Tell , don't ask, and a bit "flexible"
// to check : formatFrame impelmented at some places.. hidden coupling not so nice.
//           => force all items to impelemnt something maybe
// Actually Renederer bound in Carte, but nothing fixed =>  SRP nice

// Tricky part with instanceof, user / implementation part should know what is correct
//  => but mainly difficult to TEST carte.getFrame() cause errors, certainly bad test
//  Or use injection dependencies again
public class RendererCarteAuxTresors implements Renderer {
	// oups... Carte contains Reneder and renderer contains Carte...
	//private Carte carte;
	// null by default, no Empty()...
	private final Optional<Renderable> actors_;
	private final Optional<Renderable> terrain_;
	private final Optional<Renderable> tresors_;
	
	// to break the interdependency
	//private Frame frame;
	private int[] dimensions;
	
	public RendererCarteAuxTresors( Carte carte, // dimension enought
									ActorsOnCarte actors,
								    Terrain terrain, 
								    CollectableItemsOnCarte tresors ) {
		this.dimensions = carte.getDimensions();
		// Mock instance are not renderable
		if( actors instanceof Renderable)
			this.actors_ = Optional.ofNullable( (Renderable) actors);
		else 
			this.actors_ = Optional.empty();
		if( terrain instanceof Renderable)
			this.terrain_ = Optional.ofNullable( (Renderable) terrain);
		else 
			this.terrain_ = Optional.empty();
		if( tresors instanceof Renderable)
			this.tresors_ = Optional.ofNullable( (Renderable) tresors);
		else 
			this.tresors_ = Optional.empty();
	}
	
	@Override
	public Frame renderFrame() {
		// marked automatically as final, content can be modified
		Frame frame = createFrame();
		// first version, only option strategyReplace
		actors_.ifPresent( act -> act.fillFrame( frame, Frame.REPLACEMENT.APPEND_BEFORE ));
		// with lambda , not so consice but more control (limited to public accessor)
		terrain_.ifPresent( t -> t.fillFrameWithLambda( 
									(str, pos) -> frame.setFrame( str, pos, Frame.REPLACEMENT.REPLACE )));
		// terrain_.ifPresent( t -> t.fillFrame(frame, Frame.REPLACEMENT.REPLACE));
		tresors_.ifPresent( t -> t.fillFrame(frame, Frame.REPLACEMENT.APPEND_BEFORE));
		return frame;
	}
	// factory to be independent of dimension 
	private Frame createFrame() {
		return new Frame2D( dimensions[0], dimensions[1] );
	}	
}