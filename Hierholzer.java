import java.util.ArrayList;

public class Hierholzer {
	
	static boolean conexo(Grafo g){
		int n=0;
		ArrayList<Integer> fecho = new ArrayList<Integer>();
		fecho.add(0);
		
		do{
			for(int i: g.graph.get(fecho.get(n)).adjacentes){
				System.out.println("--" + i);
				if(!fecho.contains(i))
					fecho.add(i);
			}
			++n;
		}while(g.graph.size()!=fecho.size() && n<fecho.size());
		
		return (g.graph.size()==fecho.size());
	}
	
	static ArrayList<Integer> getCaminho(Grafo g){
		
		ArrayList<Integer> caminho = new ArrayList<Integer>();
		ArrayList<Integer> caminhoTemp = new ArrayList<Integer>();
		ArrayList<Integer> esgotados = new ArrayList<Integer>();
		ArrayList<Adj> grafo = new ArrayList<Adj>();

		System.out.println(g.graph.size());
		System.out.println(grafo.size());
		System.out.println("----");
		for(Adj j: g.graph){
			Adj j2 = new Adj();
			j2.adjacentes = (ArrayList<Integer>)j.adjacentes.clone();
			j2.vertice = j.vertice;
			grafo.add(j2);
		}
				
		int i=0;
		int x=0;
		caminho.add(0);
		
		while(true){
			if(grafo.get(i).adjacentes.size()!=0){// testa se não é esgotado
				caminhoTemp.add(i);
				int j = grafo.get(i).adjacentes.get(0);
				grafo.get(i).adjacentes.remove(0);
				if(grafo.get(i).adjacentes.size()==0)
					esgotados.add(i);
				grafo.get(j).adjacentes.remove(new Integer(i));
				if(grafo.get(j).adjacentes.size()==0)
					esgotados.add(j);
				i=j;
			}else{
				if(caminhoTemp.size()!=0){
					Integer inicio = caminho.indexOf(caminhoTemp.get(0));
					caminho.addAll(inicio, caminhoTemp);
					caminhoTemp = new ArrayList<Integer>();
					x=0;
				}else
					x++;
				if(x>=caminho.size())
					break;
				i = caminho.get(x);
			}
		}
		
		return caminho;
	}

	public static void main(String[] args) {
		int[][] matriz = {{0,1,0,1,0,0,0,0},{1,0,1,1,1,0,0,0},{0,1,0,0,1,0,0,0},{1,1,0,0,0,1,1,0},{0,1,1,0,0,0,1,1},{0,0,0,1,0,0,1,0},{0,0,0,1,1,1,0,1},{0,0,0,0,1,0,1,0}};
//		0-1-2
//		|/ \|
//		3   4
//		|\ /|
//		5-6-7
		Grafo G = new Grafo(matriz);

		for(Integer v: getCaminho(G))
			System.out.println(v);
		System.out.println(conexo(G));
	}

}
