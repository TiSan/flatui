package de.tisan.flatui.components.fframe;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.tisan.flatui.components.fcommons.Anchor;
import de.tisan.flatui.components.fcommons.FlatColors;
import de.tisan.flatui.components.fcommons.FlatLayoutManager;
import de.tisan.flatui.components.ftitlebar.DefaultFlatTitleBarListener;
import de.tisan.flatui.components.ftitlebar.FlatTitleBarWin10;

public abstract class FlatFrame extends JFrame {

	private static final long serialVersionUID = 7929945827359028981L;
	protected JPanel contentPane;
	private JLabel lbl;
	private Robot rob;
	private BufferedImage img;
	protected FlatLayoutManager layoutManager;
	protected FlatTitleBarWin10 bar;
	private int blurness;
	private boolean backgroundBlur;

	public FlatFrame(String title) {
		this(title, 5);
	}

	public FlatFrame(String title, int blurness) {
		this.blurness = blurness;
		this.backgroundBlur = true;
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(null);
		contentPane.setLayout(null);
		layoutManager = FlatLayoutManager.get(this);
		setContentPane(contentPane);
		contentPane.setBackground(FlatColors.BACKGROUND);
		setUndecorated(true);
		bar = new FlatTitleBarWin10(layoutManager, title);
		bar.setBounds(0, 0, getWidth(), 30);
		bar.setBackground(Color.black);
		bar.addFlatTitleBarListener(new DefaultFlatTitleBarListener(this));
		bar.setMaximizable(false);
		bar.setAnchor(Anchor.LEFT, Anchor.RIGHT);
		try {
			rob = new Robot();
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
		BufferedImage src = rob.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));

		fastblur(src, blurness);
		img = src;
		lbl = new JLabel(new ImageIcon(img));
		lbl.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(lbl);
		FlatFrame.this.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent e) {
				// bar.setBounds(0, 0, getWidth(), 30);
			}

			@Override
			public void componentResized(ComponentEvent e) {
				if (backgroundBlur) {
					refresh();
				}
				bar.setBounds(0, 0, getWidth(), 30);

			}

			@Override
			public void componentMoved(ComponentEvent e) {
				if (backgroundBlur) {
					refresh();
				}

			}

			@Override
			public void componentHidden(ComponentEvent e) {

			}
		});

	}

	public boolean isBackgroundBlur() {
		return backgroundBlur;
	}

	public void setBackgroundBlur(boolean backgroundBlur) {
		this.backgroundBlur = backgroundBlur;
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		if (isVisible()) {
			bar.setSize(width, 30);
		}

	}

	@Override
	public void setBounds(Rectangle r) {
		if (isVisible()) {
			bar.setSize(r.width, 30);
		}
		super.setBounds(r);
	}

	private void refresh() {
		if(img != null){
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
			int w = getSize().width;
			int h = getSize().height - 30;
			int x = 0;
			int y = 0;
			int locX = getLocation().x;
			int locY = getLocation().y;
			int xx = 0;
			int yy = 30;
			if (locX + w > dim.width) {
				x = (int) (dim.getWidth() - w);
				xx = -(int) (w - (dim.getWidth() - locX));
			} else if (locX < 0) {
				x = 0;
				xx = (int) (-locX);
			} else {
				x = locX;
			}
			if (locY + 30 + h > dim.height) {
				y = dim.height - h;
				yy = -(int) (h - (dim.getHeight() - locY));
			} else if (locY < 0) {
				y = 0;
				yy = 0;
			} else {
				y = locY + 30;
			}
			lbl.setIcon(new ImageIcon(img.getSubimage(x, y, w, h)));
			lbl.setBounds(xx, yy, getWidth(), getHeight() - 30);
		} else {
			lbl.setIcon(null);
		}
		
	}

	public void refreshBackground() {
		BufferedImage src = rob.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		fastblur(src, blurness);
		img = src;
		refresh();
	}

	public void removeBackground() {
		img = null;
		refresh();
		lbl.repaint();
	}

	public JPanel getContentPane() {
		return contentPane;
	}

	public BufferedImage getBackgroundImage() {
		return img;
	}

	public FlatLayoutManager getLayoutManager() {
		return layoutManager;
	}

	public FlatTitleBarWin10 getTitleBar() {
		return bar;
	}

	public int getBlurness() {
		return blurness;
	}

	public void setBlurness(int blurness) {
		this.blurness = blurness;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	void fastblur(BufferedImage img, int radius) {
		boolean b = true;
		for (int i = 0; i < 2; i++) {
			fastblur2(img, radius, b);
			b = false;
		}
	}

	void fastblur2(BufferedImage img, int radius, boolean shouldDark) {

		if (radius < 1) {
			return;
		}
		int w = img.getWidth();
		int h = img.getHeight();
		int wm = w - 1;
		int hm = h - 1;
		int wh = w * h;
		int div = radius + radius + 1;
		int r[] = new int[wh];
		int g[] = new int[wh];
		int b[] = new int[wh];
		int rsum, gsum, bsum, x, y, i, p, p1, p2, yp, yi, yw;
		int vmin[] = new int[Math.max(w, h)];
		int vmax[] = new int[Math.max(w, h)];

		int[] pix = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		int dv[] = new int[256 * div];
		for (i = 0; i < 256 * div; i++) {
			dv[i] = (i / div);
		}

		yw = yi = 0;

		for (y = 0; y < h; y++) {
			rsum = gsum = bsum = 0;
			for (i = -radius; i <= radius; i++) {
				p = pix[yi + Math.min(wm, Math.max(i, 0))];
				rsum += (p & 0xff0000) >> 16;
				gsum += (p & 0x00ff00) >> 8;
				bsum += p & 0x0000ff;
			}
			for (x = 0; x < w; x++) {

				r[yi] = dv[rsum];
				g[yi] = dv[gsum];
				b[yi] = dv[bsum];

				if (y == 0) {
					vmin[x] = Math.min(x + radius + 1, wm);
					vmax[x] = Math.max(x - radius, 0);
				}
				p1 = pix[yw + vmin[x]];
				p2 = pix[yw + vmax[x]];

				rsum += ((p1 & 0xff0000) - (p2 & 0xff0000)) >> 16;
				gsum += ((p1 & 0x00ff00) - (p2 & 0x00ff00)) >> 8;
				bsum += (p1 & 0x0000ff) - (p2 & 0x0000ff);
				yi++;
			}
			yw += w;
		}

		for (x = 0; x < w; x++) {
			rsum = gsum = bsum = 0;
			yp = -radius * w;
			for (i = -radius; i <= radius; i++) {
				yi = Math.max(0, yp) + x;
				rsum += r[yi];
				gsum += g[yi];
				bsum += b[yi];
				yp += w;
			}
			yi = x;
			for (y = 0; y < h; y++) {
				int ii = (0xff000000 | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum]);
				if (shouldDark) {
					// pix[yi] = ii;
					Color c = new Color(ii);
					c = c.darker().darker().darker().darker().darker();
					int[] tint = layoutManager.tint(c);
					c = new Color(tint[0], tint[1], tint[2]);
					pix[yi] = c.getRGB();
					// pix[yi] = (ii & 0xfefefe) >> 1;
					// pix[yi] = (pix[yi] & 0xfefefe) >> 1;
					// pix[yi] = (pix[yi] & 0xfefefe) >> 1;
				} else {
					pix[yi] = ii;
				}

				if (x == 0) {
					vmin[y] = Math.min(y + radius + 1, hm) * w;
					vmax[y] = Math.max(y - radius, 0) * w;
				}
				p1 = x + vmin[y];
				p2 = x + vmax[y];

				rsum += r[p1] - r[p2];
				gsum += g[p1] - g[p2];
				bsum += b[p1] - b[p2];

				yi += w;
			}
		}

	}

}
