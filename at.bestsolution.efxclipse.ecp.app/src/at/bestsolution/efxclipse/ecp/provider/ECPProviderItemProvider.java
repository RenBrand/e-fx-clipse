package at.bestsolution.efxclipse.ecp.provider;

import java.util.Collection;
import java.util.Map;
import java.util.WeakHashMap;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecp.core.ECPProject;
import org.eclipse.emf.ecp.core.util.ECPModelContext;
import org.eclipse.emf.ecp.internal.core.util.ChildrenListImpl;
import org.eclipse.emf.ecp.spi.core.InternalProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;

import at.bestsolution.efxclipse.runtime.ecp.dummy.DummyWorkspace;

@SuppressWarnings("restriction")
public class ECPProviderItemProvider extends ItemProviderAdapter implements ITreeItemContentProvider, IItemLabelProvider {

	InternalProvider provider;
	Map<Object, Object> parentsCache = new WeakHashMap<Object, Object>();


	public ECPProviderItemProvider(AdapterFactory adapterFactory, InternalProvider provider) {
		super(adapterFactory);
		this.provider = provider;
	}

	@Override
	public Collection<?> getChildren(Object object) {
		ECPModelContext modelContext = getModelContext(object);
		ChildrenListImpl childrenList = new ChildrenListImpl(object);
		provider.fillChildren(modelContext, object, childrenList);
		return childrenList;
	}

	@Override
	public String getText(Object object) {
		if (object instanceof ECPProject) {
			ECPProject project = (ECPProject) object;
			return project.getName();
		}

		IItemLabelProvider labelProvider = (IItemLabelProvider) DummyWorkspace.INSTANCE.getAdapterFactory().adapt(object,
				IItemLabelProvider.class);
		if (labelProvider != null)
			return labelProvider.getText(object);

		return object.toString();
	}

	@Override
	public Object getImage(Object object) {
		IItemLabelProvider labelProvider = (IItemLabelProvider) DummyWorkspace.INSTANCE.getAdapterFactory().adapt(object,
				IItemLabelProvider.class);
		if (labelProvider != null)
			return labelProvider.getImage(object);

		return super.getImage(object);
	}
	
	protected ECPModelContext getModelContext(Object element) {
		// TODO add proper implementation
		return null;
	}

}
