package net.ddns.kimai.explorer.metier.simulation;

public class CarteExplorer implements Simulator {

	// necessary carte ? here or in MoveService, can be send in applyMove
	// To see how to implement MoveService / DoMove / DoRecolt
	protected Carte carte;
	protected MoveService moveService;
	
	// miss simulation parameter (nbStepMax / to add in Carte )
	public CarteExplorer( Carte carte,
						  MoveService moveService ) {
		this.carte = carte;
		this.moveService = moveService;
	}
	
	@Override
	public void checkInitialConfiguration()  {
		// TODO Auto-generated method stub	
	}
	
	@Override
	public long runSimulation() {
		long nbStep = 0;
		boolean moreActionToPerform = true;
				
		while( moreActionToPerform == true ) {
			moreActionToPerform = moveService.applyMove();
			nbStep++;
		}
		return nbStep-1;
	}
	

	
//	protected void checkInitialConfigurationImpl() 
//			throws MdJInitialConfigException, InvalidPositionCarteException {
//		// check if an aventurier is on an obstacle (montagne)
//		carte.getActors().stream()
//				.filter( entry -> 
//					entry.getKey().isNotAnObstacle( carte.getFixed( entry.getValue().getPositionAndOrientation().getPosition() ) ))
//				.findFirst()
//				.orElseThrow( () -> new RuntimeException());
						//"initiale position: " " rencontre le yeti sur une montagne => disqualification 😪") ); 				
				
//		for(Aventurier aventurier :  aventuriers) {
//			Position pos = aventurier.getPosition();
//			if(	!aventurier.canAccessToMorphologie( carte.getMorphologieAt(pos) )) {
// 				String copy = String.format(
//						"initiale position: %s rencontre le yeti sur une montagne => disqualification 😪", 
//						aventurier.getNom()); 
//				aventuriers.remove( aventurier );
//				throw new MdJInitialConfigException(copy);
//			}
//		}
		// check if 2 aventuriers on same position
//		for(Aventurier aventurier1 :  aventuriers) {
//			for(Aventurier aventurier2 :  aventuriers) {
//				if( aventurier1 != aventurier2  &&
//						aventurier1.getPosition().equals(aventurier2.getPosition()) ) {
//					String copy = String.format(
//							"initial position: %s et %s sont à la même position : %s 'arrivé(e) second(e) est disqualifié(e) : ",
//							aventurier1.getNom(), aventurier2.getNom(), aventurier2.getNom());
//					aventuriers.remove(aventurier2);
//					throw new MdJInitialConfigException(copy);
//					
//				}
//			}
//		}
//	}

}
