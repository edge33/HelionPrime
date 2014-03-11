package it.mat.unical.Helion_Prime.LevelEditor;

import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoader extends URLClassLoader {
	
	   public ClassLoader(final URL[] urls, final ClassLoader parent)
	    {
	        super(urls, parent);
	    }

	    @Override
	    public void addURL(final URL url)
	    {
	        super.addURL(url);

	    }

}
