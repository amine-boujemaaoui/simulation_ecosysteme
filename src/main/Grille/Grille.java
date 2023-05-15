package main.Grille;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

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
	private Ecosysteme ecosysteme;

	public Grille(int nbCasesL, int nbCasesH, int nbPixelCoteCase, boolean contoure, Ecosysteme ecosysteme) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;
		this.cycle = 0;
		this.ecosysteme = ecosysteme;
		this.setContoure(false);

		window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase + 16, nbCasesH * nbPixelCoteCase + 119);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setTitle("Simulation ecosysteme");
		window.setLocationRelativeTo(null);
		// window.setResizable(false);
		window.setVisible(true);
		window.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE)
					Ecosysteme.simulate = !Ecosysteme.simulate;
				if (keyCode == KeyEvent.VK_S) {
					setContoure(!isContoure());
					ecosysteme.redessine();
				}
				if (keyCode == KeyEvent.VK_ENTER)
					ecosysteme.nextCycle();
				if (keyCode == KeyEvent.VK_UP) {
					ecosysteme.augmenterVistesseSimulation();
					ecosysteme.redessine();
				}
				if (keyCode == KeyEvent.VK_DOWN) {
					ecosysteme.diminuerVistesseSimulation();
					ecosysteme.redessine();
				}
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

		g.setColor(new Color(137, 165, 255));
		g.fillRect(0, 0, 100, 100);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 2));
		String etat = (Ecosysteme.simulate) ? "⏸" : "▶";
		g.drawString(etat, 25, 47);

		g.setColor(new Color(255, 124, 124));
		g.fillRect(80, 0, 220, 80);
		g.setColor(Color.BLACK);
		g.drawString("Cycle: " + cycle, 115, 49);

		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 5));
		g.drawString("Cycle suivant (Appuyer sur Entrer)", 320, 20);
		String sGrille = (contoure) ? "Masquer" : "Afficher";
		g.drawString("Grille (Appuyer sur S): " + sGrille, 320, 40);
		g.drawString("Vitesse (Appuyer sur ↑ ↓): " + ecosysteme.getVitesseSimulation(), 320, 60);

		for (i = 0; i < nbCasesL; i++)
			for (j = 0; j < nbCasesH; j++) {
				int cellX = (i * nbPixelCoteCase);
				int cellY = 80 + (j * nbPixelCoteCase);
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
		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g.drawLine(0, 80, nbCasesH * nbPixelCoteCase, 80);
		g2.setStroke(new BasicStroke(1));
		if (contoure) {
			for (i = 0; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
				g.drawLine(i, 80, i, nbCasesH * nbPixelCoteCase + 80);
			}
			for (j = 0; j <= nbCasesH * nbPixelCoteCase; j += nbPixelCoteCase) {
				g.drawLine(0, j + 80, nbCasesL * nbPixelCoteCase, j + 80);
			}
		}
		
		if(!Ecosysteme.simulate) {
			g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase));
			g.drawString("EN PAUSE", nbCasesL/2 * nbPixelCoteCase- 160, nbCasesH/2 * nbPixelCoteCase +100);
		}
	 
	}

	public boolean isContoure() {
		return contoure;
	}

	public void setContoure(boolean contoure) {
		this.contoure = contoure;
	}

}
