package com.example.lab7.functional;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import javax.swing.*;
import java.awt.*;

public class DirectedGraphWindow implements GraphWindow {

    @Override
    public void showGraphWindow(Graph<Integer, String> graph) {
        Layout<Integer, String> layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(400, 500));
        BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(450,550));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());

        JFrame frame = new JFrame("Окно представления графа");
        frame.getContentPane().add(vv);
        frame.pack();
        frame.setVisible(true);
    }
}
