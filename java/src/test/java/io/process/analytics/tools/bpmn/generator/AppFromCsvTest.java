/*
 * Copyright 2021 Bonitasoft S.A.
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
package io.process.analytics.tools.bpmn.generator;

import static io.process.analytics.tools.bpmn.generator.App.runApp;
import static io.process.analytics.tools.bpmn.generator.AppTest.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class AppFromCsvTest {

    @Test
    public void main_generates_bpmn_with_simple_discovery_data() throws Exception {
        runAndCheckBpmnAndSvgGeneration("csv/PatientsProcess/nodeSimple.csv", "csv/PatientsProcess/edgeSimple.csv",
                "from_simple_csv_diagram.bpmn");
    }

    @Test
    public void main_generates_bpmn_with_gateways() throws Exception {
        runAndCheckBpmnAndSvgGeneration("csv/PatientsProcess/gateway_node_simple.csv",
                "csv/PatientsProcess/gateway_edge_simple.csv", "from_csv_with_gateways.bpmn");
    }

    @Test
    public void main_generates_bpmn_with_patients_process_discovery_data() throws Exception {
        runAndCheckBpmnAndSvgGeneration("csv/PatientsProcess/node.csv", "csv/PatientsProcess/edge.csv",
                "from_patients_csv_diagram.bpmn");
    }

    @Test
    public void main_generates_bpmn_with_vacation_request_bonita_discovery_data() throws Exception {
        runAndCheckBpmnAndSvgGeneration("csv/VacationRequestBonita/nodes.csv", "csv/VacationRequestBonita/edges.csv",
                "from_vacation_request_bonita_csv_diagram.bpmn");
    }

    @Test
    public void main_generates_bpmn_with_vacation_request_bonita_v2_discovery_data() throws Exception {
        runAndCheckBpmnAndSvgGeneration("csv/VacationRequestBonita_v2/nodes.csv",
                "csv/VacationRequestBonita_v2/edges.csv",
                "from_vacation_request_bonita_v2_csv_diagram.bpmn");
    }

    // =================================================================================================================
    // UTILS
    // =================================================================================================================

    private static void runAndCheckBpmnAndSvgGeneration(String inputNodesFileName, String inputEdgesFileName,
            String outputFileBaseName) throws IOException {
        runAndCheckBpmnGeneration(inputNodesFileName, inputEdgesFileName, outputFileBaseName + ".xml");
        runAndCheckSvgGeneration(inputNodesFileName, inputEdgesFileName, outputFileBaseName + ".svg");
    }

    private static void runAndCheckBpmnGeneration(String inputNodesFileName, String inputEdgesFileName,
            String outputFileName) throws IOException {
        String outputPath = outputPath(outputFileName);
        runApp("--input-type=CSV", "--output", outputPath, input(inputNodesFileName), input(inputEdgesFileName));
        assertBpmnOutFile(outputPath);
    }

    private static void runAndCheckSvgGeneration(String inputNodesFileName, String inputEdgesFileName, String outputFileName) throws IOException {
        String outputPath = outputPath(outputFileName);
        runApp("--input-type=CSV", "--output-type=SVG", "--output", outputPath, input(inputNodesFileName), input(inputEdgesFileName));
        assertSvgOutFile(outputPath);
    }

    private static String outputPath(String fileName) {
        return "target/test/output/AppFromCsvTest/" + fileName;
    }

}
