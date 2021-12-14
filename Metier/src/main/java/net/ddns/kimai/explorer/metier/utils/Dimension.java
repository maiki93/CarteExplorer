package net.ddns.kimai.explorer.metier.utils;

import java.util.List;
import java.util.Objects;

public class Dimension {

	private final List<Integer> dimensions;
	
	private Dimension(List<Integer> dimensions) {
		this.dimensions = dimensions;
	}

	static public Dimension of( Integer... values ) {
		return new Dimension( List.of( values ) );
	}
	
	public int rank() {
		return dimensions.size();
	}
	
	// throw IndexOutOfBoundsException
	public Integer getElement(int index) {
		return dimensions.get(index);
	}
	
	// to make transition all over
	public int[] toArray() {
		int[] arr = new int[ rank() ];
		for(int i = 0; i < rank() ; i++  ) { arr[i] = getElement(i); }
		return arr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(dimensions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dimension other = (Dimension) obj;
		return Objects.equals(dimensions, other.dimensions);
	}
	
	// to generalize, list.stream().join()...
	@Override
	public String toString() {
		return "Dim [" + getElement(0) +"," + getElement(1) + "]";
		//return "Dim:[" +
		//		dimensions.stream()
				//.toString()
				//.collect(Collectors.joining(","));
	}
}
