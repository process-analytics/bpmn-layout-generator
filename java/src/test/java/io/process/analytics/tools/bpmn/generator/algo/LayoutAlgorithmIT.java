package io.process.analytics.tools.bpmn.generator.algo;

import static io.process.analytics.tools.bpmn.generator.export.ASCIIExporter.toAscii;
import static io.process.analytics.tools.bpmn.generator.model.Edge.edge;
import static io.process.analytics.tools.bpmn.generator.model.Position.position;
import static io.process.analytics.tools.bpmn.generator.model.Shape.shape;
import static org.assertj.core.api.Assertions.assertThat;

import io.process.analytics.tools.bpmn.generator.model.Diagram;
import io.process.analytics.tools.bpmn.generator.model.Grid;
import io.process.analytics.tools.bpmn.generator.model.Shape;
import org.junit.jupiter.api.Test;

public class LayoutAlgorithmIT {

    Shape start = shape("start");
    Shape step1 = shape("step1");
    Shape step2 = shape("step2");
    Shape step3 = shape("step3");
    Shape step4 = shape("step4");
    Shape step5 = shape("step5");
    Shape gateway1 = shape("gateway1");
    Shape gateway2 = shape("gateway2");
    Shape gateway3 = shape("gateway3");
    Shape end = shape("end");
    Shape end2 = shape("end2");

    private LayoutAlgorithm layoutAlgorithm = new LayoutAlgorithm();

    @Test
    public void should_layout_diagram_with_cycle() {
        //+-----------------------------------+
        //|              step3                |
        //|start  step1         step2  end    |
        //+-----------------------------------+
        Diagram diagram = Diagram.builder()
                .shape(start)
                .shape(step1)
                .shape(step2)
                .shape(step3)
                .shape(end)
                .edge(edge(start, step1))
                .edge(edge(step1, step2))
                .edge(edge(step2, step3))
                .edge(edge(step3, step1))
                .edge(edge(step2, end))
                .build();


        Grid grid = layoutAlgorithm.layout(diagram);

        assertThat(toAscii(grid)).isEqualTo("Diagram:\n" +
                "+-----------------------------------+\n" +
                "|              step3                |\n" +
                "|start  step1         step2  end    |\n" +
                "+-----------------------------------+\n");
    }


    @Test
    public void should_layout_diagram_with_slit_join_demo() {
        //+---------------------------------------------------------------+
        //|                                   step4                       |
        //|                            gateway       gateway step5  end2  |
        //|start  step1  step2  gateway       step3                       |
        //|                            end                                |
        //+---------------------------------------------------------------+
        Diagram diagram = Diagram.builder()
                .shape(start)
                .shape(step1)
                .shape(step2)
                .shape(gateway1)
                .shape(end2)
                .shape(gateway2)
                .shape(step3)
                .shape(step4)
                .shape(gateway3)
                .shape(step5)
                .shape(end)
                .edge(edge(start, step1))
                .edge(edge(step1, step2))
                .edge(edge(step2, gateway1))
                .edge(edge(gateway1,end))
                .edge(edge(gateway1,gateway2))
                .edge(edge(gateway2,step3))
                .edge(edge(gateway2,step4))
                .edge(edge(step3, gateway3))
                .edge(edge(step4,gateway3))
                .edge(edge(gateway3,step5))
                .edge(edge(step5, end2))
                .build();


        Grid grid = layoutAlgorithm.layout(diagram);

        assertThat(toAscii(grid)).isEqualTo(toAscii(Grid.of(
                position(start, 0, 1),
                position(step1, 1, 1),
                position(step2, 2, 1),
                position(gateway1, 3, 1),
                position(end, 4, 0),
                position(gateway2, 4, 2),
                position(step3, 5, 1),
                position(step4, 5, 3),
                position(gateway3, 6, 2),
                position(step5, 7, 2),
                position(end2, 8, 2))));
    }
}
