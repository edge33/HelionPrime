package it.mat.unical.Helion_Prime.Multiplayer;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class LimitedTextField extends PlainDocument 
{
	private int limit;
	LimitedTextField(int limit) 
	{
		super();
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
	{
		if (str == null)
			return;

		if ((getLength() + str.length()) <limit) {
			super.insertString(offset, str, attr);
		}
	}
}
