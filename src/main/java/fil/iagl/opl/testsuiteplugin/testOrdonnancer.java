package fil.iagl.opl.testsuiteplugin;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

/**
 * A goal to generate code.
 */
@Mojo(name = "ordonnancer")
public class testOrdonnancer extends AbstractMojo {

    /**
     * My file :)
     */
    @Parameter
    private File pittestReport;

    public void execute() throws MojoExecutionException {
        getLog().info("===== PLUGIN ORDONNANCER =====");

        XMLParser parser = new XMLParser(pittestReport);
//        parser.initTestList();
        parser.initDoc();


        HashMap<String, Integer> map = (HashMap<String, Integer>) parser.getTests();

//        for (Map.Entry<String, Integer> entry : map.entrySet()) {
//            getLog().info("-------  Test : " + entry.getKey() + " SCORE : " + entry.getValue() + " ---------");
//        }

        try {
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:///C:/Users/willl/IdeaProjects/commons-math/target/classes")});
            System.out.println(urlClassLoader.getURLs()[0]  + "=================");
            Class c = urlClassLoader.loadClass("org.apache.commons.math4.util.FastMath");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
