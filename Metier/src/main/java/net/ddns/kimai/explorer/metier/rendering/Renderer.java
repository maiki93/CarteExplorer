package net.ddns.kimai.explorer.metier.rendering;

// 2 strategies:
// 1. ask to Carte the list of RenederingCollections and call obj.fillFrame( ,optReplace)
//      pro : can decide here of the replacement strategy
//      cons : we ask...
// 2. ask to Carte to call directly obj.fillFrame()
//      logic (strategy replace, order of calls...) in Carte IMPLEMENTATION
public interface Renderer {

	Frame renderFrame(); 
}
