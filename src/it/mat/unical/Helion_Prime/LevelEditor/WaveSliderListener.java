package it.mat.unical.Helion_Prime.LevelEditor;

import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WaveSliderListener implements ChangeListener 
{
	private JTextField text;
    public WaveSliderListener(JTextField text) 
    {
    	super();
    	this.text = text;
	}
    public void stateChanged(ChangeEvent e) 
    {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting())
        {
            int values = (int)source.getValue();
            text.setText(((Integer)values).toString());
        }    
    }
}