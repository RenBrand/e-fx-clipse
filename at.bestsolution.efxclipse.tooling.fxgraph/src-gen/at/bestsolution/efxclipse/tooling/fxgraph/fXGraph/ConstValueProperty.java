/**
 */
package at.bestsolution.efxclipse.tooling.fxgraph.fXGraph;

import org.eclipse.xtext.common.types.JvmTypeReference;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Const Value Property</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.ConstValueProperty#getType <em>Type</em>}</li>
 *   <li>{@link at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.ConstValueProperty#getField <em>Field</em>}</li>
 * </ul>
 * </p>
 *
 * @see at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.FXGraphPackage#getConstValueProperty()
 * @model
 * @generated
 */
public interface ConstValueProperty extends SingleValueProperty
{
  /**
   * Returns the value of the '<em><b>Type</b></em>' containment reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Type</em>' containment reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Type</em>' containment reference.
   * @see #setType(JvmTypeReference)
   * @see at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.FXGraphPackage#getConstValueProperty_Type()
   * @model containment="true"
   * @generated
   */
  JvmTypeReference getType();

  /**
   * Sets the value of the '{@link at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.ConstValueProperty#getType <em>Type</em>}' containment reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Type</em>' containment reference.
   * @see #getType()
   * @generated
   */
  void setType(JvmTypeReference value);

  /**
   * Returns the value of the '<em><b>Field</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field</em>' attribute.
   * @see #setField(String)
   * @see at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.FXGraphPackage#getConstValueProperty_Field()
   * @model
   * @generated
   */
  String getField();

  /**
   * Sets the value of the '{@link at.bestsolution.efxclipse.tooling.fxgraph.fXGraph.ConstValueProperty#getField <em>Field</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field</em>' attribute.
   * @see #getField()
   * @generated
   */
  void setField(String value);

} // ConstValueProperty
