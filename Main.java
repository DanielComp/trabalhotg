import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class Main{
	
	Graph<Integer, String> gPrincipal;
	Graph<Integer,String> gPasseio;

	VisualizationViewer<Integer,String> vvPrincipal;
	VisualizationViewer<Integer,String> vvPasseio;
	
	Factory<Integer> vertexFactory;
	Factory<String> edgeFactory;
	
	JFrame frame;
	JPanel botoes;
	JPanel controlePanel;
	JButton geraCaminhoBtn;
	JButton proxBtn;
	JButton antBtn;
	JButton playBtn;
	JPanel grafo1;
	JTextField caminhoTxt;
	JMenuBar menuBar;
	JMenu modeMenu;
	JMenu grafoMenu;
	JMenuItem novoMenu;
	
	int etapaCaminho;
	ArrayList<Integer> caminho;
	
//	JMenuItem 
	
	public Main(){
		gPrincipal = new SparseGraph<Integer, String>();
		gPasseio = new SparseGraph<Integer, String>();
		//Cria as Factory's
		vertexFactory = new VertexFactory();
		edgeFactory = new EdgeFactory();
		
		desenhaJanela();
	}
	
	private void desenhaJanela(){
		//Layout usado para o grafo
		Layout<Integer, String> layout = new StaticLayout<Integer, String>(this.gPrincipal);
	    layout.setSize(new Dimension(500,500));
	    
	    Transformer<Integer,Paint> vertexFillPaint = new Transformer<Integer,Paint>() {
	        public Paint transform(Integer i) {
	        	return Color.decode("#ec5e00");
	        }
	    };
	    Transformer<Integer,Paint> vertexDrawPaint = new Transformer<Integer,Paint>() {
	        public Paint transform(Integer i) {
	            return Color.WHITE;
	        }
	    };
	    Transformer<String, Stroke> edgeStrokeTransformer =
	           new Transformer<String, Stroke>() {
	        public Stroke transform(String s) {
	             return new BasicStroke(1.5f);
	        }
	    };

	    vvPrincipal = new VisualizationViewer<Integer,String>(layout);
	    vvPrincipal.setDoubleBuffered(true);
	    vvPrincipal.setBackground(Color.LIGHT_GRAY);

	    TitledBorder borda1 = new TitledBorder("");
	    vvPrincipal.setBorder(borda1);
	    vvPrincipal.setPreferredSize(new Dimension(500,500));
	    vvPrincipal.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
	    vvPrincipal.getRenderContext().setVertexFillPaintTransformer(vertexFillPaint);
	    vvPrincipal.getRenderContext().setVertexDrawPaintTransformer(vertexDrawPaint);
	    vvPrincipal.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller());
	    vvPrincipal.getRenderContext().setEdgeStrokeTransformer(edgeStrokeTransformer);
		vvPrincipal.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
	    	    
	    EditingModalGraphMouse gm = new EditingModalGraphMouse(vvPrincipal.getRenderContext(), this.vertexFactory, this.edgeFactory);
	    vvPrincipal.setGraphMouse(gm);
	    
//	    sgv.vv2 = vv;
	    
	    frame = new JFrame("Os Caminhos de Euler");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(800, 300);
	    frame.setResizable(false);
	    
	    frame.setLayout(new BorderLayout(6,6));
	    
	    botoes = new JPanel();
		botoes.setLayout(new BoxLayout(botoes,BoxLayout.Y_AXIS));
		geraCaminhoBtn = new JButton("Corre Euler!");
		geraCaminhoBtn.addActionListener(new CaminhoListener());
		controlePanel = new JPanel();
		TitledBorder borda4 = new TitledBorder("Navegação");
		controlePanel.setBorder(borda4);
		
		antBtn = new JButton("Anterior");
		proxBtn = new JButton("Próximo");
		playBtn = new JButton("Play");
		antBtn.setEnabled(false);
		proxBtn.setEnabled(false);
		playBtn.setEnabled(false);
		antBtn.addActionListener(new NavegaListener());
		proxBtn.addActionListener(new NavegaListener());
		playBtn.addActionListener(new NavegaListener());
		
		controlePanel.add(antBtn);
		controlePanel.add(playBtn);
		controlePanel.add(proxBtn);
		
		TitledBorder borda3 = new TitledBorder("Opções");

		botoes.add(geraCaminhoBtn);
		botoes.add(controlePanel);
		botoes.setBorder(borda3);
		
		
		frame.add(botoes,BorderLayout.EAST);
		
		grafo1 = new JPanel();
		TitledBorder borda2 = new TitledBorder("Grafo Feito");
		borda2.setTitleColor(Color.BLACK);
	    borda2.setTitleJustification(2);
		grafo1.setBorder(borda2);
		grafo1.add(vvPrincipal);
		caminhoTxt = new JTextField("Caminho...");
		caminhoTxt.setEditable(false);
		caminhoTxt.setBackground(Color.WHITE);
		
		frame.add(grafo1, BorderLayout.CENTER);
		frame.add(caminhoTxt, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
		modeMenu = gm.getModeMenu(); // Obtain mode menu from the mouse
		
		modeMenu.setText("Modo");
		modeMenu.setIcon(null); // I'm using this in a main menu
		modeMenu.setPreferredSize(new Dimension(80,20)); // Change the size

		novoMenu = new JMenuItem("Novo");
		grafoMenu = new JMenu("Grafo");
		grafoMenu.add(novoMenu);
		
		menuBar.add(grafoMenu);
		menuBar.add(modeMenu);

		gm.setMode(EditingModalGraphMouse.Mode.EDITING); 
		frame.setJMenuBar(menuBar);
		frame.pack();
		frame.setVisible(true);
	}
	

	class VertexFactory implements Factory<Integer> {
		int countV = 0;
    	public Integer create() {
	        return new Integer(countV++);
	    }
    }
	class EdgeFactory implements Factory<String> {
		int countE = 0;
		public String create() {
	        return new String("e" + countE++);
	    }
    }
	
	class CaminhoListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			int [][] matriz = new int[gPrincipal.getVertexCount()][gPrincipal.getVertexCount()];
			for(String aresta: gPrincipal.getEdges()){
				Pair<Integer> p = gPrincipal.getEndpoints(aresta);
				matriz[p.getFirst()][p.getSecond()] = 1;
				matriz[p.getSecond()][p.getFirst()] = 1;
			}
			
			Grafo grafo = new Grafo(matriz);
			
			if(Hierholzer.temEuleriano(grafo)){
				etapaCaminho = 0;
				proxBtn.setEnabled(true);
				playBtn.setEnabled(true);

				caminho = Hierholzer.getCaminho(grafo);
				caminhoTxt.setText("Passeio gerado: " + caminho.toString());
				
				for(String aresta : gPrincipal.getEdges()){
					gPrincipal.removeEdge(aresta);
				}
				for(int i=0;i<caminho.size()-1;i++){
					gPrincipal.addEdge(""+i,caminho.get(i),caminho.get(i+1));
				}
				
				//vvPasseio = vvPrincipal;
				
				//Layout<Integer, String> layout = vvPasseio.getGraphLayout();
				//layout.setGraph(gPasseio);
				
			    Transformer<String, Paint> edgeColorTransformer =
			           new Transformer<String, Paint>() {
			        public Paint transform(String s) {
			             return (new Integer(s)<etapaCaminho)?Color.CYAN : Color.BLACK;
			        }
			    };
			    
			    Transformer<Integer,Paint> vertexFillPaint = new Transformer<Integer,Paint>() {
			        public Paint transform(Integer i) {
			        	return (i.equals(caminho.get(etapaCaminho))) ? Color.GREEN : Color.decode("#ec5e00") ;
			        }
			    };
				
				vvPrincipal.getRenderContext().setEdgeDrawPaintTransformer(edgeColorTransformer);
				vvPrincipal.getRenderContext().setVertexFillPaintTransformer(vertexFillPaint);
				

				frame.getContentPane().paintAll(frame.getContentPane().getGraphics());

			}
		}
	}
	
	class NavegaListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("Próximo")){
				etapaCaminho++;
				if(etapaCaminho==gPrincipal.getEdgeCount())
					proxBtn.setEnabled(false);
				antBtn.setEnabled(true);
				
				frame.getContentPane().paintAll(frame.getContentPane().getGraphics());
			}else if(e.getActionCommand().equals("Anterior")){
				etapaCaminho--;
				if(etapaCaminho==0)
					antBtn.setEnabled(false);
				proxBtn.setEnabled(true);
				frame.getContentPane().paintAll(frame.getContentPane().getGraphics());
			}else{
				etapaCaminho++;
			}
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
