import java.util.ArrayList;


public class Grafo{
		
	ArrayList<Adj> graph = new ArrayList<Adj>();

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
}
