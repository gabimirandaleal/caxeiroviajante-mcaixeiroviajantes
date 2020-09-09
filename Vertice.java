package grafo;

import java.util.ArrayList;

public class Vertice {
	int id;
	int nVizinhos;
	ArrayList<Aresta> arestas = new ArrayList<>();
	boolean isVisitado;
	int tempoVisita;
	int tempoPreto;
	float dist;
	int pai;
	
	public Vertice(int id) {
		this.id = id;
		this.nVizinhos = 0;
		this.isVisitado = false;
		this.tempoVisita = 0;
		this.tempoPreto = 0;
		this.dist = 2147483647;
		this.pai = -1;
	}
}