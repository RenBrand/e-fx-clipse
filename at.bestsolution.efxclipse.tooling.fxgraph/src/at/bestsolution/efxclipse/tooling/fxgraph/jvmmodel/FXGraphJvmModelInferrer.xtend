package at.bestsolution.efxclipse.tooling.fxgraph.jvmmodel
 
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociator
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.emf.ecore.EObject
import java.util.List
import com.google.inject.Inject
import org.eclipse.xtext.util.IAcceptor

/**
 * <p>Infers a JVM model from the source model.</p> 
 *
 * <p>The JVM model should contain all elements that would appear in the Java code 
 * which is generated from the source model. 
 * Other Xtend models link against the JVM model rather than the source model. The JVM
 * model elements should be associated with their source element by means of the
 * {@link IJvmModelAssociator}.</p>     
 */
class FXGraphJvmModelInferrer implements IJvmModelInferrer {

	@Inject 
	IJvmModelAssociator jvmModelAssociator

   	def dispatch infer(EObject e, IAcceptor<JvmDeclaredType> acceptor, boolean prelinkingPhase) {
   		
   	}
}
