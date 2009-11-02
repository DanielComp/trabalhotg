import java.util.ArrayList;
import java.util.HashMap;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.Pair;


public class Grafo{
		
	ArrayList<Adj> graph = new ArrayList<Adj>();
	private HashMap<Integer, Integer> mapaVertices2Lista = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> mapaLista2Vertices = new HashMap<Integer, Integer>();

	public Grafo(ArrayList<Adj> graph){
		this.graph = graph;
	}
	
	public Grafo(int[][] matriz){
		for(int i=0; i<matriz.length;i++){
			Adj linha = new Adj();
			linha.vertice=i;
			for(int j=0;j<matriz.length;j++){
				if(matriz[i][j]==1)
					linha.adjacentes.add(new Integer(j));
			}
			graph.add(linha);
		}
	}
	
	public Grafo(Graph<Integer, String> g){
		int i=0;
		for(Integer v : g.getVertices()){
			mapaLista2Vertices.put(i, v);
			mapaVertices2Lista.put(v, i++);
			Adj linha = new Adj();
			linha.vertice=i;
			graph.add(linha);

		}
		
		for(String e : g.getEdges()){
			Pair<Integer> p = g.getEndpoints(e);
			int primeiro = mapaVertices2Lista.get(p.getFirst());
			int segundo = mapaVertices2Lista.get(p.getSecond());
			graph.get(primeiro).adjacentes.add(segundo);
			graph.get(segundo).adjacentes.add(primeiro);
		}
	}
	
	public int getVertice(int index){
		return mapaLista2Vertices.get(index);
	}
	
	public int getListaIndex(int vertice){
		return mapaVertices2Lista.get(vertice);
	}
}
