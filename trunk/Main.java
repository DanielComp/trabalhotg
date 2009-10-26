public class Main {
	public static void main(String[] args) {
		int[][] matriz = {{0,1,0,1,0,0,0,0},{1,0,1,1,1,0,0,0},{0,1,0,0,1,0,0,0},{1,1,0,0,0,1,1,0},{0,1,1,0,0,0,1,1},{0,0,0,1,0,0,1,0},{0,0,0,1,1,1,0,1},{0,0,0,0,1,0,1,0}};
//		0-1-2
//		|/ \|
//		3   4
//		|\ /|
//		5-6-7
		Grafo G = new Grafo(matriz);

		for(Integer v: Hierholzer.getCaminho(G))
			System.out.println(v);
		System.out.println(Hierholzer.eConexo(G));
	}

}
