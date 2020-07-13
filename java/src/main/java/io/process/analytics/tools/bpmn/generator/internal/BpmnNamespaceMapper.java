package io.process.analytics.tools.bpmn.generator.internal;

import com.sun.xml.internal.bind.marshaller.NamespacePrefixMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

// https://stackoverflow.com/a/16981450/3180025
// <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" ...
//elementFormDefault="qualified" attributeFormDefault="unqualified" ... >
public class BpmnNamespaceMapper extends NamespacePrefixMapper {

    private static final String URI_BPMN_DI = "http://www.omg.org/spec/BPMN/20100524/DI";
    private static final String URI_DC = "http://www.omg.org/spec/DD/20100524/DC";
    private static final String URI_DI = "http://www.omg.org/spec/DD/20100524/DI";

    private static final Map<String, String> namespaces = new HashMap<>();
    static {
        namespaces.put(URI_BPMN_DI, "bpmndi");
        namespaces.put(URI_DC, "dc");
        namespaces.put(URI_DI, "di");
    }

    @Override
    public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
        return namespaces.getOrDefault(namespaceUri, suggestion);
    }

    @Override
    public String[] getPreDeclaredNamespaceUris() {
        Set<String> uris = namespaces.keySet();
        return uris.toArray(new String[0]);
    }

}
