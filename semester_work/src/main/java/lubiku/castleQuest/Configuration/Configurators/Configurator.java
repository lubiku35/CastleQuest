package lubiku.castleQuest.Configuration.Configurators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * <h2>Configurator</h2>
 * The Configurator interface defines methods for configuring objects using JSON data.
 * Classes implementing this interface can provide custom logic to handle the configuration based on their specific requirements.
 */
public interface Configurator {

    /**
     * <h3>configureJsonObject</h3>
     * Configures an object using the provided JsonObject.
     * @param config The JsonObject containing the configuration data.
     */
    void configureJsonObject(JsonObject config);

    /**
     * <h3>configureJsonArray</h3>
     * Configures an object using the provided JsonArray.
     * @param config The JsonArray containing the configuration data.
     */
    void configureJsonArray(JsonArray config);
}
