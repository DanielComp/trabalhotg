import java.util.ArrayList;

public class Hierholzer {
	
	static boolean eConexo(Grafo g){
		int n=0;
		ArrayList<Integer> fecho = new ArrayList<Integer>();
		fecho.add(0);
		
		do{
			for(int i: g.graph.get(fecho.get(n)).adjacentes){
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

		for(Adj j: g.graph){
			Adj j2 = new Adj();
			j2.adjacentes = (ArrayList<Integer>) j.adjacentes.clone();
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

}
