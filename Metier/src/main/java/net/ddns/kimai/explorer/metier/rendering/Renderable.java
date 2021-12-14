package net.ddns.kimai.explorer.metier.rendering;

import java.util.function.BiConsumer;

import net.ddns.kimai.explorer.metier.position.Position;

public interface Renderable {
	// with getFrame, can be called recursively
	// Frame getFrame();
	Frame fillFrame(Frame frame, Frame.REPLACEMENT replaceStrategy );
	// more configurable
	void fillFrameWithLambda( BiConsumer<String, Position> fillProcess);
	// intermediate, to delete
	// Frame fillFrame2(Frame frame, BiConsumer<String, Position> fillProcess);
	
}
