package com.example.lab7.functional;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class StateGraphWindow implements GraphWindow {

    private Graph<Integer, String> graph;
    private final Matrix matrix;

    private JComboBox<String> activeVertexBox;

    private JComboBox<String> eventBox = new JComboBox<>();

    public StateGraphWindow(Graph<Integer, String> graph, Matrix matrix) {
        this.graph = graph;
        this.matrix = matrix;
    }

    @Override
    public void showGraphWindow() {
        Layout<Integer, String> layout = new CircleLayout<>(graph);
        layout.setSize(new Dimension(400, 500));
        BasicVisualizationServer<Integer,String> vv = new BasicVisualizationServer<>(layout);
        vv.setPreferredSize(new Dimension(450,550));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        vv.getRenderContext().setVertexFillPaintTransformer(v -> v.equals(1) ? Color.GREEN : Color.RED);
        JPanel boxPanel = new JPanel();
        JButton activateButton = new JButton("Сделать активной");
        Integer[][] matrixArray = matrix.getMatrix();
        String[] vertexList = new String[matrixArray.length];
        for (int i = 0; i < matrixArray.length; i++) {
            vertexList[i] = String.valueOf(i+1);
        }
        activeVertexBox = new JComboBox<>(vertexList);
        setEventBoxPayload(1);
        JButton sendEventButton = new JButton("Создать событие");
        boxPanel.setLayout(new GridLayout(2, 2, 3, 4));
        boxPanel.add(activeVertexBox);
        boxPanel.add(activateButton);
        boxPanel.add(eventBox);
        boxPanel.add(sendEventButton);
        JPanel graphPanel = new JPanel();
        graphPanel.add(vv);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(graphPanel, BorderLayout.NORTH);
        panel.add(boxPanel, BorderLayout.AFTER_LAST_LINE);
        JFrame frame = new JFrame("Окно представления графа");
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        activateButton.addActionListener(e -> {
            frame.setVisible(false);
            Integer selectedVertex = Integer.parseInt((String) Objects.requireNonNull(activeVertexBox.getSelectedItem()));
            setEventBoxPayload(selectedVertex);
            vv.getRenderContext().setVertexFillPaintTransformer(v -> v.equals(selectedVertex) ? Color.GREEN : Color.RED);
            frame.setVisible(true);
        });

        sendEventButton.addActionListener(e -> {
            frame.setVisible(false);
            Integer selectedVertex = Integer.parseInt((String) Objects.requireNonNull(activeVertexBox.getSelectedItem()));
            Integer selectedEvent = Integer.parseInt((String) Objects.requireNonNull(eventBox.getSelectedItem()));
            Integer targetVertex = matrixArray[selectedVertex-1][selectedEvent-1];
            vv.getRenderContext().setVertexFillPaintTransformer(v -> v.equals(targetVertex) ? Color.GREEN : Color.RED);
            activeVertexBox.getModel().setSelectedItem(String.valueOf(targetVertex));
            setEventBoxPayload(targetVertex);
            frame.setVisible(true);
        });
    }

    private void setEventBoxPayload(Integer activeVertex) {
        activeVertex--;
        Integer[][] matrixArray = matrix.getMatrix();
        String[] eventList = new String[countEvents(matrixArray[activeVertex])];
        int index = 0;
        for (int i = 0; i < matrixArray[activeVertex].length; i++) {
            if (matrixArray[activeVertex][i] != 0) {
                eventList[index] = String.valueOf(i + 1);
                index++;
            }
        }
        eventBox.removeAllItems();
        for (String event : eventList)
            eventBox.addItem(event);
    }

    private Integer countEvents(Integer[] activeVertex) {
        Integer count = 0;
        for (Integer vertex : activeVertex) {
            if (vertex != 0) {
                count++;
            }
        }
        return count;
    }
}
