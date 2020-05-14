/*
 * Copyright 2020 Bonitasoft S.A.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.process.analytics.tools.bpmn.generator.export;

import io.process.analytics.tools.bpmn.generator.converter.AlgoToDisplayModelConverter;
import io.process.analytics.tools.bpmn.generator.converter.AlgoToDisplayModelConverter.DisplayDimension;
import io.process.analytics.tools.bpmn.generator.converter.AlgoToDisplayModelConverter.DisplayFlowNode;
import io.process.analytics.tools.bpmn.generator.converter.AlgoToDisplayModelConverter.DisplayLabel;
import io.process.analytics.tools.bpmn.generator.converter.AlgoToDisplayModelConverter.DisplayModel;
import io.process.analytics.tools.bpmn.generator.model.Grid;
import io.process.analytics.tools.bpmn.generator.model.ShapeType;
import io.process.analytics.tools.bpmn.generator.model.Diagram;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class SVGExporter {

    private final AlgoToDisplayModelConverter converter = new AlgoToDisplayModelConverter();

    public byte[] export(Grid grid, Diagram diagram) {
        DisplayModel model = converter.convert(grid, diagram);

        // TODO introduce a method to generate escaped double quote and avoid double quote escaping when writing xml
        StringBuilder content = new StringBuilder();
        content.append("<svg xmlns=\"http://www.w3.org/2000/svg\" version=\"1.1\" width=\"")
                .append(model.width)
                .append("\" height=\"")
                .append(model.height).append("\">\n");

        for (DisplayFlowNode flowNode : model.flowNodes) {
            DisplayDimension flowNodeDimension = flowNode.dimension;
            DisplayLabel label = flowNode.label;
            DisplayDimension labelDimension = label.dimension;

            final int strokeWidth = flowNode.strokeWidth;

            if (flowNode.type == ShapeType.ACTIVITY) {
                log.debug("Exporting activity {}", flowNode.bpmnElementId);
                content.append("<rect ");
                content.append("x=\"").append(flowNodeDimension.x).append("\" ");
                content.append("y=\"").append(flowNodeDimension.y).append("\" ");
                content.append("width=\"").append(flowNodeDimension.width).append("\" ");
                content.append("height=\"").append(flowNodeDimension.height).append("\" ");
                content.append("rx=\"").append(flowNode.rx).append("\" ");
                // TODO extract colors
                content.append("fill=\"#E3E3E3\" stroke=\"#92ADC8\" ");
                content.append("stroke-width=\"").append(strokeWidth).append("\"/>\n");
            } else if (flowNode.type == ShapeType.EVENT) {
                log.debug("Exporting event {}", flowNode.bpmnElementId);
                // TODO improve cx/cy management
                //<ellipse cx="201" cy="291" rx="15" ry="15" fill="white" stroke="black" stroke-width="2" pointer-events="all"></ellipse>
                //                  <ellipse cx="752" cy="260" rx="16" ry="16" fill="white" stroke="black" stroke-width="5" pointer-events="all"></ellipse>
                content.append("<ellipse ")
                        .append("cx=\"").append(flowNodeDimension.x).append("\" ")
                        .append("cy=\"").append(flowNodeDimension.y).append("\" ")
                        .append("rx=\"").append(flowNodeDimension.width).append("\" ")
                        .append("ry=\"").append(flowNodeDimension.height).append("\" ")
                        // TODO extract colors
                        .append("fill=\"white\" stroke=\"black\" stroke-width=\"").append(strokeWidth).append("\" ")
                        .append("pointer-events=\"all\" />\n")
                //
                ;

            } else if (flowNode.type == ShapeType.GATEWAY) {
                log.debug("Exporting gateway {}", flowNode.bpmnElementId);

            }

            content.append("<text x=\"").append(labelDimension.x);
            content.append("\" y=\"").append(labelDimension.y);
            content.append("\" text-anchor=\"middle\" font-size=\"").append(label.fontSize);
            content.append("\" fill=\"#374962\">");
            content.append(label.text).append("</text>\n");
        }
        content.append("</svg>");
        return content.toString().getBytes();
    }

}
