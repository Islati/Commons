package com.caved_in.commons.yml;


import com.caved_in.commons.utilities.StringUtil;
import com.caved_in.commons.yml.converter.YamlConverter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.error.YAMLException;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.*;

import static org.reflections.ReflectionUtils.getAllFields;

public class YamledConfiguration<T extends YamlConfigurable> {
    private InternalConverter converter = new InternalConverter();

    /*
    Map of <Path, List<Comment>>.
    Path is the key in Yaml that would be annotated.
    Comments are the list (ordered) of comments on the field annotated.
     */
    private Map<String, ArrayList<String>> comments = new LinkedHashMap<>();

    /*
    Representer for Yaml; Holds the structure, formatting, and so forth.
     */
    private Representer yamlRepresenter = new Representer();

    /*
    SnakeYaml Yaml Write, converter, etc, that wiill handle the serialization.
     */
    private Yaml snakeYaml = null;

    private ConfigSection root;

    private ConfigMode configMode = ConfigMode.DEFAULT;

    private String[] configHeader = null;

    private T objectInstance;

    private boolean initialized = false;
    private boolean skipFailedObjects = false;

    public void init(T instance) {
        if (initialized) {
            throw new RuntimeException("Unable to re-initialize an instance of YamledConfiguration once it's been established.");
        }

        this.objectInstance = instance;

        DumperOptions yamlOptions = new DumperOptions();
        yamlOptions.setIndent(2);
        yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        yamlRepresenter.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        snakeYaml = new Yaml(new CustomClassLoaderConstructor(objectInstance.getClass().getClassLoader()), yamlRepresenter, yamlOptions);

        /*
        Configure the settings for serializing via the annotations present.
         */
        configureFromSerializeOptionsAnnotation();
        initialized = true;
    }

    public Class<? extends YamlConfigurable> getConfiguringClass() {
        if (!initialized) {
            throw new IllegalAccessError("Unable to retrieve the configuring class when it's not been initialized");
        }
        return objectInstance.getClass();
    }

    public String getPath(Field field) {
        if (doSkip(field)) {
            return null;
        }

        String path = "";

        switch (getConfigMode()) {
            case PATH_BY_UNDERSCORE:
                path = field.getName().replace("_", ".");
                break;
            case FIELD_IS_KEY:
                path = field.getName();
                break;
            case DEFAULT:
            default:
                String fieldName = field.getName();
                if (fieldName.contains("_")) {
                    path = field.getName().replace("_", ".");
                } else {
                    path = field.getName();
                }
                break;
        }

        if (field.isAnnotationPresent(Path.class)) {
            Path path1 = field.getAnnotation(Path.class);
            path = path1.value();
        }

        return path;
    }

    public Map<String, Object> getConfigurationMap() throws Exception {
        Map<String, Object> mappedConfiguration = new HashMap<>();

        Set<Field> configFields = getAllFields(objectInstance.getClass());

        for (Field field : configFields) {
            String path = getPath(field);

            if (path == null) {
                continue;
            }

            try {
                mappedConfiguration.put(path, field.get(objectInstance));
            } catch (IllegalAccessException e) {
                //todo look for failed objects and perform how it should based on serialize options.
            }
        }

        YamlConverter mapConverter = converter.getConverter(Map.class);
        return (Map<String, Object>) mapConverter.toConfig(HashMap.class, mappedConfiguration, null);
    }

    public InternalConverter getConverter() {
        return converter;
    }

    public YamlConverter getConverter(Class<?> clazz) {
        return converter.getConverter(clazz);
    }

    public void loadFromConfigurationMap(Map section) throws Exception {
        Set<Field> configFields = getAllFields(objectInstance.getClass());

        for (Field field : configFields) {
            String path = getPath(field);

            if (path == null) {
                continue;
            }

            if (Modifier.isPrivate(field.getModifiers())) {
                field.setAccessible(true);
            }

            converter.fromConfig(objectInstance, field, ConfigSection.convertFromMap(section), path);
        }
    }

    public Map<String, ArrayList<String>> getComments() {
        return comments;
    }

    public void setComments(Map<String, ArrayList<String>> comments) {
        this.comments = comments;
    }

    public void loadFromYaml(File file) throws InvalidConfigurationException {
        try (InputStreamReader fileReader = new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8"))) {
            Object object = snakeYaml.load(fileReader);

            if (object != null) {
                convertMapsToSections((Map<?, ?>) object, getRootConfigSection());
            }
        } catch (IOException | ClassCastException | YAMLException e) {
            throw new InvalidConfigurationException("Could not load YML", e);
        }
    }

    public void convertMapsToSections(Map<?, ?> input, ConfigSection section) {
        if (input == null) {
            return;
        }

        for (Map.Entry<?, ?> entry : input.entrySet()) {
            String key = entry.getKey().toString();
            Object value = entry.getValue();

            if (value instanceof Map) {
                convertMapsToSections((Map<?, ?>) value, section.create(key));
            } else {
                section.set(key, value, false);
            }
        }
    }

    public void saveToYaml(File file) throws InvalidConfigurationException {
        try (OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), Charset.forName("UTF-8"))) {
            String[] header = getConfigHeader();
            if (header != null) {
                for (String line : header) {
                    fileWriter.write("# " + line + "\n");
                }

                fileWriter.write("\n");
            }

            Integer depth = 0;
            ArrayList<String> keyChain = new ArrayList<>();
            String yamlString = snakeYaml.dump(root.getValues(true));
            StringBuilder writeLines = new StringBuilder();
            for (String line : yamlString.split("\n")) {
                if (line.startsWith(new String(new char[depth]).replace("\0", " "))) {
                    keyChain.add(line.split(":")[0].trim());
                    depth = depth + 2;
                } else {
                    if (line.startsWith(new String(new char[depth - 2]).replace("\0", " "))) {
                        keyChain.remove(keyChain.size() - 1);
                    } else {
                        //Check how much spaces are infront of the line
                        int spaces = 0;
                        for (int i = 0; i < line.length(); i++) {
                            if (line.charAt(i) == ' ') {
                                spaces++;
                            } else {
                                break;
                            }
                        }

                        depth = spaces;

                        if (spaces == 0) {
                            keyChain = new ArrayList<>();
                            depth = 2;
                        } else {
                            ArrayList<String> temp = new ArrayList<>();
                            int index = 0;
                            for (int i = 0; i < spaces; i = i + 2, index++) {
                                temp.add(keyChain.get(index));
                            }

                            keyChain = temp;

                            depth = depth + 2;
                        }
                    }

                    keyChain.add(line.split(":")[0].trim());
                }

                String search;
                if (keyChain.size() > 0) {
                    search = StringUtil.join(keyChain, ".");
                } else {
                    search = "";
                }


                if (comments.containsKey(search)) {
                    for (String comment : comments.get(search)) {
                        writeLines.append(new String(new char[depth - 2]).replace("\0", " "));
                        writeLines.append("# ");
                        writeLines.append(comment);
                        writeLines.append("\n");
                    }
                }

                writeLines.append(line);
                writeLines.append("\n");
            }

            fileWriter.write(writeLines.toString());
        } catch (IOException e) {
            throw new InvalidConfigurationException("Could not save YML", e);
        }
    }

    public void addComment(String key, String value) {
        if (!comments.containsKey(key)) {
            comments.put(key, new ArrayList<String>());
        }

        comments.get(key).add(value);
    }

    public void clearComments() {
        comments.clear();
    }

    public ConfigMode getConfigMode() {
        return ConfigMode.DEFAULT;
    }

    public void setConfigMode(ConfigMode mode) {
        this.configMode = mode;
    }

    public void setConfigHeader(String[] header) {
        this.configHeader = header;
    }

    public String[] getConfigHeader() {
        return configHeader;
    }

    /**
     * Change the behaviour to take when an object fails to serialize.
     * Assigning to True will mean when an object fails to serialize it's simply skipped.
     * Assigning to False will mean when an object fails to serialize it will throw an error.
     *
     * @param skip whether or not to skip failed objects.
     */
    public void setSkipFailedObjects(boolean skip) {
        this.skipFailedObjects = skip;
    }

    /**
     * @return True if serialization will skip objects
     * that fail to serialize, false to fail-out on an object that fails to convert.
     */
    public boolean skipFailedObjects() {
        return skipFailedObjects;
    }

    public ConfigSection getRootConfigSection() {
        if (root == null) {
            setRootConfigSection(new ConfigSection());
        }
        return root;
    }

    public void setRootConfigSection(ConfigSection root) {
        this.root = root;
    }

    /**
     * Add a Custom converter. A converter can take Objects and return a pretty Object which gets saved/loaded from
     * the converter. How a converter must be build can be looked up in the converter Interface.
     *
     * @param addConverter converter to be added
     * @throws InvalidConverterException If the converter has any errors this Exception tells you what
     */
    public void addConverter(Class addConverter) throws InvalidConverterException {
        converter.addCustomConverter(addConverter);
    }

    /**
     * <p/>
     * Determines whether or not a field should be skipped during the serialization process.
     * <p/>
     * <p/>
     * <i>This can be determined in a multitude of ways:</i>
     * <ul>
     * <li>Field is Transient.</li>
     * <li>Field is Final.</li>
     * <li>Field is annotated with the {@link YamlExclude} annotation and has a value of true</li>
     * <li>Field is static and not annotated with {@link PreserveStatic}</li>
     * </ul>
     *
     * @param field Field to check skip status of.
     * @return True if the field should be skipped during serialization, false otherwise.
     */
    public boolean doSkip(Field field) {
        if (Modifier.isTransient(field.getModifiers()) || Modifier.isFinal(field.getModifiers())) {
            return true;
        }

        /*
        Whether or not if we want to explicitly exclude a field from being configured.
         */
        if (field.isAnnotationPresent(YamlExclude.class)) {
            return true;
        }

        if (Modifier.isStatic(field.getModifiers())) {
            if (!field.isAnnotationPresent(PreserveStatic.class)) {
                return true;
            }

            PreserveStatic presStatic = field.getAnnotation(PreserveStatic.class);
            return !presStatic.value();
        }

        return false;
    }

    /**
     * Internally processes the {@link SerializeOptions} annotation on classes.
     * This allows you to configure the serialization of a class without explicitly defining them
     * inside of the class.
     */
    public void configureFromSerializeOptionsAnnotation() {
        //todo implement logic to configure from external class.
        if (!getClass().isAnnotationPresent(SerializeOptions.class)) {
            return;
        }

        SerializeOptions options = getClass().getAnnotation(SerializeOptions.class);
        setConfigHeader(options.configHeader());
        setConfigMode(options.configMode());
        setSkipFailedObjects(options.skipFailedObjects());
    }
}
