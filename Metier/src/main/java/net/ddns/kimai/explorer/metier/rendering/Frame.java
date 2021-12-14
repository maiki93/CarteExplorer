package net.ddns.kimai.explorer.metier.rendering;

import net.ddns.kimai.explorer.metier.position.Position;

public interface Frame {
	// public static, it is fine in interface
	enum REPLACEMENT {
		REPLACE {
			@Override
			String replace(String prevValue, String newValue) {
				return newValue.replace(DEFAULT_STRING,"");
			}
		}, 
		APPEND_BEFORE {
			@Override
			String replace(String prevValue, String newValue) {
				return (newValue + prevValue).replace(DEFAULT_STRING,"");
			}
		}, APPEND_AFTER {
			@Override
			String replace(String prevValue, String newValue) {
				return (prevValue + newValue).replace(DEFAULT_STRING,"");
			}
		};
		public static String DEFAULT_STRING = "."; // default, must always be replaced if present
		abstract String replace(String prevValue, String newValue);		
	};
	
	void setFrame(String newValue, Position position, REPLACEMENT optReplace);
	// testing lamdba calls
	// Frame setFrame2(String newValue, Position position, REPLACEMENT optReplace);
	String toString(); // toJSON, to... Here or in a Renderer
}
