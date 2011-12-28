package is.currency.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;
import org.yaml.snakeyaml.introspector.Property;
import org.yaml.snakeyaml.nodes.CollectionNode;
import org.yaml.snakeyaml.nodes.MappingNode;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.NodeTuple;
import org.yaml.snakeyaml.nodes.SequenceNode;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.reader.UnicodeReader;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

public class Configuration extends ConfigurationNode {
	
    private Yaml yaml;
    private File file;
    private String header = null;

    public Configuration(File file) {
        super(new HashMap<String, Object>());

        DumperOptions options = new DumperOptions();

        options.setIndent(4);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yaml = new Yaml(new SafeConstructor(), new EmptyNullRepresenter(), options);

        this.file = file;
    }

    /**
     * Loads the configuration file. All errors are thrown away.
     */
    public void load() {
        FileInputStream stream = null;

        try {
            stream = new FileInputStream(file);
            read(yaml.load(new UnicodeReader(stream)));
        } catch (IOException e) {
            root = new HashMap<String, Object>();
        } catch (ConfigurationException e) {
            root = new HashMap<String, Object>();
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {}
        }
    }

    public void setHeader(String... headerLines) {
        StringBuilder header = new StringBuilder();

        for (String line : headerLines) {
            if (header.length() > 0) {
                header.append("\r\n");
            }
            header.append(line);
        }

        setHeader(header.toString());
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getHeader() {
        return header;
    }

    public boolean save() {
        FileOutputStream stream = null;

        File parent = file.getParentFile();

        if (parent != null) {
            parent.mkdirs();
        }

        try {
            stream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(stream, "UTF-8");
            if (header != null) {
                writer.append(header);
                writer.append("\r\n");
            }
            yaml.dump(root, writer);
            return true;
        } catch (IOException e) {
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {}
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    private void read(Object input) throws ConfigurationException {
        try {
            if (null == input) {
                root = new HashMap<String, Object>();
            } else {
                root = (Map<String, Object>) input;
            }
        } catch (ClassCastException e) {
            throw new ConfigurationException("Root document must be an key-value structure");
        }
    }

    public static ConfigurationNode getEmptyNode() {
        return new ConfigurationNode(new HashMap<String, Object>());
    }
}

class EmptyNullRepresenter extends Representer {

    public EmptyNullRepresenter() {
        super();
        this.nullRepresenter = new EmptyRepresentNull();
    }

    protected class EmptyRepresentNull implements Represent {
        public Node representData(Object data) {
            return representScalar(Tag.NULL, ""); // Changed "null" to "" so as to avoid writing nulls
        }
    }

    @Override
    protected NodeTuple representJavaBeanProperty(Object javaBean, Property property, Object propertyValue, Tag customTag) {
        NodeTuple tuple = super.representJavaBeanProperty(javaBean, property, propertyValue, customTag);
        Node valueNode = tuple.getValueNode();
        if (valueNode instanceof CollectionNode) {
            // Removed null check
            if (Tag.SEQ.equals(valueNode.getTag())) {
                SequenceNode seq = (SequenceNode) valueNode;
                if (seq.getValue().isEmpty()) {
                    return null; // skip empty lists
                }
            }
            if (Tag.MAP.equals(valueNode.getTag())) {
                MappingNode seq = (MappingNode) valueNode;
                if (seq.getValue().isEmpty()) {
                    return null; // skip empty maps
                }
            }
        }
        return tuple;
    }
    
}
