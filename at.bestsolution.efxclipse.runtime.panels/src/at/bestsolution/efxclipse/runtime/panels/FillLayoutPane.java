package at.bestsolution.efxclipse.runtime.panels;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.WritableBooleanValue;
import javafx.beans.value.WritableIntegerValue;
import javafx.geometry.Bounds;
import javafx.scene.Node;

public class FillLayoutPane extends AbstractLayoutPane<FillLayoutPane.FillData> {
	/**
	 * type specifies how controls will be positioned within the layout.
	 * 
	 * The default value is HORIZONTAL.
	 * 
	 * Possible values are:
	 * <ul>
	 * <li>HORIZONTAL: Position the controls horizontally from left to right</li>
	 * <li>VERTICAL: Position the controls vertically from top to bottom</li>
	 * </ul>
	 */
	public WritableBooleanValue horizontal = new SimpleBooleanProperty(this,
			"horizontal", true);

	/**
	 * marginWidth specifies the number of pixels of horizontal margin that will
	 * be placed along the left and right edges of the layout.
	 * 
	 * The default value is 0.
	 * 
	 * @since 3.0
	 */
	public WritableIntegerValue marginWidth = new SimpleIntegerProperty(this,
			"marginWidth", 0);

	/**
	 * marginHeight specifies the number of pixels of vertical margin that will
	 * be placed along the top and bottom edges of the layout.
	 * 
	 * The default value is 0.
	 * 
	 * @since 3.0
	 */
	public WritableIntegerValue marginHeight = new SimpleIntegerProperty(this,
			"marginHeight", 0);

	/**
	 * spacing specifies the number of pixels between the edge of one cell and
	 * the edge of its neighbouring cell.
	 * 
	 * The default value is 0.
	 * 
	 * @since 3.0
	 */
	public WritableIntegerValue spacing = new SimpleIntegerProperty(this,
			"spacing", 0);

	static class FillData {

		double defaultWidth = -1, defaultHeight = -1;
		double currentWhint, currentHhint, currentWidth = -1,
				currentHeight = -1;

		Size computeSize(Node control, double wHint, double hHint,
				boolean flushCache) {
			if (flushCache)
				flushCache();
			if (wHint == SWT_DEFAULT && hHint == SWT_DEFAULT) {
				if (defaultWidth == -1 || defaultHeight == -1) {
					// Size size = computeSize (wHint, hHint, flushCache);
					defaultWidth = control.prefWidth(hHint);
					defaultHeight = control.prefHeight(wHint);
				}
				return new Size(defaultWidth, defaultHeight);
			}
			if (currentWidth == -1 || currentHeight == -1
					|| wHint != currentWhint || hHint != currentHhint) {
				// Point size = control.computeSize (wHint, hHint, flushCache);
				currentWhint = wHint;
				currentHhint = hHint;
				currentWidth = control.getLayoutBounds().getWidth();
				currentHeight = control.getLayoutBounds().getHeight();
			}
			return new Size(currentWidth, currentHeight);
		}

		void flushCache() {
			defaultWidth = defaultHeight = -1;
			currentWidth = currentHeight = -1;
		}
	}

	protected Size computeSize(double wHint, double hHint, boolean flushCache) {
		Node[] children = getChildren().toArray(new Node[0]);
		int count = children.length;
		double maxWidth = 0, maxHeight = 0;
		for (int i = 0; i < count; i++) {
			Node child = children[i];
			double w = wHint, h = hHint;
			if (count > 0) {
				if (horizontal.get() && wHint != SWT_DEFAULT) {
					w = Math.max(0, (wHint - (count - 1) * spacing.get())
							/ count);
				}
				if (!horizontal.get() && hHint != SWT_DEFAULT) {
					h = Math.max(0, (hHint - (count - 1) * spacing.get())
							/ count);
				}
			}
			Size size = computeChildSize(child, w, h, flushCache);
			maxWidth = Math.max(maxWidth, size.width);
			maxHeight = Math.max(maxHeight, size.height);
		}
		double width = 0, height = 0;
		if (horizontal.get()) {
			width = count * maxWidth;
			if (count != 0)
				width += (count - 1) * spacing.get();
			height = maxHeight;
		} else {
			width = maxWidth;
			height = count * maxHeight;
			if (count != 0)
				height += (count - 1) * spacing.get();
		}
		width += marginWidth.get() * 2;
		height += marginHeight.get() * 2;
		if (wHint != SWT_DEFAULT)
			width = wHint;
		if (hHint != SWT_DEFAULT)
			height = hHint;
		return new Size(width, height);
	}

	Size computeChildSize(Node control, double wHint, double hHint,
			boolean flushCache) {
		FillData data = getConstraint(control);
		if (data == null) {
			data = new FillData();
			setConstraint(control, data);
		}
		Size size = null;
		if (wHint == SWT_DEFAULT && hHint == SWT_DEFAULT) {
			size = data.computeSize(control, wHint, hHint, flushCache);
		} else {
			// TEMPORARY CODE
			int trimX, trimY;
			trimX = trimY = 0;
			//FIXME
			// if (control instanceof Scrollable) {
			// Rectangle rect = ((Scrollable) control).computeTrim(0, 0, 0, 0);
			// trimX = rect.width;
			// trimY = rect.height;
			// } else {
			// trimX = trimY = control.getBorderWidth() * 2;
			// }
			double w = wHint == SWT_DEFAULT ? wHint : Math
					.max(0, wHint - trimX);
			double h = hHint == SWT_DEFAULT ? hHint : Math
					.max(0, hHint - trimY);
			size = data.computeSize(control, w, h, flushCache);
		}
		return size;
	}

	@Override
	protected void layoutChildren() {
		super.layoutChildren();
		
		Bounds rect = getLayoutBounds();
		Node [] children = getChildren ().toArray(new Node[0]);
		int count = children.length;
		if (count == 0) return;
		double width = rect.getWidth() - marginWidth.get() * 2;
		double height = rect.getHeight() - marginHeight.get() * 2;
		if (horizontal.get()) {
			width -= (count - 1) * spacing.get();
			double x = rect.getMinX() + marginWidth.get(), extra = width % count;
			double y = rect.getMinY() + marginHeight.get(), cellWidth = width / count;
			for (int i=0; i<count; i++) {
				Node child = children [i];
				double childWidth = cellWidth;
				if (i == 0) {
					childWidth += extra / 2;
				} else {
					if (i == count - 1) childWidth += (extra + 1) / 2;
				}
				child.resizeRelocate(x, y, childWidth, height);
				x += childWidth + spacing.get();
			}
		} else {
			height -= (count - 1) * spacing.get();
			double x = rect.getMinX() + marginWidth.get(), cellHeight = height / count;
			double y = rect.getMinY() + marginHeight.get(), extra = height % count;
			for (int i=0; i<count; i++) {
				Node child = children [i];
				double childHeight = cellHeight;
				if (i == 0) {
					childHeight += extra / 2;
				} else {
					if (i == count - 1) childHeight += (extra + 1) / 2;
				}
				child.resizeRelocate (x, y, width, childHeight);
				y += childHeight + spacing.get();
			}
		}
	}
}
