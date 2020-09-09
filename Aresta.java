package grafo;

public class Aresta {
	Vertice vizinho;
	float peso;
	
	public Aresta(Vertice vizinho, float peso) {
		this.vizinho = vizinho;
		this.peso = peso;
	}
}