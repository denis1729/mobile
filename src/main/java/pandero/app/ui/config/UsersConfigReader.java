package pandero.app.ui.config;

import org.slf4j.Logger;
import pandero.app.ui.entities.User;
import pandero.app.utils.JsonReader;
import pandero.app.utils.LoggerSingleton;
import pandero.app.utils.PathReader;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase UsersConfigReader crea clases de tipo User a partir de un archivo de configuración json.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class UsersConfigReader {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    private static final String USER_PASSWORD = "user password";
    private static final String FIRST_NAME = "first name";
    private static final String LAST_NAME = "last name";
    private static final String USER_NAME = "user name";
    private static final String TOKEN = "token";

    private HashMap<String, User> users;

    private static UsersConfigReader instance;

    private UsersConfigReader() {
        initialize();
    }

    /**
     * Retorna la instancia de UsersConfigReader.
     *
     * @return la instancia de UsersConfigReader.
     */
    public static UsersConfigReader getInstance() {
        if (instance == null) {
            instance = new UsersConfigReader();
        }
        return instance;
    }

    /**
     * Lee los valores del archivo de configuracion json.
     */
    private void initialize() {
        String usersConfigFileName = PathReader.getPathConfig() + "UserConfig.json";
        log.info("Lee archivo de configuracion de {}", usersConfigFileName);

        Map<String, Map<String, String>> dataUsers = new JsonReader(usersConfigFileName, false).getJsonObjectMain();
        users = new HashMap<>();
        dataUsers.forEach((alias, data) -> {
            User user = new User();
            user.setPassword(data.get(USER_PASSWORD));
            user.setFirstName(data.get(FIRST_NAME));
            user.setLastName(data.get(LAST_NAME));
            user.setUserName(data.get(USER_NAME));
            user.setToken(data.get(TOKEN));
            user.setAlias(alias);
            users.put(alias, user);
        });
    }

    /**
     * Devuelve un usuario por alias.
     *
     * @param userAlias - El alias del usuario.
     * @return un usuario.
     */
    public User getUserByAlias(String userAlias) {
        log.info("Usuario {} con alias de {}", users.get(userAlias).getUserName(), userAlias);
        return users.get(userAlias);
    }

    /**
     * Retorna todos los usuarios
     *
     * @return usuarios
     */
    public Map<String, User> getUsers() {
        return users;
    }
}
