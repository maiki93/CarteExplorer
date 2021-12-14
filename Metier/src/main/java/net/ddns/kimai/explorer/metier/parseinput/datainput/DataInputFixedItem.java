package net.ddns.kimai.explorer.metier.parseinput.datainput;

import java.lang.reflect.Constructor;

import net.ddns.kimai.explorer.metier.carte.FixedItem;
import net.ddns.kimai.explorer.metier.carte.item.Morphologie;
import net.ddns.kimai.explorer.metier.parseinput.DataInput;
import net.ddns.kimai.explorer.metier.position.Position;

// DataInputItem<T> DataInput<T, Position, Random>
public class DataInputFixedItem extends DataInput<FixedItem, Position, RandomProperties> {
	
	// until now, only one
	// nbItem
	
	public DataInputFixedItem( Class<? extends FixedItem> clazz, Position position) {
		this.classItem = clazz;
		this.propsItem = position;
		this.random = false;
	}
	
	public DataInputFixedItem( Class<? extends FixedItem> clazz, RandomProperties randomP) {
		this.classItem = clazz;
		this.propsRandom = randomP;
		this.random = true;
	}
	
	// createItem, different with enumeration
//	public FixedItem createItem() {
//		//Class<? extends FixedItem> clazz = getItemClass();
//		// tmp = null
//		Class<?> tmp = this.classItem.getDeclaringClass();
//		// tmp2 Morphologie, ok
//		Class<?> tmp2 = this.classItem.getEnclosingClass();
//		// tmp2.
//		//FixedItem item = Morphologie.fromClass( this.classItem );
//		//FixedItem item = ((Morphologie)tmp2).fromClass( this.classItem );
//		
//		return item;
//	}
	
//	public FixedItem createItemFromEnum( Enum<?> clazz) {
//		//FixedItem item = Morphologie.fromClass( this.classItem );
//		FixedItem item = null;
//		Class<?> tmp = this.classItem.getDeclaringClass();
//		return item;
//	}
	
	public FixedItem createItem() {
		Class clazz3 = getItemClass().getSuperclass();
		//Class clazz = getItemClass();
		
		// to test many name() function
		@SuppressWarnings("unchecked")
		Object o = Enum.valueOf(clazz3, "MONTAGNE"); //getItemClass().getName());
		// here return Morphologie$2 (without full name)
		FixedItem objFixed = (FixedItem) o; 
		// or retrive from the parse line : M but need MONTAGNE
		
		//((Morphologie.MONTAGNE.getClass())clazz3).toString2();
		//((Class ) getItemClass()).toString2();
		//clazz.toString2();
		
		//FixedItem objFixed = Morphologie.fromClass( getItemClass() );
		//return Morphologie.MONTAGNE;
		return objFixed;
	}
	
	/*
	 * Test
	 * 
	Class<? extends FixedItem> clazz = data.getItemClass(); // Morphologie$2
	Class<?> clazz3 = data.getItemClass().getSuperclass(); // ok Morphologie
	String clazzName3 = clazz3.getName();
	String clazzName = data.getItemClass().getName(); // string morpho$2
	//Object obj = clazz.getClass();
	Class<? extends Morphologie> clazz2 = (Class<? extends Morphologie>) data.getItemClass();
	Object obj2 = clazz.asSubclass(FixedItem.class);
	Object[] obj3 = clazz.getClasses(); // []
	
	//((Enum)clazz.getClass()).ordinal();
	
	// @SuppressWarnings("unchecked")
	// Object o  = Enum.valueOf(Morphologie.class, "M"); // Exception
	//Object o2  = Enum.valueOf( clazz3, "M");
	
	boolean bol = clazz.isEnum(); // false
	boolean bol3 = clazz3.isEnum(); //true because of anonymous class
	boolean bol4  = Enum.class.isAssignableFrom(clazz); // better ? should be
	boolean bol5  = Enum.class.isAssignableFrom(clazz);
	//Enum<Morphologie> test = clazz3;
	
	try {
		Class clz = Class.forName(clazzName);
		String tmp = clz.toString();
		Class clz3 = Class.forName(clazzName3 );
		Constructor[] ctor = clz3.getConstructors();
		Constructor[] ctor2 = clz.getConstructors();
		// Exception Object o2 = Enum.valueOf( clz, "M");
		Object o2 = Enum.valueOf( clz3, "MONTAGNE"); // works fine
		// Exception Object o3 = Enum.valueOf(clz3, clazz.getName() );
		//Object o3 = Enum.valueOf(clz,""); // Morphologie$2 Not an enum type 
		FixedItem fixed = (Morphologie) o2;
		
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	// Object obj4 = clazz.getEnclosingClass();
	 try {
		Object obj4 = clazz.getMethod("createItem");
	} catch (NoSuchMethodException | SecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	// Object obj4

	 * 
	 * 
	 */
	

}
