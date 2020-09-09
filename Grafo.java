package grafo;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Grafo {

	ArrayList<Vertice> v = new ArrayList();
	int valorV;
	float matrizDijkstra[][];
	float verifica[];
	ArrayList<Vertice> ordemTopologica = new ArrayList();
	ArrayList<String> acumula = new ArrayList();
	ArrayList<Vertice> listaIdsVisitados = new ArrayList();
	ArrayList<Grafo> grafosArray = new ArrayList();

	public void ligaVertices(Vertice v1, Vertice v2, float peso) {
		Aresta a1 = new Aresta(v2, peso);
		v1.arestas.add(a1);
		v1.nVizinhos++;
	}

	public void mostrarGrafo(Vertice v1) {

		if (v1.isVisitado == false) {
			v1.isVisitado = true;
			valorV++;
			v1.tempoVisita = valorV;
			for (int j = 0; j < v1.arestas.size(); j++) {
				mostrarGrafo(v1.arestas.get(j).vizinho);
			}
			valorV++;
			v1.tempoPreto = valorV;
			ordemTopologica.add(0, v1);
		}

	}

	public void CarregarArquivo() {
		try {
			Scanner in = new Scanner(
					new FileReader("C:\\Users\\gabri\\eclipse-workspace\\Grafo\\src\\grafo\\ncit30.txt"));

			int i = 0;
			while (in.hasNextLine()) {

				String line = in.nextLine();
				acumula.add(line);
				i++;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void CriarGrafo(Grafo grafo) {
		int qntCidadesVertices = Integer.parseInt(acumula.get(0));
		for (int i = 0; i < qntCidadesVertices; i++) {
			grafo.v.add(new Vertice(i));
			// System.out.println(grafo.v.get(i).id);
		}

		for (int i = 1; i < acumula.size(); i++) {// 1 até 30
			if (i <= acumula.size() - 2) {// 2 até 29
				for (int j = i + 1; j < acumula.size(); j++) {
					String[] distanciaXY1 = acumula.get(i).split(" ");
					int posicaoX1 = Integer.parseInt(distanciaXY1[0]);
					int posicaoY1 = Integer.parseInt(distanciaXY1[1]);
					String[] distanciaXY2 = acumula.get(j).split(" ");
					int posicaoX2 = Integer.parseInt(distanciaXY2[0]);
					int posicaoY2 = Integer.parseInt(distanciaXY2[1]);
					int auxX = (posicaoX1 - posicaoX2) * (posicaoX1 - posicaoX2);
					int auxY = (posicaoY1 - posicaoY2) * (posicaoY1 - posicaoY2);
					float peso = (float) Math.sqrt(auxX + auxY);
					Vertice v1 = grafo.v.get(i - 1);
					Vertice v2 = grafo.v.get(j - 1);
					grafo.ligaVertices(v1, v2, peso);
					grafo.ligaVertices(v2, v1, peso);

				}
			}
		}

//		float resultado = CaixeiroViajante(grafo);
//		System.out.println(resultado);
	}

	public float CaixeiroViajante() {
		verifica = new float[grafosArray.size()];
		int menorVertice = 0;
		float distancia = 0;
		int vertice = 0;
		verifica[menorVertice] = -1;	
		for (int i = 0; i < grafosArray.size(); i++) {
			if (i < grafosArray.size() - 1) {
				menorVertice = retornarMenorValor(grafosArray.get(vertice), vertice);
				System.out.println(menorVertice+" kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
				verifica[menorVertice]=-1;
				distancia += grafosArray.get(vertice).matrizDijkstra[menorVertice][0];
				
			} else {
				distancia += grafosArray.get(grafosArray.size() - 1).matrizDijkstra[0][0];
			}
			System.out.println("\nGrafo: "+ vertice);
			for (int j = 0; j < grafosArray.get(vertice).matrizDijkstra.length; j++) {
				System.out.print("Matriz[" + j + "] = " + grafosArray.get(vertice).matrizDijkstra[j][0] + " ");
				System.out.println("Matriz[" + j + "] = " + grafosArray.get(vertice).matrizDijkstra[j][1] + " ");
			}
			vertice = menorVertice;
		}
		return distancia;
	}

	public int retornarMenorValor(Grafo grafo, int id) {
		float menorValor = 2147483647;
		int vertice = 0;
		for (int i = 0; i < grafo.matrizDijkstra.length; i++) {
			if (verifica[i] != -1) {
				if (grafo.matrizDijkstra[i][0] < menorValor) {
					menorValor = grafo.matrizDijkstra[i][0];
					vertice = i;
				}
			}else {
				continue;
			}
		}
		return vertice;
	}

	public float atribuirValor(Grafo grafo) {
		float resul = 0;
		for (int i = 0; i < grafo.matrizDijkstra.length; i++) {
			if (grafo.matrizDijkstra[i][0] != 0.0 && verifica[i]!=-1) {
				resul = grafo.matrizDijkstra[i][0];
				return resul;
			}
		}
		return resul;
	}

	public boolean verificarId(int id) {
		for (int i = 0; i < listaIdsVisitados.size(); i++) {
			if (listaIdsVisitados.get(i).id == id) {
				return true;
			}else {
				continue;
			}
		}
		return false;
	}

	public void inicializaDijkstra() {
		for (int i = 0; i < matrizDijkstra.length; i++) {
			matrizDijkstra[i][0] = 0;
			matrizDijkstra[i][1] = -1;
		}
	}

	public void dijkstra(Grafo grafo, Vertice inicial) {
		ArrayList<Vertice> n = new ArrayList();
		ArrayList<Vertice> simulaHeap = new ArrayList();
		grafo.matrizDijkstra = new float[grafo.v.size()][2];
		inicializaDijkstra();
		inicial.dist = 0;
		float menorDist = 2147483647;
		int cont = 0;
		v.get(inicial.id).dist = 0;
		while (cont < v.size()) {
			Vertice aux = null;
			for (int i = 0; i < v.size(); i++) {
				if (!n.contains(v.get(i))) {
					if (v.get(i).dist < menorDist) {
						aux = v.get(i);
						menorDist = v.get(i).dist;
					}
				} else {
					continue;
				}
			}

			n.add(aux);
			cont++;
			// System.out.println(aux.id);

			for (int i = 0; i < aux.arestas.size(); i++) {
				// System.out.println(aux.arestas.size() + " vertice: "+aux.id);
				if (aux.arestas.get(i).vizinho.dist == 2147483647) {
					// System.out.println(aux.arestas.get(i).vizinho.id);
					aux.arestas.get(i).vizinho.dist = aux.dist + aux.arestas.get(i).peso;
					aux.arestas.get(i).vizinho.pai = aux.id;
					matrizDijkstra[aux.arestas.get(i).vizinho.id][0] = aux.arestas.get(i).peso + aux.dist;
					matrizDijkstra[aux.arestas.get(i).vizinho.id][1] = aux.id;
					v.get(aux.arestas.get(i).vizinho.id).dist = aux.arestas.get(i).peso + aux.dist;
				} else {
					if (aux.arestas.get(i).vizinho.dist > (aux.dist + aux.arestas.get(i).peso)) {
						// System.out.println(aux.arestas.get(i).vizinho.id);
						aux.arestas.get(i).vizinho.dist = aux.dist + aux.arestas.get(i).peso;
						aux.arestas.get(i).vizinho.pai = aux.id;
						matrizDijkstra[aux.arestas.get(i).vizinho.id][0] = aux.arestas.get(i).peso + aux.dist;
						matrizDijkstra[aux.arestas.get(i).vizinho.id][1] = aux.id;
						v.get(aux.arestas.get(i).vizinho.id).dist = aux.arestas.get(i).peso + aux.dist;
					}
				}

			}
			menorDist = 2147483647;

		}
	}

	public static void main(String[] args) {
		Grafo grafo = new Grafo();
		grafo.CarregarArquivo();
		for (int i = 0; i < Integer.parseInt(grafo.acumula.get(0)); i++) {
			grafo.grafosArray.add(new Grafo());
			grafo.grafosArray.get(i).CarregarArquivo();
			grafo.grafosArray.get(i).CriarGrafo(grafo.grafosArray.get(i));
			grafo.grafosArray.get(i).matrizDijkstra = new float[grafo.grafosArray.get(i).v.size()][2];
			grafo.grafosArray.get(i).dijkstra(grafo.grafosArray.get(i), grafo.grafosArray.get(i).v.get(i));
//			System.out.println("\nGrafo "+ i);
//			for (int j = 0; j < grafo.grafosArray.get(i).matrizDijkstra.length; j++) {
//				System.out.print("Matriz[" + j + "] = " + grafo.grafosArray.get(i).matrizDijkstra[j][0] + " ");
//				System.out.println("Matriz[" + j + "] = " + grafo.grafosArray.get(i).matrizDijkstra[j][1] + " ");
//			}
		}
		System.out.println(grafo.CaixeiroViajante());

	}
}