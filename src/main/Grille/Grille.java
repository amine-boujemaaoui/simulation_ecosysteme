package main.Grille;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.ImageIcon;
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
	private Ecosysteme ecosysteme;
	private int caseX, caseY;
	private boolean showCase = false;
	private int decalageAffichage = 100;

	public Grille(int nbCasesL, int nbCasesH, int nbPixelCoteCase, boolean contoure, Ecosysteme ecosysteme) {
		int i, j;
		this.nbCasesL = nbCasesL;
		this.nbCasesH = nbCasesH;
		this.nbPixelCoteCase = nbPixelCoteCase;
		this.cycle = 0;
		this.ecosysteme = ecosysteme;
		this.setContoure(false);

		window = new JFrame();
		window.setSize(nbCasesL * nbPixelCoteCase + 16, nbCasesH * nbPixelCoteCase + 139);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(this);
		window.setTitle("Simulation ecosysteme");
		window.setLocationRelativeTo(null);
		// window.setResizable(false);
		window.setVisible(true);
		window.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_SPACE) {
					Ecosysteme.simulate = !Ecosysteme.simulate;
				}
				if (keyCode == KeyEvent.VK_S && showCase == false) {
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
				if (keyCode == KeyEvent.VK_R) {
					ecosysteme.initSimulation();
					ecosysteme.redessine();
				}

			}

		});

		window.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				int mouseCode = e.getButton();
				if (mouseCode == MouseEvent.BUTTON1) {
					caseX = e.getX() / nbPixelCoteCase;
					caseY = e.getY() / nbPixelCoteCase - 128 / nbPixelCoteCase;
					if (caseX >= 0 && caseX < nbCasesL && caseY >= 0 && caseY < nbCasesH) {
						showCase = !showCase;
						if (contoure)
							setContoure(false);
						ecosysteme.redessine();
					} else if (e.getX() < 105 && e.getY() < 127)
						Ecosysteme.simulate = !Ecosysteme.simulate;
					else if (e.getX() > 105 && e.getX() < 205 && e.getY() < 127) {
						ecosysteme.initSimulation();
						ecosysteme.redessine();
					}

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

	public void iconFond(int i, int j, ImageIcon icon) {
		m[i][j].setIcon(icon);
	}

	public void addDisqueAnimaux(ImageIcon icon, int i, int j, int rayon, Color c) {
		m[i][j].addDisqueAnimaux(rayon, c, icon);
	}

	public void addDisqueVegetaux(ImageIcon icon, int i, int j, int rayon, Color c) {
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
		g.fillRect(0, 0, decalageAffichage, decalageAffichage);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 2));
		String etat = (Ecosysteme.simulate) ? "⏸" : "▶";
		g.drawString(etat, decalageAffichage / 3, decalageAffichage / 2 + 8);

		g.setColor(new Color(135, 255, 151));
		g.fillRect(decalageAffichage, 0, decalageAffichage * 2, decalageAffichage);
		g.setColor(Color.BLACK);
		g.drawString("↺", decalageAffichage + decalageAffichage / 3, decalageAffichage / 2 + 8);

		g.setColor(new Color(255, 124, 124));
		g.fillRect(decalageAffichage * 2, 0, decalageAffichage * 3, decalageAffichage);
		g.setColor(Color.BLACK);
		g.drawString("Cycle: " + cycle, decalageAffichage * 2 + decalageAffichage / 3, decalageAffichage / 2 + 8);

		g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 5));
		String sGrille = (contoure) ? "Masquer " : "Afficher ";
		sGrille += (showCase) ? "la grille (Appuyer sur S) ❌" : "la grille (Appuyer sur S)";
		g.drawString("Cycle suivant (Appuyer sur Entrer)", decalageAffichage * 5 + 4, 14);
		g.drawString(sGrille + " ", decalageAffichage * 5 + 4, 34);
		g.drawString("Vitesse (Appuyer sur ↑ ↓): " + ecosysteme.getVitesseSimulation() + " ms",
				decalageAffichage * 5 + 4, 54);
		g.drawString("Relancer la simulation (Appuyer sur R)", decalageAffichage * 5 + 4, 74);
		g.drawString("Zoomer sur une case (cliquer sur une case)", decalageAffichage * 5 + 4, 94);

		if (!showCase) {
			for (i = 0; i < nbCasesL; i++)
				for (j = 0; j < nbCasesH; j++) {
					int cellX = (i * nbPixelCoteCase);
					int cellY = decalageAffichage + (j * nbPixelCoteCase);
					g.drawImage(m[i][j].getIcon().getImage(), cellX, cellY, nbPixelCoteCase, nbPixelCoteCase, null);
					for (Disque d : m[i][j].lAnimaux) {
						int rayon = d.getRayon();
						g.drawImage(d.getIcon().getImage(),
								cellX + nbPixelCoteCase / 2 - rayon / 2
										+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
								cellY + nbPixelCoteCase / 2 - rayon / 2
										+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
								rayon, rayon, null);
					}
					for (Disque d : m[i][j].lVegetaux) {
						int rayon = d.getRayon();
						g.drawImage(d.getIcon().getImage(),
								cellX + nbPixelCoteCase / 2 - rayon / 2
										+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
								cellY + nbPixelCoteCase / 2 - rayon / 2
										+ r.nextInt(-nbPixelCoteCase / 2, nbPixelCoteCase / 4 + 1),
								rayon, rayon, null);
						;
					}
				}
		} else {
			int cellX = 0;
			int cellY = decalageAffichage;
			int showCaseNbPixelCoteCase = nbPixelCoteCase * nbCasesL;
			g.drawImage(m[caseX][caseY].getIcon().getImage(), cellX, cellY, showCaseNbPixelCoteCase, showCaseNbPixelCoteCase,
					null);

			for (Disque d : m[caseY][caseY].lAnimaux) {
				int rayon = d.getRayon() * nbCasesL;
				g.drawImage(d.getIcon().getImage(),
						cellX + showCaseNbPixelCoteCase / 2 - rayon / 2
								+ r.nextInt(-showCaseNbPixelCoteCase / 2, showCaseNbPixelCoteCase / 4 + 1),
						cellY + showCaseNbPixelCoteCase / 2 - rayon / 2
								+ r.nextInt(-showCaseNbPixelCoteCase / 2, showCaseNbPixelCoteCase / 4 + 1),
						rayon, rayon, null);
			}
			for (Disque d : m[caseX][caseY].lVegetaux) {
				int rayon = d.getRayon() * nbCasesL;
				g.drawImage(d.getIcon().getImage(),
						cellX + showCaseNbPixelCoteCase / 2 - rayon / 2
								+ r.nextInt(-showCaseNbPixelCoteCase / 2, showCaseNbPixelCoteCase / 4 + 1),
						cellY + showCaseNbPixelCoteCase / 2 - rayon / 2
								+ r.nextInt(-showCaseNbPixelCoteCase / 2, showCaseNbPixelCoteCase / 4 + 1),
						rayon, rayon, null);
				;
			}
			g.setColor(Color.WHITE);
			g.setFont(new Font("Sans-serif", Font.BOLD, nbPixelCoteCase / 4));
			g.drawString("Niveau d'eau: " + this.ecosysteme.getZone(caseX, caseY).getEau(), nbPixelCoteCase,
					decalageAffichage + nbPixelCoteCase);
			g.drawString("Temperature: " + this.ecosysteme.getZone(caseX, caseY).getTemperature(), nbPixelCoteCase,
					decalageAffichage + nbPixelCoteCase + 20);
			g.drawString("Nombre d'insectes: " + this.ecosysteme.getZone(caseX, caseY).getNbInsecte(), nbPixelCoteCase,
					decalageAffichage + nbPixelCoteCase + 60);
			g.drawString("Nombre de mammiferes: " + this.ecosysteme.getZone(caseX, caseY).getNbMammifere(),
					nbPixelCoteCase, decalageAffichage + nbPixelCoteCase + 80);
			g.drawString("Nombre d'oiseaux: " + this.ecosysteme.getZone(caseX, caseY).getNbOiseau(), nbPixelCoteCase,
					decalageAffichage + nbPixelCoteCase + 100);
			g.drawString("Nombre d'arbres: " + this.ecosysteme.getZone(caseX, caseY).getNbArbre(), nbPixelCoteCase,
					decalageAffichage + nbPixelCoteCase + 140);
			g.drawString("Nombre de vivaces: " + this.ecosysteme.getZone(caseX, caseY).getNbVivace(), nbPixelCoteCase,
					decalageAffichage + nbPixelCoteCase + 160);
		}

		g.setColor(Color.BLACK);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(3));
		g.drawLine(0, decalageAffichage, nbCasesH * nbPixelCoteCase, decalageAffichage);
		g.drawLine(nbCasesH * nbPixelCoteCase, 0, nbCasesH * nbPixelCoteCase,
				nbCasesH * nbPixelCoteCase + decalageAffichage);
		g.drawLine(0, 0, 0, nbCasesH * nbPixelCoteCase + decalageAffichage);
		g.drawLine(0, 0, nbCasesH * nbPixelCoteCase, 0);
		g.drawLine(0, nbCasesH * nbPixelCoteCase + decalageAffichage, nbCasesH * nbPixelCoteCase,
				nbCasesH * nbPixelCoteCase + decalageAffichage);

		g2.setStroke(new BasicStroke(1));
		if (contoure) {
			for (i = 0; i <= nbCasesL * nbPixelCoteCase; i += nbPixelCoteCase) {
				g.drawLine(i, decalageAffichage, i, nbCasesH * nbPixelCoteCase + decalageAffichage);
			}
			for (j = 0; j <= nbCasesH * nbPixelCoteCase; j += nbPixelCoteCase) {
				g.drawLine(0, j + decalageAffichage, nbCasesL * nbPixelCoteCase, j + decalageAffichage);
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
