import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.TitledBorder;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseGraph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class Main{
	
	Graph<Integer, String> gPrincipal;
//	Graph<Integer,String> gPasseio;

	VisualizationViewer<Integer,String> vvPrincipal;
//	VisualizationViewer<Integer,String> vvPasseio;
	
	Factory<Integer> vertexFactory;
	Factory<String> edgeFactory;
	
	JFrame frame;
	JPanel botoes;
	JPanel controlePanel;
	JButton geraCaminhoBtn;
	JButton proxBtn;
	JButton antBtn;
	JButton playBtn;
	JButton layoutRedondo;
	JPanel grafoPanel;
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
//		gPasseio = new SparseGraph<Integer, String>();
		//Cria as Factory's
		vertexFactory = new VertexFactory();
		edgeFactory = new EdgeFactory();
		
		desenhaJanela();
	}
	
	private void criaVV(){

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
		vvPrincipal.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<Integer,String>());
	}
	
	private void desenhaJanela(){
		
		criaVV();
	    	    
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
		
		layoutRedondo = new JButton("Layout Redondo");
		layoutRedondo.addActionListener(new LayoutRedondo());
		
		TitledBorder borda3 = new TitledBorder("Opções");

		botoes.add(geraCaminhoBtn);
		botoes.add(controlePanel);
		botoes.add(layoutRedondo);
		botoes.setBorder(borda3);
		
		
		frame.add(botoes,BorderLayout.EAST);
		
		grafoPanel = new JPanel();
		TitledBorder borda2 = new TitledBorder("Grafo Feito");
		borda2.setTitleColor(Color.BLACK);
	    borda2.setTitleJustification(2);
		grafoPanel.setBorder(borda2);
		grafoPanel.add(vvPrincipal);
		
		caminhoTxt = new JTextField("Caminho...");
		caminhoTxt.setEditable(false);
		caminhoTxt.setBackground(Color.WHITE);
		
		frame.add(grafoPanel, BorderLayout.CENTER);
		frame.add(caminhoTxt, BorderLayout.SOUTH);
		
		menuBar = new JMenuBar();
		modeMenu = gm.getModeMenu(); // Obtain mode menu from the mouse
		modeMenu.setText("Modo");
		modeMenu.setIcon(null);
		modeMenu.setPreferredSize(new Dimension(80,20));
		modeMenu.setMnemonic(KeyEvent.VK_M);

		novoMenu = new JMenuItem("Novo");
		novoMenu.addActionListener(new NovoGrafoListener());
		novoMenu.setAccelerator( KeyStroke.getKeyStroke( KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		novoMenu.setMnemonic(KeyEvent.VK_N);
		grafoMenu = new JMenu("Grafo");
		grafoMenu.add(novoMenu);
		grafoMenu.setMnemonic(KeyEvent.VK_G);
		
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
	        return new String("" + countE++);
	    }
    }
	
	class CaminhoListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Grafo grafo = new Grafo(gPrincipal);
			
			if(Hierholzer.temEuleriano(grafo)){
				etapaCaminho = 0;
				proxBtn.setEnabled(true);
				playBtn.setEnabled(true);

				caminho = new ArrayList<Integer>();
				for(int i : Hierholzer.getCaminho(grafo))
					caminho.add(grafo.getVertice(i));
				
				caminhoTxt.setText("Passeio gerado: " + caminho);
				
				for(String aresta : gPrincipal.getEdges()){
					gPrincipal.removeEdge(aresta);
				}
				for(int i=0;i<caminho.size()-1;i++){
					gPrincipal.addEdge(""+i,caminho.get(i),caminho.get(i+1));
				}
				
				DefaultModalGraphMouse gm = new DefaultModalGraphMouse();
				gm.setMode(DefaultModalGraphMouse.Mode.TRANSFORMING);
				vvPrincipal.setGraphMouse(gm);
				
				modeMenu.setEnabled(false);

				
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
	
	class LayoutRedondo implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Layout<Integer, String> layout = new CircleLayout<Integer, String>(gPrincipal);
		    layout.setSize(new Dimension(500,500));
		    vvPrincipal.setGraphLayout(layout);

			frame.getContentPane().paintAll(frame.getContentPane().getGraphics());
		}
		
	}
	
	class NovoGrafoListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			frame.dispose();
			new Main();
		}
		
	}
	
	public static void main(String[] args) {
		new Main();
	}
}
