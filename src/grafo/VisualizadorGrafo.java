package grafo;

import java.awt.Color;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.filter.GraphDistanceFilter;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.Control;
import prefuse.controls.ControlAdapter;
import prefuse.controls.DragControl;
import prefuse.controls.PanControl;
import prefuse.controls.WheelZoomControl;
import prefuse.controls.ZoomControl;
import prefuse.controls.ZoomToFitControl;
import prefuse.data.Graph;
import prefuse.data.Tuple;
import prefuse.data.event.TupleSetListener;
import prefuse.data.io.DataIOException;
import prefuse.data.io.GraphMLReader;
import prefuse.data.tuple.TupleSet;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.LabelRenderer;
import prefuse.util.ColorLib;
import prefuse.util.PrefuseLib;
import prefuse.util.force.ForceSimulator;
import prefuse.visual.NodeItem;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;


public class VisualizadorGrafo  {
	
	private static Display d;
	
	public Display GrafoPrefuse()
	{
		
            Graph graph = null;
		try {
			graph = new GraphMLReader().readGraph("RedeDeCoautoria.xml");
		} catch (DataIOException e) {
			e.printStackTrace();
			System.err.println("Error loading graph. Exiting...");
			System.exit(1);
		}

		final Visualization vis = new Visualization();
		vis.setValue("graph.edges", null, VisualItem.INTERACTIVE, Boolean.FALSE);

		final TupleSet focusGroup = vis.getGroup(Visualization.FOCUS_ITEMS); 
            focusGroup.addTupleSetListener(new TupleSetListener() {
            public void tupleSetChanged(TupleSet ts, Tuple[] add, Tuple[] rem)
            {
                for ( int i=0; i<rem.length; ++i )
                    ((VisualItem)rem[i]).setFixed(false);
                for ( int i=0; i<add.length; ++i ) {
                    ((VisualItem)add[i]).setFixed(false);
                    ((VisualItem)add[i]).setFixed(true);
                }
                vis.run("draw");
            }
        });
        
        VisualGraph vg = vis.addGraph("graph", graph);
        NodeItem focus = (NodeItem)vg.getNode(0);
        PrefuseLib.setX(focus, null, 400);
        PrefuseLib.setY(focus, null, 300);
        focusGroup.setTuple(focus);
        final GraphDistanceFilter filter = new GraphDistanceFilter("graph", 4);

		LabelRenderer r = new LabelRenderer("name");
		r.setRoundedCorner(50, 200); 
		r.setMaxTextWidth(50);
		
		vis.setRendererFactory(new DefaultRendererFactory(r));

		ColorAction fill = new ColorAction("graph.nodes", VisualItem.FILLCOLOR, ColorLib.rgb(100, 216, 238));
		ColorAction text = new ColorAction("graph.nodes", VisualItem.TEXTCOLOR,
				ColorLib.gray(0));
		ColorAction edges = new ColorAction("graph.edges",
				VisualItem.STROKECOLOR, ColorLib.gray(200));
		fill.add("_fixed", ColorLib.rgb(0,176,209));
                fill.add("_highlight", ColorLib.rgb(255,63,63));
		
		graph.edges();
		
		ActionList color = new ActionList();
		color.add(fill);
		color.add(text);
		color.add(edges);
		
		ActionList draw = new ActionList();
                draw.add(filter);
   
		ForceDirectedLayout fdl = new ForceDirectedLayout("graph");
                ForceSimulator fsim = fdl.getForceSimulator();
                fsim.getForces()[0].setParameter(0, -3.2f);
        
		ActionList layout = new ActionList(Activity.INFINITY);
		layout.add(fdl);
		layout.add(fill);
		layout.add(new RepaintAction());
		
		vis.putAction("color", color);
		vis.putAction("layout", layout);
		vis.putAction("draw", draw);
		vis.runAfter("draw", "layout");

		Control hoverc = new ControlAdapter() {
		public void itemClicked(VisualItem item, MouseEvent e) 
		{
			if(item instanceof NodeItem)
			{
				if(item.get("name").toString().equals("IFSP"))
                                {
                                    filter.setDistance(4);
                                }else
                                {
                                    filter.setDistance(2);
                                }
				JPopupMenu jpub = new JPopupMenu();
				jpub.add(((String) item.get("name")));
                                nome =(String) item.get("name");
				jpub.show(e.getComponent(),(int) item.getX(),
	                            (int) item.getY());
				focusGroup.setTuple((NodeItem)item);
			}
		}};
		
		vis.run("color");
		vis.run("layout");
                
                d = new Display(vis);
		d.setSize(677, 627);
		d.addControlListener(new DragControl());
		d.addControlListener(new PanControl());
		d.addControlListener(new ZoomToFitControl());
                d.addControlListener(new ZoomControl());
                d.addControlListener(new WheelZoomControl());

                d.addControlListener(hoverc);

                d.setForeground(Color.GRAY);
                d.setBackground(Color.WHITE);
                
                return d;
	}
        
        private String nome="";
        public String getNodeSelecionado(){return nome;}
        

}
