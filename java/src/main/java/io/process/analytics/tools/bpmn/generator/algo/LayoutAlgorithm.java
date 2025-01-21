package io.process.analytics.tools.bpmn.generator.algo;

import io.process.analytics.tools.bpmn.generator.model.Diagram;
import io.process.analytics.tools.bpmn.generator.model.Grid;

public class LayoutAlgorithm {

    private ShapeLayouter shapeLayouter = new ShapeLayouter();
    private ShapeSorter shapeSorter = new ShapeSorter();


    public Grid layout(Diagram diagram) {
        Diagram sortedDiagram = shapeSorter.sort(diagram);
        return shapeLayouter.layout(sortedDiagram);
    }

}
