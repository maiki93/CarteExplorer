package net.ddns.kimai.explorer.inputparameterprovider;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import net.ddns.kimai.explorer.metier.InputParameter;

// 2nd implementationd as service, role identical to FileConfigurationProvider
// to see how to deal with error in parsing / exception... caller has no access to out
public class InputParameterConsole implements InputParameter {
	
	// internal state, may give help to the user on which entry is mandatory / help ?
	// closing stream by exlplicit call ?
	private final InputStream in;
	private final PrintStream out;
	private final Scanner keyboard;

	// for test or for usage, IO base class certainly to provide
	// working, but to improve in test
	public InputParameterConsole( InputStream in,
								  PrintStream out) {
		this.in = in;
		this.out = out;
		keyboard = new Scanner(in);
		out.println("== Interactive Mode");
	}
	
	// expects null as end of input / may change later to Optional()
	@Override
	public String nextEntry() {
		String line;
		out.println("Add item : ");
		line = keyboard.nextLine();
		
		if( line.contains("help") )
			help();
		if( line.contains("end") )
			line = null;
			// may close all stream here ?
		return line;
	}
	
	private void help() {
		out.println("help: "
		          + "ex: C - 3 - 3");
	}
}
