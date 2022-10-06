package pandero.app.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * La clase JsonReader es un utilitario que nos facilita la lectura de un archivo json.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class JsonReader {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    private JSONObject jsonObjectMain;
    private JSONArray jsonObjectMainArray = new JSONArray();

    /**
     * Constructor.
     *
     * @param filePath - El path del Json File.
     */
    public JsonReader(String filePath, boolean isJsonArray) {
        parseJSON(filePath, isJsonArray);
    }

    public JsonReader() {
    }

    /**
     * Crea un json array de un mapa.
     *
     * @param listOfMap value
     * @return jsonArray
     */
    public static JSONArray createJsonArray(List<Map<String, String>> listOfMap) {
        JSONArray jsonArray = new JSONArray();
        listOfMap.forEach(mapObject -> jsonArray.add(mapObject));
        return jsonArray;
    }

    /**
     * Crea un json array.
     *
     * @param list value
     * @return jsonArray
     */
    public static JSONArray createJsonArray(ArrayList<String> list) {
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(list);
        return jsonArray;
    }

    /**
     * Parsea el json file dentro de un JSONObject.
     *
     * @param filePath - El path del Json File.
     */
    private void parseJSON(String filePath, boolean isJsonArray) {
        try {
            FileReader reader = new FileReader(filePath);
            JSONParser jsonParser = new JSONParser();
            if (isJsonArray) {
                jsonObjectMainArray.addAll((JSONArray) jsonParser.parse(reader));
            } else {
                jsonObjectMain = (JSONObject) jsonParser.parse(reader);
            }
        } catch (FileNotFoundException ex) {
            log.error("FileNotFoundException cuando se leia el archivo de configuración ", ex);
        } catch (ParseException ex) {
            log.error("ParseException cuando se leia el archivo de configuración ", ex);
        } catch (IOException ex) {
            log.error("IOException cuando se leia el archivo de configuración ", ex);
        } catch (NullPointerException ex) {
            log.error("NullPointerException cuando se leia el archivo de configuración ", ex);
        }
    }

    /**
     * Obtiene el valor jsonObject dado el key del objeto json principal.
     *
     * @param key - Nombre del key.
     * @return El valor de jsonObject.
     */
    public JSONObject getJSONObject(String key) {
        return (JSONObject) jsonObjectMain.get(key);
    }

    /**
     * Obtiene el valor jsonArray dado el key del objeto json principal.
     *
     * @param key - Nombre del key.
     * @return El valor de jsonObject.
     */
    public JSONArray getJSONArray(String key) {
        return (JSONArray) jsonObjectMain.get(key);
    }

    /**
     * Obtiene el valor de jsonObject dado el key de un jsonObject.
     *
     * @param jsonObject - El Json Object.
     * @param key        - Nombre del key.
     * @return El valor de Json object del key.
     */
    private JSONObject getJSONObject(JSONObject jsonObject, String key) {
        return (JSONObject) jsonObject.get(key);
    }

    /**
     * Obtiene un string específico del key enviado de jsonObject.
     *
     * @param jsonObject - Json Object.
     * @param key        - Nombre del key.
     * @return String.
     */
    private String getKeyValueFromJSONObject(JSONObject jsonObject, String key) {
        return (String) jsonObject.get(key);
    }


    /**
     * Optiene los key de un jsonObject.
     *
     * @param key - Nombre del key.
     * @return El valor del key enviado.
     */
    public String getKeyValue(String key) {
        return (String) jsonObjectMain.get(key);
    }

    /**
     * Obtiene el jsonObject que coincide con los parámetros dados de un jsonArray.
     *
     * @param objectName - El jsonObject para el array.
     * @param idKey      - El key para los datos.
     * @param idValue    - Valor para la coincidencia.
     * @return El jsonObject que coincide con el key enviado.
     */
    private JSONObject getJSONObjectFromArrayById(String objectName, String idKey, String idValue) {
        JSONObject jsonObject = null;
        JSONArray jsonArray = (JSONArray) jsonObjectMain.get(objectName);
        for (Object anArr : jsonArray) {
            jsonObject = (JSONObject) anArr;
            if (jsonObject.get(idKey).equals(idValue)) {
                break;
            }
        }
        return jsonObject;
    }

    /**
     * Gets a jsonObject from the main json and then gets a value given the key.
     *
     * @param objectName - the name of the Object.
     * @param key        - to return the data.
     * @return the data.
     */
    public String getKeyValue(String objectName, String key) {
        JSONObject jsonObject = getJSONObject(objectName);
        return getKeyValueFromJSONObject(jsonObject, key);
    }


    /**
     * Obtiene un hashMap which key is the user type and value is a hashMap
     * that contains the values for Name, Password.
     *
     * @param objectName - the name of the Object.
     * @param idKey      - the key for the data.
     * @param idValue    - value for  matches.
     * @param key        - the current key value.
     * @return the hashMap.
     */
    public HashMap<String, Object> getArrayKeyValues(String objectName, String idKey, String idValue, String key) {
        JSONObject jsonObject = getJSONObjectFromArrayById(objectName, idKey, idValue);
        JSONArray arr = (JSONArray) jsonObject.get(key);

        HashMap<String, Object> arrMap = new HashMap<>();

        for (Object anArr : arr) {
            JSONObject jsonArrElement = (JSONObject) anArr;

            Iterator keysItrElement = jsonArrElement.keySet().iterator();
            String arrMapKey = (String) keysItrElement.next();

            JSONObject jsonSubElement = (JSONObject) jsonArrElement.get(arrMapKey);
            Iterator keysItrSubElement = jsonSubElement.keySet().iterator();

            HashMap<String, String> jsonMap = new HashMap<>();
            while (keysItrSubElement.hasNext()) {
                String jsonMapKey = (String) keysItrSubElement.next();
                String jsonMapValue = (String) jsonSubElement.get(jsonMapKey);
                jsonMap.put(jsonMapKey, jsonMapValue);
            }

            arrMap.put(arrMapKey, jsonMap);
        }
        return arrMap;
    }

    /**
     * Agrega un objeto json en una posicion
     *
     * @param index  posicion
     * @param object objeto json a agregar
     */
    public void addJsonArray(int index, Object object) {
        jsonObjectMainArray.add(index, object);
    }

    /**
     * actualiza un objeto json en una posicion
     *
     * @param index posicion
     * @param key   llave
     * @param value valor
     */
    public void setObjectIntoJsonArray(int index, String key, Object value) {
        ((JSONObject) jsonObjectMainArray.get(index)).put(key, value);
    }

    /**
     * Crea un archivo json
     *
     * @param pathJson directorio del archivo json
     */
    public void createJsonFile(String pathJson, boolean isJsonArray) {
        FileWriter file = null;
        try {
            file = new FileWriter(pathJson);
            if (isJsonArray) {
                file.write(jsonObjectMainArray.toJSONString());
            } else {
                file.write(jsonObjectMain.toJSONString());
            }
            file.close();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Obtiene un Json array de acuerdo al key.
     *
     * @param key - Nombre del key.
     * @return El Json array del key enviado.
     */
    public JSONArray getJsonArrayByKey(String key) {
        return (JSONArray) jsonObjectMain.get(key);
    }

    public JSONObject getJsonObjectMain() {
        return jsonObjectMain;
    }

    public void setJsonObjectMain(JSONObject jsonObjectMain) {
        this.jsonObjectMain = jsonObjectMain;
    }

    public JSONArray getJsonObjectMainArray() {
        return jsonObjectMainArray;
    }

    public void setJsonObjectMainArray(JSONArray jsonObjectMainArray) {
        this.jsonObjectMainArray = jsonObjectMainArray;
    }
}
