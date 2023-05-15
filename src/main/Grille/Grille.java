package main.Grille;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import main.Ecosysteme;

public class Grille extends JPanel {
	private static final long serialVersionUID = 1L;
	private int nbCasesL, nbCasesH;
	private int nbPixelCoteCase;
	private CaseGrille[][] m;
	private int cycle;
	private boolean contoure;
	private JFrame window;
	public Random r = new Random();

	public Grille(int nbCasesL, int nbCasesH, int nbPixelCoteCase, boolean contoure) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;
		this.cycle = 0;
		this.setContoure(false);

		window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase + 8, nbCasesH * nbPixelCoteCase + 25 + 35);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);
		window.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE)
					Ecosysteme.simulate = !Ecosysteme.simulate;
				if (keyCode == KeyEvent.VK_S)
					setContoure(!isContoure());
			}
		});

		this.m = new CaseGrille[nbCasesL][nbCasesH];
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++)
				m[i][j] = new CaseGrille();
	}

	public void redessine() {
		repaint();
	}

	public void colorieFond(int i, int j, Color c) {
		m[i][j].setCouleur(c);
	}

	public void iconFond(int i, int j, Image icon) {
		m[i][j].setIcon(icon);
	}

	public void addDisqueAnimaux(Image icon, int i, int j, int rayon, Color c) {
		m[i][j].addDisqueAnimaux(rayon, c, icon);
	}

	public void addDisqueVegetaux(Image icon, int i, int j, int rayon, Color c) {
		m[i][j].addDisqueVegetaux(rayon, c, icon);
	}

	public void resetlDisques(int i, int j) {
		m[i][j].resetlDisques();
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	public JFrame getWindow() {
		return window;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int i, j;
		g.setFont(new Font("Sans-serif", Font.BOLD, 20));
		g.drawString("Cycle: " + cycle, 20, 25);
		g.drawString("Grille (Appuyer sur S): " + contoure, 600, 25);
		String etat = "";
		if (Ecosysteme.simulate)
			etat += "▶ en cours";
		else
			etat += "⏸ en pause";
		g.drawString("Etat (Appuyer sur espace): " + etat, 150, 25);
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++) {
				int cellX = (i * nbPixelCoteCase);
				int cellY = nbPixelCoteCase / 2 + (j * nbPixelCoteCase);
				g.drawImage(m[i][j].getIcon(), cellX, cellY, nbPixelCoteCase, nbPixelCoteCase, null);
				for (Disque d : m[i][j].lAnimaux) {
					int rayon = d.getRayon();
					g.drawImage(d.getIcon(),
							cellX + nbPixelCoteCase / 2 - rayon / 2
									+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
							cellY + nbPixelCoteCase / 2 - rayon / 2
									+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
							rayon, rayon, null);
				}
				for (Disque d : m[i][j].lVegetaux) {
					int rayon = d.getRayon();
					g.drawImage(d.getIcon(),
							cellX + nbPixelCoteCase / 2 - rayon / 2
									+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
							cellY + nbPixelCoteCase / 2 - rayon / 2
									+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
							rayon, rayon, null);
					;
				}
			}
		if (contoure) {
			for (i = 0; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
				g.drawLine(i, nbPixelCoteCase / 2, i, nbCasesH * nbPixelCoteCase + nbPixelCoteCase / 2);
			}
			for (j = 0; j <= nbCasesH * nbPixelCoteCase; j += nbPixelCoteCase) {
				g.drawLine(0, j + nbPixelCoteCase / 2, nbCasesL * nbPixelCoteCase, j + nbPixelCoteCase / 2);
			}
		}
	}

	public boolean isContoure() {
		return contoure;
	}

	public void setContoure(boolean contoure) {
		this.contoure = contoure;
	}

}
