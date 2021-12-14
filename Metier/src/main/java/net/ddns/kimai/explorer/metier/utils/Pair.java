package net.ddns.kimai.explorer.metier.utils;

import java.util.Objects;

public class Pair<I,V> {

	private final I item_;
	private final V value_;
			
	public Pair(I item, V value) {
		this.item_ = item;
		this.value_ = value;
	}
	
	static public <I,V> Pair<I, V> of(I item, V value) {
		return new Pair<I,V>(item, value);
	}

	public I item() { return item_; };
	public V value() { return value_; }

	@Override
	public int hashCode() {
		return Objects.hash(item_, value_);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		Pair other = (Pair) obj;
		return Objects.equals(item_, other.item_) && Objects.equals(value_, other.value_);
	}
}
