package main.Grille;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GrilleNature extends JPanel {
	private static final long serialVersionUID = 1L;
	private int nbCasesL, nbCasesH;
	private int nbPixelCoteCase;
	private CaseGrille[][] m;

	/**
	 * Constructeur.
	 * 
	 * @param nbCasesL        La largeur (en nombre de cases) de la grille.
	 * @param nbCasesH        La hauteur (en nombre de cases) de la grille.
	 * @param nbPixelCoteCase Nb de Pixel d'une case de la grille
	 **/
	public GrilleNature(int nbCasesL, int nbCasesH, int nbPixelCoteCase) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;

		JFrame window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase + 50, nbCasesH * nbPixelCoteCase + 50);
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

	public void addDisque(int i, int j, int rayon, Color c) {
		m[i][j].addDisque(rayon, c);
	}
	
	public void resetlDisques(int i, int j) {
		m[i][j].resetlDisques();
	}

	@Override
	// Fonction d'affichage de la grille appelée par repaint
	protected void paintComponent(Graphics g) {
		// Colorie les cases de casesAColorier
		super.paintComponent(g);
		int i, j;
		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++)

			{
				int cellX = 10 + (i * nbPixelCoteCase);
				int cellY = 10 + (j * nbPixelCoteCase);
				g.setColor(m[i][j].getCouleur());
				g.fillRect(cellX, cellY, nbPixelCoteCase, nbPixelCoteCase);

				// Place des disques
				int x = 20;
				for (Disque d : m[i][j].lDisques) {
					g.setColor(d.getCouleur());
					g.fillOval(cellX + 10, cellY + x, d.getRayon(), d.getRayon());
					x+=20;
				}

			}

		// Redessine la grille
		g.setColor(Color.BLACK);
		g.drawRect(10, 10, nbCasesL * nbPixelCoteCase, nbCasesH * nbPixelCoteCase);

		for (i = 10; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
			g.drawLine(i, 10, i, nbCasesH * nbPixelCoteCase + 10);
		}

		for (i = 10; i <= nbCasesH * nbPixelCoteCase; i += nbPixelCoteCase) {
			g.drawLine(10, i, nbCasesL * nbPixelCoteCase + 10, i);
		}
	}

}
