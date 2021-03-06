package at.bestsolution.efxclipse.text.jface.quickassist;

import org.eclipse.jface.text.source.Annotation;

import at.bestsolution.efxclipse.text.jface.contentassist.ICompletionProposal;


/**
 * Quick assist processor for quick fixes and quick assists.
 * <p>
 * A processor can provide just quick fixes, just quick assists
 * or both.
 * </p>
 * <p>
 * This interface can be implemented by clients.</p>
 *
 * @since 3.2
 */
public interface IQuickAssistProcessor {

	/**
	 * Returns the reason why this quick assist processor
	 * was unable to produce any completion proposals.
	 *
	 * @return an error message or <code>null</code> if no error occurred
	 */
	String getErrorMessage();

	/**
	 * Tells whether this processor has a fix for the given annotation.
	 * <p>
	 * <strong>Note:</strong> This test must be fast and optimistic i.e. it is OK to return
	 * <code>true</code> even though there might be no quick fix.
	 * </p>
	 *
	 * @param annotation the annotation
	 * @return <code>true</code> if the assistant has a fix for the given annotation
	 */
	boolean canFix(Annotation annotation);

	/**
	 * Tells whether this assistant has assists for the given invocation context.
	 *
	 * @param invocationContext the invocation context
	 * @return <code>true</code> if the assistant has a fix for the given annotation
	 */
	boolean canAssist(IQuickAssistInvocationContext invocationContext);

	/**
	 * Returns a list of quick assist and quick fix proposals for the
	 * given invocation context.
	 *
	 * @param invocationContext the invocation context
	 * @return an array of completion proposals or <code>null</code> if no proposals are available
	 */
	ICompletionProposal[] computeQuickAssistProposals(IQuickAssistInvocationContext invocationContext);

}
