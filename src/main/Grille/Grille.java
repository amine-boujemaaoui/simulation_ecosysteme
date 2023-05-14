package main.Grille;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Grille extends JPanel {
	private static final long serialVersionUID = 1L;
	private int nbCasesL, nbCasesH;
	private int nbPixelCoteCase;
	private CaseGrille[][] m;
	private int cycle;

	/**
	 * Constructeur.
	 * 
	 * @param nbCasesL        La largeur (en nombre de cases) de la grille.
	 * @param nbCasesH        La hauteur (en nombre de cases) de la grille.
	 * @param nbPixelCoteCase Nb de Pixel d'une case de la grille
	 **/
	public Grille(int nbCasesL, int nbCasesH, int nbPixelCoteCase) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;
		this.cycle = 0;

		JFrame window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase + 16, nbCasesH * nbPixelCoteCase + 79);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setVisible(true);

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
	
	public void addDisque(int i, int j, int rayon, Color c) {
		m[i][j].addDisque(rayon, c);
	}

	public void resetlDisques(int i, int j) {
		m[i][j].resetlDisques();
	}

	public void setCycle(int cycle) {
		this.cycle = cycle;
	}

	@Override
	// Fonction d'affichage de la grille appelée par repaint
	protected void paintComponent(Graphics g) {
		// Colorie les cases de casesAColorier
		super.paintComponent(g);
		int i, j;
		g.setFont(new Font("Sans-serif", Font.BOLD, 20));
		g.drawString("Cycle: " + cycle, 20, 25);
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++) {
				int cellX = (i * nbPixelCoteCase);
				int cellY = 40 + (j * nbPixelCoteCase);
				//g.setColor(m[i][j].getCouleur());
				//g.fillRect(cellX, cellY, nbPixelCoteCase, nbPixelCoteCase);
				
				g.drawImage(m[i][j].getIcon(),cellX,cellY, null);

				// Place des disques
				int x = 15;
				for (Disque d : m[i][j].lDisques) {
					g.setColor(d.getCouleur());
					g.fillOval(cellX + 5, cellY + 5 + x, d.getRayon(), d.getRayon());
					x += 15;
				}
			}

		// Redessine la grille
		/*
		 * g.setColor(Color.BLACK); g.drawRect(10, 10, nbCasesL * nbPixelCoteCase,
		 * nbCasesH * nbPixelCoteCase);
		 * 
		 * for (i = 10; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
		 * g.drawLine(i, 10, i, nbCasesH * nbPixelCoteCase + 10); }
		 * 
		 * for (i = 10; i <= nbCasesH * nbPixelCoteCase; i += nbPixelCoteCase) {
		 * g.drawLine(10, i, nbCasesL * nbPixelCoteCase + 10, i); }
		 */
	}

}
