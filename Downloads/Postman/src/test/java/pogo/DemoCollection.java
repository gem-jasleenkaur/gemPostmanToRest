import com.fasterxml.jackson.annotation.JsonProperty;

public class DemoCollection {
    private Info info;
    private Item[] item;

    // Getters and setters

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Item[] getItem() {
        return item;
    }

    public void setItem(Item[] item) {
        this.item = item;
    }
}

class Info {
    @JsonProperty("_postman_id")
    private String postmanId;
    private String name;
    private String schema;
    @JsonProperty("_exporter_id")
    private String exporterId;

    // Getters and setters

    public String getPostmanId() {
        return postmanId;
    }

    public void setPostmanId(String postmanId) {
        this.postmanId = postmanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getExporterId() {
        return exporterId;
    }

    public void setExporterId(String exporterId) {
        this.exporterId = exporterId;
    }
}

class Item {
    private String name;
    private Event[] event;
    private Request request;
    private Response[] response;

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Event[] getEvent() {
        return event;
    }

    public void setEvent(Event[] event) {
        this.event = event;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response[] getResponse() {
        return response;
    }

    public void setResponse(Response[] response) {
        this.response = response;
    }
}

class Event {
    private String listen;
    private Script script;

    // Getters and setters

    public String getListen() {
        return listen;
    }

    public void setListen(String listen) {
        this.listen = listen;
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }
}

class Script {
    private String[] exec;
    private String type;

    // Getters and setters

    public String[] getExec() {
        return exec;
    }

    public void setExec(String[] exec) {
        this.exec = exec;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Request {
    private String method;
    private Header[] header;
    private Body body;
    private Url url;

    // Getters and setters

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Header[] getHeader() {
        return header;
    }

    public void setHeader(Header[] header) {
        this.header = header;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}

class Header {
    private String key;
    private String value;
    private String type;

    // Getters and setters

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

class Body {
    private String mode;
    private String raw;
    private Options options;

    // Getters and setters

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }
}

class Options {
    private Raw raw;

    // Getters and setters

    public Raw getRaw() {
        return raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }
}

class Raw {
    private String language;

    // Getters and setters

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}

class Url {
    private String raw;
    private String[] host;
    private String[] path;

    // Getters and setters

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String[] getHost() {
        return host;
    }

    public void setHost(String[] host) {
        this.host = host;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }
}

class Response {
    // Define Response properties here as needed
    // For simplicity, I'm not defining the entire structure
    // You can add properties as necessary
}
