 
package at.bestsolution.efxclipse.testcases.e4.handlers;

import org.eclipse.e4.core.di.annotations.Execute;

@SuppressWarnings("restriction")
public class FunctionHandler {
	@Execute
	public void execute() {
		System.err.println("============================> Executed function key");
	}
		
}