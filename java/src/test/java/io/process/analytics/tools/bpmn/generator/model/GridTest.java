package io.process.analytics.tools.bpmn.generator.model;

import static io.process.analytics.tools.bpmn.generator.model.Position.position;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GridTest {

    private Grid grid = new Grid();
    private Shape nodeA = Shape.shape("a");
    private Shape nodeB = Shape.shape("b");
    private Shape nodeC = Shape.shape("c");

    @Test
    public void should_move_element_when_adding_row() {
        grid.add(position(nodeA, 0, 0));
        grid.add(position(nodeB, 0, 1));
        grid.add(position(nodeC, 0, 2));


        grid.addRowAfter(1);

        assertThat(grid.getPositions()).containsExactly(
                position(nodeA, 0, 0),
                position(nodeB, 0, 1),
                position(nodeC, 0, 3)
        );
    }

    @Test
    public void should_not_move_element_when_adding_row_after_all_existing_elements() {
        grid.add(position(nodeA, 0, 0));
        grid.add(position(nodeB, 0, 1));
        grid.add(position(nodeC, 0, 2));


        grid.addRowAfter(2);

        assertThat(grid.getPositions()).containsExactly(
                position(nodeA, 0, 0),
                position(nodeB, 0, 1),
                position(nodeC, 0, 2)
        );
    }

}