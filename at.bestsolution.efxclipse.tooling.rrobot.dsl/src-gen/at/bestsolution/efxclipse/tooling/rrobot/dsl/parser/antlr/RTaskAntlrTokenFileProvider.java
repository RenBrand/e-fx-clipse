/*
* generated by Xtext
*/
package at.bestsolution.efxclipse.tooling.rrobot.dsl.parser.antlr;

import java.io.InputStream;
import org.eclipse.xtext.parser.antlr.IAntlrTokenFileProvider;

public class RTaskAntlrTokenFileProvider implements IAntlrTokenFileProvider {
	
	public InputStream getAntlrTokenFile() {
		ClassLoader classLoader = getClass().getClassLoader();
    	return classLoader.getResourceAsStream("at/bestsolution/efxclipse/tooling/rrobot/dsl/parser/antlr/internal/InternalRTask.tokens");
	}
}
