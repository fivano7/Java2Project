/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hr.algebra.utilities;

import com.sun.jndi.fscontext.RefFSContextFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 *
 * @author filip
 */
public class JndiUtils {

    public static Properties readConfigFile(String fileLocation) {

        Hashtable conf = new Hashtable();
        Properties appProps = new Properties();

        conf.put(Context.INITIAL_CONTEXT_FACTORY,
                RefFSContextFactory.class.getName());

        conf.put(Context.PROVIDER_URL, "file:" + fileLocation);

        try {
            Context context = new InitialContext(conf);

            NamingEnumeration enumeration = context.listBindings("");

            List<String> fileNames = new ArrayList<>();

            while (enumeration.hasMore()) {
                Binding fileItem = (Binding) enumeration.next();

                fileNames.add(fileItem.getName());
            }

            String configurationFileName = fileNames
                    .stream()
                    .filter(fileName
                            -> new File(fileLocation + fileName)
                            .isDirectory() == false)
                    .filter(fileName -> fileName.endsWith(".properties"))
                    .findFirst()
                    .get();

            appProps.load(new FileInputStream(fileLocation
                    + configurationFileName));

        } catch (NamingException | IOException ex) {
            Logger.getLogger(JndiUtils.class.getName()).log(Level.SEVERE, null,
                    ex);

        }

        return appProps;
    }

}
